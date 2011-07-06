package com.cordys.uiunit.eastwind.mdm;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.INotification;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.dbschema.cwsutilities.IUIDBSchema;
import com.cordys.dbschema.cwsutilities.IUIDBSchemaEditor;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;
import com.cordys.webservice.cwsutilities.DBSchemaWebServiceInterfaceProperties;
import com.cordys.webservice.cwsutilities.DatabaseMetadataCustomMethodProperties;
import com.cordys.webservice.cwsutilities.IUIDBSchemaWebServiceInterfaceCreatorWizard;
import com.cordys.webservice.cwsutilities.IUIDatabaseMetadataTable;

public class WebServicesGeneration extends WorkSpaceOrganizer{
	
	@Test
	@UIUnitTimeout(1200000)
	public void createFolderStructure(){		
		IUIProject project = getProject();
		IUICWSFolder mdm = project.addFolder(MDMConstants.MDM_FOLDER_MDM.toLowerCase());
		mdm.addFolder(EastWindArtifacts.FOLDER_DBSCHEMA);		
	}

	@UIUnitTimeout(120000000)
	@Test
	public void createWSGenerationForHub()
	{
		
		IUIProject project = getProject();	  	 
	    IUICWSFolder mdmFolder=project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
	    IUICWSFolder folder = mdmFolder.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_DBSCHEMA);
	    folder = folder.addFolder("WSAppServerPackageNorthwindDatabaseMetadata");
	    folder = folder.addFolder("WebServices NorthwindDatabaseMetadata");	    
	    IUIDBSchema dbschema = null;	    
	    IUIDBSchemaWebServiceInterfaceCreatorWizard wizard = null;
	    DBSchemaWebServiceInterfaceProperties prop = null;
	    dbschema  = project.getExistingChildDocument(IUIDBSchema.class, "databasemetadata/NorthwindDatabaseMetadata");
	    
		wizard = (IUIDBSchemaWebServiceInterfaceCreatorWizard)dbschema.generateWebservicesOperations();
		prop = new DBSchemaWebServiceInterfaceProperties();
		prop.setWsFolderPath("EastWind/mdm/databasemetadata/WSAppServerPackageNorthwindDatabaseMetadata/WebServices NorthwindDatabaseMetadata");
		prop.setSelectSpecificTables(new String[]{"Products"});
		prop.setWebServiceInterfaceName("WebServiceInterfaceNorthwindDatabaseMetadata");
		prop.setWsiDefinitionSetName("NorthwindDatabaseWebService");
		prop.setWsNamespace("http://schemas.cordys.com/NorthwindDatabaseMetadata1"); 
		wizard.createWebServiceInterface(prop);
		this.getContext().waitForIdle(1000);
		wizard.close();
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createMetaDataAndWSGenerationForSpoke1()
	{

		IUIProject project = getProject();	  	 
	    IUICWSFolder folder=project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
	    folder = folder.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_DBSCHEMA);
	    IUIDBSchema dbschema = null;
	    IUIDBSchemaWebServiceInterfaceCreatorWizard wizard = null;
	    DBSchemaWebServiceInterfaceProperties prop = null;
		dbschema = folder.addDocument(IUIDBSchema.class);
		IUIDBSchemaEditor dbschemaEditor = dbschema.openEditor();
		dbschemaEditor.selectMetadaDataServiceName(MDMConstants.MDM_SPOKE1_WSAPP);
		dbschemaEditor.loadTables("*");
		dbschemaEditor.save(MDMConstants.MDM_SPOKE1_DBNAME, MDMConstants.MDM_SPOKE1_DBNAME);
		INotification notification = null;
		long timeout = System.currentTimeMillis() + 50000;
		while (notification == null && System.currentTimeMillis() < timeout) {
			// try to find a notification for 50 seconds
			if (dbschemaEditor.hasOpenNotification()) {
				notification = dbschemaEditor.findNotificationStack().get(0);
			}
		}
		if (notification != null) {
			org.junit.Assert.assertEquals("Database Metadata Successfully Generated.", notification.getMessage());
			notification.close();
		}
		wizard = (IUIDBSchemaWebServiceInterfaceCreatorWizard)dbschema.generateWebservicesOperations();
		prop = new DBSchemaWebServiceInterfaceProperties();
		prop.setWebServiceInterfaceName(MDMConstants.MDM_WS_INTERFACE_SPOKE1);
		prop.setWebServiceOperationName(MDMConstants.MDM_WS_DEFINITIONSET_SPOKE1);
		wizard.createWebServiceInterface(prop);
		this.getContext().waitForIdle(1000);
		wizard.close();
		 this.getContext().waitForIdle();

	}
	@UIUnitTimeout(120000000)
	@Test
	public void createMetaDataAndWSGenerationForSpoke2()
	{

		IUIProject project = getProject();	  	 
	    IUICWSFolder folder=project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
	    folder = folder.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_DBSCHEMA);
	    IUIDBSchema dbschema = null;
	    IUIDBSchemaWebServiceInterfaceCreatorWizard wizard = null;
	    DBSchemaWebServiceInterfaceProperties prop = null;
		dbschema = folder.addDocument(IUIDBSchema.class);
		IUIDBSchemaEditor dbschemaEditor = dbschema.openEditor();
		dbschemaEditor.selectMetadaDataServiceName(MDMConstants.MDM_SPOKE2_WSAPP);
		dbschemaEditor.loadTables("*");
		dbschemaEditor.save(MDMConstants.MDM_SPOKE2_DBNAME, MDMConstants.MDM_SPOKE2_DBNAME);
		INotification notification = null;
		long timeout = System.currentTimeMillis() + 50000;
		while (notification == null && System.currentTimeMillis() < timeout) {
			// try to find a notification for 50 seconds
			if (dbschemaEditor.hasOpenNotification()) {
				notification = dbschemaEditor.findNotificationStack().get(0);
			}
		}
		if (notification != null) {
			org.junit.Assert.assertEquals("Database Metadata Successfully Generated.", notification.getMessage());
			notification.close();
		}
		wizard = (IUIDBSchemaWebServiceInterfaceCreatorWizard)dbschema.generateWebservicesOperations();
		prop = new DBSchemaWebServiceInterfaceProperties();
		prop.setWebServiceInterfaceName(MDMConstants.MDM_WS_INTERFACE_SPOKE2);
		prop.setWebServiceOperationName(MDMConstants.MDM_WS_DEFINITIONSET_SPOKE2);
		wizard.createWebServiceInterface(prop);
		this.getContext().waitForIdle(1000);
		wizard.close();
		 this.getContext().waitForIdle();

	}

	
	/**
	 * The following are used in Runtime to verify the MDM data synchronization
	 */
	
	@Test
	@UIUnitTimeout(1200000)
	public void generateCustomWSForProductsCount(){
		IUIProject project = getProject();	  	 
	    IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
	    folder = folder.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_DBSCHEMA);
	    IUIDBSchema dbschema = folder.getExistingChildDocument(IUIDBSchema.class,MDMConstants.MDM_SPOKE1_DBNAME);
	    folder = dbschema.getExistingChildDocument(IUICWSFolder.class,"Tables");
	    IUIDatabaseMetadataTable table = folder.getExistingChildDocument(IUIDatabaseMetadataTable.class,"Products");
	    DatabaseMetadataCustomMethodProperties properties = new DatabaseMetadataCustomMethodProperties();
	    properties.setSelectCondition("count(ProductID) ProductsCount");
	    properties.setSelectExistingFolder(true);
	    properties.setFolderPath(MDMConstants.MDM_SPOKE1_WS_FOLDER_PATH);	   
	    properties.setWsName(MDMConstants.MDM_WS_DEFINITIONSET_SPOKE1);
	    properties.setSelectExistingWSInterface(true);
	    properties.setWsInterfaceName(MDMConstants.MDM_WS_INTERFACE_SPOKE1);
	    properties.setWsOperationName(MDMConstants.MDM_WS_OPERATION_PRODUCTS_COUNT);
	    properties.setWsNameSpace(MDMConstants.MDM_RUNTIME_NS);
	    table.generateCustomWebService(properties);
	    this.getContext().waitForIdle();
	}	
	/*
	@UIUnitTimeout(120000000)
	@Test
	public void GenerateWSToUpdateProducts()
	{
		
		IUIProject project = getProject();	   	       
	    IUIDBSchema dbschema = null;	    
	    IUIDBSchemaWebServiceInterfaceCreatorWizard wizard = null;
	    DBSchemaWebServiceInterfaceProperties prop = null;
	    dbschema  = project.getExistingChildDocument(IUIDBSchema.class, "mdm/databasemetadata/"+MDMConstants.MDM_RUNTIME_DBSCHEMA);
	    
		wizard = (IUIDBSchemaWebServiceInterfaceCreatorWizard)dbschema.generateWebservicesOperations();
		prop = new DBSchemaWebServiceInterfaceProperties();
		prop.setWsFolderPath("EastWind/mdm/databasemetadata/"+MDMConstants.MDM_WS_DEFINITIONSET_RUNTIME+"/"+MDMConstants.MDM_WS_INTERFACE_RUNTIME);
		prop.setWebServiceInterfaceName("WebServiceInterfaceNorthwindDatabaseMetadata");
		prop.setWsiDefinitionSetName(MDMConstants.MDM_WS_INTERFACE_RUNTIME);
		prop.setExistingWsiDefinitionSet(true);
		prop.setSelectSpecificTables(new String[]{"Products"});		
		wizard.createWebServiceInterface(prop);
		this.getContext().waitForIdle(1000);
		wizard.close();
	}	
	*/
	
}
