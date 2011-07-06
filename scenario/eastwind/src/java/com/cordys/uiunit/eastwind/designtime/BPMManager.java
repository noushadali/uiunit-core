package com.cordys.uiunit.eastwind.designtime;

import org.junit.Before;
import org.junit.Test;

import com.cordys.bpm.cwsutilities.IUIBPM;
import com.cordys.bpm.cwsutilities.IUIBPMDesigner;
import com.cordys.bpm.cwsutilities.IUIBPMDocumentCreatorWizard;
import com.cordys.bpm.cwsutilities.IUIMessageMapView;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMActivity;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMDecisionCase;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMEndEvent;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMForEach;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMHumanTask;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMServiceTask;
import com.cordys.bpm.cwsutilities.designer.IUIShapeBPMStartEvent;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMDecisionCaseProperties;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMForEachProperties;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMHumanTaskProperties;
import com.cordys.bpm.cwsutilities.designer.properties.IUIShapeBPMStartEventProperties;
import com.cordys.cm.uiunit.elements.cordys.ICordysContextMenu;
import com.cordys.cm.uiunit.elements.cordys.ITreeItem;
import com.cordys.cm.uiunit.framework.IKeyboard;
import com.cordys.cm.uiunit.framework.IMouse;
import com.cordys.cm.uiunit.framework.IMouse.Speed;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.notification.cwsutilities.IWorklist;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceInterface;
import com.cordys.webservice.cwsutilities.IUIWebServiceOperation;
import com.cordys.xform.cwsutilities.IXForm;


public class BPMManager extends WorkSpaceOrganizer{
	IUIProject project;
	IUICWSFolder bpmFolder;
	IUICWSFolder roleFolder;
	IUICWSFolder wlFolder;
	IUICWSFolder dbFolder;
	IUICWSFolder dmFolder;
	IUICWSFolder dbNWPackageFolder;
	IUICWSFolder dbEWPackageFolder;
	IUICWSFolder nwFolder;
	IUICWSFolder ewFolder;
	IUICWSFolder wsDataMapFolder;
	IUICWSFolder xfFolder;
	IUICWSFolder schemaFolder;
	IUIBPM bpm;
	IUIBPMDesigner bpmEditor;
	IUIBPMDocumentCreatorWizard docWizard;
	IUIProject projectItem;

	IKeyboard keyboard;
	IMouse mouse;
	Speed oldSpeed;
	
	String designerId;

	@Before
	@UIUnitTimeout(1800000)
	public void initializeEnvironment(){
		project = getProject();
		bpmFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BPM);
		
		keyboard = this.getContext().getRootContext().getTestEnvironment().getKeyboard();
		mouse = this.getContext().getRootContext().getTestEnvironment().getMouse();
		oldSpeed = mouse.getSpeed();
		
		bpm = bpmFolder.addDocument(IUIBPM.class);
	}

	@Test
	@UIUnitTimeout(1800000)
	public void createSubProcessUpdateProductionDB() throws InterruptedException
	{
		bpmEditor = bpm.getDesigner();

		bpmEditor.save(EastWindArtifacts.BPM_UPDATEPRODUCTION,EastWindArtifacts.BPM_UPDATEPRODUCTION);

		//create start event
		IUIShapeBPMStartEvent startEvent  = bpmEditor.addStartEvent(130, 70);

		//add empty activity1
		IUIShapeBPMActivity emptyActivity1 = bpmEditor.addActivity("Empty Activity", 80, 410);

		//add for each
		IUIShapeBPMForEach forEach1 = bpmEditor.makeGroupAsForEach("For Each", emptyActivity1);
		
		//add empty activity2
		IUIShapeBPMActivity emptyActivity2 = bpmEditor.addActivity("Empty Activity 123", 600, 285);
		//add for each2
		IUIShapeBPMForEach forEach2 = bpmEditor.makeGroupAsForEach("For Each123", emptyActivity2);
		IUIShapeBPMForEachProperties humanTaskPS ;
		humanTaskPS = forEach2.openProperties();
		humanTaskPS.setDescription("For Each123");
		humanTaskPS.close();
		//add end event
		IUIShapeBPMEndEvent endEvent = bpmEditor.addEndEvent(650,550);
		
		//add verticalLanes
		bpmEditor.addVerticalLane("NorthWindDB", 60, 40);
		bpmEditor.addVerticalLane("Data Transformation", 330, 20);
		bpmEditor.addVerticalLane("ProductionDB", 580, 20);
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		//Get the Workspace Explorer of BPM Designer
		projectItem = bpmEditor.getProjectFromWorkspaceDockedView(getDefaultSolutionName(), getDefaultProjectName());
		bpmEditor.waitForIdle();
		//System.out.println("ProjectItemName :" + projectItem.getName());
		dbFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		//IUIShapeBPMServiceTask getOrdersObject = bpmEditor.insertWebServiceFromContextMenu("GetOrdersObject", 80, 160);
		dbNWPackageFolder = dbFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND);
		nwFolder = dbNWPackageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
		IUIWebServiceDefinitionSet wsDefSet = nwFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);

		IUIWebServiceInterface wsInf = wsDefSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_NORTHWIND);
		IUIWebServiceOperation wsOprn = null;

		//create getOrders object
		wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrdersObject");
		IUIShapeBPMServiceTask getOrdersObject = bpmEditor.addServiceTask(wsOprn, null, 80, 160);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//create GetOrder_x0020_DetailsObjectsForOrders
		//IUIShapeBPMServiceTask getOrders_x0020_Object = bpmEditor.insertWebServiceFromContextMenu("GetOrder_x0020_DetailsObjectsForOrderID", 80, 260);
		wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrder_x0020_DetailsObjectsForOrderID");
		IUIShapeBPMServiceTask getOrders_x0020_Object = bpmEditor.addServiceTask(wsOprn, null, 80, 260);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//add data transformation
		//IUIShapeBPMServiceTask dataTransformation = bpmEditor.insertWebServiceFromContextMenu(EastWindArtifacts.WS_OPERATION_DATATRANSFORMATION, 350, 260);
		wsDataMapFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP);
		wsDefSet = wsDataMapFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_DATATRANSFORMATION);

		wsInf = wsDefSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_DATATRANSFORMATION);
		wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, EastWindArtifacts.WS_OPERATION_DATATRANSFORMATION);

		IUIShapeBPMServiceTask dataTransformation = bpmEditor.addServiceTask(wsOprn,null,350,260);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//initialize web services for EW
		//IUIShapeBPMServiceTask updateOrders = bpmEditor.insertWebServiceFromContextMenu("UpdateOrders", 600, 140);
		dbEWPackageFolder = dbFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_PRODUCTION);
		ewFolder = dbEWPackageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_PRODUCTIONDB);
		wsDefSet = ewFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_PRODUCTION);
		wsInf = wsDefSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_PRODUCTIONDB);

		//add update orders
		wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, "UpdateOrders");
		IUIShapeBPMServiceTask updateOrders = bpmEditor.addServiceTask(wsOprn, null, 600, 140);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//add UpdateOrder_x0020_Details
		//IUIShapeBPMServiceTask updateOrder_x0020_Details = bpmEditor.insertWebServiceFromContextMenu("UpdateOrder_x0020_Details", 600, 425);
		wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, "UpdateOrder_x0020_Details");
		IUIShapeBPMServiceTask updateOrder_x0020_Details = bpmEditor.addServiceTask(wsOprn,null,600, 425);
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//make connectors
		startEvent.makeConnectorTo(getOrdersObject.getActivity());
		getOrdersObject.getActivity().makeConnectorTo(getOrders_x0020_Object.getActivity());
		getOrders_x0020_Object.getActivity().makeConnectorTo(forEach1);
		
		updateOrders.getActivity().makeConnectorTo(forEach2);
		forEach2.makeConnectorTo(updateOrder_x0020_Details.getActivity());
		updateOrder_x0020_Details.getActivity().makeConnectorTo(endEvent);
		
		forEach1.makeConnectorTo(dataTransformation.getActivity());
		dataTransformation.getActivity().makeConnectorTo(updateOrders.getActivity());
		bpmEditor.save();
		
		//property sheet
		IUIShapeBPMStartEventProperties startEventPS = startEvent.openProperties();
		startEventPS.setInputMessage("GetOrdersObjectInput");
	
		bpmEditor.save();
		startEventPS.close();

		String ns2 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/NorthwindDatabaseMetadata");
		String ns4 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/");
		String ns6 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/saleproductionmapws");
		String ns7 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/ProductionDatabaseMetadata");
		
		//property sheet foreach
		IUIShapeBPMForEachProperties forEachPS = forEach1.openProperties();
		forEachPS.showTab(IUIShapeBPMForEachProperties.TAB_GENERAL);
		forEachPS.setSelectCondition(IUIShapeBPMForEachProperties.VALUE_STATIC, ns2+":GetOrder_x0020_DetailsObjectsForOrderIDOutput/"+ns2+":GetOrder_x0020_DetailsObjectsForOrderIDResponse/"+ns2+":tuple/"+ns2+":old/"+ns2+":Order_x0020_Details");
		bpmEditor.save();
		forEachPS.close();

		//property sheet foreach 2
		forEachPS = forEach2.openProperties();
		forEachPS.showTab(IUIShapeBPMForEachProperties.TAB_GENERAL);
		forEachPS.setSelectCondition(IUIShapeBPMForEachProperties.VALUE_STATIC, ns6+":SalesProductionDataTransformation_WebserviceOperationInput/"+ns6+":SalesProductionDataTransformation_WebserviceOperation/"+ns4+":Orders/"+ns4+":OrderDetails");
		bpmEditor.save();
		forEachPS.close();

		//message map
		String getOrders_x0020_ObjectId = getOrders_x0020_Object.getActivity().getId();
		String forEach1Id =	forEach1.getId();
		String emptyActivityId = emptyActivity1.getId();
		String emptyActivity2Id = emptyActivity2.getId();
		String dataTransformationId = dataTransformation.getActivity().getId();
		String updateOrdersId = updateOrders.getActivity().getId();
		String forEach2Id = forEach2.getId();
		String updateOrder_X0020_DetailsId = updateOrder_x0020_Details.getActivity().getId();

		//message map
		String getOrdersObjectName = getOrdersObject.getActivity().getName();
		String forEach1Name =	forEach1.getName();
		String dataTransformationName = dataTransformation.getActivity().getName();
		String forEach2Name = forEach2.getName();

		dataTransformation.getActivity().click();
		IUIMessageMapView mmView = bpmEditor.switchToMessageMap();

		//Create ProcessSpecificMessages
		mmView.createProcessSpecificMessage("OrderLinesInput");
		this.getContext().waitForIdle();
		
		ITreeItem sourceTree=mmView.findActivityItemInSource("OrderLinesInput");
		String inputxml="<OrderDetails><tuple><new><Order_x0020_Details><ID/><ProductID/><UnitPrice/><Discount/><Quantity/></Order_x0020_Details></new></tuple></OrderDetails>";
		mmView.pasteXML(inputxml, sourceTree, "Element", bpmEditor);
		
		mmView.createProcessSpecificMessage("OrderMapInput");
		mmView.createProcessSpecificMessage("dummy");

		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();

		this.getContext().waitForIdle();

		ITreeItem sTreeItem = null;
		ITreeItem tTreeItem = null;

		//assignment SalesProductionDataTransformation_WebserviceOperation
		mmView.selectArtifactItem("SalesProductionDataTransformation_WebserviceOperation");
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, getOrdersObjectName, ns2+":GetOrdersObjectOutput/"+ns2+":GetOrdersObjectResponse/"+ns2+":tuple/"+ns2+":old/"+ns2+":Orders");
		sTreeItem.click();
		sTreeItem.clickOnTreeItemImage();
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, dataTransformationId, ns6+":SalesProductionDataTransformation_WebserviceOperationInput/"+ns6+":SalesProductionDataTransformation_WebserviceOperation/"+ns4+":Orders");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(dataTransformationId, 1, "Replace Content With", "Children with Target NS", null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderMapInput");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, dataTransformationId, ns6+":SalesProductionDataTransformation_WebserviceOperationInput/"+ns6+":SalesProductionDataTransformation_WebserviceOperation/"+ns4+":Orders");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(dataTransformationId, 2, "Add", "Children with Target NS" , null, null);

		//assignment Empty Activity
		mmView.selectArtifactItem("Empty Activity");

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, forEach1Name, "instance:iteratorName_" + forEach1Id);
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderLinesInput/bpm:OrderDetails");
		sTreeItem.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Find in Source Assigns");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivityId, 1, "Add", "Children" , null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderLinesInput");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderMapInput");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivityId, 2, "Add", "Children" , null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderLinesInput/bpm:OrderDetails");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivityId, 3, "Delete", null , null, null);

		//assignment emptyactivity2
		mmView.selectArtifactItem("Empty Activity 123");
		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:dummy");
		//change assignment "bpm:OrderMapInput/"+ns4+":OrderDetails"
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivity2Id, 1, "Delete", null, null, "bpm:OrderMapInput/bpm:OrderDetails");
		//mmView.selectOperationOnAssignment(emptyActivity2Id, 1, "Delete", null, null, "bpm:OrderLinesInput/bpm:OrderDetails/bpm:tuple/bpm:new/bpm:Order_x0020_Details/bpm:OrderID");
        //System.out.println("For each2 ID is"+forEach2Id);
				
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, forEach2Name, "instance:iteratorName_" + forEach2Id);
		Assert.assertNotNull(sTreeItem);
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderLinesInput/bpm:OrderDetails/bpm:tuple/bpm:new/bpm:Order_x0020_Details");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivity2Id, 2, "Replace Content With", "Children with Target NS", null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:dummy");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivity2Id, 3, "Delete", null, null, "bpm:OrderLinesInput/bpm:OrderDetails/bpm:tuple/bpm:new/bpm:Order_x0020_Details/bpm:OrderID");
		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":OrderID");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderLinesInput/bpm:OrderDetails/bpm:tuple/bpm:new/bpm:Order_x0020_Details/bpm:ID");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderLinesInput/bpm:OrderDetails/bpm:tuple");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderMapInput");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivity2Id, 5, "Add", "Select with Target NS", null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_TARGET, "bpm:OrderLinesInput/bpm:OrderDetails/bpm:tuple/bpm:new/bpm:Order_x0020_Details");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(emptyActivity2Id, 6, "Delete", null, null, null);

		//assignment GetOrder_X0020_DetailsObjectsForOrders
		mmView.selectArtifactItem("GetOrder_x0020_DetailsObjectsForOrderID");

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, getOrdersObjectName, ns2+":GetOrdersObjectOutput/"+ns2+":GetOrdersObjectResponse/"+ns2+":tuple/"+ns2+":old/"+ns2+":Orders/"+ns2+":OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, getOrders_x0020_ObjectId, ns2+":GetOrder_x0020_DetailsObjectsForOrderIDInput/"+ns2+":GetOrder_x0020_DetailsObjectsForOrderID/"+ns2+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);

		//assignment updateOrders
		mmView.selectArtifactItem("UpdateOrders");

		//bpm.refresh();
		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":old");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(updateOrdersId, 1, "Delete", null, null, null);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":OrderID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ID");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":CustomerID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":Customer");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":EmployeeID");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":Employee");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":OrderDate");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":Order_Date");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":RequiredDate");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":Required_Date");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShippedDate");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":Shipped_Date");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipVia");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipVia");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":Freight");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":Freight");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipName");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipName");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipAddress");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipAddress");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipCity");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipCity");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipRegion");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipRegion");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipPostalCode");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipPostalCode");
		mmView.createAssignment(sTreeItem, tTreeItem);

		sTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_SOURCE, dataTransformationName, ns6+":SalesProductionDataTransformation_WebserviceOperationOutput/"+ns6+":SalesProductionDataTransformation_WebserviceOperationResponse/"+ns6+":tuple/"+ns4+":Production/"+ns4+":ShipCountry");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrdersId, ns7+":UpdateOrdersInput/"+ns7+":UpdateOrders/"+ns7+":tuple/"+ns7+":new/"+ns7+":Orders/"+ns7+":ShipCountry");
		mmView.createAssignment(sTreeItem, tTreeItem);

		//assignment UpdateOrder_X0020_Details
		mmView.selectArtifactItem("UpdateOrder_x0020_Details");

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrder_X0020_DetailsId, ns7+":UpdateOrder_x0020_DetailsInput/"+ns7+":UpdateOrder_x0020_Details/"+ns7+":tuple");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(updateOrder_X0020_DetailsId, 1, "Delete", null, null, null);

		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderMapInput");
		tTreeItem = mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, updateOrder_X0020_DetailsId, ns7+":UpdateOrder_x0020_DetailsInput/"+ns7+":UpdateOrder_x0020_Details");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(updateOrder_X0020_DetailsId, 2, "Add", "Children with Target NS", null, null);

		//save
		bpmEditor.switchToModel();

		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll(); 
		bpmEditor.close();
	}
	
	@Test
    @UIUnitTimeout(1800000)
    public void createSubprocessDiscountApproval() throws InterruptedException
    {
		bpmEditor = bpm.getDesigner();
		
		//Save the BPM
		bpmEditor.save(EastWindArtifacts.BPM_DISCOUNTAPPROVAL,EastWindArtifacts.BPM_DISCOUNTAPPROVAL);
		
		//create start event
		IUIShapeBPMStartEvent startEvent  = bpmEditor.addStartEvent(100, 75);
		
		//create decision case to check if Discount > 20%
		IUIShapeBPMDecisionCase decisionCase1 = bpmEditor.addDecisionCase("Discount > 20%", 300, 100);
		
		//create end
		IUIShapeBPMEndEvent endEvent2 = bpmEditor.addEndEvent(100,500);
		
		//create decision case for Discount Approved
		IUIShapeBPMDecisionCase decisionCase2 = bpmEditor.addDecisionCase("Discount Approved", 300, 400);
		
		//create end
		IUIShapeBPMEndEvent endEvent1 = bpmEditor.addEndEvent(350, 550);
		
		//add verticalLanes
		bpmEditor.addVerticalLane(EastWindArtifacts.ROLES_SALESREPRESENTATIVE, 30, 20);
		bpmEditor.addVerticalLane(EastWindArtifacts.ROLES_SALESMANAGER, 280, 20);
		bpmEditor.addVerticalLane(EastWindArtifacts.ROLES_VPSALES, 530, 20);
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		projectItem = bpmEditor.getProjectFromWorkspaceDockedView(getDefaultSolutionName(), getDefaultProjectName());
		xfFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		wlFolder = projectItem.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS);
		//create Sales Order Complete
		//IUIShapeBPMHumanTask salesOrderCompleteTask = bpmEditor.insertUITaskFromContextMenu(EastWindArtifacts.XFORM_SALESORDERCOMPLETE, 50, 300);
		IXForm humanTask = xfFolder.getExistingChildDocument(IXForm.class,EastWindArtifacts.XFORM_SALESORDERCOMPLETE);		
		IUIShapeBPMHumanTask salesOrderCompleteTask = bpmEditor.addHumanTask(humanTask, null, 50, 300);		
		IWorklist worklist = wlFolder.getExistingChildDocument(IWorklist.class, EastWindArtifacts.WORKLISTS_SALESREPRESENTATIVE);
		worklist.refresh();
		mouse.setSpeed(IMouse.Speed.VERYSLOW);
		worklist.dragAndDropOnElement(salesOrderCompleteTask.getActivity());
		mouse.setSpeed(oldSpeed);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//create human task Approve Discount
		//IUIShapeBPMHumanTask approveDiscountTask = bpmEditor.insertUITaskFromContextMenu(EastWindArtifacts.XFORM_SALESORDERAPPROVAL, 300, 250);
		//Change the name
		//approveDiscountTask.getActivity().setName("Manager Discount Approval");		
		//Insert the worklist to tast
		//approveDiscountTask.insertWorklistFromContextMenu(EastWindArtifacts.WORKLISTS_MANAGER);
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_SALESORDERAPPROVAL);
		IUIShapeBPMHumanTask  approveDiscountTask = bpmEditor.addHumanTask(humanTask, "Manager Discount Approval", 300, 250);
		IUIShapeBPMHumanTaskProperties humanTaskPS;
		humanTaskPS = approveDiscountTask.openProperties();
		humanTaskPS.setDescription("Manager Discount Approval");
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_WORKLIST,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.WORKLISTS_MANAGER);
		bpmEditor.save();
		humanTaskPS.close();		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//create Sales Order Approval
		//IUIShapeBPMHumanTask salesOrderApprovalTask = bpmEditor.insertUITaskFromContextMenu(EastWindArtifacts.XFORM_SALESORDERAPPROVAL, 580, 300);
		//Change the name
		//salesOrderApprovalTask.getActivity().setName("VP Discount Approval");
		//Insert the worklist to tast
		//salesOrderApprovalTask.insertWorklistFromContextMenu(EastWindArtifacts.WORKLISTS_VPSALES);
		humanTask = xfFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_SALESORDERAPPROVAL);		
		IUIShapeBPMHumanTask salesOrderApprovalTask = bpmEditor.addHumanTask(humanTask, "VP Discount Approval", 550, 300);		
		
		humanTaskPS = salesOrderApprovalTask.openProperties();
		humanTaskPS.setDescription("VP Discount Approval");
		humanTaskPS.setWorkAssignment(IUIShapeBPMHumanTaskProperties.ASSIGNEE_TYPE_WORKLIST,IUIShapeBPMHumanTaskProperties.VALUE_STATIC,EastWindArtifacts.WORKLISTS_VPSALES);
		bpmEditor.save();
		humanTaskPS.close();
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//make connectors
		startEvent.makeConnectorTo(decisionCase1);
		decisionCase1.makeConnectorTo(approveDiscountTask.getActivity());
		approveDiscountTask.getActivity().makeConnectorTo(decisionCase2);
		decisionCase1.makeConnectorTo(salesOrderApprovalTask.getActivity());
		salesOrderApprovalTask.getActivity().makeConnectorTo(decisionCase2);
		decisionCase2.makeConnectorTo(salesOrderCompleteTask.getActivity());
		salesOrderCompleteTask.getActivity().makeConnectorTo(endEvent2);
		decisionCase2.makeConnectorTo(endEvent1);
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//Message Map Create Process Specific Messages
		IUIMessageMapView mmView = bpmEditor.switchToMessageMap();
		
		mmView.createProcessSpecificMessage("OrderInfo");
		ITreeItem treeItemOrderInfo = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInfo");
		String stOrderInfo = "<OrderInfo><OrderID/><DisocuntDetails><TotalDiscount/></DisocuntDetails></OrderInfo>";
		mmView.pasteXML(stOrderInfo, treeItemOrderInfo, "Element", bpmEditor);
		mmView.createProcessSpecificMessage("dummy");
		
		bpmEditor.switchToModel();
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		//Property Sheet
		//start event
		IUIShapeBPMStartEventProperties startEventPS =  startEvent.openProperties();
		startEventPS.setTriggerType(IUIShapeBPMStartEventProperties.TRIGGER_MESSAGE, "OrderInfo");
		bpmEditor.save();
		startEventPS.close();
		String ns2 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/1.0/xforms/processapi");
		String ns3 = bpmEditor.getNamespacePrefix("http://schemas.cordys.com/NorthwindDatabaseMetadata");
		IUIShapeBPMDecisionCaseProperties decisionCasePS;
		//Discount Approved
		decisionCasePS = decisionCase2.openProperties();
		decisionCasePS.setCondition(IUIShapeBPMDecisionCaseProperties.CONDITION_DEFAULT, null, 2);
		decisionCasePS.setCondition(IUIShapeBPMDecisionCaseProperties.CONDITION_STATIC,ns2+":SalesOrdApproval_SalesOrdApprovalModel_OP/"+ns2+":formoutputdata/"+ns2+":freeformcontrols/"+ns2+":approveButton = 'Yes'", 1);
		bpmEditor.save();
		decisionCasePS.close();
		
		//Discount > 20%
		decisionCasePS = decisionCase1.openProperties();
		decisionCasePS.setCondition(IUIShapeBPMDecisionCaseProperties.CONDITION_DEFAULT, null, 2);
		decisionCasePS.setCondition(IUIShapeBPMDecisionCaseProperties.CONDITION_STATIC,"bpm:OrderInfo/bpm:OrderInfo/bpm:DisocuntDetails/bpm:TotalDiscount < 0.2" , 1);
		bpmEditor.save();
		decisionCasePS.close();

		//Message Map
		String salesApproveID = salesOrderApprovalTask.getActivity().getId();
		String salesCompleteID = salesOrderCompleteTask.getActivity().getId();
		String discountApprovalID = approveDiscountTask.getActivity().getId();

		//For Creating MessageMap Assignments, click on Sales Order Approval task
		salesOrderApprovalTask.getActivity().click();
		
		mmView = bpmEditor.switchToMessageMap();
		
		//VP Discount Approval
		//VP Discount Approval
		mmView.selectArtifactItem("VP Discount Approval");
		ITreeItem sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInfo/bpm:OrderInfo/bpm:OrderID");
		ITreeItem tTreeItem =  mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, salesApproveID, ns2+":SalesOrdApproval_SalesOrdApprovalModel_IP/"+ns2+":forminputdata/"+ns2+":OrdersModel/"+ns3+":GetOrdersObject/"+ns3+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		bpmEditor.save();

		//Manager Discount Approval
		mmView.selectArtifactItem("Manager Discount Approval");
		sTreeItem = mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:OrderInfo/bpm:OrderInfo/bpm:OrderID");
		tTreeItem =  mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, discountApprovalID, ns2+":SalesOrdApproval_SalesOrdApprovalModel_IP/"+ns2+":forminputdata/"+ns2+":OrdersModel/"+ns3+":GetOrdersObject/"+ns3+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		bpmEditor.save();

		//SalesRepOrderComplete
		mmView.selectArtifactItem(EastWindArtifacts.XFORM_SALESORDERCOMPLETE);
		sTreeItem =  mmView.findProcessSpecficItem(IUIMessageMapView.TREE_SOURCE, "bpm:dummy");
		tTreeItem =  mmView.findActivityTreeItem(IUIMessageMapView.TREE_TARGET, salesCompleteID, ns2+":SalesRepOrderComplete_SalesRepOrderCompleteModel_IP/"+ns2+":forminputdata/"+ns2+":OrdersModel/"+ns3+":GetOrdersObject/"+ns3+":OrderID");
		mmView.createAssignment(sTreeItem, tTreeItem);
		mmView.selectOperationOnAssignment(salesCompleteID, 1, "Delete", null, null, null);

		//save document
		bpmEditor.switchToModel();
		
		bpmEditor.save();
		project.getCWSIDE().getWorkspaceDocumentsView().saveAll();
		
		bpmEditor.close();
    }	
}
