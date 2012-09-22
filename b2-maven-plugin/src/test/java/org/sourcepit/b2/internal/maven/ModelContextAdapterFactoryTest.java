/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.internal.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sourcepit.b2.model.builder.harness.ModelBuilderHarness.addFeatureProject;
import static org.sourcepit.b2.model.builder.harness.ModelBuilderHarness.createBasicModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.sourcepit.b2.model.builder.harness.ModelBuilderHarness;
import org.sourcepit.b2.model.interpolation.internal.module.B2MetadataUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.common.maven.model.MavenArtifact;
import org.sourcepit.common.maven.model.MavenModelFactory;
import org.sourcepit.common.testing.Environment;
import org.sourcepit.common.testing.Workspace;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

public class ModelContextAdapterFactoryTest
{
   private final Environment env = Environment.get("env-test.properties");

   @Rule
   public Workspace ws = newWorkspace();

   protected Workspace newWorkspace()
   {
      return new Workspace(new File(env.getBuildDir(), "ws"), false);
   }

   public Environment getEnvironment()
   {
      return env;
   }

   protected File getResource(String path) throws IOException
   {
      File resources = getResourcesDir();
      File resource = new File(resources, path).getCanonicalFile();
      return ws.importFileOrDir(resource);
   }

   protected File getResourcesDir()
   {
      return getEnvironment().getResourcesDir();
   }

   @Test
   public void testAdoptionReactorProjectURIMappings() throws IOException
   {

      final ModuleArtifactResolver artifactResolver = new ModuleArtifactResolver()
      {
         public SetMultimap<MavenArtifact, String> resolve(MavenSession session, MavenProject project, String scope)
         {
            return LinkedHashMultimap.create();
         }
      };

      List<MavenProject> projects = new ArrayList<MavenProject>();

      Model pom = new Model();
      pom.setGroupId("project1.groupId");
      pom.setArtifactId("project1.artifactId");
      pom.setVersion("project1.version");

      MavenProject project = new MavenProject(pom);
      project.setFile(new File("project1/pom.xml"));
      projects.add(project);

      pom = new Model();
      pom.setGroupId("project2.groupId");
      pom.setArtifactId("project2.artifactId");
      pom.setVersion("project2.version");

      project = new MavenProject(pom);
      project.setFile(new File("project2/pom.xml"));
      projects.add(project);

      final MavenSession session = mock(MavenSession.class);
      when(session.getProjects()).thenReturn(projects);

      ModelContextAdapterFactory adapterFactory = new ModelContextAdapterFactory(artifactResolver);

      // fake an uri mapping
      final MavenProject project1 = projects.get(0);
      final URI gavUri = URI.createURI("gav:/project1/project1/module/1.0");
      final URI fileURI = URI.createFileURI(project1.getFile().getAbsolutePath());
      adapterFactory.adapt(session, project1).getResourceSet().getURIConverter().getURIMap().put(gavUri, fileURI);

      final MavenProject project2 = projects.get(1);
      final ModelContext modelContext = adapterFactory.adapt(session, project2);
      final URI mappedUri = modelContext.getResourceSet().getURIConverter().getURIMap().get(gavUri);
      assertEquals(fileURI, mappedUri);
   }

   @Test
   public void testAssemblyClassifierToFeatureMapping() throws IOException
   {
      MavenModelFactory mvnFactory = MavenModelFactory.eINSTANCE;

      final MavenArtifact artifact = mvnFactory.createMavenArtifact();
      artifact.setGroupId("groupId");
      artifact.setArtifactId("artifactId");
      artifact.setVersion("version");
      artifact.setType("module");
      artifact.setFile(new File(ws.getRoot(), "b2.module"));

      BasicModule module = createBasicModule("groupId.artifactId");
      FeatureProject featureProject = addFeatureProject(module, "features", module.getId()
         + ".feature", module.getVersion());
      B2MetadataUtils.addAssemblyName(featureProject, "public");
      B2MetadataUtils.addAssemblyClassifier(featureProject, "");

      featureProject = addFeatureProject(module, "features", module.getId() + ".test.feature",
         module.getVersion());
      B2MetadataUtils.setTestFeature(featureProject, true);
      B2MetadataUtils.addAssemblyName(featureProject, "test");
      B2MetadataUtils.addAssemblyClassifier(featureProject, "test");

      Resource resource = new XMLResourceImpl(URI.createFileURI(artifact.getFile().getAbsolutePath()));
      resource.getContents().add(module);
      resource.save(null);

      final ModuleArtifactResolver artifactResolver = new ModuleArtifactResolver()
      {
         public SetMultimap<MavenArtifact, String> resolve(MavenSession session, MavenProject project, String scope)
         {
            final SetMultimap<MavenArtifact, String> moduleArtifactsToAssemblyClassifiers = LinkedHashMultimap.create();
            if (Artifact.SCOPE_TEST.equals(scope))
            {
               moduleArtifactsToAssemblyClassifiers.get(artifact).add("test");
            }
            else
            {
               moduleArtifactsToAssemblyClassifiers.get(artifact).add("");
            }
            return moduleArtifactsToAssemblyClassifiers;
         }
      };

      ModelContextAdapterFactory adapterFactory = new ModelContextAdapterFactory(artifactResolver);

      final MavenSession session = mock(MavenSession.class);

      Model pom = new Model();
      pom.setGroupId("project.groupId");
      pom.setArtifactId("project.artifactId");
      pom.setVersion("project.version");

      final MavenProject project = new MavenProject(pom);
      project.setFile(new File("project/pom.xml"));

      ModelContext modelContext = adapterFactory.adapt(session, project);
      assertNotNull(modelContext);
      assertNotNull(modelContext.getResourceSet());
      URI uri = URI.createURI("gav:/project.groupId/project.artifactId/module/project.version#/");
      assertEquals(uri, modelContext.getModuleUri());
      assertNotNull(modelContext.getResourceSet().getURIConverter().getURIMap().get(uri.trimFragment()));

      SetMultimap<AbstractModule, FeatureProject> scope = modelContext.getMainScope();
      assertEquals(1, scope.size());

      AbstractModule actualModule = scope.keys().iterator().next();
      assertEquals(module.getId(), actualModule.getId());

      Set<FeatureProject> assemblyNames = scope.get(actualModule);
      assertEquals("groupId.artifactId.feature", assemblyNames.iterator().next().getId());

      scope = modelContext.getTestScope();
      assertEquals(1, scope.size());

      actualModule = scope.keys().iterator().next();
      assertEquals(module.getId(), actualModule.getId());

      assemblyNames = scope.get(actualModule);
      assertEquals("groupId.artifactId.test.feature", assemblyNames.iterator().next().getId());

   }
}
