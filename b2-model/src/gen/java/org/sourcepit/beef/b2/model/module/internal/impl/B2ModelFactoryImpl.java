/**
 * <copyright> </copyright>
 * 
 * $Id$
 */

package org.sourcepit.beef.b2.model.module.internal.impl;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sourcepit.beef.b2.model.module.Annotation;
import org.sourcepit.beef.b2.model.module.B2ModelFactory;
import org.sourcepit.beef.b2.model.module.B2ModelPackage;
import org.sourcepit.beef.b2.model.module.BasicModule;
import org.sourcepit.beef.b2.model.module.Category;
import org.sourcepit.beef.b2.model.module.CompositeModule;
import org.sourcepit.beef.b2.model.module.FeatureInclude;
import org.sourcepit.beef.b2.model.module.FeatureProject;
import org.sourcepit.beef.b2.model.module.FeaturesFacet;
import org.sourcepit.beef.b2.model.module.PluginInclude;
import org.sourcepit.beef.b2.model.module.PluginProject;
import org.sourcepit.beef.b2.model.module.PluginsFacet;
import org.sourcepit.beef.b2.model.module.ProductDefinition;
import org.sourcepit.beef.b2.model.module.ProductsFacet;
import org.sourcepit.beef.b2.model.module.Reference;
import org.sourcepit.beef.b2.model.module.SiteProject;
import org.sourcepit.beef.b2.model.module.SitesFacet;
import org.sourcepit.beef.b2.model.module.internal.impl.CB2ModelFactoryImpl;
import org.sourcepit.beef.b2.model.module.util.Identifier;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class B2ModelFactoryImpl extends EFactoryImpl implements B2ModelFactory
{
   /**
    * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated NOT
    */
   public static B2ModelFactory init()
   {
      try
      {
         B2ModelFactory theB2ModelFactory = (B2ModelFactory) EPackage.Registry.INSTANCE
            .getEFactory(B2ModelPackage.eNS_URI);
         if (theB2ModelFactory != null)
         {
            return theB2ModelFactory;
         }
      }
      catch (Exception exception)
      {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new CB2ModelFactoryImpl();
   }

   /**
    * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2ModelFactoryImpl()
   {
      super();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public EObject create(EClass eClass)
   {
      switch (eClass.getClassifierID())
      {
         case B2ModelPackage.BASIC_MODULE :
            return createBasicModule();
         case B2ModelPackage.COMPOSITE_MODULE :
            return createCompositeModule();
         case B2ModelPackage.PLUGINS_FACET :
            return createPluginsFacet();
         case B2ModelPackage.FEATURES_FACET :
            return createFeaturesFacet();
         case B2ModelPackage.SITES_FACET :
            return createSitesFacet();
         case B2ModelPackage.PLUGIN_PROJECT :
            return createPluginProject();
         case B2ModelPackage.FEATURE_PROJECT :
            return createFeatureProject();
         case B2ModelPackage.SITE_PROJECT :
            return createSiteProject();
         case B2ModelPackage.PLUGIN_INCLUDE :
            return createPluginInclude();
         case B2ModelPackage.CATEGORY :
            return createCategory();
         case B2ModelPackage.FEATURE_INCLUDE :
            return createFeatureInclude();
         case B2ModelPackage.ANNOTATION :
            return createAnnotation();
         case B2ModelPackage.ESTRING_MAP_ENTRY :
            return (EObject) createEStringMapEntry();
         case B2ModelPackage.PRODUCTS_FACET :
            return createProductsFacet();
         case B2ModelPackage.PRODUCT_DEFINITION :
            return createProductDefinition();
         case B2ModelPackage.REFERENCE :
            return createReference();
         default :
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public Object createFromString(EDataType eDataType, String initialValue)
   {
      switch (eDataType.getClassifierID())
      {
         case B2ModelPackage.EJAVA_FILE :
            return createEJavaFileFromString(eDataType, initialValue);
         case B2ModelPackage.ELOCALE :
            return createELocaleFromString(eDataType, initialValue);
         case B2ModelPackage.IDENTIFIER :
            return createIdentifierFromString(eDataType, initialValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   @Override
   public String convertToString(EDataType eDataType, Object instanceValue)
   {
      switch (eDataType.getClassifierID())
      {
         case B2ModelPackage.EJAVA_FILE :
            return convertEJavaFileToString(eDataType, instanceValue);
         case B2ModelPackage.ELOCALE :
            return convertELocaleToString(eDataType, instanceValue);
         case B2ModelPackage.IDENTIFIER :
            return convertIdentifierToString(eDataType, instanceValue);
         default :
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public BasicModule createBasicModule()
   {
      BasicModuleImpl basicModule = new BasicModuleImpl();
      return basicModule;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public CompositeModule createCompositeModule()
   {
      CompositeModuleImpl compositeModule = new CompositeModuleImpl();
      return compositeModule;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginsFacet createPluginsFacet()
   {
      PluginsFacetImpl pluginsFacet = new PluginsFacetImpl();
      return pluginsFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeaturesFacet createFeaturesFacet()
   {
      FeaturesFacetImpl featuresFacet = new FeaturesFacetImpl();
      return featuresFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SitesFacet createSitesFacet()
   {
      SitesFacetImpl sitesFacet = new SitesFacetImpl();
      return sitesFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginProject createPluginProject()
   {
      PluginProjectImpl pluginProject = new PluginProjectImpl();
      return pluginProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureProject createFeatureProject()
   {
      FeatureProjectImpl featureProject = new FeatureProjectImpl();
      return featureProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public SiteProject createSiteProject()
   {
      SiteProjectImpl siteProject = new SiteProjectImpl();
      return siteProject;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public PluginInclude createPluginInclude()
   {
      PluginIncludeImpl pluginInclude = new PluginIncludeImpl();
      return pluginInclude;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Category createCategory()
   {
      CategoryImpl category = new CategoryImpl();
      return category;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public FeatureInclude createFeatureInclude()
   {
      FeatureIncludeImpl featureInclude = new FeatureIncludeImpl();
      return featureInclude;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Annotation createAnnotation()
   {
      AnnotationImpl annotation = new AnnotationImpl();
      return annotation;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Map.Entry<String, String> createEStringMapEntry()
   {
      EStringMapEntryImpl eStringMapEntry = new EStringMapEntryImpl();
      return eStringMapEntry;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductsFacet createProductsFacet()
   {
      ProductsFacetImpl productsFacet = new ProductsFacetImpl();
      return productsFacet;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public ProductDefinition createProductDefinition()
   {
      ProductDefinitionImpl productDefinition = new ProductDefinitionImpl();
      return productDefinition;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Reference createReference()
   {
      ReferenceImpl reference = new ReferenceImpl();
      return reference;
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public File createEJavaFileFromString(EDataType eDataType, String initialValue)
   {
      return (File) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertEJavaFileToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Locale createELocaleFromString(EDataType eDataType, String initialValue)
   {
      return (Locale) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertELocaleToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public Identifier createIdentifierFromString(EDataType eDataType, String initialValue)
   {
      return (Identifier) super.createFromString(eDataType, initialValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public String convertIdentifierToString(EDataType eDataType, Object instanceValue)
   {
      return super.convertToString(eDataType, instanceValue);
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @generated
    */
   public B2ModelPackage getB2ModelPackage()
   {
      return (B2ModelPackage) getEPackage();
   }

   /**
    * <!-- begin-user-doc --> <!-- end-user-doc -->
    * 
    * @deprecated
    * @generated
    */
   @Deprecated
   public static B2ModelPackage getPackage()
   {
      return B2ModelPackage.eINSTANCE;
   }

} // B2ModelFactoryImpl
