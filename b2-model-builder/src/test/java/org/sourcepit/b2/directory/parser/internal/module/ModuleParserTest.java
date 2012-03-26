/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.common.internal.utils.NlsUtils;
import org.sourcepit.b2.directory.parser.internal.facets.SimpleLayoutFacetsParserRuleTest;
import org.sourcepit.b2.directory.parser.internal.facets.StructuredLayoutFacetsParserRuleTest;
import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.directory.parser.module.ModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.WhitelistModuleFilter;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.BasicModule;
import org.sourcepit.b2.model.module.CompositeModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.b2.model.session.B2Session;
import org.sourcepit.common.utils.lang.PipedException;

public class ModuleParserTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      IModuleParser parser = lookup();
      assertNotNull(parser);
   }

   public void testNull() throws Exception
   {
      IModuleParser parser = lookup();
      try
      {
         parser.parse(null);
         fail();
      }
      catch (PipedException e)
      {
         assertTrue(e.getCause() instanceof IllegalArgumentException);
      }

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      try
      {
         parser.parse(request);
         fail();
      }
      catch (PipedException e)
      {
         assertTrue(e.getCause() instanceof IllegalArgumentException);
      }

      request.setModuleDirectory(new File(""));
      request.setConverter(null);

      try
      {
         parser.parse(request);
         fail();
      }
      catch (PipedException e)
      {
         assertTrue(e.getCause() instanceof IllegalArgumentException);
      }
   }

   public void testSimpleComponent_WithoutNlsProperties() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.canRead());

      FileUtils.forceDelete(new File(moduleDir, "module.properties"));
      FileUtils.forceDelete(new File(moduleDir, "module_de.properties"));

      initSession(moduleDir);

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(moduleDir);

      ModuleParser modelParser = lookup();
      BasicModule module = (BasicModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());
      assertEquals(module.getVersion(), request.getConverter().getModuleVersion());

      EList<Locale> locales = module.getLocales();
      assertEquals(1, locales.size());
      assertEquals(NlsUtils.DEFAULT_LOCALE, locales.get(0));
   }

   public void testSimpleComponent() throws Exception
   {
      File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.canRead());

      initSession(moduleDir);

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleDirectory(moduleDir);

      ModuleParser modelParser = lookup();
      BasicModule module = (BasicModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.simple.layout", module.getId());
      assertEquals(module.getVersion(), request.getConverter().getModuleVersion());

      EList<Locale> locales = module.getLocales();
      assertFalse(locales.isEmpty());
      assertEquals(new Locale(""), locales.get(0)); // default locale
      assertEquals(new Locale("de"), locales.get(1));

      assertEquals(4, module.getFacets().size());

      PluginsFacet pluginsFacet = module.getFacetByName("plugins");
      assertNotNull(pluginsFacet);

      EList<PluginProject> plugins = pluginsFacet.getProjects();
      assertEquals(2, plugins.size());

      PluginsFacet testsFacet = module.getFacetByName("tests");
      assertNotNull(testsFacet);

      EList<PluginProject> tests = pluginsFacet.getProjects();
      assertEquals(2, tests.size());
   }

   public void testComposedComposite() throws Exception
   {
      final File moduleDir = workspace.importResources("composed-component");

      final File simpleDir = new File(moduleDir, "simple-layout");
      final File structuredDir = new File(moduleDir, "structured-layout");

      final B2Session session = initSession(simpleDir, structuredDir, moduleDir);

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);

      ModuleParser modelParser = lookup();

      request.setModuleDirectory(simpleDir);
      session.getCurrentProject().setModuleModel(modelParser.parse(request));

      session.setCurrentProject(session.getProjects().get(1));

      request.setModuleDirectory(structuredDir);
      session.getCurrentProject().setModuleModel(modelParser.parse(request));

      session.setCurrentProject(session.getProjects().get(2));

      request.setModuleDirectory(moduleDir);
      CompositeModule module = (CompositeModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.composite.layout", module.getId());
      assertEquals(module.getVersion(), request.getConverter().getModuleVersion());

      assertEquals(2, module.getModules().size());

      SimpleLayoutFacetsParserRuleTest
         .assertSimpleLayout((BasicModule) findModuleByDir(module.getModules(), simpleDir));
      StructuredLayoutFacetsParserRuleTest.assertStructuredLayout((BasicModule) findModuleByDir(module.getModules(),
         structuredDir));
   }

   public void testComposedCompositeWithExclude() throws Exception
   {
      final File moduleDir = workspace.importResources("composed-component");

      final File simpleDir = new File(moduleDir, "simple-layout");
      final File structuredDir = new File(moduleDir, "structured-layout");

      final B2Session session = initSession(simpleDir, structuredDir, moduleDir);

      ModuleParsingRequest request = new ModuleParsingRequest();
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setModuleFilter(new WhitelistModuleFilter(simpleDir));

      ModuleParser modelParser = lookup();
      
      request.setModuleDirectory(simpleDir);
      session.getCurrentProject().setModuleModel(modelParser.parse(request));

      session.setCurrentProject(session.getProjects().get(1));

      request.setModuleDirectory(structuredDir);
      session.getCurrentProject().setModuleModel(modelParser.parse(request));

      session.setCurrentProject(session.getProjects().get(2));

      request.setModuleDirectory(moduleDir);
      CompositeModule module = (CompositeModule) modelParser.parse(request);
      assertNotNull(module);

      assertNotNull(module.getId());
      assertNotNull(module.getVersion());
      assertEquals("org.sourcepit.b2.test.resources.composite.layout", module.getId());
      assertEquals(module.getVersion(), request.getConverter().getModuleVersion());

      assertEquals(1, module.getModules().size());

      SimpleLayoutFacetsParserRuleTest
         .assertSimpleLayout((BasicModule) findModuleByDir(module.getModules(), simpleDir));

      assertNull(findModuleByDir(module.getModules(), structuredDir));
   }

   private AbstractModule findModuleByDir(Collection<AbstractModule> modules, File moduleDir)
   {
      for (AbstractModule module : modules)
      {
         if (moduleDir.equals(module.getDirectory()))
         {
            return module;
         }
      }
      return null;
   }

   private ModuleParser lookup() throws Exception
   {
      return lookup(ModuleParser.class);
   }
}
