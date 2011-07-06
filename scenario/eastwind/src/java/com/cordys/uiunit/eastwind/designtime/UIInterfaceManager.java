package com.cordys.uiunit.eastwind.designtime;

import java.awt.Point;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.ApplicationFactory;
import com.cordys.cm.uiunit.elements.cordys.IApplication;
import com.cordys.cm.uiunit.elements.cordys.ICordysContextMenu;
import com.cordys.cm.uiunit.elements.cordys.ITabs;
import com.cordys.cm.uiunit.elements.cordys.internal.Tabs;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.elements.html.ITextInput;
import com.cordys.cm.uiunit.elements.html.internal.Div;
import com.cordys.cm.uiunit.elements.html.internal.HTMLElement;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceInterface;
import com.cordys.webservice.cwsutilities.IUIWebServiceOperation;
import com.cordys.xform.cwsutilities.IXForm;
import com.cordys.xform.cwsutilities.IXFormEditor;
import com.cordys.xform.cwsutilities.ModelAssociation;
import com.cordys.xform.cwsutilities.internal.ControlsPropertyView;
import com.cordys.xform.cwsutilities.internal.GenerateUIFromWebServiceProperties;
import com.cordys.xform.cwsutilities.internal.ToolBox;


@UIUnitTimeout(1200000)
public class UIInterfaceManager extends WorkSpaceOrganizer {
	@Test
	@UIUnitTimeout(1200000)
	public void createOrderRegistrationUI(){
		String scriptText = "function button1_Click(eventObject)" +"\\n"+
		"{" +"\\n"+
		"	var triggerOrderEntryClone = cordys.cloneXMLDocument(triggerOrderEntry.XMLDocument);" +"\\n"+
		"	var orderheaderclone = cordys.cloneXMLDocument(OrdersModel.getData());" +"\\n"+
		"	var orderlinesclone = cordys.cloneXMLDocument(Order_x0020_DetailsModel.getData());" +"\\n"+
		"	var node = cordys.selectXMLNode(orderheaderclone,'.//*[local-name()=\\\"Orders\\\"]').firstChild;" +"\\n"+
		"	while(node)" +"\\n"+
		"	{" +"\\n"+
		"		cordys.appendXMLNode(node,cordys.selectXMLNode(triggerOrderEntryClone,'.//*[local-name()=\\\"Orders\\\"]'));" +"\\n"+
		"		node = cordys.selectXMLNode(orderheaderclone,'.//*[local-name()=\\\"Orders\\\"]').firstChild;" +"\\n"+
		"	}" +"\\n"+
		"	node = cordys.selectXMLNode(orderlinesclone,'.//*[local-name()=\\\"tuple\\\"]');" +"\\n"+
		"	while(node)" +"\\n"+
		"	{" +"\\n"+
		"		if(! node.getAttribute('pseudosync_id'))" +"\\n"+
		"			{" +"\\n"+
		"				var orderDetail = cordys.selectXMLNode(node,'.//*[local-name()=\\\"new\\\"]/*[local-name()=\\\"Order_x0020_Details\\\"]');" +"\\n"+
		"				cordys.appendXMLNode(orderDetail,cordys.selectXMLNode(triggerOrderEntryClone,'.//*[local-name()=\\\"OrderDetails\\\"]'));" +"\\n"+
		"			}" +"\\n"+
		"		node = node.nextSibling;" +"\\n"+
		"	}" +"\\n"+
		"	DummyModel.clear();" +"\\n"+
		"	DummyModel.setMethodRequest(triggerOrderEntryClone);" +"\\n"+
		"	DummyModel.reset();" +"\\n"+
		"}" +"\\n"+
		"var categoryID;" +"\\n"+
		"function categoryid_Change(eventObject)" +"\\n"+
		"{" +"\\n"+
		"	categoryID = eventObject.srcElement.value;" +"\\n"+
		"	ProductsModel.reset();" +"\\n"+
		"}" +"\\n"+
		"function productid_Change(eventObject)" +"\\n"+
		"{" +"\\n"+
		"	var product = eventObject.srcElement.value;" +"\\n"+
		"	if(product =='')" +"\\n"+
		"	return;" +"\\n"+
		"	index = Order_x0020_DetailsTable.getIndex();" +"\\n"+
		"	var productexpr ='.//*[local-name()=\\\"tuple\\\"]/*[local-name()=\\\"old\\\"]/*[local-name()=\\\"Products\\\"][*[local-name()=\\\"ProductID\\\"] =\\\" '+product+' \\\"]';" +"\\n"+
		"	var tuple = cordys.selectXMLNode(ProductsModel.getData(),productexpr);" +"\\n"+
		"	unitprice[index].setValue(cordys.getNodeText(tuple,\\\".//*[local-name()=\\\'UnitPrice\\\']\\\"));" +"\\n"+
		"	}" +"\\n"+
		"function Form_InitDone(eventObject)" +"\\n"+
		"{" +"\\n"+
		"	OrdersGroup.create();" +"\\n"+
		"}" +"\\n"+
		"function ProductsModel_OnRequest(eventObject)" +"\\n"+
		"{" +"\\n"+
		"	cordys.setNodeText(eventObject.request,'.//*[local-name()=\\\"CategoryID\\\"]', categoryID);" +"\\n"+
		"}";

		String xmlText = "<xml>" +
		  "<xml id='triggerOrderEntry'>" +
		    "<SOAP:Envelope url='com.eibus.web.soap.Gateway.wcp?organization=o%3Dsystem%2Ccn" +
		    "%3Dcordys%2Ccn%3DBld164%2Co%3Dvanenburg.com&amp;messageOptions=0' xmlns:SOAP='http://schemas.xmlsoap.org/soap/envelope/'>" +
		      "<SOAP:Body>" +
		      "<ExecuteProcess xmlns='http://schemas.cordys.com/bpm/execution/1.0\'>" +
		      "<type>definition</type>" +
		      "<receiver>bpm/OrderEntryProcess</receiver>" +
		      "<message>" +
		      "	<OrderInformation xmlns='http://schemas.cordys.com/default'>" +
		      "	 <OrderInformation xmlns='http://schemas.cordys.com/default'>" +
		      "   <Orders></Orders>" +
		      "   <OrderDetails></OrderDetails>" +
		      "  </OrderInformation>" +
		      " </OrderInformation>" +
		      "</message>"+
		      "<source>Run from Studio</source>" +
		    "</ExecuteProcess>"+
		      "</SOAP:Body>" +
		    "</SOAP:Envelope>" +
		  "</xml>" +
		"</xml>";
     
	IUIProject project = getProject();
	IUICWSFolder xformFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
	IXForm xform = xformFolder.addDocument(IXForm.class);
	this.getCWSIDE().getWorkspaceDocumentsView().focus();
	IXFormEditor xformDesigner = xform.openEditor();
	//Insert Script and XML
	xformDesigner.insertXML(xmlText);
	xformDesigner.insertScript(scriptText);

	ITabs desingerTabs = xformDesigner.findElement(ITabs.class, "designerTabs");
	desingerTabs.selectTab("xformsdesigner");
	Div editorDiv = xformDesigner.findElement(Div.class, "content");
	int contentOffsetLeft = editorDiv.getElementOffsetLeftRelativeToCordysRoot();
	int contentOffsetHeight = editorDiv.getElementOffsetTopRelativeToCordysRoot();
	int contentDivOffsetLeft = editorDiv.getElementOffsetWidth();
	int contentDivOffsetHeight = editorDiv.getElementOffsetHeight();
	

	IUIProject eastwindproject = xformDesigner.getProjectFromWorkspaceDockedView("Sales", "EastWind");
	IUICWSFolder dbSchemaFolder = eastwindproject.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
	IUICWSFolder packageFolder = dbSchemaFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND);	
	IUICWSFolder wsapps = packageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
	IUIWebServiceDefinitionSet webServiceDefinitionSet = wsapps.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);
	IUIWebServiceInterface webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_NORTHWIND);
	IUIWebServiceOperation webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrdersObject");
	
	GenerateUIFromWebServiceProperties generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
	generateUIProperties.setBInputUI(false);
	generateUIProperties.setBOutputUI(true);
	generateUIProperties.setIsFirstMethod(true);
	xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
	xformDesigner.setModelAutomatic("OrdersModel", false);

	//Create Init-Done event
	xformDesigner.setEventForControl("content", "selectInitDone"); 
	
	webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetCategoriesObjects");
	generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
	generateUIProperties.setBInputUI(false);
	generateUIProperties.setBOutputUI(false);
	generateUIProperties.setSelectRequiredModelChoice(null);
	generateUIProperties.setIsFirstMethod(false);
	//Drag and drop GetCategoriesObjects
	xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
	
	webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetProductsObjectsForCategoryID");
	generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
	generateUIProperties.setBInputUI(false);
	generateUIProperties.setBOutputUI(false);
	generateUIProperties.setSelectRequiredModelChoice(null);
	generateUIProperties.setIsFirstMethod(false);
	generateUIProperties.chooseModelForAssociation("r_option1");
	//Drag and drop GetProductsObjectsForCategoryID
	xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
	
	webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class,"GetOrder_x0020_DetailsObjectsForOrderID");
	generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
	generateUIProperties.setBInputUI(false);
	generateUIProperties.setBOutputUI(true);
	generateUIProperties.setSelectRequiredModelChoice(null);
	generateUIProperties.setIsFirstMethod(false);
	generateUIProperties.chooseModelForAssociation("r_option0");
	//Drag drop of GetOrder_x0020_DetailsObject
	xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
	//xformDesigner.setModelAssociation("Order_x0020_DetailsModel", "OrderID");
	xformDesigner.setModelAutomatic("Order_x0020_DetailsModel", false);
	xformDesigner.createModel("DummyModel", "DummyModel");
	xformDesigner.focus();
	xformDesigner.setLabel("xo__xInput__34", "Category");
	this.getContext().waitForIdle();
	HTMLElement category = xformDesigner.findElement(HTMLElement.class,"xo__xInput__34"); 
	Point p = new Point(3,4);
	category.getContextMenuwithMouse(p).clickOnMenuItem("Change To/Select");
	//change orderid1 id to categoryid
	category.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
	IApplication propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
	propertiesApp.findElement(ITextInput.class, "propertyId").setValue("categoryid");
//	category.getContextMenuwithMouse().clickOnMenuItem("Properties");
	// propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
	ControlsPropertyView contolPropertiesView = new ControlsPropertyView(xformDesigner,propertiesApp);
	contolPropertiesView.setDataSetModelContent("CategoriesModel", "CategoryID", "CategoryName");
	propertiesApp.close();
	//contolPropertiesView
	desingerTabs = xformDesigner.findElement(ITabs.class, "designerTabs");
	desingerTabs.selectTab("xformsdesigner");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Select");
	this.getContext().waitForIdle();
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Properties");
	contolPropertiesView.setDataSetModelContent("ProductsModel", "ProductID", "ProductName");
	xformDesigner.setEventForControl("xo__xInput__35", "selectValueChanged");
	xformDesigner.setEventForControl("xo__xInput__34", "selectValueChanged");
	xformDesigner.setModelEvent("ProductsModel", "selectOnRequest");
	Tabs tabgroup= xformDesigner.findElement(Tabs.class,"cwsLeftRegionTab" );		
	tabgroup.selectTab("_toolbox_"+xformDesigner.getId());
	xformDesigner.dragControl(ToolBox.BUTTON);
	xformDesigner.setLabel("button1", "Place Order");
	xformDesigner.setEventForControl("button1", "selectActivate");
	xformDesigner.setModelEvent("ProductsModel", "selectOnRequest");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__15").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__17").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__20").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__21").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__22").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__23").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.findElement(HTMLElement.class,"xo__xInput__38").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
	xformDesigner.focus();
	xformDesigner.save(EastWindArtifacts.XFORM_ORDERREGISTRATION,EastWindArtifacts.XFORM_ORDERREGISTRATION);
	xformDesigner.showOnCordysDesktop();
	xformDesigner.save();
	xformDesigner.close();
	}

	@Test
	@UIUnitTimeout(1200000)
	public void createAssignSalesRepUI(){
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder xformFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm xform = xformFolder.addDocument(IXForm.class);
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
		IUICWSFolder wsapps = packageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
		IUIWebServiceDefinitionSet webServiceDefinitionSet = wsapps.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);
		IUIWebServiceInterface webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_NORTHWIND);	
		IUIWebServiceOperation webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrdersObject");
		GenerateUIFromWebServiceProperties generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(false);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(true);
		//Drag and drop GetOrdersObject
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		Div ordersGroup = xformDesigner.findElement(Div.class, "groupbox1");
		IHTMLElement ordersHead = ordersGroup.findElement("__boxHeaderElement");
		//Delete control bar & pagination bar
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Control bar/Delete control bar");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Pagination bar/Delete pagination bar");
		//change input to output
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Split/Vertical");
		this.getContext().waitForIdle();
		Point p = new Point(3,4);	
		IHTMLElement employeeId = ordersGroup.findElement("xo__xInput__17");
		employeeId.dragAndDrop(xformDesigner.findElement(Div.class, "splitterpage2"));
		HTMLElement employeId = ordersGroup.findElement(HTMLElement.class, "xo__xInput__17");
		employeId.getContextMenuwithMouse(p).clickOnMenuItem("Change To/List");
		//employeeId.getContext().executeJavascript(employeeId.getHTMLPointer()+".style.height=180px");
		//employeeId.doubleClick();
		xformDesigner.setLabel("xo__xInput__17", "Sales Representative");
		xformDesigner.setSizeForControl("xo__xInput__17", "150px", "250px");
		//xformDesigner.
		employeId.doClickWithMouse(p);

		HTMLElement element1 = ordersGroup.findElement(HTMLElement.class,"xo__xInput__15");
		element1.getContextMenuwithMouse(new Point(10,20)).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__18").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__21").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__28").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		//Drag frame to get columnchooser not at the toolbar for OrderDetails table. Delete frame later.
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrder_x0020_DetailsObjectsForOrderID");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(false);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.chooseModelForAssociation("r_option0");
		//Drag drop of GetOrder_x0020_DetailsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		xformDesigner.findElement(HTMLElement.class,"table1").getContextMenuwithMouse(p).clickOnMenuItem("Control bar/Delete control bar");
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetEmployeesObjects");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(false);
		generateUIProperties.setBOutputUI(false);
		generateUIProperties.setIsFirstMethod(false);
		//Drag and drop GetEmployeesObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class,"GetProductsObjects");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBInputUI(false);
		generateUIProperties.setBOutputUI(false);
		generateUIProperties.setIsFirstMethod(false);
		//Drag and drop GetProductsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		//xformDesigner.setModelAssociation("Order_x0020_DetailsModel", "OrderID");
		xformDesigner.findElement("xo__xInput__17").getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
		IApplication propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		ControlsPropertyView contolPropertiesView = new ControlsPropertyView(xformDesigner,propertiesApp);
		contolPropertiesView.setDataSetModelContent( "EmployeesModel", "EmployeeID", "FirstName");
		propertiesApp.close();
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Select");
		xformDesigner.findElement("xo__xInput__35").getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
		propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		contolPropertiesView = new ControlsPropertyView(xformDesigner,propertiesApp);
		contolPropertiesView.setDataSetModelContent( "ProductsModel", "ProductID", "ProductName");
		xformDesigner.focus();
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__16").getContextMenuwithMouse(new Point(10,20)).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__19").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__20").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__22").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__23").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__24").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__25").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__26").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__27").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__34").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__38").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		xformDesigner.focus();
		xformDesigner.save(EastWindArtifacts.XFORM_ASSIGNSALESREP,EastWindArtifacts.XFORM_ASSIGNSALESREP);
		xformDesigner.close();
	}

	@Test
	@UIUnitTimeout(1200000)
	public void createSalesOrderApprovalUI(){
		
		String scriptText = "function approveButton_Click(eventObject)" +"\\n"+
		"{" +"\\n"+
		"eventObject.srcElement.setValue('Yes');" +"\\n"+
		"document.getElementById('rejectButton').setLabel('Reject');" +"\\n"+
		"}" +"\\n"+
		"function rejectButton_Click(eventObject)" +"\\n"+
		"{" +"\\n"+
		  "eventObject.srcElement.setValue('No');" +"\\n"+
		  "document.getElementById('approveButton').setLabel('Approve');" +"\\n"+
		"}";

		String xmlText = "<xml>" +
						"<xml id='Answer' xmlns='http://Approval'>" +
						"<Approval />" +
						"</xml>" +
						"</xml>";
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder xformsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
	    IXForm xform = xformsFolder.addDocument(IXForm.class);
		this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IXFormEditor xformDesigner = xform.openEditor();
	
		Div editorDiv = xformDesigner.findElement(Div.class, "content");
		int contentDivOffsetLeft = editorDiv.getScreenXCoordinate();
		int contentDivOffsetHeight = editorDiv.getScreenYCoordinate();
		int contentOffsetLeft = editorDiv.getElementOffsetLeftRelativeToCordysRoot();
		int contentOffsetHeight = editorDiv.getElementOffsetTopRelativeToCordysRoot();
		
		xformDesigner.insertXML(xmlText);
		xformDesigner.insertScript(scriptText);
		xformDesigner.dragControl(ToolBox.TABPAGE);
		IHTMLElement tabStrip = editorDiv.findElement("tabstrip");
		tabStrip.getContext().executeJavascript(tabStrip.findNearByElement(".firstChild.firstChild").getHTMLPointer()+".innerHTML=\"Order for Approval\"");
		IHTMLElement tabGroup = editorDiv.findElement("tabgroup1");
		tabGroup.getContext().executeJavascript(tabGroup.getHTMLPointer()+".style.height=\"550px\"");
		
		IUIProject eastwindproject = xformDesigner.getProjectFromWorkspaceDockedView("Sales", "EastWind");
		IUICWSFolder dbSchemaFolder = eastwindproject.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		IUICWSFolder packageFolder = dbSchemaFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND);
		IUICWSFolder wsapps = packageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);		
		IUIWebServiceDefinitionSet webServiceDefinitionSet = wsapps.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);
		IUIWebServiceInterface webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_NORTHWIND);	
		IUIWebServiceOperation webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrdersObject");
		GenerateUIFromWebServiceProperties generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(true);
		//Drag and drop GetEmployeesObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		xformDesigner.focus();

		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetProductsObjects");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.setSelectRequiredModelChoice(null);
		//Drag and drop GetProductsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		xformDesigner.focus();
		tabGroup.click();
		Tabs tabgroup= xformDesigner.findElement(Tabs.class,"cwsLeftRegionTab" );		
		
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetEmployeesObjects");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		//generateUIProperties.setBOutputUI(true);
		generateUIProperties.setSelectRequiredModelChoice(null);
		//Drag and drop GetOrdersObject
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+500);
		this.getContext().waitForIdle();
		xformDesigner.focus();
					
		Div ordersGroup = xformDesigner.findElement(Div.class, "groupbox1");
		IHTMLElement ordersHead = ordersGroup.findElement("__boxHeaderElement");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Control bar/Delete control bar");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Pagination bar/Delete pagination bar");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Layout/Horizontal");
		
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrder_x0020_DetailsObjectsForOrderID");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.chooseModelForAssociation("r_option0");//To be associated to the orders model created as very first(starts with zero for model association option) model
		//Drag and drop GetOrder_x0020_DetailsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		Point p = new Point(3,4);
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Select");
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Properties");
		IApplication propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		ControlsPropertyView contolPropertiesView = new ControlsPropertyView(xformDesigner,propertiesApp);
		contolPropertiesView.setDataSetModelContent("ProductsModel", "ProductID", "ProductName");
		//xformDesigner.setModelAssociation("Order_x0020_DetailsModel", "OrderID");
		propertiesApp.close();
		tabgroup.selectTab("_toolbox_"+xformDesigner.getId());	
		xformDesigner.dragControl(ToolBox.TABPAGE,"tabstrip");
		tabStrip.getContext().executeJavascript(tabStrip.findNearByElement(".lastChild.firstChild").getHTMLPointer()+".innerHTML=\"Existing Order for Customer\"");
		tabStrip.findNearByElement(".lastChild").click();
		
		tabgroup.selectTab("workspaceAppPaletteId_"+xformDesigner.getId());
		xformDesigner.focus();
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrdersObjectsForCustomerID");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.setSelectRequiredModelChoice(ModelAssociation.CREATE_NEW_MODEL);
		generateUIProperties.chooseModelForAssociation("r_option0");
		//Drag and drop GetOrder_x0020_DetailsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		this.getContext().waitForIdle();
		
		IHTMLElement orderTable = xformDesigner.findElement(HTMLElement.class,"table2");
		orderTable.findElement(HTMLElement.class,"xo__xInput__54").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__57").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__59").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__60").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__61").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__62").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__63").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__64").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__65").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__66").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		orderTable.findElement(HTMLElement.class,"xo__xInput__53").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		//ordersGroup.findElement("xo__xInput__55").getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Change To/Output");
		orderTable.findElement(HTMLElement.class,"xo__xInput__56").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		orderTable.findElement(HTMLElement.class,"xo__xInput__58").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		orderTable.findElement(HTMLElement.class,"xo__xInput__55").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Select");
		orderTable.findElement(HTMLElement.class,"xo__xInput__55").getContextMenuwithMouse(p).clickOnMenuItem("Properties");
		propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		contolPropertiesView = new ControlsPropertyView(xformDesigner,propertiesApp);
		contolPropertiesView.setDataSetModelContent("EmployeesModel", "EmployeeID", "FirstName");
		propertiesApp.close();
		xformDesigner.focus();
		//Drag and drop GetOrder_x0020_DetailsObjects
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrder_x0020_DetailsObjectsForOrderID");
		Assert.assertNotNull(webservice);
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.setSelectRequiredModelChoice(ModelAssociation.CREATE_NEW_MODEL);
		generateUIProperties.chooseModelForAssociation("r_option4");
		
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		//uncheck Discount
		xformDesigner.focus();
		//xformDesigner.setModelAssociation("Order_x0020_Details1Model", "OrderID");
		tabgroup.selectTab("_toolbox_"+xformDesigner.getId());
		
		xformDesigner.dragControl(ToolBox.BUTTON,contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+700);
		xformDesigner.setLabel("button1", "Approve");
		HTMLElement  approveButton = xformDesigner.findElement(HTMLElement.class,"button1");
		Point p1 = new Point();
		p1.x=12;
		p1.y=10;
		approveButton.getContextMenuwithMouse(p1).clickOnMenuItem("Properties");
	
		propertiesApp.findElement(ITextInput.class, "propertyId").setValue("approveButton");
		propertiesApp.close();
		xformDesigner.setEventForControl("approveButton", "selectActivate");
		
		xformDesigner.dragControl(ToolBox.BUTTON,contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+750);
		xformDesigner.setLabel("button2", "Reject");
		//approveButton.click();
		approveButton = xformDesigner.findElement(HTMLElement.class,"button2");
		approveButton.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
		propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		propertiesApp.findElement(ITextInput.class, "propertyId").setValue("rejectButton");
		//approveButton.click();
		xformDesigner.focus();
		xformDesigner.setEventForControl("rejectButton", "selectActivate");
		xformDesigner.focus();
		xformDesigner.save(EastWindArtifacts.XFORM_SALESORDERAPPROVAL,EastWindArtifacts.XFORM_SALESORDERAPPROVAL);
		xformDesigner.close();
	}

	@Test
	@UIUnitTimeout(1200000)
	public void testSalesRepOrderComplete()
	{
		String scriptText = "function button1_Click(eventObject)"+"\\n"+
		"{" +"\\n"+
		"var index = Order_x0020_DetailsTable.getIndex();" +"\\n"+
		" cordys.setNodeText(DiscountDecision_WebserviceOperationModel.getRequest,'.//*[local-name()=\\\"ProductID\\\"]',productid[index].getValue());" +"\\n"+
		" cordys.setNodeText(DiscountDecision_WebserviceOperationModel.getRequest,'.//*[local-name()=\\\"UnitPrice\\\"]',unitprice[index].getValue());" +"\\n"+
		" cordys.setNodeText(DiscountDecision_WebserviceOperationModel.getRequest,'.//*[local-name()=\\\"Quantity\\\"]',quantity[index].getValue());" +"\\n"+
		" DiscountDecision_WebserviceOperationModel.reset();" +"\\n"+
		" discountRangeText.setValue(cordys.getNodeText(DiscountDecision_WebserviceOperationModel.getData(),'.//*[local-name()=\\\"DiscountRange\\\"]'));" +"\\n"+
		" }" +"\\n"+
		" function toolbarSaveButton_BeforeSave(eventObject)" +"\\n"+
		" {" +"\\n"+
		" eventObject.returnValue = false;" +"\\n"+
		" return false;" +"\\n"+
		" }" +"\\n"+
		" var selectedOrderDetRowIdx;" +"\\n"+
		" function Order_x0020_DetailsTable_OnSelectRow(eventObject)" +"\\n"+
		" {" +"\\n"+
		" selectedOrderDetRowIdx = eventObject.rowIndex;" +"\\n"+
		"}";
		
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder xformFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm xform = xformFolder.addDocument(IXForm.class);
		this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IXFormEditor xformDesigner = xform.openEditor();
		
		Div editorDiv = xformDesigner.findElement(Div.class, "content");
		int contentDivOffsetLeft = editorDiv.getScreenXCoordinate();
		int contentDivOffsetHeight = editorDiv.getScreenYCoordinate();
		int contentOffsetLeft = editorDiv.getElementOffsetLeftRelativeToCordysRoot();
		int contentOffsetHeight = editorDiv.getElementOffsetTopRelativeToCordysRoot();
		
		Tabs tabgroup= xformDesigner.findElement(Tabs.class,"cwsLeftRegionTab" );
		xformDesigner.insertScript(scriptText);
		
		IUIProject eastwindproject = xformDesigner.getProjectFromWorkspaceDockedView("Sales", "EastWind");
		IUICWSFolder dbSchemaFolder = eastwindproject.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA);
		IUICWSFolder packageFolder = dbSchemaFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WSAPPS_PACKAGE_FOLDER_NORTHWIND);		
		IUICWSFolder wsapps = packageFolder.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.WS_FOLDER_NORTHWIND);
		IUIWebServiceDefinitionSet webServiceDefinitionSet = wsapps.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_NORTHWIND);
		IUIWebServiceInterface webServiceInterface = webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_NORTHWIND);	
		IUIWebServiceOperation webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrdersObject");
		GenerateUIFromWebServiceProperties generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(true);
		//Drag and drop GetOrdersObject
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+300);
		
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetProductsObjects");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setIsFirstMethod(false);
		//Drag and drop GetProductsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		xformDesigner.focus();
		
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "GetOrder_x0020_DetailsObjectsForOrderID");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setBOutputUI(true);
		generateUIProperties.setIsFirstMethod(false);
		generateUIProperties.chooseModelForAssociation("r_option0");
		//Drag and drop GetOrder_x0020_DetailsObjects
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+400);
		
		wsapps = eastwindproject.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDECISIONCASE);
		webServiceDefinitionSet = wsapps.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_DECISIONCASE);
		webServiceInterface =  webServiceDefinitionSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_DECISIONCASE);
		webservice = webServiceInterface.getExistingChildDocument(IUIWebServiceOperation.class, "DiscountDecision_WebserviceOperation");
		generateUIProperties = new GenerateUIFromWebServiceProperties(webservice);
		generateUIProperties.setIsFirstMethod(true);
		xformDesigner.dragMethod(generateUIProperties, contentOffsetLeft+contentDivOffsetLeft/2, (contentOffsetHeight+contentDivOffsetHeight/2)+500);
		
		Div ordersGroup = xformDesigner.findElement(Div.class, "groupbox1");
		IHTMLElement ordersHead = ordersGroup.findElement("__boxHeaderElement");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Control bar/Delete control bar");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Pagination bar/Delete pagination bar");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Layout/Horizontal");
		ordersHead.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Set Default Size");
		this.getContext().waitForIdle();
		Point p = new Point(3,4);
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__15").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__16").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__17").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__18").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__19").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__20").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__21").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__22").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__23").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__24").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__25").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__26").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__27").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");
		ordersGroup.findElement(HTMLElement.class,"xo__xInput__28").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Output");

		xformDesigner.focus();

		//xformDesigner.setModelAssociation("Order_x0020_DetailsModel", "OrderID");
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Change To/Select");
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__35").getContextMenuwithMouse(p).clickOnMenuItem("Properties");
		IApplication propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		ControlsPropertyView contolPropertiesView = new ControlsPropertyView(xformDesigner,propertiesApp);
		contolPropertiesView.setDataSetModelContent("ProductsModel", "ProductID", "ProductName");
		propertiesApp.close();
		xformDesigner.setEventForControl("table1", "selectOnRowSelect");
		xformDesigner.setEventForControl("toolbarSaveButton", "selectBeforeSave");
		tabgroup.selectTab("_toolbox_"+xformDesigner.getId());
		xformDesigner.dragControl(ToolBox.INPUT);
		xformDesigner.setLabel("input39", "Possible Discount Range");
		IHTMLElement discountRangeText = xformDesigner.findElement(Div.class, "input39");
		//discountRangeText.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Label/Side-Left");
		discountRangeText.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
		propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		propertiesApp.findElement(ITextInput.class, "propertyId").setValue("discountRangeText");
		xformDesigner.dragControl(ToolBox.BUTTON);
		xformDesigner.setLabel( "button1", "Check Discount");
		xformDesigner.setEventForControl("button1", "selectActivate");

		//xformDesigner.createModel("DummyUpdate", "DummyUpdate");

		/*String xmlText = "<xml>" +
					"  <xml id=\"cobocRequest\">" +
					"    <SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\" url=\"com.eibus.web.soap.Gateway.wcp?organization=o%3Dsystem%2Ccn%3Dcordys%2Ccn%3Ddefaultinst%2Co%3Dvanenburg.com&amp;messageOptions=0\">" +
					"      <SOAP:Body>" +
					"        <TestRuleExecution xmlns=\"http://schemas.cordys.com/ruleengine/4.2\">" +
					"						<ruletypes xmlns=\"http://schemas.cordys.com/ruleengine/4.2\">" +
					"            <ruletype>constraint</ruletype>" +
					"          </ruletypes>" +
					"          <template_id xmlns=\"http://schemas.cordys.com/ruleengine/4.2\">c28e6132-47be-4cca-9826-49e4bb40948d</template_id>" +
					"          <operation xmlns=\"http://schemas.cordys.com/ruleengine/4.2\">insert</operation>" +
					"          <execute_actions xmlns=\"http://schemas.cordys.com/ruleengine/4.2\">true</execute_actions>" +
					"          <objects xmlns=\"http://schemas.cordys.com/ruleengine/4.2\">" +
					"            <sch:OrderDetailsSalesOrd xmlns:sch=\"http://schemas.cordys.com/\">" +
					"              <sch:OrderID>10555</sch:OrderID>" +
					"              <sch:ProductID>1</sch:ProductID>" +
					"              <sch:UnitPrice>14.40</sch:UnitPrice>" +
					"              <sch:Quantity>35</sch:Quantity>" +
					"              <sch:Discount />" +
					"              <sch:DiscountRange>string</sch:DiscountRange>" +
					"            </sch:OrderDetailsSalesOrd>" +
					"          </objects>" +
					"        </TestRuleExecution>" +
					"     </SOAP:Body>" +
					"    </SOAP:Envelope>" +
					"  </xml>" +
					"</xml>";
			xformDesigner.insertXML( xmlText);*/
		
		xformDesigner.findElement(HTMLElement.class,"xo__xInput__34").getContextMenuwithMouse(p).clickOnMenuItem("Delete");
		xformDesigner.focus();
		xformDesigner.save(EastWindArtifacts.XFORM_SALESORDERCOMPLETE,EastWindArtifacts.XFORM_SALESORDERCOMPLETE);
		xformDesigner.close();
	}

	@Test
	@UIUnitTimeout(1200000)
	public void createNotifyApplicationServiceUI(){
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder xformFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
		IXForm xform = xformFolder.addDocument(IXForm.class);
		this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IXFormEditor xformDesigner = xform.openEditor();	
		String input1 = xformDesigner.dragControl(ToolBox.INPUT);
		xformDesigner.setLabel(input1, "[Header]");
		String input2 = xformDesigner.dragControl(ToolBox.INPUT);
		xformDesigner.setLabel(input2, "[Task]");
		Div textField1 = xformDesigner.findElement(Div.class, "input1");
		textField1.click();
		textField1.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
		IApplication propertiesApp = ApplicationFactory.findFirstApplicationWhereIDStartsWith(xformDesigner.getContext(), "propertiesXForm");
		propertiesApp.findElement(ITextInput.class, "propertyId").setValue("taskHeader");
		propertiesApp.close();
		Div textField2 = xformDesigner.findElement(Div.class, input2);
		textField2.click();
		textField2.getContextMenu(ICordysContextMenu.class).clickOnMenuItem("Properties");
		propertiesApp.findElement(ITextInput.class, "propertyId").setValue("taskDescription");
		propertiesApp.close();
		xformDesigner.save(EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE,EastWindArtifacts.XFORM_NOTIFYAPPLICATIONSERVICE);
		xformDesigner.close();
	}
}
