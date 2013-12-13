/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.PluginProject;
import org.sourcepit.b2.model.module.PluginsFacet;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class ModuleIconIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "clean", "-Dtycho.mode=maven");
      assertThat(err, is(0));

      AbstractModule module = loadModule(moduleDir);

      PluginsFacet brandings = module.getFacetByName("features-branding-plugins");

      PluginProject assemblyBranding = brandings.getProjectById("org.sourcepit.b2.its.ModuleIconIT.branding");
      assertNotNull(assemblyBranding);
      assertModuleIconConfigured(assemblyBranding.getDirectory());

      PluginProject pluginsBranding = brandings.getProjectById("org.sourcepit.b2.its.ModuleIconIT.plugins.branding");
      assertNotNull(pluginsBranding);
      assertModuleIconConfigured(pluginsBranding.getDirectory());

      PluginProject testsBranding = brandings.getProjectById("org.sourcepit.b2.its.ModuleIconIT.tests.branding");
      assertNotNull(testsBranding);
      assertModuleIconConfigured(testsBranding.getDirectory());
   }

   private static void assertModuleIconConfigured(File pluginDir)
   {
      assertTrue(new File(pluginDir, "module32.png").exists());
      
      final PropertiesMap propertiesMap = new LinkedPropertiesMap();
      propertiesMap.load(new File(pluginDir, "about.ini"));
      assertEquals("module32.png", propertiesMap.get("featureImage"));
   }
}
