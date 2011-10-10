/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.module;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils;
import org.sourcepit.b2.model.module.test.internal.harness.EcoreUtils.RunnableWithEObject;

public class IdentifiableTest extends TestCase
{
   public void testToIdentifier() throws Exception
   {
      EcoreUtils.foreachSupertype(ModuleModelPackage.eINSTANCE.getIdentifiable(), new RunnableWithEObject()
      {
         public void run(EObject eObject)
         {
            Identifiable identifiable = (Identifiable) eObject;
            try
            {
               identifiable.toIdentifier();
               fail();
            }
            catch (IllegalArgumentException e)
            {
            }

            identifiable.setId("foo");
            assertNotNull(identifiable);

            assertTrue(identifiable.isIdentifyableBy(identifiable.toIdentifier()));
         }
      });
   }
}