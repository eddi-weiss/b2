/*
 * Copyright (C) 2007 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package org.sourcepit.b2.model.common.util;

import org.eclipse.emf.common.util.URI;

public final class ArtifactIdentifier
{
   private final ArtifactReference ref;

   public ArtifactIdentifier(String groupId, String artifactId, String version, String type)
   {
      this(groupId, artifactId, version, null, type);
   }

   public ArtifactIdentifier(String groupId, String artifactId, String version, String classifier, String type)
   {
      ref = new ArtifactReference(groupId, artifactId, version, classifier, type);
   }

   public String getGroupId()
   {
      return ref.getGroupId();
   }

   public String getArtifactId()
   {
      return ref.getArtifactId();
   }

   public String getVersion()
   {
      return ref.getVersionRange();
   }

   public String getClassifier()
   {
      return ref.getClassifier();
   }

   public String getType()
   {
      return ref.getType();
   }

   public boolean isTargetOf(ArtifactReference ref)
   {
      return ref != null && ref.isAmingAt(this);
   }

   public ArtifactReference toArtifactReference()
   {
      return ref;
   }

   public URI toUri()
   {
      return ref.toUri();
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((ref == null) ? 0 : ref.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      ArtifactIdentifier other = (ArtifactIdentifier) obj;
      if (ref == null)
      {
         if (other.ref != null)
            return false;
      }
      else if (!ref.equals(other.ref))
         return false;
      return true;
   }


}