/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.session.internal.impl;

import org.sourcepit.b2.model.session.ModuleProject;
import org.sourcepit.b2.model.session.util.ArtifactVersion;
import org.sourcepit.b2.model.session.util.DefaultArtifactVersion;
import org.sourcepit.b2.model.session.util.InvalidVersionSpecificationException;
import org.sourcepit.b2.model.session.util.VersionRange;

public class CModuleDependencyImpl extends ModuleDependencyImpl
{
   @Override
   public boolean isSatisfiableBy(ModuleProject moduleProject)
   {
      if (equals(getGroupId(), moduleProject.getGroupId()))
      {
         if (equals(getArtifactId(), moduleProject.getArtifactId()))
         {
            try
            {
               ArtifactVersion version = new DefaultArtifactVersion(moduleProject.getVersion());
               VersionRange range = VersionRange.createFromVersionSpec(getVersionRange());
               if (range.hasRestrictions())
               {
                  return range.containsVersion(version);
               }
               else
               {
                  return range.getRecommendedVersion().compareTo(version) == 0;
               }
            }
            catch (InvalidVersionSpecificationException e)
            {
            }
         }
      }

      return false;
   }

   private static boolean equals(Object o1, Object o2)
   {
      if (o1 == null)
      {
         return o2 == null;
      }
      return o1.equals(o2);
   }
}
