/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import org.sourcepit.b2.common.internal.utils.NlsUtils;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.ModuleIdDerivator;
import org.sourcepit.b2.model.module.AbstractModule;

public abstract class AbstractModuleParserRule<M extends AbstractModule>
   implements
      Comparable<AbstractModuleParserRule<M>>
{
   @Inject
   private ModuleIdDerivator moduleIdDerivator;

   public final M parse(IModuleParsingRequest request)
   {
      final M module = doParse(request);
      if (module != null)
      {
         final Set<Locale> locales = NlsUtils.getNlsPropertyFiles(module.getDirectory(), "module", "properties")
            .keySet();
         if (locales.isEmpty())
         {
            module.getLocales().add(NlsUtils.DEFAULT_LOCALE);
         }
         else
         {
            module.getLocales().addAll(locales);
         }
      }
      return module;
   }

   protected abstract M doParse(IModuleParsingRequest request);

   protected String getModuleVersion(IConverter converter)
   {
      return converter.getModuleVersion();
   }

   protected String getModuleId(final File baseDir)
   {
      return moduleIdDerivator.deriveModuleId(baseDir);
   }


   public int compareTo(AbstractModuleParserRule<M> otherRule)
   {
      final int otherPrio = otherRule.getPriority();
      final int priority = getPriority();
      if (otherPrio == priority)
      {
         return 0;
      }
      return priority < otherPrio ? 1 : -1;
   }

   protected int getPriority()
   {
      return Integer.MAX_VALUE / 2;
   }
}
