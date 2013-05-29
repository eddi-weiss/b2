/**
 * Copyright (c) 2011 Sourcepit.org contributors and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.sourcepit.b2.model.module;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Site Project</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.sourcepit.b2.model.module.SiteProject#getParent <em>Parent</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.SiteProject#getCategories <em>Categories</em>}</li>
 * <li>{@link org.sourcepit.b2.model.module.SiteProject#getFeatureReferences <em>Feature References</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.sourcepit.b2.model.module.ModuleModelPackage#getSiteProject()
 * @model
 * @generated
 */
public interface SiteProject extends Project
{
   /**
    * Returns the value of the '<em><b>Parent</b></em>' container reference.
    * It is bidirectional and its opposite is '{@link org.sourcepit.b2.model.module.SitesFacet#getProjects
    * <em>Projects</em>}'.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Parent</em>' container reference isn't clear, there really should be more of a
    * description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Parent</em>' container reference.
    * @see #setParent(SitesFacet)
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getSiteProject_Parent()
    * @see org.sourcepit.b2.model.module.SitesFacet#getProjects
    * @model opposite="projects" transient="false"
    * @generated
    */
   SitesFacet getParent();

   /**
    * Sets the value of the '{@link org.sourcepit.b2.model.module.SiteProject#getParent <em>Parent</em>}' container
    * reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value the new value of the '<em>Parent</em>' container reference.
    * @see #getParent()
    * @generated
    */
   void setParent(SitesFacet value);

   /**
    * Returns the value of the '<em><b>Categories</b></em>' containment reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.Category}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Categories</em>' containment reference list isn't clear, there really should be more of
    * a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Categories</em>' containment reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getSiteProject_Categories()
    * @model containment="true" resolveProxies="true"
    * @generated
    */
   EList<Category> getCategories();

   /**
    * Returns the value of the '<em><b>Feature References</b></em>' containment reference list.
    * The list contents are of type {@link org.sourcepit.b2.model.module.StrictReference}.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Feature References</em>' containment reference list isn't clear, there really should be
    * more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Feature References</em>' containment reference list.
    * @see org.sourcepit.b2.model.module.ModuleModelPackage#getSiteProject_FeatureReferences()
    * @model containment="true" resolveProxies="true"
    * @generated
    */
   EList<StrictReference> getFeatureReferences();

} // SiteProject