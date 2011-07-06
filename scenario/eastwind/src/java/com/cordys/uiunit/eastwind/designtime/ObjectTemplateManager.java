package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.objecttemplate.cwsutilities.IUIObjectTemplate;
import com.cordys.objecttemplate.cwsutilities.IUIObjectTemplateEditor;

public class ObjectTemplateManager extends WorkSpaceOrganizer
{			
	@Test
	@UIUnitTimeout(800000)
	public void createOrdersObjectTemplate()
	{
		//Get Folder for The Object Template
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_OBJETTEMPLATES);
		//Create the Object Template
		createObjectTemplate(folder, EastWindArtifacts.OBJECT_TEMPLATE_ORDERS, EastWindArtifacts.SCHEMA_FRAG_ORDERS);		
	}
	@Test
	@UIUnitTimeout(800000)
	public void createProductionObjectTemplate()
	{
		//Get Folder for The Object Template
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_OBJETTEMPLATES);
		
		//Create the Object Template
		createObjectTemplate(folder, EastWindArtifacts.OBJECT_TEMPLATE_PRODUCTION, EastWindArtifacts.SCHEMA_FRAG_PRODUCTION);		
	}
	@Test
	@UIUnitTimeout(800000)
	public void createOrderDetailsObjectTemplate()
	{
		//Get Folder for The Object Template
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_OBJETTEMPLATES);
		//Create the Object Template
		createObjectTemplate(folder, EastWindArtifacts.OBJECT_TEMPLATE_ORDERDETAILS, EastWindArtifacts.SCHEMA_FRAG_ORDERDETAILS);				
	}
	private void createObjectTemplate(IUICWSFolder folder,String name,String schemaFragment)
	{
		//Create the Object Template
		IUIObjectTemplate objectTemplate = folder.addDocument(IUIObjectTemplate.class);
		//Create the Object Template
		IUIObjectTemplateEditor objectTemplateEditor = objectTemplate.openDesigner();			
		//Select the Schema fragment
		objectTemplateEditor.setExistingSchema(schemaFragment);
		//Select the Validate Schema Option
		objectTemplateEditor.setValidateSchema(true);
		//Select the Execute the Rules
		objectTemplateEditor.setExcuteRules(true);
		//View Schema
		//IUISchemaFragmentEditor schemaEditor = objectTemplateEditor.viewSchema();
		//Close the Schema Editor
		//schemaEditor.close();
		//Select the Special Attributes Tab
		objectTemplateEditor.selectSpecialAttTab();		
		//Save the Object Template
		objectTemplateEditor.save(name, name);
		//Close the Object Template
		objectTemplateEditor.close();
	}
}
