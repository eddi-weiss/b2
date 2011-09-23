/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.sourcepit.beef.b2.model.common.Annotatable;
import org.sourcepit.beef.b2.model.session.B2Session;
import org.sourcepit.beef.b2.model.session.ModuleDependency;
import org.sourcepit.beef.b2.model.session.ModuleProject;
import org.sourcepit.beef.b2.model.session.SessionModelPackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionModelPackage
 * @generated
 */
public class SessionModelAdapterFactory extends AdapterFactoryImpl
{
   /**
    * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected static SessionModelPackage modelPackage;

   /**
    * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SessionModelAdapterFactory()
   {
      if (modelPackage == null)
      {
         modelPackage = SessionModelPackage.eINSTANCE;
      }
   }

   /**
    * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This implementation
    * returns <code>true</code> if the object is either the model's package or is an instance object of the model. <!--
    * end-user-doc -->
    * 
    * @return whether this factory is applicable for the type of the object.
    * @generated
    */
   @Override
   public boolean isFactoryForType(Object object)
   {
      if (object == modelPackage)
      {
         return true;
      }
      if (object instanceof EObject)
      {
         return ((EObject) object).eClass().getEPackage() == modelPackage;
      }
      return false;
   }

   /**
    * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   protected SessionModelSwitch<Adapter> modelSwitch = new SessionModelSwitch<Adapter>()
   {
      @Override
      public Adapter caseB2Session(B2Session object)
      {
         return createB2SessionAdapter();
      }

      @Override
      public Adapter caseModuleProject(ModuleProject object)
      {
         return createModuleProjectAdapter();
      }

      @Override
      public Adapter caseModuleDependency(ModuleDependency object)
      {
         return createModuleDependencyAdapter();
      }

      @Override
      public Adapter caseAnnotatable(Annotatable object)
      {
         return createAnnotatableAdapter();
      }

      @Override
      public Adapter defaultCase(EObject object)
      {
         return createEObjectAdapter();
      }
   };

   /**
    * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param target the object to adapt.
    * @return the adapter for the <code>target</code>.
    * @generated
    */
   @Override
   public Adapter createAdapter(Notifier target)
   {
      return modelSwitch.doSwitch((EObject) target);
   }


   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.session.B2Session
    * <em>B2 Session</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.session.B2Session
    * @generated
    */
   public Adapter createB2SessionAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.session.ModuleProject
    * <em>Module Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.session.ModuleProject
    * @generated
    */
   public Adapter createModuleProjectAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.session.ModuleDependency
    * <em>Module Dependency</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
    * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.session.ModuleDependency
    * @generated
    */
   public Adapter createModuleDependencyAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.sourcepit.beef.b2.model.common.Annotatable
    * <em>Annotatable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
    * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
    * 
    * @return the new adapter.
    * @see org.sourcepit.beef.b2.model.common.Annotatable
    * @generated
    */
   public Adapter createAnnotatableAdapter()
   {
      return null;
   }

   /**
    * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null. <!--
    * end-user-doc -->
    * 
    * @return the new adapter.
    * @generated
    */
   public Adapter createEObjectAdapter()
   {
      return null;
   }

} // SessionModelAdapterFactory