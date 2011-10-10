/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.execution;

import java.io.File;

import javax.inject.Inject;

import org.sourcepit.b2.common.internal.utils.LinkedPropertiesMap;
import org.sourcepit.b2.common.internal.utils.PropertiesMap;
import org.sourcepit.b2.execution.B2;
import org.sourcepit.b2.internal.generator.DefaultTemplateCopier;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.test.internal.harness.B2ModelHarness;

public class B2Test extends AbstractB2SessionWorkspaceTest
{
   @Inject
   private B2 b2;

   @Override
   protected String setUpModulePath()
   {
      return "composed-component/simple-layout";
   }

   public void testSkipInterpolator() throws Exception
   {
      File moduleDir = getCurrentModuleDir();
      assertTrue(moduleDir.canRead());

      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put("b2.skipInterpolator", "true");
      properties.put("b2.skipGenerator", "true");

      IConverter converter = ConverterUtils.newDefaultTestConverter(properties);
      assertTrue(converter.isSkipInterpolator());
      assertTrue(converter.isSkipGenerator());

      AbstractModule module = b2.generate(moduleDir, null, null, converter, new DefaultTemplateCopier());

      assertNotNull(module);
      B2ModelHarness.assertHasDerivedElements(module);
   }

}