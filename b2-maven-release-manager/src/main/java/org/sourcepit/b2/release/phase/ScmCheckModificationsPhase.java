/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.release.phase;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.release.ReleaseExecutionException;
import org.apache.maven.shared.release.ReleaseFailureException;
import org.apache.maven.shared.release.ReleaseResult;
import org.apache.maven.shared.release.config.ReleaseDescriptor;
import org.apache.maven.shared.release.env.ReleaseEnvironment;
import org.sourcepit.b2.release.B2ReleaseHelper;

public class ScmCheckModificationsPhase extends org.apache.maven.shared.release.phase.ScmCheckModificationsPhase
{
   private B2ReleaseHelper releaseHelper;

   @Override
   public ReleaseResult execute(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException, ReleaseFailureException
   {
      addB2ModificationExcludes(releaseDescriptor);
      return super.execute(releaseDescriptor, releaseEnvironment, releaseHelper.adaptModuleProjects(reactorProjects));
   }

   @Override
   public ReleaseResult simulate(ReleaseDescriptor releaseDescriptor, ReleaseEnvironment releaseEnvironment,
      List<MavenProject> reactorProjects) throws ReleaseExecutionException, ReleaseFailureException
   {
      addB2ModificationExcludes(releaseDescriptor);
      return super.simulate(releaseDescriptor, releaseEnvironment, releaseHelper.adaptModuleProjects(reactorProjects));
   }

   private static void addB2ModificationExcludes(ReleaseDescriptor releaseDescriptor)
   {
      final List<String> modificationExcludes = new ArrayList<String>(releaseDescriptor.getCheckModificationExcludes());
      modificationExcludes.add("**/.b2");
      modificationExcludes.add("**/pom.xml");
      releaseDescriptor.setCheckModificationExcludes(modificationExcludes);
   }
}
