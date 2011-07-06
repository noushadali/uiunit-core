/**
 * 
 */
package com.cordys.uiunit.eastwind.designtime;


import org.junit.Before;
import org.junit.Test;

import com.cordys.bpm.cwsutilities.IUIBPM;
import com.cordys.bpm.cwsutilities.IUIBPMDesigner;
import com.cordys.bpm.cwsutilities.IUIBPMDocumentCreatorWizard;
import com.cordys.bpm.cwsutilities.IUIMessageMapView;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMActivity;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMEndEvent;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMExceptionEvent;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMForEach;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMHumanTask;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMServiceTask;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMStartEvent;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMSubprocess;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMForEachProperties;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMHumanTaskProperties;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMStartEventProperties;
import com.cordys.cm.uiunit.elements.cordys.ITreeItem;
import com.cordys.cm.uiunit.framework.IKeyboard;
import com.cordys.cm.uiunit.framework.IMouse;
import com.cordys.cm.uiunit.framework.IMouse.Speed;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceInterface;
import com.cordys.webservice.cwsutilities.IUIWebServiceOperation;
import com.cordys.xform.cwsutilities.IXForm;

/**
 * @author j prasanna
 * @editor s praveen
 */
public class BPMOrderEntryProcess extends WorkSpaceOrganizer{
	
	IUIProject project;
	IUICWSFolder bpmFolder;
	IUICWSFolder roleFolder;
	IUICWSFolder wlFolder;
	IUICWSFolder dbNWPackageFolder;
	IUICWSFolder dbEWPackageFolder;
	IUICWSFolder dbFolder;
	IUICWSFolder dmFolder;
	IUICWSFolder nwFolder;
	IUICWSFolder ewFolder;
	IUICWSFolder wsDataMapFolder;
	IUICWSFolder xfFolder;
	IUICWSFolder schemaFolder;
	IUIBPM bpm;
	IUIBPMDesigner bpmEditor;
	IKeyboard keyboard;
	IUIBPMDocumentCreatorWizard docWizard;
	IMouse mouse;
	Speed oldSpeed;
	String designerId;
	
	IUIProject projectItem;

	@Before
	@UIUnitTimeout(1800000)
	public void initializeEnvironment(){
		project = getProject();
		bpmFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BPM);
		/*
		roleFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ROLES);
		wlFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS);
		//dbFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		dmFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DATAMAP);
		//nwFolder = dbFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
		//ewFolder = dbFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_PRODUCTIONDB);
		xfFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		schemaFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		wsDataMapFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP);
		*/
		keyboard = this.getContext().getRootContext().getTestEnvironment().getKeyboard();

		mouse = this.getContext().getRootContext().getTestEnvironment().getMouse();
		oldSpeed = mouse.getSpeed();
		bpm = bpmFolder.addDocument(IUIBPM.class);
	}
	
	@Test
	@UIUnitTimeout(1800000)
	public void createBPMOrderEntryProcess() throws InterruptedException {

		//bpmFolder.refresh();
		bpmEditor = bpm.getDesigner();
		
		IXForm humanTask = null;
		//Save the BPM
		bpmEditor.save(EastWindArtifacts.BPM_ORDERENTRYPROCESS,EastWindArtifacts.BPM_ORDERENTRYPROCESS);
		//create start event
		IUIShapeBPMStartEvent startEvent  = bpmEditor.addStartEvent(370, 50);
		//add end near Notification Task
		IUIShapeBPMEndEvent endEventNotification = bpmEditor.addEndEvent(150, 250);
		
		//create customer end
		IUIShapeBPMEndEvent endEventCustomer = bpmEditor.addEndEvent(150, 550);

		//Empty Activity
		IUIShapeBPMActivity emptyActivity = bpmEditor.addActivity("Empty Activity", 320, 250);

		//create loop for each
		IUIShapeBPMForEach forEach = bpmEditor.makeGroupAsForEach("For Each", emptyActivity);

		//add end event
		IUIShapeBPMEndEvent endEventSubprocess = bpmEditor.addEndEvent(750, 550); 
		
		bpmEditor.addVerticalLane("Customer", 80, 20);
		bpmEditor.addVerticalLane("Sales Department", 300, 20);

		//create sub process discount approval
		IUIShapeBPMSubprocess activityDA = bpmEditor.addSubProcess(700, 230);
		IUIShapeBPMSubprocess activityUP = bpmEditor.addSubProcess(700, 330);
		bpmEditor.addVerticalLane("Sales Representative", 680, 20); 
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		//Drag and Drops from Editors Workspace Explorer
		
		/*bpmEditor.getProjectFromWorkspaceDockedView(getDefaultSolutionName(), getDefaultProjectName());
		bpmEditor.findElement("tabPageLabel").click();*/
		
		projectItem = bpmEditor.getProjectFromWorkspaceDockedView(getDefaultSolutionName(), getDefaultProjectName());
		xfFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		wlFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS);
		dbFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		dbNWPackageFolder = dbFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND);
		dbEWPackageFolder = dbFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_PRODUCTION);
		nwFolder = dbNWPackageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
		ewFolder = dbEWPackageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_PRODUCTIONDB);
		bpmFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BPM);
		//create update orders
		IUIWebServiceDefinitionSet wsDefinitionSet = nwFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);
		
		IUIWebServiceInterface wsInterface = wsDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_NORTHWIND);
		IUIWebServiceOperation wsOperation = wsInterface.getExistingChildDocument(IUIWebServiceOperation.class, "UpdateOrders");
		IUIShapeBPMServiceTask updateOrders = bpmEditor.addServiceTask(wsOperation, null, 320, 130);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		IUIShapeBPMHumanTaskProperties humanTaskPS ;
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE);
		IUIShapeBPMHumanTask notificationTask = bpmEditor.addHumanTask(humanTask, "Notification Task", 100, 130);
		humanTaskPS = notificationTask.openProperties();
		humanTaskPS.setDescription("Notification Task");
		bpmEditor.save();
		humanTaskPS.close();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		//create Notify Customer with Order ID Details
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE);

		IUIShapeBPMHumanTask notifyCustomerOrderIDDetails = bpmEditor.addHumanTask(humanTask, "Notify Customer OrderID Details", 100, 300);
		humanTaskPS = notifyCustomerOrderIDDetails.openProperties();
		humanTaskPS.setDescription("Notify Customer OrderID Details");
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		humanTaskPS.close();
		//create Notify Customer with Discount Details
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE);

		IUIShapeBPMHumanTask notifyCustomerDiscountDetails = bpmEditor.addHumanTask(humanTask, "Notify Customer Discount Details", 100, 400);
		humanTaskPS = notifyCustomerDiscountDetails.openProperties();
		humanTaskPS.setDescription("Notify Customer Discount Details");
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		humanTaskPS.close();
		//create UpdateOrder_x0020_Details
		wsOperation = wsInterface.getExistingChildDocument(IUIWebServiceOperation.class, "UpdateOrder_x0020_Details");

		IUIShapeBPMServiceTask updateOrder_x0020_Details = bpmEditor.addServiceTask(wsOperation,null,320,380);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		//create SalesRep Assignment
		xfFolder.refresh();
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_ASSIGNSALESREP);
		humanTask.refresh();
		this.getContext().waitForIdle();
		IUIShapeBPMHumanTask salesRepAssignment  = bpmEditor.addHumanTask(humanTask, null, 320, 490);
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//create Notify Sales Coordinator with discount details
		xfFolder.refresh();
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE);
		humanTask.refresh();
		this.getContext().waitForIdle();
		IUIShapeBPMHumanTask notifySalesCoordinatorDiscountDetails = bpmEditor.addHumanTask(humanTask,"Notify Sales Coordinator with DiscountDetails", 320, 590);
		humanTaskPS = notifySalesCoordinatorDiscountDetails.openProperties();
		humanTaskPS.setDescription("Notify Sales Coordinator with DiscountDetails");
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		humanTaskPS.close();
		//create Send Task to Sales Representative
		xfFolder.refresh();
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_SALESORDERCOMPLETE);
		humanTask.refresh();
		this.getContext().waitForIdle();
		IUIShapeBPMHumanTask sendTaskToSalesRepresentative = bpmEditor.addHumanTask(humanTask,"Send Task To SalesRepresentative", 700, 130);
		humanTaskPS=sendTaskToSalesRepresentative.openProperties();
		humanTaskPS.setDescription("Send Task To SalesRepresentative");
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		humanTaskPS.close();
		IUIBPM subProcess = bpmFolder.getExistingChildDocument(IUIBPM.class, EastWindArtifacts.BPM_DISCOUNTAPPROVAL);
		//subProcess.refresh();
		this.getContext().waitForIdle();
		mouse.setSpeed(IMouse.Speed.VERYSLOW);
		subProcess.dragAndDropOnElement(activityDA);
		mouse.setSpeed(oldSpeed);
		//UIShapeBPMSubprocess subprocessDA = (UIShapeBPMSubprocess)bpmEditor.getLatestObject();

		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//create sub process update production db
		bpmFolder.refresh();
		
		subProcess = bpmFolder.getExistingChildDocument(IUIBPM.class, EastWindArtifacts.BPM_UPDATEPRODUCTION);
		//subProcess.refresh();
		this.getContext().waitForIdle();
		mouse.setSpeed(IMouse.Speed.VERYSLOW);
		subProcess.dragAndDropOnElement(activityUP);
		mouse.setSpeed(oldSpeed);
		//UIShapeBPMSubprocess subprocessUP = (UIShapeBPMSubprocess)bpmEditor.getLatestObject();
		
		//bpmEditor.getAppPalette("_toolbox").close();
		//make connectors
		startEvent.makeConnectorTo(updateOrders.getActivity());
		updateOrders.getActivity().makeConnectorTo(forEach);
		//add exception	
		bpmEditor.findElement("tabPageLabel").click();
		IUIShapeBPMExceptionEvent exceptionEvent = bpmEditor.addExceptionEvent(updateOrders.getActivity());
		project.getCWSIDE().getWorkspaceDocumentsView();
		exceptionEvent.makeConnectorTo(notificationTask.getActivity());
		forEach.makeConnectorTo(updateOrder_x0020_Details.getActivity());
		notificationTask.getActivity().makeConnectorTo(endEventNotification);
		updateOrder_x0020_Details.getActivity().makeConnectorTo(notifyCustomerOrderIDDetails.getActivity());
		notifyCustomerOrderIDDetails.getActivity().makeConnectorTo(salesRepAssignment.getActivity());
		salesRepAssignment.getActivity().makeConnectorTo(sendTaskToSalesRepresentative.getActivity());
		sendTaskToSalesRepresentative.getActivity().makeConnectorTo(activityDA);
		activityDA.makeConnectorTo(activityUP);
		activityUP.makeConnectorTo(endEventSubprocess);
		activityDA.makeConnectorTo(notifySalesCoordinatorDiscountDetails.getActivity());
		notifySalesCoordinatorDiscountDetails.getActivity().makeConnectorTo(notifyCustomerDiscountDetails.getActivity());
		notifyCustomerDiscountDetails.getActivity().makeConnectorTo(endEventCustomer);

		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
//      -------------------
//		BPM Design is done
//      -------------------		
		IUIMessageMapView mmView = bpmEditor.switchToMessageMap();
		mouse.setSpeed(IMouse.Speed.VERYSLOW);
		//IUISchemaFragment schemaFrag = null;
		mmView.createProcessSpecificMessage("OrderInformation");
		ITreeItem treeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInformation");
		mmView.pasteXML("<OrderInformation><Orders/><OrderDetails/></OrderInformation>", treeItem, "Element", bpmEditor);
		bpmEditor.save();		

		bpmEditor.switchToModel();
		//property sheet
		//Assigning message to start event
		IUIShapeBPMStartEventProperties startEventPS =  startEvent.openProperties();
		startEventPS.setInputMessage("OrderInformation");

		bpmEditor.save();
		startEventPS.close();
		
		String ns2 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/NorthwindDatabaseMetadata");
		String ns6 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/1.0/xforms/processapi");

		//foreach
		IUIShapeBPMForEachProperties forEachPS = forEach.openProperties();
		forEachPS.setSelectCondition(IUIShapeBPMForEachProperties.VALUE_STATIC, "bpm:OrderInformation/bpm:OrderInformation/bpm:OrderDetails/"+ns2+":Order_x0020_Details");
		bpmEditor.save();
		forEachPS.close();
	

		//Sales Rep Assignment
		humanTaskPS = salesRepAssignment.openProperties();
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_WORKLIST,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.WORKLISTS_SALESCOORDINATOR);
		bpmEditor.save();
		humanTaskPS.close();

		//Notify sales coordinator with discount details
		humanTaskPS = notifySalesCoordinatorDiscountDetails.openProperties();
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_WORKLIST,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.WORKLISTS_SALESCOORDINATOR);
		bpmEditor.save();
		humanTaskPS.setTaskType(IUIShapeBPMHumanTaskProperties.MESSAGE_TYPE_INFO);
		humanTaskPS.close();

		//Send task to sales representative
		humanTaskPS = sendTaskToSalesRepresentative.openProperties();
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_USER,IUIShapeBPMHumanTaskProperties.VALUE_MESSAGE,"bpm:EmployeeInfo/bpm:EmployeeInfo/bpm:SalesRepDN");
		bpmEditor.save();
		humanTaskPS.close();

		humanTaskPS = notificationTask.openProperties();
		bpmEditor.save();
		humanTaskPS.setTaskType(IUIShapeBPMHumanTaskProperties.MESSAGE_TYPE_INFO);
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_ROLE,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.ROLES_CUSTOMER);
		bpmEditor.save();
		humanTaskPS.close();

		humanTaskPS = notifyCustomerDiscountDetails.openProperties();
		humanTaskPS.setTaskType(IUIShapeBPMHumanTaskProperties.MESSAGE_TYPE_INFO);
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_ROLE,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.ROLES_CUSTOMER);
		bpmEditor.save();
		humanTaskPS.close();
		humanTaskPS = notifyCustomerOrderIDDetails.openProperties();
		humanTaskPS.setTaskType(IUIShapeBPMHumanTaskProperties.MESSAGE_TYPE_INFO);
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_ROLE,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.ROLES_CUSTOMER);
		bpmEditor.save();
		humanTaskPS.close();
		bpmEditor.save();
		//switch to message map
		String updateOrdersId = updateOrders.getActivity().getId();
		String subprocessDAId = activityDA.getId();
		String emptyId = emptyActivity.getId();
		String forEachId = forEach.getId();
		String updateOrderDetailsId = updateOrder_x0020_Details.getActivity().getId();
		String salesRepAssignmentId = salesRepAssignment.getActivity().getId();
		String notifyDDSCId = notifySalesCoordinatorDiscountDetails.getActivity().getId();
		String notifyCustomerOrderIDDetailsId = notifyCustomerOrderIDDetails.getActivity().getId();
		String saledRepId = sendTaskToSalesRepresentative.getActivity().getId();
		String subProcessUPId = activityUP.getId();
		String notifyCustomeDD = notifyCustomerDiscountDetails.getActivity().getId();
		String notificationTaskId = notificationTask.getActivity().getId();
		String updateOrdersName = updateOrders.getActivity().getName();
		String forEachName = forEach.getName();		
		String saledRepName = sendTaskToSalesRepresentative.getActivity().getName();
		String subProcessUPName = activityUP.getName();

		updateOrders.getActivity().click();
		mmView = bpmEditor.switchToMessageMap();



		mmView.createProcessSpecificMessage("OrderLinesInput");
		mmView.createProcessSpecificMessage("OrderMapInput");

//		ITreeItem dropItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:Ordertuple");
//		dropItem.click();

		mmView.createProcessSpecificMessage("Ordertuple");
		ITreeItem orderTupleTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:Ordertuple");
		mmView.pasteXML("<Ordertuple><tuple><new><Order_x0020_Details><OrderID/></Order_x0020_Details></new></tuple></Ordertuple>", orderTupleTreeItem, "Element", bpmEditor);

	
		mmView.createProcessSpecificMessage("EmployeeInfo");
		ITreeItem employeeInfoTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:EmployeeInfo");
		mmView.pasteXML("<EmployeeInfo><SalesRepDN/></EmployeeInfo>", employeeInfoTreeItem, "Element", bpmEditor);		
		
		mmView.createProcessSpecificMessage("OrderInfo");
		ITreeItem treeItemOrderInfo = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInfo");
		String stOrderInfo = "<OrderInfo><OrderID/><DisocuntDetails><TotalDiscount/></DisocuntDetails></OrderInfo>";
		mmView.pasteXML(stOrderInfo, treeItemOrderInfo, "Element", bpmEditor);
		
		mmView.createProcessSpecificMessage("dummy");
		bpmEditor.save();


		//message map
		ITreeItem sTreeItem = null;
		ITreeItem tTreeItem = null;

		//assignment update orders
		mmView.selectArtifactItem("UpdateOrders");
		mmView.switchToPreAssignment();
		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns2+":UpdateOrdersInput/"+ns2+":UpdateOrders/"+ns2+":tuple/"+ns2+":old");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(updateOrdersId, 1, "Delete", null, null, null);

		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInformation/bpm:OrderInformation/bpm:Orders");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns2+":UpdateOrdersInput/"+ns2+":UpdateOrders/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(updateOrdersId, 2, "Replace Content With", "Children with Target NS", null, null);

		bpmEditor.save();

		//assignment Empty Activity
		mmView.selectArtifactItem("Empty Activity");
		mmView.switchToPreAssignment();

		sTreeItem =  mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, forEachName, "instance:iteratorName_"+forEachId);
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:Ordertuple/bpm:Ordertuple/bpm:tuple/bpm:new/bpm:Order_x0020_Details");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyId, 1, "Replace Content With", "Children with Target NS", null, null);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, updateOrdersName, ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:Ordertuple/bpm:Ordertuple/bpm:tuple/bpm:new/bpm:Order_x0020_Details/bpm:OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:Ordertuple/bpm:Ordertuple/bpm:tuple");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderLinesInput");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyId, 3, "Add", "Select", null, null);

		bpmEditor.save();

		//assignment UpdateOrder_X0020_Details
		mmView.selectArtifactItem("UpdateOrder_x0020_Details");
		mmView.switchToPreAssignment();

		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET , updateOrderDetailsId, ns2+":UpdateOrder_x0020_DetailsInput/"+ns2+":UpdateOrder_x0020_Details/"+ns2+":tuple");
		mmView.createAssignment(sTreeItem , tTreeItem);
		mmView.selectOperationOnAssignment(updateOrderDetailsId, 1, "Delete", null, null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderLinesInput");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrderDetailsId, ns2+":UpdateOrder_x0020_DetailsInput/"+ns2+":UpdateOrder_x0020_Details");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(updateOrderDetailsId, 2, "Add", "Select Multiple with Target NS", "bpm:OrderLinesInput/bpm:tuple", null);
		bpmEditor.save();

		//Sales Rep Assignment
		mmView.selectArtifactItem(EastWindArtifacts.XFORM_ASSIGNSALESREP);
		mmView.switchToPreAssignment();

		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, salesRepAssignmentId,ns6+":AssignSalesRep_AssignSalesRepModel_IP/"+ns6+":forminputdata/"+ns6+":EmployeesModel/"+ns2+":GetEmployeesObjects/"+ns2+":fromEmployeeID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(salesRepAssignmentId, 1, "Replace Content With" ,"Fixed Value", "1", null);

		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, salesRepAssignmentId,ns6+":AssignSalesRep_AssignSalesRepModel_IP/"+ns6+":forminputdata/"+ns6+":EmployeesModel/"+ns2+":GetEmployeesObjects/"+ns2+":toEmployeeID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(salesRepAssignmentId, 2, "Replace Content With" ,"Fixed Value", "10", null);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, updateOrdersName, ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, salesRepAssignmentId, ns6+":AssignSalesRep_AssignSalesRepModel_IP/"+ns6+":forminputdata/"+ns6+":OrdersModel/"+ns2+":GetOrdersObject/"+ns2+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);

		bpmEditor.save();

		//post assignment
		mmView.switchToPostAssignment();
		tTreeItem  = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:EmployeeInfo/bpm:EmployeeInfo/bpm:SalesRepDN");
		mmView.createAssignmentForTargetLeafNode(tTreeItem);
		mmView.selectOperationOnAssignment(salesRepAssignmentId, 4, "Replace Content With", "Expression", "concat(\"cn=\","+ns6+":AssignSalesRep_AssignSalesRepModel_OP/"+ns6+":formoutputdata/"+ns6+":EmployeesModel/"+ns2+":GetEmployeesObjectsResponse/"+ns2+":tuple/"+ns2+":old/"+ns2+":Employees/"+ns2+":FirstName,\",cn=organizational users,\",instance:instanceProperties/instance:organization)", null);
		
		bpmEditor.save();

		//Notify Discount Details to sales coordinator
		mmView.selectArtifactItem("Notify Sales Coordinator with DiscountDetails");
		mmView.switchToPreAssignment();
		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInfo/bpm:OrderInfo/bpm:DisocuntDetails/bpm:TotalDiscount");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, notifyDDSCId, ns6+":NotifyApplicationService_NotifyApplicationServiceModel_IP/"+ns6+":forminputdata/"+ns6+":freeformcontrols/"+ns6+":taskHeader");
		mmView.createAssignment(sTreeItem, tTreeItem);

		bpmEditor.save();

		//assignment Send task to Sales Representative
		mmView.selectArtifactItem("Send Task To SalesRepresentative");
		mmView.switchToPreAssignment();
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, updateOrdersName, ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, saledRepId, ns6+":SalesRepOrderComplete_SalesRepOrderCompleteModel_IP/"+ns6+":forminputdata/"+ns6+":OrdersModel/"+ns2+":GetOrdersObject/"+ns2+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);

		bpmEditor.save();

		//Assignment Discount Approval
		mmView.selectArtifactItem("DiscountApproval");
		mmView.switchToPreAssignment();
		

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, updateOrdersName, ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderInfo/bpm:OrderInfo/bpm:OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		
		
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, saledRepName, ns6+":SalesRepOrderComplete_SalesRepOrderCompleteModel_OP/"+ns6+":formoutputdata/"+ns6+":Order_x0020_DetailsModel/"+ns2+":GetOrder_x0020_DetailsObjectsForOrderIDResponse/"+ns2+":tuple/"+ns2+":old/"+ns2+":Order_x0020_Details/"+ns2+":Discount");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderInfo/bpm:OrderInfo/bpm:DisocuntDetails/bpm:TotalDiscount");
		mmView.createAssignment(sTreeItem, tTreeItem);
		
		mmView.createAssignmentInConsolidatedView("PreAssignment",subprocessDAId, 2, "Replace Content With", "Expression", "(sum("+ns6+":SalesRepOrderComplete_SalesRepOrderCompleteModel_OP/"+ns6+":formoutputdata/"+ns6+":Order_x0020_DetailsModel/"+ns2+":GetOrder_x0020_DetailsObjectsForOrderIDResponse/"+ns2+":tuple/"+ns2+":old/"+ns2+":Order_x0020_Details/"+ns2+":Discount/text())) div(count(ns6:SalesRepOrderComplete_SalesRepOrderCompleteModel_OP/"+ns6+":formoutputdata/"+ns6+":Order_x0020_DetailsModel/"+ns2+":GetOrder_x0020_DetailsObjectsForOrderIDResponse/"+ns2+":tuple))", null);


		bpmEditor.save();

		//Assignment ProductionDBUpdate
		mmView.selectArtifactItem("ProductionDBUpdate");
		mmView.switchToPreAssignment();
		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInfo/bpm:OrderInfo/bpm:OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, subProcessUPName, ns2+":GetOrdersObjectInput/"+ns2+":GetOrdersObject/"+ns2+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.createAssignmentInConsolidatedView("PreAssignment",subProcessUPId, 1, "Replace Content With","Select with Target NS", null, null);

		bpmEditor.save();

		//Assignment NotificationTask
		mmView.selectArtifactItem("Notification Task");
		mmView.switchToPreAssignment();
		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, notificationTaskId,ns6+":NotifyApplicationService_NotifyApplicationServiceModel_IP/"+ns6+":forminputdata/"+ns6+":freeformcontrols/"+ns6+":taskHeader");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.createAssignmentInConsolidatedView("PreAssignment",notificationTaskId, 1, "Replace Content With", "Fixed Value", "Error", null);

		bpmEditor.save();

		//Assignment Notify customer with OrderID details
		mmView.selectArtifactItem("Notify Customer OrderID Details");
		mmView.switchToPreAssignment();
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, updateOrdersName, ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, notifyCustomerOrderIDDetailsId, ns6+":NotifyApplicationService_NotifyApplicationServiceModel_IP/"+ns6+":forminputdata/"+ns6+":freeformcontrols/"+ns6+":taskHeader");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.createAssignmentInConsolidatedView("PreAssignment",notifyCustomerOrderIDDetailsId, 1, "Replace Content With", "Expression", "concat(\"Your Order \",/"+ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID,\" has been created \")", null);

		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, notifyCustomerOrderIDDetailsId, ns6+":NotifyApplicationService_NotifyApplicationServiceModel_IP/"+ns6+":forminputdata/"+ns6+":freeformcontrols/"+ns6+":taskDescription");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.createAssignmentInConsolidatedView("PreAssignment",notifyCustomerOrderIDDetailsId, 2, "Replace Content With", "Fixed Value", "task", null);

		bpmEditor.save();

		//Notify customer with Discount Details
		mmView.selectArtifactItem("Notify Customer Discount Details");
		mmView.switchToPreAssignment();
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, updateOrdersName, ns2+":UpdateOrdersOutput/"+ns2+":UpdateOrdersResponse/"+ns2+":tuple/"+ns2+":new/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, notifyCustomeDD, ns6+":NotifyApplicationService_NotifyApplicationServiceModel_IP/"+ns6+":forminputdata/"+ns6+":freeformcontrols/"+ns6+":taskHeader");
		mmView.createAssignment(sTreeItem, tTreeItem);
		bpmEditor.switchToModel();
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		bpmEditor.close();
	}
}
