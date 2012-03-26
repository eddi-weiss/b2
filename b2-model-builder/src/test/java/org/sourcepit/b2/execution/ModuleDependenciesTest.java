/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.execution;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.sourcepit.b2.model.builder.B2ModelBuildingRequest;
import org.sourcepit.b2.model.builder.IB2ModelBuilder;
import org.sourcepit.b2.model.builder.internal.tests.harness.AbstractB2SessionWorkspaceTest;
import org.sourcepit.b2.model.builder.internal.tests.harness.ConverterUtils;
import org.sourcepit.b2.model.session.ModuleDependency;
import org.sourcepit.b2.model.session.ModuleProject;

/**
 * @author Bernd
 */
public class ModuleDependenciesTest extends AbstractB2SessionWorkspaceTest
{
   @Inject
   private IB2ModelBuilder modelBuilder;

   @Override
   protected String setUpModulePath()
   {
      return "reactor-build";
   }

   public void testModuleDependencies() throws Exception
   {
      ModuleProject rcpProject = getModuleProjectByArtifactId("rcp");

      EList<ModuleDependency> dependencies = rcpProject.getDependencies();
      assertEquals(1, dependencies.size());

      // assertEquals("rcp.help", dependencies.get(0).getArtifactId());
      assertEquals("rcp.ui", dependencies.get(0).getArtifactId());
   }

   public void testDependencyFeatures() throws Exception
   {
      ModuleProject rcpProject = getModuleProjectByArtifactId("rcp");
      getCurrentSession().setCurrentProject(rcpProject);

      B2ModelBuildingRequest request = new B2ModelBuildingRequest();
      request.setModuleDirectory(rcpProject.getDirectory());
      request.setConverter(ConverterUtils.TEST_CONVERTER);
      request.setInterpolate(true);
      modelBuilder.build(request);
   }
}
