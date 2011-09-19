/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.session;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * 
 * @see org.sourcepit.beef.b2.model.session.SessionModelPackage
 * @generated
 */
public interface SessionModelFactory extends EFactory
{
   /**
    * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   SessionModelFactory eINSTANCE = org.sourcepit.beef.b2.model.session.internal.impl.SessionModelFactoryImpl.init();

   /**
    * Returns a new object of class '<em>B2 Session</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>B2 Session</em>'.
    * @generated
    */
   B2Session createB2Session();

   /**
    * Returns a new object of class '<em>Module Project</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return a new object of class '<em>Module Project</em>'.
    * @generated
    */
   ModuleProject createModuleProject();

   /**
    * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @return the package supported by this factory.
    * @generated
    */
   SessionModelPackage getSessionModelPackage();

} // SessionModelFactory
