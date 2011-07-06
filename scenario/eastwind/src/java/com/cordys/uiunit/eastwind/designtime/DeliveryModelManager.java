package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.notification.cwsutilities.IWorkFlowDeliveryModelEditor;
import com.cordys.xform.cwsutilities.IXForm;

@UIUnitTimeout(12000000)
public class DeliveryModelManager extends WorkSpaceOrganizer{
	@Test
	@UIUnitTimeout(120000)
	public void createOrderRegistrationModel(){
		IUIProject project = getProject();
		IUICWSFolder xformsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm orederRegistrationXForm = xformsFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_ORDERREGISTRATION);
		IWorkFlowDeliveryModelEditor deliveryModelEditor = orederRegistrationXForm.createDeliveryModel();
		deliveryModelEditor.addOperation("freeformControls", false);
		deliveryModelEditor.addOperation("GetOrdersObject", true);
		deliveryModelEditor.addOperation("GetCategoriesObjects", true);
		deliveryModelEditor.addOperation("GetProductsObjectsForCategoryID", true);
		deliveryModelEditor.addOperation("GetOrder_x0020_DetailsObjectsForOrderID", true);
		deliveryModelEditor.save(EastWindArtifacts.DELIVERYMODEL_ORDERREGISTRATION, EastWindArtifacts.DELIVERYMODEL_ORDERREGISTRATION);
		deliveryModelEditor.close();
	}
	
	@Test
	@UIUnitTimeout(120000)
	public void createAssignSalesRepModel(){
		IUIProject project = getProject();
		IUICWSFolder xformsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm orederRegistrationXForm = xformsFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_ASSIGNSALESREP);
		IWorkFlowDeliveryModelEditor deliveryModelEditor = orederRegistrationXForm.createDeliveryModel();
		deliveryModelEditor.addOperation("freeformControls", false);
		deliveryModelEditor.addOperation("GetOrdersObject", true);
		deliveryModelEditor.addOperation("GetOrder_x0020_DetailsObjectsForOrderID", true);
		deliveryModelEditor.addOperation("GetEmployeesObjects", true);
		//deliveryModelEditor.addOperation("GetProductsObjects", true);
		deliveryModelEditor.save(EastWindArtifacts.DELIVERYMODEL_ASSIGNSALESREP, EastWindArtifacts.DELIVERYMODEL_ASSIGNSALESREP);
		deliveryModelEditor.close();
	}
	
	@Test
	@UIUnitTimeout(120000)
	public void createSalesOrdApprovalModel(){
		IUIProject project = getProject();
		IUICWSFolder xformsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm orederRegistrationXForm = xformsFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_SALESORDERAPPROVAL);
		IWorkFlowDeliveryModelEditor deliveryModelEditor = orederRegistrationXForm.createDeliveryModel();
		deliveryModelEditor.addOperation("freeformControls", false);
		deliveryModelEditor.addOperation("GetEmployeesObjects", true);
		//deliveryModelEditor.addOperation("GetProductsObjects", true);
		deliveryModelEditor.addOperation("GetOrdersObject", true);
		deliveryModelEditor.addOperation("GetOrder_x0020_DetailsObjectsForOrderID", true);
		deliveryModelEditor.addOperation("GetOrdersObjectsForCustomerID", true);
		deliveryModelEditor.save(EastWindArtifacts.DELIVERYMODEL_SALESORDERAPPROVAL, EastWindArtifacts.DELIVERYMODEL_SALESORDERAPPROVAL);
		deliveryModelEditor.close();
	}
	
	
	@Test
	@UIUnitTimeout(120000)
	public void createSalesRepOrderCompleteModel(){
		IUIProject project = getProject();
		IUICWSFolder xformsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm orederRegistrationXForm = xformsFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_SALESORDERCOMPLETE);
		IWorkFlowDeliveryModelEditor deliveryModelEditor = orederRegistrationXForm.createDeliveryModel();
		deliveryModelEditor.addOperation("freeformControls", false);
		deliveryModelEditor.addOperation("GetOrdersObject", true);
		//deliveryModelEditor.addOperation("GetProductsObjects", true);
		deliveryModelEditor.addOperation("GetOrder_x0020_DetailsObjectsForOrderID", true);
		deliveryModelEditor.addOperation("DiscountDecision_WebserviceOperation", true);		
		deliveryModelEditor.save(EastWindArtifacts.DELIVERYMODEL_SALESORDERCOMPLETE, EastWindArtifacts.DELIVERYMODEL_SALESORDERCOMPLETE);
		deliveryModelEditor.close();
	}
	
	
	@Test
	@UIUnitTimeout(120000)
	public void createNotifyApplicationServiceModel(){
		IUIProject project = getProject();
		IUICWSFolder xformsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm orederRegistrationXForm = xformsFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE);
		IWorkFlowDeliveryModelEditor deliveryModelEditor = orederRegistrationXForm.createDeliveryModel();
		deliveryModelEditor.addOperation("freeformControls", false);
		deliveryModelEditor.save(EastWindArtifacts.DELIVERYMODEL_NOTIFYAPPLICATIONSERVICE, EastWindArtifacts.DELIVERYMODEL_NOTIFYAPPLICATIONSERVICE);
		deliveryModelEditor.close();
	}
	
}
