/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.release.phase;

import static org.mockito.Mockito.mock;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.LegacySupport;
import org.apache.maven.shared.release.phase.ReleasePhase;
import org.codehaus.plexus.ContainerConfiguration;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusTestCase;

public class CompatibilityTest extends PlexusTestCase
{
   @Override
   protected void customizeContainerConfiguration(ContainerConfiguration cc)
   {
      super.customizeContainerConfiguration(cc);
      cc.setClassPathScanning(PlexusConstants.SCANNING_INDEX)
         .setAutoWiring(true).setName("maven");
   }

   public void testAssureAllReleasePhasesAreImplementedByB2() throws Exception
   {
      LegacySupport buildContext = lookup(LegacySupport.class);
      buildContext.setSession(mock(MavenSession.class));
      
      @SuppressWarnings({ "unchecked", "rawtypes" })
      final Map<String, ReleasePhase> releasePhasesMap = (Map) getContainer().lookupMap(ReleasePhase.ROLE);
      assertFalse(releasePhasesMap.isEmpty());

      for (Entry<String, ReleasePhase> entry : releasePhasesMap.entrySet())
      {
         final String roleHint = entry.getKey();
         final String implName = entry.getValue().getClass().getName();

         assertTrue("Release phase " + roleHint + " must be implemented by b2. Current impl: " + implName,
            implName.startsWith("org.sourcepit.b2.release.phase."));
      }
   }
}
