/*
Copyright 2014 Bernd Vogt and others.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.sourcepit.b2.examples.structured.module.tests;

import org.sourcepit.b2.examples.structured.module.ExampleUtilsTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class AllContinuousTests
{
   public static Test suite()
   {
      final TestSuite suite = new TestSuite();
      // $JUnit-BEGIN$

      suite.addTestSuite(SimpleTest.class);
      suite.addTestSuite(ExampleUtilsTest.class);

      // $JUnit-END$
      return suite;
   }

   private AllContinuousTests()
   {
      super();
   }
}
