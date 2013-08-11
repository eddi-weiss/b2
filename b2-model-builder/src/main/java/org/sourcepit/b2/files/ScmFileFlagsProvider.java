/**
 * Copyright (c) 2013 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.files;

import static java.lang.Integer.valueOf;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_FORBIDDEN;
import static org.sourcepit.b2.files.ModuleFiles.FLAG_HIDDEN;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.sourcepit.common.utils.props.PropertiesSource;

@Named
public class ScmFileFlagsProvider implements FileFlagsProvider, FileFlagsInvestigatorFactroy
{
   @Override
   public Map<File, Integer> getFileFlags(File moduleDir, PropertiesSource properties)
   {
      final Set<String> scmDirs = getScmDirectories(properties);
      final Map<File, Integer> flags = new HashMap<File, Integer>(scmDirs.size());
      for (String scmDir : scmDirs)
      {
         final File dir = new File(moduleDir, scmDir);
         if (dir.exists())
         {
            flags.put(dir, valueOf(FLAG_HIDDEN | FLAG_FORBIDDEN));
         }
      }
      return flags;
   }

   @Override
   public FileFlagsInvestigator createFileFlagsCollector(File moduleDir, PropertiesSource properties)
   {
      final Set<String> scmDirs = getScmDirectories(properties);
      return new FileFlagsInvestigator()
      {
         @Override
         public int getFileFlags(File file)
         {
            if (file.isDirectory() && scmDirs.contains(file.getName()))
            {
               return FLAG_HIDDEN | FLAG_FORBIDDEN;
            }
            return 0;
         }
      };
   }

   private static Set<String> getScmDirectories(PropertiesSource properties)
   {
      return split(properties.get("b2.scmDirectories", ".git, .svn, .hg, CVS, .bzr"), ",");
   }

   private static Set<String> split(String values, String separator)
   {
      final Set<String> result = new HashSet<String>();
      for (String value : values.split(separator))
      {
         value = value.trim();
         if (value.length() > 0)
         {
            result.add(value);
         }
      }
      return result;
   }

}
