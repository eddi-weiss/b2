/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.validation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.plexus.logging.Logger;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.b2.directory.parser.module.IModuleParsingRequest;
import org.sourcepit.b2.directory.parser.module.ModuleParserLifecycleParticipant;
import org.sourcepit.b2.model.builder.util.B2SessionService;
import org.sourcepit.b2.model.builder.util.ModuleWalker;
import org.sourcepit.b2.model.module.AbstractModule;
import org.sourcepit.common.utils.lang.ThrowablePipe;
import org.sourcepit.common.utils.props.PropertiesMap;

/**
 * @author Bernd Vogt <bernd.vogt@sourcepit.org>
 */
@Named
public class ModuleValidation implements ModuleParserLifecycleParticipant
{
   @Inject
   private Logger logger;

   @Inject
   private B2SessionService sessionService;

   @Inject
   private Map<String, ModuleValidationConstraint> constraintMap;

   public void preParse(IModuleParsingRequest request)
   { // noop
   }

   public void postParse(IModuleParsingRequest request, AbstractModule module, ThrowablePipe errors)
   {
      logger.info("Validating " + module.getId() + ".");
      if (module != null && errors.isEmpty())
      {
         final PropertiesMap properties = sessionService.getCurrentProperties();

         final Map<String, ModuleValidationConstraint> enabledConstraintsMap = getEnabledConstraints(properties);

         validate(enabledConstraintsMap, module, properties);
      }
   }

   private Map<String, ModuleValidationConstraint> getEnabledConstraints(final PropertiesMap properties)
   {
      final Map<String, ModuleValidationConstraint> enabledConstraintsMap = new LinkedHashMap<String, ModuleValidationConstraint>();
      for (Entry<String, ModuleValidationConstraint> entry : constraintMap.entrySet())
      {
         final String constraintId = entry.getKey();
         if (isConstraintEnabled(properties, constraintId))
         {
            enabledConstraintsMap.put(constraintId, entry.getValue());
         }
      }
      return enabledConstraintsMap;
   }

   private void validate(final Map<String, ModuleValidationConstraint> enabledConstraintsMap, AbstractModule module,
      final PropertiesMap properties)
   {
      final boolean quickFixesEnabled = properties.getBoolean("b2.validation.quickFixes.enabled", false);
      if (quickFixesEnabled)
      {
         logger.info("Quick fixes are enabled.");
      }
      else
      {
         logger
            .info("Quick fixes are disabled. You can enable it by setting the propety b2.validation.quickFixes.enabled=true.");
      }

      final ModuleWalker walker = new ModuleWalker(false, true)
      {
         @Override
         protected boolean doVisit(EObject eObject)
         {
            for (Entry<String, ModuleValidationConstraint> entry : enabledConstraintsMap.entrySet())
            {
               final String constraintId = entry.getKey();
               final ModuleValidationConstraint constraint = entry.getValue();
               constraint.validate(eObject, properties,
                  quickFixesEnabled ? isQuickFixesEnabled(properties, constraintId) : false);
            }
            return true;
         }
      };
      walker.walk(module);
   }

   private boolean isConstraintEnabled(PropertiesMap properties, String constraintId)
   {
      return properties.getBoolean("b2.validation.constraints." + constraintId + ".enabled", true);
   }

   private boolean isQuickFixesEnabled(PropertiesMap properties, String constraintId)
   {
      return properties.getBoolean("b2.validation.quickFixes." + constraintId + ".enabled", true);
   }
}
