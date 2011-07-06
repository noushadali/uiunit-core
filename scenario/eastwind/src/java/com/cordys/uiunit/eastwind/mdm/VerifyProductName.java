package com.cordys.uiunit.eastwind.mdm;

import org.junit.Assert;
import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.ApplicationFactory;
import com.cordys.cm.uiunit.elements.cordys.IApplication;
import com.cordys.cm.uiunit.elements.html.IButton;
import com.cordys.cm.uiunit.elements.html.IFrame;
import com.cordys.cm.uiunit.elements.html.ITextInput;
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
import com.cordys.xform.cwsutilities.internal.GenerateUIFromWebServiceProperties;

public class VerifyProductName extends WorkSpaceOrganizer{

	@Test
	@UIUnitTimeout(1200000)
	public void createUI()
	{
		IUIProject project = getProject();	  	 
	    IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);
	    IXForm xform = folder.addDocument(IXForm.class);
	    this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IXFormEditor xformDesigner = xform.openEditor();
		Div editorDiv = xformDesigner.findElement(Div.class, "content");
		int contentDivOffsetLeft = editorDiv.getElementOffsetWidth();
		int contentDivOffsetHeight = editorDiv.getElementOffsetHeight();
		int contentOffsetLeft = editorDiv.getElementOffsetLeftRelativeToCordysRoot();
		int contentOffsetHeight = editorDiv.getElementOffsetTopRelativeToCordysRoot();
		IUIProject eastwindproject = xformDesigner.getProjectFromWorkspaceDockedView("Sales", "EastWind");		
		IUICWSFolder dbSchemaFolder = eastwindproject.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		IUICWSFolder packageFolder = dbSchemaFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND);
		IUICWSFolder northwind = packageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
		
		IUIWebServiceDefinitionSet webServiceDefinitionSet = northwind.getExistingChildDocument(IUIWebServiceDefinitionSet.class, MDMConstants.MDM_WS_DEFINITIONSET_HUB);
		IUIWebServiceInterface webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, MDMConstants.MDM_WS_INTERFACE_HUB);
		IUIWebServiceOperation webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetProductsObject");
		GenerateUIFromWebServiceProperties generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(true);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(true);
		//Drag and drop GetOrdersObject
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		this.getContext().waitForIdle();
		xformDesigner.save("GetProductsObject", "GetProductsObject");
		this.getContext().waitForIdle();
		xformDesigner.showPreview();
		IApplication preview = null;
		 long timeout = System.currentTimeMillis() + 90000;
		  while (preview == null && System.currentTimeMillis() < timeout) {
			  preview = ApplicationFactory.findFirstApplicationWhereIDStartsWith(this.getContext().getRootContext(), "_xform_preview");			  
		 }
		if (preview == null) {
			org.junit.Assert.fail("Preview of Xform has fialed");
		}
		IFrame previewFrame = preview.findElement(com.cordys.cm.uiunit.elements.html.IFrame.class,"previewFrame");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ITextInput productid = previewFrame.findElement(ITextInput.class, "productid");
		productid.setValue("77");
		previewFrame.findElement(IButton.class,"GetProductsObjectButton").click();
		
		Assert.assertEquals("Juice",previewFrame.findElement(ITextInput.class, "productname").getValue());		
		xformDesigner.close();	
	}
}
