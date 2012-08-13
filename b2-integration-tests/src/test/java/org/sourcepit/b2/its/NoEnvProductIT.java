/*
 * Copyright (C) 2012 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.its;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EList;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.sourcepit.b2.model.interpolation.layout.IInterpolationLayout;
import org.sourcepit.b2.model.interpolation.layout.SimpleInterpolationLayout;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.b2.model.module.ProductDefinition;
import org.sourcepit.b2.model.module.ProductsFacet;

public class NoEnvProductIT extends AbstractB2IT
{
   @Override
   protected boolean isDebug()
   {
      return false;
   }

   @Test
   public void test() throws Exception
   {
      final File deployTargetDir = new File(environment.getBuildDir(),
         "remote-repository/snapshots/org/sourcepit/b2/its/NoEnvProductIT/1.0.0-SNAPSHOT");
      if (deployTargetDir.exists())
      {
         FileUtils.forceDelete(deployTargetDir);
      }
      assertFalse(deployTargetDir.exists());

      final File moduleDir = getResource(getClass().getSimpleName());
      int err = build(moduleDir, "-e", "-B", "deploy", "-P buildProducts");
      assertThat(err, is(0));

      AbstractModule module = loadModule(moduleDir);

      EList<ProductsFacet> productsFacets = module.getFacets(ProductsFacet.class);
      assertThat(productsFacets.size(), Is.is(1));

      ProductsFacet productsFacet = productsFacets.get(0);
      EList<ProductDefinition> productDefinitions = productsFacet.getProductDefinitions();
      assertThat(productDefinitions.size(), Is.is(1));

      ProductDefinition productDefinition = productDefinitions.get(0);
      final String uid = productDefinition.getAnnotationEntry("product", "uid");
      assertNotNull(uid);

      final IInterpolationLayout layout = new SimpleInterpolationLayout();
      final File projectDir = new File(layout.pathOfFacetMetaData(module, "products", uid));
      assertTrue(projectDir.exists());

      final File[] productZips = new File(projectDir, "target/products").listFiles(new FileFilter()
      {
         public boolean accept(File pathname)
         {
            return pathname.isFile() && pathname.getName().endsWith(".zip");
         }
      });
      assertThat(productZips.length, is(1));

      final File productZip = productZips[0];
      assertTrue(productZip.exists());
      String productZipName = productZip.getName();
      assertTrue(productZipName.endsWith(".zip"));

      final String classifier = productZipName.substring(productZipName.lastIndexOf('-') + 1,
         productZipName.length() - 4);
      
      final File[] deployedProducts = deployTargetDir.listFiles(new FileFilter()
      {
         public boolean accept(File pathname)
         {
            return pathname.getName().endsWith(classifier + ".zip");
         }
      });

      assertThat(deployedProducts.length, is(1));
   }
}
