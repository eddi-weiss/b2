/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.internal.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sites Facet</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.beef.b2.internal.model.SitesFacet#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getSitesFacet()
 * @model
 * @generated
 */
public interface SitesFacet extends ProjectFacet<SiteProject>
{
   /**
    * Returns the value of the '<em><b>Projects</b></em>' containment reference list. The list contents are of type
    * {@link org.sourcepit.beef.b2.internal.model.SiteProject}. It is bidirectional and its opposite is '
    * {@link org.sourcepit.beef.b2.internal.model.SiteProject#getParent <em>Parent</em>}'. <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Projects</em>' containment reference list isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Projects</em>' containment reference list.
    * @see org.sourcepit.beef.b2.internal.model.B2ModelPackage#getSitesFacet_Projects()
    * @see org.sourcepit.beef.b2.internal.model.SiteProject#getParent
    * @model opposite="parent" containment="true"
    * @generated
    */
   EList<SiteProject> getProjects();

} // SitesFacet
