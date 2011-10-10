/*
 * Copyright (C) 2011 Bosch Software Innovations GmbH. All rights reserved.
 */

package org.sourcepit.b2.model.builder.util;

import java.io.File;

import org.sourcepit.b2.model.module.AbstractModule;

public final class CacheUtils
{
   public static final IModelCache NULL_CACHE = new IModelCache()
   {
      public AbstractModule get(File moduleDir)
      {
         return null;
      }
   };

   private CacheUtils()
   {
      super();
   }

   public static IModelCache getNullSafeCache(IModelCache cache)
   {
      return cache == null ? NULL_CACHE : cache;
   }

   public static AbstractModule get(IModelCache cache, File moduleDir)
   {
      return getNullSafeCache(cache).get(moduleDir);
   }
}