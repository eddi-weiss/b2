/**
 * Copyright (c) 2012 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.its.util;

import java.io.File;

import org.apache.maven.model.Scm;


public interface SCM
{

   public abstract Scm createMavenScmModel(File projectDir, String version);

   public abstract void create();

   public abstract void switchVersion(String version);

}