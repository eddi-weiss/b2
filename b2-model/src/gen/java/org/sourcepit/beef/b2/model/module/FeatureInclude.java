/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module;


/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Feature Include</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.model.module.FeatureInclude#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.model.module.ModulePackage#getFeatureInclude()
 * @model
 * @generated
 */
public interface FeatureInclude extends Reference
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.model.module.FeatureProject#getIncludedFeatures <em>Included Features</em>}'. <!--
    * begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(FeatureProject)
    * @see org.sourcepit.beef.b2.model.module.ModulePackage#getFeatureInclude_Parent()
    * @see org.sourcepit.beef.b2.model.module.FeatureProject#getIncludedFeatures
    * @model opposite="includedFeatures" required="true" transient="false"
    * @generated
    */
   FeatureProject getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.beef.b2.model.module.FeatureInclude#getParent <em>Parent</em>}'
    * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(FeatureProject value);

} // FeatureInclude
