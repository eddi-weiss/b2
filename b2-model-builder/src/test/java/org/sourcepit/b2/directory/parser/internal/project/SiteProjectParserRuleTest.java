/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.project;

import java.io.File;

import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractModuleParserTest;
import org.sourcepit.b2.model.module.SiteProject;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;

public class SiteProjectParserRuleTest extends AbstractModuleParserTest
{
   public void testBasic() throws Exception
   {
      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      assertNotNull(parserRule);
   }

   public void testNull() throws Exception
   {
      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      assertNull(parserRule.parse(null, null));
   }

   public void testNonPluginDir() throws Exception
   {
      final File moduleDir = workspace.importResources("composed-component/simple-layout");
      assertTrue(moduleDir.exists());

      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      assertNull(parserRule.parse(moduleDir, B2ModelBuildingRequest.newDefaultProperties()));
   }

   public void testParseSiteDirectory() throws Exception
   {
      final File siteDir = workspace.importResources("composed-component/simple-layout/example.site", "example.site");
      assertTrue(siteDir.exists());


      final SiteProjectParserRule parserRule = lookupSiteProjectParserRule();
      SiteProject project = parserRule.detect(siteDir, B2ModelBuildingRequest.newDefaultProperties());
      assertNotNull(project);
      assertNull(project.getId());
      
      final PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.moduleVersion", "1");
      
      parserRule.initializeeee(project, properties);
      assertEquals(siteDir, project.getDirectory());
      assertEquals("example.site", project.getId());
      assertEquals("1", project.getVersion());
   }

   private SiteProjectParserRule lookupSiteProjectParserRule() throws Exception
   {
      return (SiteProjectParserRule) lookup(AbstractProjectParserRule.class, "site");
   }
}
