package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.dbschema.cwsutilities.DBSchemaWsAppServerPackageProperties;
import com.cordys.dbschema.cwsutilities.IUIDBSchema;
import com.cordys.dbschema.cwsutilities.IUIDBSchemaWsAppServerPackageCreatorWizard;
import com.cordys.wsapps.cwsutilities.IUIWSAppServerPackage;
import com.cordys.wsapps.cwsutilities.IUIWSAppServerPackageEditor;
import com.cordys.wsapps.cwsutilities.WsAppServerPackageProperties;

public class WsAppPackageManagerProduction extends WorkSpaceOrganizer{
	IUIProject project = null;

	@Test
	@UIUnitTimeout(10000000)
	public void generateWsAppPackageForProductionDB(){

		 project = getProject();  	   
  	     IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);  	    
         IUIDBSchema schemaDef=folder.getExistingChildDocument(IUIDBSchema.class, EastWindArtifacts.PRODUCTION_DBSCHEMA);         
		 IUIDBSchemaWsAppServerPackageCreatorWizard wiz = (IUIDBSchemaWsAppServerPackageCreatorWizard) schemaDef.generateWsAppServerPackage();
	 	 DBSchemaWsAppServerPackageProperties dbschemaproperties = new DBSchemaWsAppServerPackageProperties();
	 	 dbschemaproperties.setNewFolderName(EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_PRODUCTION);
	 	 wiz.createWsAppServerPackage(dbschemaproperties);
	 	 long timeout = System.currentTimeMillis() + 50000;
		 while (System.currentTimeMillis() < timeout) {
			// waiting for 50 seconds
		 }	 	
	 }

	 @Test
	 @UIUnitTimeout(10000000)
	 public void generateWsAppWebServiceForProductionDb(){

         project = getProject();        
		 IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA );
		 IUICWSFolder wsappPackagefolder = folder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_PRODUCTION );		
		 IUIWSAppServerPackage wsappPackage = wsappPackagefolder.getExistingChildDocument(IUIWSAppServerPackage.class, EastWindArtifacts.WSAPPS_PACKAGE_PRODUCTIONDB);		
		 IUIWSAppServerPackageEditor wsappPackageEditor= (IUIWSAppServerPackageEditor)wsappPackage.openDesigner();
		 WsAppServerPackageProperties wsapPackageProperties = new WsAppServerPackageProperties();
		 wsapPackageProperties.setWebserviceDefinitionSetName(EastWindArtifacts.WS_DEFINITIONSET_PRODUCTION);
		 wsapPackageProperties.setWebServiceInterfaceName(EastWindArtifacts.WS_INTERFACE_PRODUCTIONDB);
		 wsappPackageEditor.generateWebServiceInterface(wsapPackageProperties);
		 long timeout = System.currentTimeMillis() + 50000;
			while (System.currentTimeMillis() < timeout) {
				// waiting for 50 seconds
		 }
		 wsappPackageEditor.save();
		 wsappPackageEditor.close();
	 }
}


