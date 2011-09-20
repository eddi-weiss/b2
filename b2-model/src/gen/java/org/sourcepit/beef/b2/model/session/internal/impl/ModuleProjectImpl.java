/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.internal.impl;

import java.io.File;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.common.Annotation;
import org.sourcepit.beef.b2.model.common.CommonModelPackage;
import org.sourcepit.beef.b2.model.session.B2Session;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Project</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl#getAnnotations <em>Annotations</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl#getGroupId <em>Group Id</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl#getSession <em>Session</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl#getArtifactId <em>Artifact Id</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.sourcepit.beef.b2.model.session.internal.impl.ModuleProjectImpl#getDirectory <em>Directory</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleProjectImpl extends EObjectImpl implements ModuleProject
{
   /**
    * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list. <!--
    * begin-user-doc --> <!-- end-user-doc -->
    * 
    * @see #getAnnotations()
    * @generated
    * @ordered
    */
   protected EList<Annotation> annotations;

   /**
    * The default value of the '{@link #getGroupId() <em>Group Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getGroupId()
    * @generated
    * @ordered
    */
   protected static final String GROUP_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getGroupId() <em>Group Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getGroupId()
    * @generated
    * @ordered
    */
   protected String groupId = GROUP_ID_EDEFAULT;

   /**
    * The default value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getArtifactId()
    * @generated
    * @ordered
    */
   protected static final String ARTIFACT_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getArtifactId()
    * @generated
    * @ordered
    */
   protected String artifactId = ARTIFACT_ID_EDEFAULT;

   /**
    * The default value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getVersion()
    * @generated
    * @ordered
    */
   protected static final String VERSION_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getVersion()
    * @generated
    * @ordered
    */
   protected String version = VERSION_EDEFAULT;

   /**
    * The default value of the '{@link #getDirectory() <em>Directory</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getDirectory()
    * @generated
    * @ordered
    */
   protected static final File DIRECTORY_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getDirectory() <em>Directory</em>}' attribute. <!-- begin-user-doc --> <!--
    * end-user-doc -->
    * 
    * @see #getDirectory()
    * @generated
    * @ordered
    */
   protected File directory = DIRECTORY_EDEFAULT;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected ModuleProjectImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   protected EClass eStaticClass()
   {
      return SessionModelPackage.Literals.MODULE_PROJECT;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<Annotation> getAnnotations()
   {
      if (annotations == null)
      {
         annotations = new EObjectContainmentWithInverseEList<Annotation>(Annotation.class, this,
            SessionModelPackage.MODULE_PROJECT__ANNOTATIONS, CommonModelPackage.ANNOTATION__PARENT);
      }
      return annotations;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getGroupId()
   {
      return groupId;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setGroupId(String newGroupId)
   {
      String oldGroupId = groupId;
      groupId = newGroupId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_PROJECT__GROUP_ID,
            oldGroupId, groupId));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2Session getSession()
   {
      if (eContainerFeatureID() != SessionModelPackage.MODULE_PROJECT__SESSION)
         return null;
      return (B2Session) eContainer();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public NotificationChain basicSetSession(B2Session newSession, NotificationChain msgs)
   {
      msgs = eBasicSetContainer((InternalEObject) newSession, SessionModelPackage.MODULE_PROJECT__SESSION, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setSession(B2Session newSession)
   {
      if (newSession != eInternalContainer()
         || (eContainerFeatureID() != SessionModelPackage.MODULE_PROJECT__SESSION && newSession != null))
      {
         if (EcoreUtil.isAncestor(this, newSession))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newSession != null)
            msgs = ((InternalEObject) newSession).eInverseAdd(this, SessionModelPackage.B2_SESSION__PROJECTS,
               B2Session.class, msgs);
         msgs = basicSetSession(newSession, msgs);
         if (msgs != null)
            msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_PROJECT__SESSION, newSession,
            newSession));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getArtifactId()
   {
      return artifactId;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setArtifactId(String newArtifactId)
   {
      String oldArtifactId = artifactId;
      artifactId = newArtifactId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_PROJECT__ARTIFACT_ID,
            oldArtifactId, artifactId));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getVersion()
   {
      return version;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setVersion(String newVersion)
   {
      String oldVersion = version;
      version = newVersion;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_PROJECT__VERSION, oldVersion,
            version));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public File getDirectory()
   {
      return directory;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public void setDirectory(File newDirectory)
   {
      File oldDirectory = directory;
      directory = newDirectory;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, SessionModelPackage.MODULE_PROJECT__DIRECTORY,
            oldDirectory, directory));
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotation getAnnotation(String source)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String getAnnotationEntry(String source, String key)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String putAnnotationEntry(String source, String key, String value)
   {
      // TODO: implement this method
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_PROJECT__ANNOTATIONS :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getAnnotations()).basicAdd(otherEnd, msgs);
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetSession((B2Session) otherEnd, msgs);
      }
      return super.eInverseAdd(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_PROJECT__ANNOTATIONS :
            return ((InternalEList<?>) getAnnotations()).basicRemove(otherEnd, msgs);
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            return basicSetSession(null, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs)
   {
      switch (eContainerFeatureID())
      {
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            return eInternalContainer().eInverseRemove(this, SessionModelPackage.B2_SESSION__PROJECTS, B2Session.class,
               msgs);
      }
      return super.eBasicRemoveFromContainerFeature(msgs);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_PROJECT__ANNOTATIONS :
            return getAnnotations();
         case SessionModelPackage.MODULE_PROJECT__GROUP_ID :
            return getGroupId();
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            return getSession();
         case SessionModelPackage.MODULE_PROJECT__ARTIFACT_ID :
            return getArtifactId();
         case SessionModelPackage.MODULE_PROJECT__VERSION :
            return getVersion();
         case SessionModelPackage.MODULE_PROJECT__DIRECTORY :
            return getDirectory();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_PROJECT__ANNOTATIONS :
            getAnnotations().clear();
            getAnnotations().addAll((Collection<? extends Annotation>) newValue);
            return;
         case SessionModelPackage.MODULE_PROJECT__GROUP_ID :
            setGroupId((String) newValue);
            return;
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            setSession((B2Session) newValue);
            return;
         case SessionModelPackage.MODULE_PROJECT__ARTIFACT_ID :
            setArtifactId((String) newValue);
            return;
         case SessionModelPackage.MODULE_PROJECT__VERSION :
            setVersion((String) newValue);
            return;
         case SessionModelPackage.MODULE_PROJECT__DIRECTORY :
            setDirectory((File) newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public void eUnset(int featureID)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_PROJECT__ANNOTATIONS :
            getAnnotations().clear();
            return;
         case SessionModelPackage.MODULE_PROJECT__GROUP_ID :
            setGroupId(GROUP_ID_EDEFAULT);
            return;
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            setSession((B2Session) null);
            return;
         case SessionModelPackage.MODULE_PROJECT__ARTIFACT_ID :
            setArtifactId(ARTIFACT_ID_EDEFAULT);
            return;
         case SessionModelPackage.MODULE_PROJECT__VERSION :
            setVersion(VERSION_EDEFAULT);
            return;
         case SessionModelPackage.MODULE_PROJECT__DIRECTORY :
            setDirectory(DIRECTORY_EDEFAULT);
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID)
   {
      switch (featureID)
      {
         case SessionModelPackage.MODULE_PROJECT__ANNOTATIONS :
            return annotations != null && !annotations.isEmpty();
         case SessionModelPackage.MODULE_PROJECT__GROUP_ID :
            return GROUP_ID_EDEFAULT == null ? groupId != null : !GROUP_ID_EDEFAULT.equals(groupId);
         case SessionModelPackage.MODULE_PROJECT__SESSION :
            return getSession() != null;
         case SessionModelPackage.MODULE_PROJECT__ARTIFACT_ID :
            return ARTIFACT_ID_EDEFAULT == null ? artifactId != null : !ARTIFACT_ID_EDEFAULT.equals(artifactId);
         case SessionModelPackage.MODULE_PROJECT__VERSION :
            return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
         case SessionModelPackage.MODULE_PROJECT__DIRECTORY :
            return DIRECTORY_EDEFAULT == null ? directory != null : !DIRECTORY_EDEFAULT.equals(directory);
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String toString()
   {
      if (eIsProxy())
         return super.toString();

      StringBuffer result = new StringBuffer(super.toString());
      result.append(" (groupId: ");
      result.append(groupId);
      result.append(", artifactId: ");
      result.append(artifactId);
      result.append(", version: ");
      result.append(version);
      result.append(", directory: ");
      result.append(directory);
      result.append(')');
      return result.toString();
   }

} // ModuleProjectImpl
