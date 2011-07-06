package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.INotification;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.dbschema.cwsutilities.IUIDBSchema;
import com.cordys.dbschema.cwsutilities.IUIDBSchemaEditor;

@UIUnitTimeout(1800000000)
public class DBSchemaManager extends WorkSpaceOrganizer {
	IUIProject project;

	@Test
	public void createNorthwindDbSchema() {
		project = getProject();	  	 
	    IUICWSFolder folder=project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_DBSCHEMA); 
		IUIDBSchema dbschema = folder.addDocument(IUIDBSchema.class);
		IUIDBSchemaEditor dbschemaEditorNorthwind = dbschema.openEditor();
		dbschemaEditorNorthwind.selectMetadaDataServiceName("NorthwindWSApps");
		dbschemaEditorNorthwind.loadTables("*");
		dbschemaEditorNorthwind.save(EastWindArtifacts.NORTHWIND_DBSCHEMA, EastWindArtifacts.NORTHWIND_DBSCHEMA);
		INotification notification = null;
		long timeout = System.currentTimeMillis() + 50000;
		while (notification == null && System.currentTimeMillis() < timeout) {
			// try to find a notification for 50 seconds
			if (dbschemaEditorNorthwind.hasOpenNotification()) {
				notification = dbschemaEditorNorthwind.findNotificationStack().get(0);
			}
		}
		if (notification != null) {
			org.junit.Assert.assertEquals("Database Metadata Successfully Generated.", notification.getMessage());
			notification.close();
		}
		dbschemaEditorNorthwind.close();
	}	 
  	 
	@Test
	public void createProductionDbSchema() {
		
		  project = getProject();	  	 
	  	  IUICWSFolder folder=project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_DBSCHEMA );  	 
		  IUIDBSchema dbschema = folder.addDocument(IUIDBSchema.class);
	   	  IUIDBSchemaEditor dbschemaEditorProduction = dbschema.openEditor();
	   	  dbschemaEditorProduction.selectMetadaDataServiceName("ProductionWSApps");
	   	  dbschemaEditorProduction.loadTables("*");
	   	  dbschemaEditorProduction.save(EastWindArtifacts.PRODUCTION_DBSCHEMA, EastWindArtifacts.PRODUCTION_DBSCHEMA);	   	
	   	  INotification notification = null;
	   	  long timeout = System.currentTimeMillis() + 50000;
		  while (notification == null && System.currentTimeMillis() < timeout) {
			// try to find a notification for 50 seconds
			if (dbschemaEditorProduction.hasOpenNotification()) {
				notification = dbschemaEditorProduction.findNotificationStack().get(0);
			}
		 }
		 if (notification != null) {
			org.junit.Assert.assertEquals("Database Metadata Successfully Generated.", notification.getMessage());
			notification.close();
		 }
	   	  dbschemaEditorProduction.close();		
	}
}
