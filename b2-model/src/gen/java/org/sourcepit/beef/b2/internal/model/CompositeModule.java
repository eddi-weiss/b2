/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Package</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.CompositeModule#getModules <em>Modules</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getCompositeModule()
 * @model
 * @generated
 */
public interface CompositeModule extends AbstractModule
{
   /**
    * Returns the value of the '<em><b>Modules</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.internal.model.AbstractModule}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.AbstractModule#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Modules</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Modules</em>' containment reference list.
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getCompositeModule_Modules()
    * @see org.sourcepit.beef.b2.internal.model.AbstractModule#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<AbstractModule> getModules();

} // ModulePackage
