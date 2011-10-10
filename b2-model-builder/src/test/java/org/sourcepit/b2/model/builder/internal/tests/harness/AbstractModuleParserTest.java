/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.util.Collection;

import org.sourcepit.b2.model.module.AbstractFacet;
import org.sourcepit.b2.test.resources.internal.harness.AbstractInjectedWorkspaceTest;


public abstract class AbstractModuleParserTest extends AbstractInjectedWorkspaceTest
{
   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected static <F extends AbstractFacet> F findFacetByName(Collection<F> facets, String name)
   {
      for (F f : facets)
      {
         if (name.equals(f.getName()))
         {
            return f;
         }
      }
      return null;
   }
}