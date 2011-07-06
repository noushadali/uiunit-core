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

public class WsAppPackageManagerNorthwind extends WorkSpaceOrganizer{
	 IUIProject project = null;

	 @Test
	 @UIUnitTimeout(1000000000)
	 public void generateWsAppPackageForNorthwindDB(){

		  project = getProject();  	      
  	      IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);  	      
		  IUIDBSchema schemaDef=folder.getExistingChildDocument(IUIDBSchema.class, EastWindArtifacts.NORTHWIND_DBSCHEMA );		 
		  IUIDBSchemaWsAppServerPackageCreatorWizard wiz = (IUIDBSchemaWsAppServerPackageCreatorWizard) schemaDef.generateWsAppServerPackage();
	 	  DBSchemaWsAppServerPackageProperties dbschemaproperties = new DBSchemaWsAppServerPackageProperties();
	 	  dbschemaproperties.setNewFolderName(EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND );
	 	  wiz.createWsAppServerPackage(dbschemaproperties);
	 	  long timeout = System.currentTimeMillis() + 50000;
			while (System.currentTimeMillis() < timeout) {
				// waiting for 50 seconds
			}
	 } 

	 @Test
	 @UIUnitTimeout(1000000000)
	 public void generateWsAppWebServiceForNorthwindDB(){

		 project = getProject();		
		 IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA );
		 IUICWSFolder wsappPackagefolder = folder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND );
		 IUIWSAppServerPackage wsappPackage = wsappPackagefolder.getExistingChildDocument(IUIWSAppServerPackage.class, EastWindArtifacts.WSAPPS_PACKAGE_NORTHWIND );
		 IUIWSAppServerPackageEditor wsappPackageEditor= (IUIWSAppServerPackageEditor)wsappPackage.openDesigner();
		 WsAppServerPackageProperties wsapPackageProperties = new WsAppServerPackageProperties();
		 wsapPackageProperties.setWebserviceDefinitionSetName(EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);
		 wsapPackageProperties.setWebServiceInterfaceName(EastWindArtifacts.WS_INTERFACE_NORTHWIND);
		 wsappPackageEditor.generateWebServiceInterface(wsapPackageProperties);
		 long timeout = System.currentTimeMillis() + 50000;
			while (System.currentTimeMillis() < timeout) {
				// waiting for 50 seconds
		  }
		 wsappPackageEditor.save();
		 wsappPackageEditor.close();
	 }
}
