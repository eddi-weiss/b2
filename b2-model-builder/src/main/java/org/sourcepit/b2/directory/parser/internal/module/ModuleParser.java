/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.directory.parser.internal.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.directory.parser.module.IModuleParser;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.ModuleParserLifecycleParticipant;
import org.sourcepit.b2.model.builder.util.DecouplingB2ModelWalker;
import org.sourcepit.b2.model.builder.util.IConverter;
import org.sourcepit.b2.model.builder.util.IModelCache;
import org.sourcepit.b2.model.common.Annotatable;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;

@Named
public class ModuleParser implements IModuleParser
{
   @Inject
   private List<AbstractModuleParserRule<? extends AbstractModule>> rules;

   @Inject
   private List<IModuleParserExtender> extenders;

   @Inject
   private List<ModuleParserLifecycleParticipant> lifecycleParticipants;

   public AbstractModule parse(final IModuleParsingRequest request)
   {
      return newLifecyclePhase().execute(request);
   }

   private LifecyclePhase<AbstractModule, IModuleParsingRequest, ModuleParserLifecycleParticipant> newLifecyclePhase()
   {
      return new LifecyclePhase<AbstractModule, IModuleParsingRequest, ModuleParserLifecycleParticipant>(
         lifecycleParticipants)
      {
         @Override
         protected void pre(ModuleParserLifecycleParticipant participant, IModuleParsingRequest request)
         {
            participant.preParse(request.getModuleDirectory());
         }

         @Override
         protected AbstractModule doExecute(IModuleParsingRequest request)
         {
            return doParse(request);
         }

         @Override
         protected void post(ModuleParserLifecycleParticipant participant, IModuleParsingRequest request,
            AbstractModule result, ThrowablePipe errors)
         {
            participant.postParse(request.getModuleDirectory(), result, errors);
         }
      };
   }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   private AbstractModule doParse(IModuleParsingRequest request)
   {
      checkRequest(request);
      
      final List<AbstractModuleParserRule<? extends AbstractModule>> orderedRules = new ArrayList<AbstractModuleParserRule<? extends AbstractModule>>(
         rules);
      // Task #58: Workaround for javac shortcoming
      Collections.sort((List) orderedRules);

      for (AbstractModuleParserRule<? extends AbstractModule> rule : orderedRules)
      {
         final AbstractModule module = rule.parse(request);
         if (module != null)
         {
            return enhance(module, request.getConverter(), request.getModelCache());
         }
      }
      return null;
   }

   protected AbstractModule enhance(AbstractModule module, final IConverter converter, IModelCache cache)
   {
      if (!extenders.isEmpty())
      {
         final DecouplingB2ModelWalker walker = new DecouplingB2ModelWalker(cache)
         {
            @Override
            protected boolean doVisit(EObject eObject)
            {
               if (eObject instanceof Annotatable)
               {
                  for (IModuleParserExtender extender : extenders)
                  {
                     extender.extend((Annotatable) eObject, converter);
                  }
               }
               return true;
            }
         };
         walker.walk(module);
      }
      return module;
   }

   protected void checkRequest(IModuleParsingRequest request)
   {
      if (request == null)
      {
         throw new IllegalArgumentException("Request must not be null.");
      }

      final File baseDir = request.getModuleDirectory();
      if (baseDir == null)
      {
         throw new IllegalArgumentException("Project must not be null.");
      }

      final IConverter converter = request.getConverter();
      if (converter == null)
      {
         throw new IllegalArgumentException("converter must not be null.");
      }
   }
}
