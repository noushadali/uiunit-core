package com.cordys.uiunit.eastwind.mdm;

import static junit.framework.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.cm.uiunit.elements.cordys.INotification;
import com.cordys.cm.uiunit.elements.cordys.ITree;
import com.cordys.cm.uiunit.elements.cordys.ITreeItem;
import com.cordys.cm.uiunit.elements.html.IDropDown;
import com.cordys.cm.uiunit.elements.html.IFrame;
import com.cordys.cm.uiunit.elements.html.ITextInput;
import com.cordys.cm.uiunit.framework.IKeyboard;
import com.cordys.cm.uiunit.framework.IMouse;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.datatransformation.cwsutilities.IDatamap;
import com.cordys.datatransformation.cwsutilities.IDatamapEditor;
import com.cordys.dbschema.cwsutilities.IUIDBSchema;
import com.cordys.dbschema.cwsutilities.IUIDBSchemaEditor;
import com.cordys.mdm.cusputilities.MDMService;
import com.cordys.mdm.cwsutilities.datastore.IMDMHubDataStore;
import com.cordys.mdm.cwsutilities.datastore.IMDMHubDataStoreEditor;
import com.cordys.mdm.cwsutilities.datastore.IMDMSpokeDataStore;
import com.cordys.mdm.cwsutilities.datastore.IMDMSpokeDataStoreEditor;
import com.cordys.mdm.cwsutilities.designer.IShapeAutomaticConnectorProperties;
import com.cordys.mdm.cwsutilities.designer.IShapeHubEntity;
import com.cordys.mdm.cwsutilities.designer.IShapeMDMAutomaticConnector;
import com.cordys.mdm.cwsutilities.designer.IShapeSpokeEntity;
import com.cordys.mdm.cwsutilities.entity.IMDMHubEntity;
import com.cordys.mdm.cwsutilities.entity.IMDMHubEntityEditor;
import com.cordys.mdm.cwsutilities.entity.IMDMSpokeEntity;
import com.cordys.mdm.cwsutilities.entity.IMDMSpokeEntityEditor;
import com.cordys.mdm.cwsutilities.model.IMDMModel;
import com.cordys.mdm.cwsutilities.model.IMDMModelDesigner;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;
import com.cordys.webservice.cwsutilities.DBSchemaWebServiceInterfaceProperties;
import com.cordys.webservice.cwsutilities.IUIDBSchemaWebServiceInterfaceCreatorWizard;

public class MDMModel extends WorkSpaceOrganizer{
	
	
	
		
	@UIUnitTimeout(120000000)
	@Test
	public void createMDMhubDataStoreEntity()
	{
		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
		IDropDown publisherProcessor = null;
		IMDMHubDataStore hubDatastore = folder.addDocument(IMDMHubDataStore.class);
		IMDMHubDataStoreEditor hubDatastoreEditor = hubDatastore.openDesigner();
		publisherProcessor = hubDatastoreEditor.findElement(IDropDown.class,"selPublisherServiceGroup");
		publisherProcessor.selectByText(MDMConstants.MDM_HUB_PUBLISHER);
	    this.getContext().waitForIdle();
		hubDatastoreEditor.save(MDMConstants.MDM_HUB_DATASTORE, MDMConstants.MDM_HUB_DATASTORE);
		assertEquals(MDMConstants.MDM_HUB_DATASTORE,hubDatastore.getName());
		hubDatastoreEditor.close();		
			
		
		//Data Entity Creation
		IMDMHubDataStore hubDataStore = folder.getExistingChildDocument(IMDMHubDataStore.class, MDMConstants.MDM_HUB_DATASTORE);
		IMDMHubEntity hubEntity = hubDataStore.addHubEntityDocument();
		getProject().getCWSIDE().getWorkspaceDocumentsView().focus();
		IMDMHubEntityEditor hubEntityEditor = hubEntity.openDesigner();
		hubEntityEditor.maximize();
		this.getContext().waitForIdle();
		hubEntityEditor.selectGeneraltab(MDMConstants.MDM_HUB_BUS_OBJECT_PATH);
		hubEntityEditor.selectKeyFields(MDMConstants.MDM_AUDIT_KEYFIELD,MDMConstants.MDM_AUDIT_KEYFIELD_TYPE);
		hubEntityEditor.focus();
		hubEntityEditor.selectReadWebService(MDMConstants.MDM_HUB_READ_WS_PATH);
		hubEntityEditor.selectUpdateWebService(MDMConstants.MDM_HUB_UPDATE_WS_PATH);
		hubEntityEditor.selectAdvanced();
		hubEntityEditor.setAuditTableName(MDMConstants.MDM_HUB_AUDITTABLE);
		hubEntityEditor.save(MDMConstants.MDM_HUB_ENTITY,MDMConstants.MDM_HUB_ENTITY);
		hubEntityEditor.close();
		this.getContext().waitForIdle();
	}
	@UIUnitTimeout(120000000)
	@Test
	public void createMDMSpoke1DataStoreEntity()
	{
		
	 /****************************************************************************************************
							SPOKE1 DataEntity Creation
	 * 
	 *****************************************************************************************************/
		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
		IDropDown publisherProcessor = null;
		IMDMSpokeDataStore spokeDataStore = null;
		IMDMSpokeEntity spokeEntity = null;
		IMDMSpokeEntityEditor spokeEntityEditor = null;
		ITextInput entityName = null;
		ITextInput entityDesc = null;
		spokeDataStore = folder.addDocument(IMDMSpokeDataStore.class);
		IMDMSpokeDataStoreEditor spokeDataStoreEditor = spokeDataStore.openDesigner();
		publisherProcessor = spokeDataStoreEditor.findElement(IDropDown.class,"selPublisherServiceGroup");
		publisherProcessor.selectByText(MDMConstants.MDM_SPOKE1_PUBLISHER);
		spokeDataStoreEditor.save(MDMConstants.MDM_SPOKE1_DATASTORE, MDMConstants.MDM_SPOKE1_DATASTORE);
		spokeDataStoreEditor.close();	
		spokeDataStore = folder.getExistingChildDocument(IMDMSpokeDataStore.class, MDMConstants.MDM_SPOKE1_DATASTORE);
		spokeEntity = spokeDataStore.addSpokeEntityDocument();
		spokeEntityEditor = spokeEntity.openDesigner();
		spokeEntityEditor.maximize();
		this.getContext().waitForIdle();
		spokeEntityEditor.selectGeneral(MDMConstants.MDM_SPOKE1_BUS_OBJECT_PATH);
		spokeEntityEditor.selectEntityType(2);
		entityName = spokeEntityEditor.findElement(ITextInput.class,"inpName");
		entityDesc = spokeEntityEditor.findElement(ITextInput.class,"txtDescription");
		entityName.click();
		entityName.setValue(MDMConstants.MDM_SPOKE1_ENTITY);
		entityDesc.click();
		entityDesc.setValue(MDMConstants.MDM_SPOKE1_ENTITY);		
		spokeEntityEditor.selectKeyFields(MDMConstants.MDM_AUDIT_KEYFIELD,MDMConstants.MDM_AUDIT_KEYFIELD_TYPE);			
		spokeEntityEditor.selectSnifferObject("Trigger");
		spokeEntityEditor.selectAdvanced();
		spokeEntityEditor.setAuditTableName(MDMConstants.MDM_SPOKE1_AUDITTABLE); 
		spokeEntityEditor.selectReadWebService(MDMConstants.MDM_SPOKE1_READ_WS_PATH);
		spokeEntityEditor.selectUpdateWebService(MDMConstants.MDM_SPOKE1_UPDATE_WS_PATH);
		spokeEntityEditor.save();
		spokeEntityEditor.close();
		this.getContext().waitForIdle();
		
	}
	@UIUnitTimeout(120000000)
	@Test
	public void createMDMSpoke2DataStoreEntity()
	{
		/****************************************************************************************************
		SPOKE2 DataEntity Creation
	 * 
	 *****************************************************************************************************/
		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
		IDropDown publisherProcessor = null;
		IMDMSpokeDataStore spokeDataStore = null;
		IMDMSpokeEntity spokeEntity = null;
		IMDMSpokeEntityEditor spokeEntityEditor = null;
		ITextInput entityName = null;
		ITextInput entityDesc = null;
		spokeDataStore = folder.addDocument(IMDMSpokeDataStore.class);
		IMDMSpokeDataStoreEditor spokeDataStoreEditor = spokeDataStore.openDesigner();
		spokeDataStoreEditor = spokeDataStore.openDesigner();
		publisherProcessor = spokeDataStoreEditor.findElement(IDropDown.class,"selPublisherServiceGroup");
		publisherProcessor.selectByText(MDMConstants.MDM_SPOKE2_PUBLISHER);
		spokeDataStoreEditor.save(MDMConstants.MDM_SPOKE2_DATASTORE, MDMConstants.MDM_SPOKE2_DATASTORE);
		spokeDataStoreEditor.close();
		spokeDataStore = folder.getExistingChildDocument(IMDMSpokeDataStore.class, MDMConstants.MDM_SPOKE2_DATASTORE);
		spokeEntity = spokeDataStore.addSpokeEntityDocument();
		spokeEntityEditor = spokeEntity.openDesigner();
		spokeEntityEditor.maximize();
		this.getContext().waitForIdle();
		spokeEntityEditor.selectGeneral(MDMConstants.MDM_SPOKE2_BUS_OBJECT_PATH);
		spokeEntityEditor.selectEntityType(2);
		entityName = spokeEntityEditor.findElement(ITextInput.class,"inpName");
		entityDesc = spokeEntityEditor.findElement(ITextInput.class,"txtDescription");
		entityName.click();
		entityName.setValue(MDMConstants.MDM_SPOKE2_ENTITY);
		entityDesc.click();
		entityDesc.setValue(MDMConstants.MDM_SPOKE2_ENTITY);
		spokeEntityEditor.selectKeyFields(MDMConstants.MDM_AUDIT_KEYFIELD,MDMConstants.MDM_AUDIT_KEYFIELD_TYPE);			
		spokeEntityEditor.selectSnifferObject("Trigger");
		spokeEntityEditor.selectAdvanced();
		spokeEntityEditor.setAuditTableName(MDMConstants.MDM_SPOKE2_AUDITTABLE); 
		spokeEntityEditor.selectReadWebService(MDMConstants.MDM_SPOKE2_READ_WS_PATH);
		spokeEntityEditor.selectUpdateWebService(MDMConstants.MDM_SPOKE2_UPDATE_WS_PATH);
		spokeEntityEditor.save();
		spokeEntityEditor.close();
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createDataTransformation()
	{
		ITree sourcetree = null;
		ITree targettree = null;
		String sourcePrefix = null;
		String targetPrefix = null;
		IDatamapEditor datmapEditor = null;
		IFrame modelerFrame = null;
		IDatamap salesDataTrans = null;
		IUICWSFolder mdm = getProject().getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
		/*
		//HUB TO SPOKE1 Transformation
		salesDataTrans=(IDatamap)mdm.addDocument(IDatamap.class);
		datmapEditor = salesDataTrans.openEditor();
		salesDataTrans.openEditor();
		datmapEditor.save(MDMExtnConstants.MDM_DT_HUB_TO_SPOKE1,MDMExtnConstants.MDM_DT_HUB_TO_SPOKE1);
		datmapEditor.zoomSourceSchemaFragment(MDMExtnConstants.MDM_HUB_BUS_OBJECT_PATH,true);
		datmapEditor.zoomSourceSchemaFragment(MDMExtnConstants.MDM_SPOKE1_BUS_OBJECT_PATH,false);
		modelerFrame =datmapEditor.findElement(IFrame.class, "modelerFrame");
		sourcetree = modelerFrame.findElement(ITree.class, "sourcetree_elem");
		targettree = modelerFrame.findElement(ITree.class, "targettree_elem");
		sourcetree.getRoot().expandAll();
		targettree.getRoot().expandAll();	
		sourcePrefix = "hub";
		targetPrefix = "nor";	
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductID",targetPrefix+":ProductID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductName",targetPrefix+":ProductName",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":SupplierID",targetPrefix+":SupplierID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":CategoryID",targetPrefix+":CategoryID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":QuantityPerUnit",targetPrefix+":QuantityPerUnit",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitPrice",targetPrefix+":UnitPrice",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsInStock",targetPrefix+":UnitsInStock",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsOnOrder",targetPrefix+":UnitsOnOrder",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ReorderLevel",targetPrefix+":ReorderLevel",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":Discontinued",targetPrefix+":Discontinued",sourcetree,targettree);
		datmapEditor.save();
		*/
		//SPOKE1 to HUB Transformation
		
		salesDataTrans=(IDatamap)mdm.addDocument(IDatamap.class);
		datmapEditor = salesDataTrans.openEditor();
		salesDataTrans.openEditor();
		datmapEditor.save(MDMConstants.MDM_DT_SPOKE1_TO_HUB,MDMConstants.MDM_DT_SPOKE1_TO_HUB);
		datmapEditor.zoomSourceSchemaFragment(MDMConstants.MDM_SPOKE1_BUS_OBJECT_PATH,true);
		datmapEditor.zoomSourceSchemaFragment(MDMConstants.MDM_HUB_BUS_OBJECT_PATH,false);
		modelerFrame =datmapEditor.findElement(IFrame.class, "modelerFrame");
		sourcetree = modelerFrame.findElement(ITree.class, "sourcetree_elem");
		targettree = modelerFrame.findElement(ITree.class, "targettree_elem");
		sourcetree.getRoot().expandAll();
		targettree.getRoot().expandAll();	
		sourcePrefix = "eas";
		targetPrefix = "nor";	
		
		
		ITreeItem souOrderId=  sourcetree.findItem(sourcePrefix+":ProductID");
	   	IMouse mouse= this.getContext().getRootContext().getTestEnvironment().getMouse();
	   	IKeyboard kbd=this.getContext().getRootContext().getTestEnvironment().getKeyboard();
	   	souOrderId.moveMouseToThis();
	   	mouse.click();
	   
	   	ITreeItem tarOrderId =  targettree.findItem(targetPrefix+":ProductID");
	   	kbd.controlKeyDown();
	   	tarOrderId.moveMouseToThis();
	   	mouse.click();
		   
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductID",targetPrefix+":ProductID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductName",targetPrefix+":ProductName",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":SupplierID",targetPrefix+":SupplierID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":CategoryID",targetPrefix+":CategoryID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":QuantityPerUnit",targetPrefix+":QuantityPerUnit",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitPrice",targetPrefix+":UnitPrice",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsInStock",targetPrefix+":UnitsInStock",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsOnOrder",targetPrefix+":UnitsOnOrder",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ReorderLevel",targetPrefix+":ReorderLevel",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":Discontinued",targetPrefix+":Discontinued",sourcetree,targettree);

		kbd.controlKeyUp();
		
		datmapEditor.save();
		/*
		//HUB TO SPOKE2 Transformation
		salesDataTrans=(IDatamap)mdm.addDocument(IDatamap.class);
		datmapEditor = salesDataTrans.openEditor();
		salesDataTrans.openEditor();
		datmapEditor.save(MDMExtnConstants.MDM_DT_HUB_TO_SPOKE2,MDMExtnConstants.MDM_DT_HUB_TO_SPOKE2);
		datmapEditor.zoomSourceSchemaFragment(MDMExtnConstants.MDM_HUB_BUS_OBJECT_PATH,true);
		datmapEditor.zoomSourceSchemaFragment(MDMExtnConstants.MDM_SPOKE2_BUS_OBJECT_PATH,false);
		modelerFrame =datmapEditor.findElement(IFrame.class, "modelerFrame");
		sourcetree = modelerFrame.findElement(ITree.class, "sourcetree_elem");
		targettree = modelerFrame.findElement(ITree.class, "targettree_elem");
		sourcetree.getRoot().expandAll();
		targettree.getRoot().expandAll();	
		sourcePrefix = "hub";
		targetPrefix = "eas";	
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductID",targetPrefix+":ProductID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductName",targetPrefix+":ProductName",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":SupplierID",targetPrefix+":SupplierID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":CategoryID",targetPrefix+":CategoryID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":QuantityPerUnit",targetPrefix+":QuantityPerUnit",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitPrice",targetPrefix+":UnitPrice",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsInStock",targetPrefix+":UnitsInStock",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsOnOrder",targetPrefix+":UnitsOnOrder",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ReorderLevel",targetPrefix+":ReorderLevel",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":Discontinued",targetPrefix+":Discontinued",sourcetree,targettree);
		datmapEditor.save();
		*/
		//SPOKE2 TO HUB Transformation
		salesDataTrans=(IDatamap)mdm.addDocument(IDatamap.class);
		datmapEditor = salesDataTrans.openEditor();
		salesDataTrans.openEditor();
		datmapEditor.save(MDMConstants.MDM_DT_SPOKE2_TO_HUB,MDMConstants.MDM_DT_SPOKE2_TO_HUB);
		datmapEditor.zoomSourceSchemaFragment(MDMConstants.MDM_SPOKE2_BUS_OBJECT_PATH,true);
		datmapEditor.zoomSourceSchemaFragment(MDMConstants.MDM_HUB_BUS_OBJECT_PATH,false);
		modelerFrame =datmapEditor.findElement(IFrame.class, "modelerFrame");
		sourcetree = modelerFrame.findElement(ITree.class, "sourcetree_elem");
		targettree = modelerFrame.findElement(ITree.class, "targettree_elem");
		sourcetree.getRoot().expandAll();
		targettree.getRoot().expandAll();	
		sourcePrefix = "eas";
		targetPrefix = "nor";	
		souOrderId=  sourcetree.findItem(sourcePrefix+":ProductID");
		mouse= this.getContext().getRootContext().getTestEnvironment().getMouse();
		kbd=this.getContext().getRootContext().getTestEnvironment().getKeyboard();
		souOrderId.moveMouseToThis();
		mouse.click();
	   
	    tarOrderId =  targettree.findItem(targetPrefix+":ProductID");
	    kbd.controlKeyDown();
	    tarOrderId.moveMouseToThis();
	    mouse.click();
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductID",targetPrefix+":ProductID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ProductName",targetPrefix+":ProductName",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":SupplierID",targetPrefix+":SupplierID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":CategoryID",targetPrefix+":CategoryID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":QuantityPerUnit",targetPrefix+":QuantityPerUnit",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitPrice",targetPrefix+":UnitPrice",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsInStock",targetPrefix+":UnitsInStock",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":UnitsOnOrder",targetPrefix+":UnitsOnOrder",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":ReorderLevel",targetPrefix+":ReorderLevel",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode(sourcePrefix+":Discontinued",targetPrefix+":Discontinued",sourcetree,targettree);
		
		kbd.controlKeyUp();
		
		datmapEditor.save();
	}
	@UIUnitTimeout(120000000)
	@Test
	public void createMDMModel()
	{
		IUIProject project = getProject();
		IUICWSFolder mdm = project.getExistingChildDocument(IUICWSFolder.class, MDMConstants.MDM_FOLDER_MDM);
		IMDMModel model = mdm.addDocument(IMDMModel.class);
		IMDMModelDesigner designer = model.getDesigner();
		designer.Initialization();
		designer.focus();	
		project = designer.getProjectFromWorkspaceDockedView(getDefaultSolutionName(), getDefaultProjectName());
		mdm = project.getExistingChildDocument(IUICWSFolder.class, MDMConstants.MDM_FOLDER_MDM);
		IMDMHubDataStore hubDataStore = mdm.getExistingChildDocument(IMDMHubDataStore.class,MDMConstants.MDM_HUB_DATASTORE);
		IMDMHubEntity hubEntityDoc = hubDataStore.getExistingChildDocument(IMDMHubEntity.class, MDMConstants.MDM_HUB_ENTITY);
		IShapeHubEntity hubEntity = designer.addHubEntity(hubEntityDoc, 500, 100);
		
		IMDMSpokeDataStore spokeDataStore = mdm.getExistingChildDocument(IMDMSpokeDataStore.class,MDMConstants.MDM_SPOKE1_DATASTORE);
		IMDMSpokeEntity spokeEntityDoc = spokeDataStore.getExistingChildDocument(IMDMSpokeEntity.class, MDMConstants.MDM_SPOKE1_ENTITY);
		IShapeSpokeEntity spokeEntity = designer.addSpokeEntity(spokeEntityDoc, 700, 500);
		spokeDataStore = mdm.getExistingChildDocument(IMDMSpokeDataStore.class,MDMConstants.MDM_SPOKE2_DATASTORE);
		spokeEntityDoc = spokeDataStore.getExistingChildDocument(IMDMSpokeEntity.class, MDMConstants.MDM_SPOKE2_ENTITY);		
		IShapeSpokeEntity spokeEntity2 = designer.addSpokeEntity(spokeEntityDoc, 300, 500);
		IShapeMDMAutomaticConnector connector = null;
		connector = designer.makeConnectorTo(spokeEntity,hubEntity);	
		designer.save(MDMConstants.MDM_MODEL,MDMConstants.MDM_MODEL);

		//connector.moveMouseToThis();
		Point p = new Point(1,2);
		//connector.doDoubleClickWithMouse(p);		
		//IShapeAutomaticConnectorProperties property = connector.getProperties();		
		IShapeAutomaticConnectorProperties property = connector.openProperties();
		property.setSpoke2HubProperties(MDMConstants.MDM_DT_SPOKE1_TO_HUB,null,null,false,3);
		property.close();
		designer.save();
		connector = designer.makeConnectorTo(spokeEntity2,hubEntity);
		p=new Point(100,160);
		connector.doDoubleClickWithMouse(p);
		//property = connector.getProperties();
		property = connector.openProperties();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		property.setSpoke2HubProperties(MDMConstants.MDM_DT_SPOKE2_TO_HUB,null,null,false,3);
		property.close();
		designer.save();
		designer.close();
	}
	
}
