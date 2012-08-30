/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.builder.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.sourcepit.b2.model.module.FeatureInclude;
import org.sourcepit.b2.model.module.PluginInclude;
import org.sourcepit.b2.model.module.RuledReference;
import org.sourcepit.b2.model.module.VersionMatchRule;
import org.sourcepit.common.utils.props.LinkedPropertiesMap;
import org.sourcepit.common.utils.props.PropertiesMap;
import org.sourcepit.common.utils.props.PropertiesSource;

public class DefaultConverter2Test
{

   @Test
   public void testGetIncludedFeatures() throws Exception
   {
      String facetName = "foo";
      String propertyName = "includedFeatures";
      boolean isSource = false;

      String key1 = "b2.facets[\"" + facetName + "\"]." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedFeatures(key1, facetName, isSource);
      testGetIncludedFeatures(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      Converter2 converter = new DefaultConverter2();

      List<FeatureInclude> result = converter.getIncludedFeatures(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   @Test
   public void testGetIncludedSourceFeatures() throws Exception
   {
      String facetName = "foo";
      String propertyName = "includedSourceFeatures";
      boolean isSource = true;

      String key1 = "b2.facets[\"" + facetName + "\"]." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedFeatures(key1, facetName, isSource);
      testGetIncludedFeatures(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      Converter2 converter = new DefaultConverter2();

      List<FeatureInclude> result = converter.getIncludedFeatures(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   private void testGetIncludedFeatures(String key, String facetName, boolean isSource)
   {
      Converter2 converter = new DefaultConverter2();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<FeatureInclude> result;

      // empty
      result = converter.getIncludedFeatures(properties, facetName, isSource);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = converter.getIncludedFeatures(properties, facetName, isSource);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try
      {
         result = converter.getIncludedFeatures(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try
      {
         result = converter.getIncludedFeatures(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      properties.put(key, "foo.feature:1.0.0:optional:murks");
      try
      {
         result = converter.getIncludedFeatures(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:optional");
      result = converter.getIncludedFeatures(properties, facetName, isSource);
      assertEquals(3, result.size());

      FeatureInclude requirement;
      requirement = result.get(0);
      assertEquals("foo.feature", requirement.getId());
      assertNull(requirement.getVersion());
      assertFalse(requirement.isOptional());

      requirement = result.get(1);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertFalse(requirement.isOptional());

      requirement = result.get(2);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertTrue(requirement.isOptional());
   }

   @Test
   public void testGetIncludedPlugins() throws Exception
   {
      String facetName = "foo";
      String propertyName = "includedPlugins";
      boolean isSource = false;

      String key1 = "b2.facets[\"" + facetName + "\"]." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedPlugins(key1, facetName, isSource);
      testGetIncludedPlugins(key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      Converter2 converter = new DefaultConverter2();

      List<PluginInclude> result = converter.getIncludedPlugins(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   @Test
   public void testGetIncludedSourcePlugins() throws Exception
   {
      String facetName = "foo";
      String propertyName = "includedSourcePlugins";
      boolean isSource = true;

      String key1 = "b2.facets[\"" + facetName + "\"]." + propertyName;
      String key2 = "b2." + propertyName;

      testGetIncludedPlugins(key1, facetName, isSource);
      testGetIncludedPlugins(key2, facetName, isSource);
      
      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      Converter2 converter = new DefaultConverter2();

      List<PluginInclude> result = converter.getIncludedPlugins(properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   private void testGetIncludedPlugins(String key, String facetName, boolean isSource)
   {
      Converter2 converter = new DefaultConverter2();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<PluginInclude> result;

      // empty
      result = converter.getIncludedPlugins(properties, facetName, isSource);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = converter.getIncludedPlugins(properties, facetName, isSource);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try
      {
         result = converter.getIncludedPlugins(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try
      {
         result = converter.getIncludedPlugins(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      properties.put(key, "foo.feature:1.0.0:unpack:murks");
      try
      {
         result = converter.getIncludedPlugins(properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:unpack");
      result = converter.getIncludedPlugins(properties, facetName, isSource);
      assertEquals(3, result.size());

      PluginInclude include;
      include = result.get(0);
      assertEquals("foo.feature", include.getId());
      assertNull(include.getVersion());
      assertFalse(include.isUnpack());

      include = result.get(1);
      assertEquals("foo.feature", include.getId());
      assertEquals("1.0.0", include.getVersion());
      assertFalse(include.isUnpack());

      include = result.get(2);
      assertEquals("foo.feature", include.getId());
      assertEquals("1.0.0", include.getVersion());
      assertTrue(include.isUnpack());
   }

   @Test
   public void testGetRequiredFeatures() throws Exception
   {
      final Method method = Converter2.class.getDeclaredMethod("getRequiredFeatures", PropertiesSource.class,
         String.class, boolean.class);
      final boolean isSource = false;

      final String facetName = "foo";
      final String propertyName = "requiredFeatures";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   @Test
   public void testGetRequiredSourceFeatures() throws Exception
   {
      final Method method = Converter2.class.getDeclaredMethod("getRequiredFeatures", PropertiesSource.class,
         String.class, boolean.class);
      final boolean isSource = true;

      final String facetName = "foo";
      final String propertyName = "requiredSourceFeatures";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   @Test
   public void testGetRequiredPlugins() throws Exception
   {
      final Method method = Converter2.class.getDeclaredMethod("getRequiredPlugins", PropertiesSource.class,
         String.class, boolean.class);
      final boolean isSource = false;

      final String facetName = "foo";
      final String propertyName = "requiredPlugins";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   @Test
   public void testGetRequiredSourcePlugins() throws Exception
   {
      final Method method = Converter2.class.getDeclaredMethod("getRequiredPlugins", PropertiesSource.class,
         String.class, boolean.class);
      final boolean isSource = true;

      final String facetName = "foo";
      final String propertyName = "requiredSourcePlugins";
      testGetRequiredFeaturesOrPluginsAndKeyOrdering(method, facetName, propertyName, isSource);
   }

   private void testGetRequiredFeaturesOrPluginsAndKeyOrdering(final Method method, final String facetName,
      String propertyName, boolean isSource) throws Exception
   {
      String key1 = "b2.facets[\"" + facetName + "\"]." + propertyName;
      testGetRequiredFeaturesOrPlugins(method, key1, facetName, isSource);

      String key2 = "b2." + propertyName;
      testGetRequiredFeaturesOrPlugins(method, key2, facetName, isSource);

      // test key1 overrides key2
      PropertiesMap properties = new LinkedPropertiesMap();
      properties.put(key1, "key1");
      properties.put(key2, "key2");

      Converter2 converter = new DefaultConverter2();

      List<RuledReference> result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(1, result.size());
      assertEquals("key1", result.get(0).getId());
   }

   private void testGetRequiredFeaturesOrPlugins(final Method method, final String key, String facetName,
      boolean isSource) throws Exception
   {
      Converter2 converter = new DefaultConverter2();

      PropertiesMap properties = new LinkedPropertiesMap();

      List<RuledReference> result;
      RuledReference requirement;

      // empty
      result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(0, result.size());

      properties.put(key, " ,, ");
      result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(0, result.size());

      // invalid
      properties.put(key, "foo.feature:!.0.0");
      try
      {
         result = invoke(method, converter, properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      properties.put(key, "foo.feature:1.0.0:murks");
      try
      {
         result = invoke(method, converter, properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      properties.put(key, "foo.feature:1.0.0:perfect:murks");
      try
      {
         result = invoke(method, converter, properties, facetName, isSource);
         fail();
      }
      catch (IllegalArgumentException e)
      {
      }

      // valid
      properties.put(key, "foo.feature, foo.feature:1.0.0, foo.feature:1.0.0:perfect");
      result = invoke(method, converter, properties, facetName, isSource);
      assertEquals(3, result.size());

      requirement = result.get(0);
      assertEquals("foo.feature", requirement.getId());
      assertNull(requirement.getVersion());
      assertSame(VersionMatchRule.COMPATIBLE, requirement.getVersionMatchRule());

      requirement = result.get(1);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.COMPATIBLE, requirement.getVersionMatchRule());

      requirement = result.get(2);
      assertEquals("foo.feature", requirement.getId());
      assertEquals("1.0.0", requirement.getVersion());
      assertSame(VersionMatchRule.PERFECT, requirement.getVersionMatchRule());
   }

   @SuppressWarnings("unchecked")
   private static List<RuledReference> invoke(final Method method, Converter2 converter, PropertiesMap properties,
      String facetName, boolean isSource) throws Exception
   {
      try
      {
         return (List<RuledReference>) method.invoke(converter, properties, facetName, isSource);
      }
      catch (InvocationTargetException e)
      {
         Throwable t = e.getTargetException();
         if (t instanceof Exception)
         {
            throw (Exception) t;
         }
         if (t instanceof Error)
         {
            throw (Error) t;
         }
         throw new Error(t);
      }
   }

}
