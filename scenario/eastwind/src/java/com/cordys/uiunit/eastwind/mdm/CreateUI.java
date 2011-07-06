package com.cordys.uiunit.eastwind.mdm;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.ICordysContextMenu;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.elements.html.internal.Div;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceInterface;
import com.cordys.webservice.cwsutilities.IUIWebServiceOperation;
import com.cordys.xform.cwsutilities.IXForm;
import com.cordys.xform.cwsutilities.IXFormEditor;
import com.cordys.xform.cwsutilities.ModelAssociation;
import com.cordys.xform.cwsutilities.internal.GenerateUIFromWebServiceProperties;
import com.cordys.xform.cwsutilities.internal.ToolBox;

public class CreateUI extends WorkSpaceOrganizer{
	@Test
	@UIUnitTimeout(1200000)
	public void insertProductUI()
	{
		IUIProject project = getProject();	  	 
	    IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
	    IXForm xform = folder.addDocument(IXForm.class);
	    this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IXFormEditor xformDesigner = xform.openEditor();
		String scriptText = "function navInsert1_AfterInsert(eventObject)"+
		"\\n{"+
		"\\nvar productCount = cordys.getNodeText(Products1Model.getData(),\\\".//tns:ProductsCount\\\");"+
		"\\nproductid.setValue(parseInt(productCount) + 1);"+
		"\\n};"+
		
		"\\nfunction button1_Click(eventObject)"+
		"\\n{"+
		    "\\nvar productModel = ProductsModel.getMethodRequest();"+
		    "\\nvar productId = cordys.getElementById(productModel,\\\"ProductID\\\");"+
		    "\\nvar productCount = cordys.getNodeText(Products1Model.getData(),\\\".//tns:ProductsCount\\\");"+
		    "\\ncordys.setNodeText(productModel,\\\".//tns:fromProductID\\\",parseInt(productCount),\\\"\\\");"+   
		    "\\ncordys.setNodeText(productModel,\\\".//tns:toProductID\\\",parseInt(productCount),\\\"\\\");"+
		    "\\nProductsModel.reset();"+    
		"\\n}";
		xformDesigner.insertScript(scriptText);
		String button = xformDesigner.dragControl(ToolBox.BUTTON);
		Div editorDiv = xformDesigner.findElement(Div.class, "content");
		int contentDivOffsetLeft = editorDiv.getElementOffsetWidth();
		int contentDivOffsetHeight = editorDiv.getElementOffsetHeight();
		int contentOffsetLeft = editorDiv.getElementOffsetLeftRelativeToCordysRoot();
		int contentOffsetHeight = editorDiv.getElementOffsetTopRelativeToCordysRoot();
		IUIProject eastwindproject = xformDesigner.getProjectFromWorkspaceDockedView("Sales", "EastWind");		
		IUICWSFolder mdmFolder = eastwindproject.getExistingChildDocument(IUICWSFolder.class, "mdm");
		IUICWSFolder dbSchemaFolder = mdmFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		IUIWebServiceDefinitionSet webServiceDefinitionSet = dbSchemaFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, MDMConstants.MDM_WS_DEFINITIONSET_SPOKE1);
		IUIWebServiceInterface webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, MDMConstants.MDM_WS_INTERFACE_SPOKE1);
		IUIWebServiceOperation webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetProductsObjects");
		GenerateUIFromWebServiceProperties generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(false);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(true);
		//Drag and drop GetOrdersObject
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		this.getContext().waitForIdle();
		xformDesigner.focus();		
		Div ProductsTable = xformDesigner.findElement(Div.class, "table1");		
		IHTMLElement tableHead = ProductsTable.findElement("xo__xTable__1_elementBar");
		tableHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Change To/Groupbox");
		tableHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Set Default Size");
		dbSchemaFolder = mdmFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		webServiceDefinitionSet = dbSchemaFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, MDMConstants.MDM_WS_DEFINITIONSET_SPOKE1);
		webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, MDMConstants.MDM_WS_INTERFACE_SPOKE1);	
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, MDMConstants.MDM_WS_OPERATION_PRODUCTS_COUNT);
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(false);
		generateUIProperties.setBOutputUI(false);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.setSelectRequiredModelChoice(ModelAssociation.CREATE_NEW_MODEL);
		//Drag and drop GetOrdersObject
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		this.getContext().waitForIdle();
		xformDesigner.showOnCordysDesktop();		
		xformDesigner.setLabel(button, "Get Last Product");
		xformDesigner.setEventForControl(button, "selectActivate");
		xformDesigner.setEventForControl("navInsert1", "selectAfter");
		xformDesigner.save("ManageProduct", "ManageProduct");		
		xformDesigner.close();		
	}
}
