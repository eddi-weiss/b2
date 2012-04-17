/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.internal.maven;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sourcepit.b2.execution.AbstractB2SessionLifecycleParticipant;
import org.sourcepit.b2.execution.B2Request;
import org.sourcepit.b2.execution.B2SessionLifecycleParticipant;
import org.sourcepit.b2.internal.generator.AbstractPomGenerator;
import org.sourcepit.b2.internal.generator.FixedModelMerger;
import org.sourcepit.b2.model.builder.util.DecouplingModelCache;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.b2.model.module.SitesFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.maven.bootstrap.participation.BootstrapSession;

@Named
public class MavenB2LifecycleParticipant extends AbstractB2SessionLifecycleParticipant
   implements
      B2SessionLifecycleParticipant
{
   private static final String CACHE_KEY = B2BootstrapParticipant.class.getName() + "#modelCache";

   private static final String CACHE_KEY_SESSION = B2BootstrapParticipant.class.getName() + "#session";

   @Inject
   private MavenProjectHelper projectHelper;

   @Inject
   private BootstrapSessionService bootSessionsService;

   @Inject
   private LayoutManager layoutManager;

   public void postPrepareProject(B2Session session, ModuleProject project, B2Request request, AbstractModule module,
      ThrowablePipe errors)
   {
      final LinkedHashSet<String> classifiers = new LinkedHashSet<String>();
      for (SitesFacet sitesFacet : module.getFacets(SitesFacet.class))
      {
         for (SiteProject siteProject : sitesFacet.getProjects())
         {
            String cl = siteProject.getClassifier();
            classifiers.add(cl == null || cl.length() == 0 ? null : cl);
         }
      }

      // add default model if no site classifiers are specified
      if (classifiers.isEmpty())
      {
         classifiers.add(null);
      }

      final BootstrapSession bootSession = bootSessionsService.getBootstrapSession();
      final MavenProject bootProject = bootSession.getCurrentProject();

      // HACK
      final DecouplingModelCache modelCache = (DecouplingModelCache) request.getModelCache();

      final ResourceSet resourceSet = modelCache.getResourceSet();

      for (String classifier : classifiers)
      {
         AbstractModule classifiedModule = module;
         if (classifiedModule.eResource() != null)
         {
            classifiedModule = EcoreUtil.copy(classifiedModule);
         }

         final URI moduleUri = MavenURIResolver.toArtifactIdentifier(bootProject, classifier, "module").toUri();
         resourceSet.createResource(moduleUri).getContents().add(classifiedModule);
         modelCache.put(classifiedModule);
      }

      final File pomFile = new File(module.getAnnotationEntry(AbstractPomGenerator.SOURCE_MAVEN,
         AbstractPomGenerator.KEY_POM_FILE));

      bootProject.setContextValue("pom", pomFile);

      final String layoutId = module.getLayoutId();
      final File sessionFile = new File(layoutManager.getLayout(layoutId).pathOfMetaDataFile(module, "b2.session"));
      final URI uri = URI.createFileURI(sessionFile.getAbsolutePath());

      final Resource resource = resourceSet.createResource(uri);
      resource.getContents().add(session);
      try
      {
         resource.save(null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      projectHelper.attachArtifact(bootProject, "session", null, sessionFile);

      processAttachments(bootProject, pomFile);

      bootSession.setData(CACHE_KEY_SESSION, uri.toString());

      storeModelCache(bootSession, modelCache);
   }

   private void storeModelCache(BootstrapSession session, final DecouplingModelCache modelCache)
   {
      session.setData(CACHE_KEY, modelCache.getDirToUriMap());
   }

   private void processAttachments(MavenProject wrapperProject, File pomFile)
   {
      final List<Artifact> attachedArtifacts = wrapperProject.getAttachedArtifacts();
      if (attachedArtifacts == null)
      {
         return;
      }

      Xpp3Dom artifactsNode = new Xpp3Dom("artifacts");
      for (Artifact artifact : attachedArtifacts)
      {
         Xpp3Dom artifactNode = new Xpp3Dom("artifact");

         if (artifact.getClassifier() != null)
         {
            Xpp3Dom classifierNode = new Xpp3Dom("classifier");
            classifierNode.setValue(artifact.getClassifier());
            artifactNode.addChild(classifierNode);
         }

         Xpp3Dom typeNode = new Xpp3Dom("type");
         typeNode.setValue(artifact.getType());
         artifactNode.addChild(typeNode);

         Xpp3Dom fileNode = new Xpp3Dom("file");
         fileNode.setValue(artifact.getFile().getAbsolutePath());
         artifactNode.addChild(fileNode);

         artifactsNode.addChild(artifactNode);
      }

      Xpp3Dom configNode = new Xpp3Dom("configuration");
      configNode.addChild(artifactsNode);

      PluginExecution exec = new PluginExecution();
      exec.setId("b2-attach-artifatcs");
      exec.setPhase("initialize");
      exec.getGoals().add("attach-artifact");
      exec.setConfiguration(configNode);

      Plugin plugin = new Plugin();
      plugin.setGroupId("org.codehaus.mojo");
      plugin.setArtifactId("build-helper-maven-plugin");
      plugin.setVersion("1.7");
      plugin.getExecutions().add(exec);
      plugin.setInherited(false);

      Build build = new Build();
      build.getPlugins().add(plugin);

      Model model = new Model();
      model.setBuild(build);

      final Model moduleModel;
      try
      {
         moduleModel = new DefaultModelReader().read(pomFile, null);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }

      new FixedModelMerger().merge(moduleModel, model, false, null);
      try
      {
         new DefaultModelWriter().write(pomFile, null, moduleModel);
      }
      catch (IOException e)
      {
         throw new IllegalStateException(e);
      }
   }
}