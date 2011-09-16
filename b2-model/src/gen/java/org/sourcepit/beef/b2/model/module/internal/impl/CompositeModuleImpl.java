/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sourcepit.beef.b2.model.module.AbstractModule;
import org.sourcepit.beef.b2.model.module.B2ModelPackage;
import org.sourcepit.beef.b2.model.module.CompositeModule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Composite Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.internal.impl.CompositeModuleImpl#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CompositeModuleImpl extends AbstractModuleImpl implements CompositeModule
{
   /**
    * The cached value of the '{@link #getModules() <em>Modules</em>}' containment reference list. <!-- begin-user-doc
    * --> <!-- end-user-doc -->
    * 
    * @see #getModules()
    * @generated
    * @ordered
    */
   protected EList<AbstractModule> modules;

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected CompositeModuleImpl()
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
      return B2ModelPackage.Literals.COMPOSITE_MODULE;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public EList<AbstractModule> getModules()
   {
      if (modules == null)
      {
         modules = new EObjectContainmentWithInverseEList<AbstractModule>(AbstractModule.class, this,
            B2ModelPackage.COMPOSITE_MODULE__MODULES, B2ModelPackage.ABSTRACT_MODULE__PARENT);
      }
      return modules;
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
         case B2ModelPackage.COMPOSITE_MODULE__MODULES :
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getModules()).basicAdd(otherEnd, msgs);
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
         case B2ModelPackage.COMPOSITE_MODULE__MODULES :
            return ((InternalEList<?>) getModules()).basicRemove(otherEnd, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
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
         case B2ModelPackage.COMPOSITE_MODULE__MODULES :
            return getModules();
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
         case B2ModelPackage.COMPOSITE_MODULE__MODULES :
            getModules().clear();
            getModules().addAll((Collection<? extends AbstractModule>) newValue);
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
         case B2ModelPackage.COMPOSITE_MODULE__MODULES :
            getModules().clear();
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
         case B2ModelPackage.COMPOSITE_MODULE__MODULES :
            return modules != null && !modules.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} // CompositeModuleImpl