/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.internal.tests.harness;

import java.util.List;

import org.junit.Assert;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * @author Bernd
 */
public final class EcoreUtils
{
   private EcoreUtils()
   {
      super();
   }

   public static void assertEEquals(EObject expected, EObject actual)
   {
      Assert.assertEquals(expected.eClass(), actual.eClass());

      EList<EAttribute> eAttributes = expected.eClass().getEAllAttributes();
      for (EAttribute eAttribute : eAttributes)
      {
         Assert.assertEquals(expected.eGet(eAttribute), actual.eGet(eAttribute));
      }

      EList<EReference> eAllContainments = expected.eClass().getEAllContainments();
      for (EReference eReference : eAllContainments)
      {
         final Object expectedValue = expected.eGet(eReference);
         final Object actualValue = actual.eGet(eReference);
         if (eReference.isMany())
         {
            @SuppressWarnings("unchecked")
            List<EObject> expectedList = (List<EObject>) expectedValue;
            @SuppressWarnings("unchecked")
            List<EObject> actualList = (List<EObject>) actualValue;
            for (int i = 0; i < expectedList.size(); i++)
            {
               assertEEquals(expectedList.get(i), actualList.get(i));
            }
         }
         else if (expectedValue instanceof EObject)
         {
            assertEEquals((EObject) expectedValue, (EObject) actualValue);
         }
         else
         {
            Assert.assertEquals(expectedValue, actualValue);
         }
      }
   }
}
