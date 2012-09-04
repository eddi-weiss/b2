/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.interpolation.internal.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.builder.util.FeaturesConverter;
import org.sourcepit.b2.model.builder.util.ISourceService;
import org.sourcepit.b2.model.builder.util.UnpackStrategy;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.LayoutManager;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.FeatureProject;
import org.sourcepit.b2.model.module.FeaturesFacet;
import org.sourcepit.b2.model.module.ModuleModelFactory;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.module.internal.util.EWalkerImpl;
import org.sourcepit.common.utils.path.PathMatcher;
import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class FeaturesInterpolator
{
   private final ISourceService sourceService;

   private final LayoutManager layoutManager;

   private final UnpackStrategy unpackStrategy;

   private final FeaturesConverter converter;

   private final IncludesAndRequirementsResolver resolutionContextResolver;

   @Inject
   public FeaturesInterpolator(@NotNull ISourceService sourceService, @NotNull LayoutManager layoutManager,
      @NotNull UnpackStrategy unpackStrategy, FeaturesConverter converter,
      IncludesAndRequirementsResolver resolutionContextResolver)
   {
      this.sourceService = sourceService;
      this.layoutManager = layoutManager;
      this.unpackStrategy = unpackStrategy;
      this.converter = converter;
      this.resolutionContextResolver = resolutionContextResolver;
   }

   public void interpolate(AbstractModule module, PropertiesSource moduleProperties)
   {
      final FeaturesFacet featuresFacet = createFeaturesFacet("features");

      final EList<PluginsFacet> pluginsFacets = module.getFacets(PluginsFacet.class);
      for (PluginsFacet pluginsFacet : pluginsFacets)
      {
         interpolatePluginFeatures(module, featuresFacet, pluginsFacet, moduleProperties);
      }

      final int size = featuresFacet.getProjects().size();
      if (size > 0)
      {
         module.getFacets().add(featuresFacet);

         for (PluginsFacet pluginsFacet : pluginsFacets)
         {
            final FeatureProject mainFeature = AbstractIncludesAndRequirementsResolver
               .findFeatureProjectForPluginsFacet(pluginsFacet, false);
            addIncludesAndRequirements(pluginsFacet, mainFeature, moduleProperties, false);

            final FeatureProject sourceFeature = AbstractIncludesAndRequirementsResolver
               .findFeatureProjectForPluginsFacet(pluginsFacet, true);
            if (sourceFeature != null)
            {
               addIncludesAndRequirements(pluginsFacet, sourceFeature, moduleProperties, true);
            }
         }
      }

      interpolateAssemblyFeatures(module, featuresFacet, moduleProperties);

      if (size == 0 && !featuresFacet.getProjects().isEmpty())
      {
         module.getFacets().add(featuresFacet);
      }
   }

   private void interpolateAssemblyFeatures(AbstractModule module, FeaturesFacet featuresFacet,
      PropertiesSource moduleProperties)
   {
      final List<String> assemplyNames = converter.getAssemblyNames(moduleProperties);
      if (!assemplyNames.isEmpty())
      {
         final List<FeatureProject> assemblyFeatures = new ArrayList<FeatureProject>();

         for (String assemblyName : assemplyNames)
         {
            final String featureId = deriveFeatureId(module, assemblyName, moduleProperties);
            final String facetName = featuresFacet.getName();

            final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
            final String path = layout.pathOfFacetMetaData(module, facetName, featureId);

            final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
            featureProject.setDerived(true);
            featureProject.setId(featureId);
            featureProject.setVersion(module.getVersion());
            featureProject.setDirectory(new File(path));

            B2MetadataUtils.setModuleId(featureProject, module.getId());
            B2MetadataUtils.setModuleVersion(featureProject, module.getVersion());
            B2MetadataUtils.addAssemblyName(featureProject, assemblyName);

            final EWalkerImpl eWalker;
            eWalker = createIncludesAppenderForAssembly(moduleProperties, featureProject, assemblyName);
            eWalker.walk(module.getFacets(FeaturesFacet.class));
            eWalker.walk(module.getFacets(PluginsFacet.class));

            resolutionContextResolver.appendIncludesAndRequirements(moduleProperties, module, featureProject,
               assemblyName);

            // skip assembly feature if there is onle one single include
            final FeatureProject singleIncludedFeature = returnSingleIncludedFeatureProject(module, featureProject);
            if (singleIncludedFeature == null)
            {
               if (hasIncludes(featureProject))
               {
                  assemblyFeatures.add(featureProject);
               }
            }
            else
            {
               B2MetadataUtils.addAssemblyName(singleIncludedFeature, assemblyName);
            }
         }
         // hide assembly features until all assembly names are processed
         featuresFacet.getProjects().addAll(assemblyFeatures);
      }
   }

   private static boolean hasIncludes(FeatureProject featureProject)
   {
      return !featureProject.getIncludedFeatures().isEmpty() || !featureProject.getIncludedPlugins().isEmpty();
   }

   private FeatureProject returnSingleIncludedFeatureProject(AbstractModule module, final FeatureProject featureProject)
   {
      if (isIncludesJustOneFeature(featureProject))
      {
         return module.resolveReference(featureProject.getIncludedFeatures().get(0), FeaturesFacet.class);
      }
      return null;
   }

   private boolean isIncludesJustOneFeature(final FeatureProject featureProject)
   {
      return featureProject.getIncludedFeatures().size() == 1 && featureProject.getIncludedPlugins().isEmpty()
         && featureProject.getRequiredFeatures().isEmpty() && featureProject.getRequiredPlugins().isEmpty();
   }


   private void interpolatePluginFeatures(AbstractModule module, FeaturesFacet featuresFacet,
      PluginsFacet pluginsFacet, PropertiesSource moduleProperties)
   {
      FeatureProject featureProject = createFeatureProject(module, featuresFacet, pluginsFacet, moduleProperties, false);
      featuresFacet.getProjects().add(featureProject);

      if (sourceService.isSourceBuildEnabled(moduleProperties))
      {
         featureProject = createFeatureProject(module, featuresFacet, pluginsFacet, moduleProperties, true);
         featuresFacet.getProjects().add(featureProject);
      }
   }

   private FeatureProject createFeatureProject(AbstractModule module, FeaturesFacet featuresFacet,
      PluginsFacet pluginsFacet, PropertiesSource moduleProperties, boolean isSource)
   {
      final String featureId = deriveFeatureId(module, pluginsFacet, moduleProperties, isSource);

      final FeatureProject featureProject = ModuleModelFactory.eINSTANCE.createFeatureProject();
      featureProject.setDerived(true);
      featureProject.setId(featureId);
      featureProject.setVersion(module.getVersion());

      final String facetName = pluginsFacet.getName();
      B2MetadataUtils.setModuleId(featureProject, module.getId());
      B2MetadataUtils.setModuleVersion(featureProject, module.getVersion());
      B2MetadataUtils.setFacetName(featureProject, facetName);
      B2MetadataUtils.setSourceFeature(featureProject, isSource);

      final IInterpolationLayout layout = layoutManager.getLayout(module.getLayoutId());
      featureProject.setDirectory(new File(layout.pathOfFacetMetaData(module, featuresFacet.getName(),
         featureProject.getId())));

      return featureProject;
   }

   // TODO check duplicated includes
   private void addIncludesAndRequirements(PluginsFacet pluginsFacet, FeatureProject featureProject,
      PropertiesSource moduleProperties, boolean isSource)
   {
      final String facetName = pluginsFacet.getName();

      // includes from module structure (plugin includes and assembly includes)
      final IncludesAppender includesAppender;
      includesAppender = createIncludesAppenderForFacet(moduleProperties, isSource, featureProject, facetName);
      includesAppender.walk(pluginsFacet.getProjects());

      // inter facets requirements
      resolutionContextResolver.appendIncludesAndRequirements(moduleProperties, pluginsFacet.getParent(),
         featureProject);
   }

   private String deriveFeatureId(AbstractModule module, String assemblyName, PropertiesSource properties)
   {
      final String classifier = converter.getAssemblyClassifier(properties, assemblyName);
      return converter.getFeatureId(properties, module.getId(), classifier, false);
   }

   private String deriveFeatureId(AbstractModule module, PluginsFacet pluginsFacet, PropertiesSource properties,
      boolean isSource)
   {
      final String classifier = converter.getFacetClassifier(properties, pluginsFacet.getName());
      return converter.getFeatureId(properties, module.getId(), classifier, isSource);
   }

   private FeaturesFacet createFeaturesFacet(String facetName)
   {
      final FeaturesFacet featuresFacet = ModuleModelFactory.eINSTANCE.createFeaturesFacet();
      featuresFacet.setDerived(true);
      featuresFacet.setName(facetName);
      return featuresFacet;
   }

   private IncludesAppender createIncludesAppenderForAssembly(final PropertiesSource moduleProperties,
      final FeatureProject featureProject, final String assemblyName)
   {
      return new IncludesAppender(unpackStrategy, featureProject, false)
      {
         @Override
         protected String getSourcePluginId(PluginProject pp)
         {
            throw new UnsupportedOperationException();
         }

         @Override
         protected PathMatcher createFeatureMatcher()
         {
            return converter.getFeatureMatcherForAssembly(moduleProperties, assemblyName);
         }

         @Override
         protected PathMatcher createPluginMatcher()
         {
            return converter.getPluginMatcherForAssembly(moduleProperties, assemblyName);
         }
      };
   }

   private IncludesAppender createIncludesAppenderForFacet(final PropertiesSource moduleProperties, boolean isSource,
      final FeatureProject featureProject, final String facetName)
   {
      final IncludesAppender includesAppender;
      includesAppender = new IncludesAppender(unpackStrategy, featureProject, isSource)
      {
         @Override
         protected String getSourcePluginId(PluginProject pp)
         {
            return converter.getSourcePluginId(moduleProperties, pp.getId());
         }

         @Override
         protected PathMatcher createPluginMatcher()
         {
            return converter.getPluginMatcherForFacet(moduleProperties, facetName);
         }

         @Override
         protected PathMatcher createFeatureMatcher()
         {
            return null;
         }
      };
      return includesAppender;
   }

   private static abstract class IncludesAppender extends EWalkerImpl
   {
      private final FeatureProject targetProject;
      private final PathMatcher featureMatcher;
      private final PathMatcher pluginMatcher;
      private final UnpackStrategy unpackStrategy;
      private final boolean isSource;

      private IncludesAppender(UnpackStrategy unpackStrategy, FeatureProject targetProject, boolean isSource)
      {
         this.unpackStrategy = unpackStrategy;
         this.targetProject = targetProject;
         this.featureMatcher = createFeatureMatcher();
         this.pluginMatcher = createPluginMatcher();
         this.isSource = isSource;
      }

      @Override
      protected boolean visit(EObject eObject)
      {
         if (eObject != targetProject)
         {
            if (eObject instanceof FeatureProject)
            {
               process((FeatureProject) eObject);
            }
            else if (eObject instanceof PluginProject)
            {
               process((PluginProject) eObject);
            }
         }
         return eObject instanceof FeaturesFacet || eObject instanceof PluginsFacet;
      }

      private void process(FeatureProject fp)
      {
         if (featureMatcher.isMatch(fp.getId()))
         {
            final FeatureInclude inc = AbstractIncludesAndRequirementsResolver.toFeatureInclude(fp);

            if (B2MetadataUtils.isTestFeature(fp))
            {
               B2MetadataUtils.setTestFeature(targetProject, true);
            }

            if (B2MetadataUtils.isSourceFeature(fp))
            {
               B2MetadataUtils.setSourceFeature(targetProject, true);
            }

            targetProject.getIncludedFeatures().add(inc);
         }
      }

      private void process(PluginProject pp)
      {
         final String pluginId = isSource ? getSourcePluginId(pp) : pp.getId();
         if (pluginMatcher.isMatch(pluginId))
         {
            final PluginInclude inc = AbstractIncludesAndRequirementsResolver.toPluginInclude(pp);
            inc.setId(pluginId);
            if (isSource)
            {
               inc.setUnpack(false);
            }
            else
            {
               inc.setUnpack(unpackStrategy.isUnpack(pp));
            }

            if (pp.isTestPlugin())
            {
               B2MetadataUtils.setTestFeature(targetProject, true);
            }

            if (isSource)
            {
               B2MetadataUtils.setSourceFeature(targetProject, isSource);
            }

            targetProject.getIncludedPlugins().add(inc);
         }
      }

      protected abstract String getSourcePluginId(PluginProject pp);

      protected abstract PathMatcher createPluginMatcher();

      protected abstract PathMatcher createFeatureMatcher();
   }
}
