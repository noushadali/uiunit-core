package com.cordys.uiunit.eastwind.designtime.bam;

import org.junit.Before;
import org.junit.Test;

import com.cordys.bpm.cwsutilities.IUIBPM;
import com.cordys.bpm.cwsutilities.IUIBPMDesigner;
import com.cordys.bpm.cwsutilities.IUIBPMMessageFilter;
import com.cordys.cm.uiunit.elements.cordys.ITreeItem;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class MessageFilteringOfProcessForBAM extends WorkSpaceOrganizer{

	IUIProject project ;
	IUICWSFolder bpmFolder; 
	@Before
	@UIUnitTimeout(1800000)
	public void initializeEnvironment(){
		
		project = getProject();
		bpmFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BPM);
	}
	@Test
	@UIUnitTimeout(1000000)
	public void messageFilteringOnOrderEntryProcess() throws Exception
	{
		IUIBPM orderEntryProcess = bpmFolder.getExistingChildDocument(IUIBPM.class, EastWindArtifacts.BPM_ORDERENTRYPROCESS);
		orderEntryProcess.openDesigner();
		this.getContext().waitForIdle();
		IUIBPMDesigner bpmDesigner = orderEntryProcess.getDesigner();
		//Message filtering for BAM Artifacts
		IUIBPMMessageFilter messageFilterView = bpmDesigner.switchToMessageFilter();
		ITreeItem filterAttributeItem = null;
		
		//filtering the updateorders activity
		messageFilterView.selectArtifactItem("UpdateOrders");
		filterAttributeItem = messageFilterView.findActivityTreeItem("UpdateOrders", "ns2:UpdateOrdersOutput/ns2:UpdateOrdersResponse/ns2:tuple/ns2:new/ns2:Orders/ns2:OrderID");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("UpdateOrders", "ns2:UpdateOrdersOutput/ns2:UpdateOrdersResponse/ns2:tuple/ns2:new/ns2:Orders/ns2:CustomerID");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("UpdateOrders", "ns2:UpdateOrdersOutput/ns2:UpdateOrdersResponse/ns2:tuple/ns2:new/ns2:Orders/ns2:EmployeeID");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("UpdateOrders", "ns2:UpdateOrdersOutput/ns2:UpdateOrdersResponse/ns2:tuple/ns2:new/ns2:Orders/ns2:OrderDate");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("UpdateOrders", "ns2:UpdateOrdersOutput/ns2:UpdateOrdersResponse/ns2:tuple/ns2:new/ns2:Orders/ns2:ShippedDate");
		messageFilterView.createMessageFilter(filterAttributeItem);
		
		//filterign updateorder_x0020_details
		messageFilterView.selectArtifactItem("UpdateOrder_x0020_Details");
		filterAttributeItem = messageFilterView.findActivityTreeItem("UpdateOrder_x0020_Details", "ns2:UpdateOrder_x0020_DetailsOutput/ns2:UpdateOrder_x0020_DetailsResponse/ns2:tuple/ns2:new/ns2:Order_x0020_Details/ns2:ProductID");
		messageFilterView.createMessageFilter(filterAttributeItem);
		
		bpmDesigner.save();
	}
	
	@Test
	@UIUnitTimeout(1000000)
	public void messageFilteringOnDiscountApprovalProcess() throws Exception
	{
		IUIBPM discountApprovalProcess = bpmFolder.getExistingChildDocument(IUIBPM.class, EastWindArtifacts.BPM_DISCOUNTAPPROVAL);
		discountApprovalProcess.openDesigner();
		this.getContext().waitForIdle();
		IUIBPMDesigner bpmDesigner = discountApprovalProcess.getDesigner();
		//Message filtering for BAM Artifacts
		IUIBPMMessageFilter messageFilterView = bpmDesigner.switchToMessageFilter();
		ITreeItem filterAttributeItem = null;
		
		//filtering the SalesRepOrderComplete activity
		messageFilterView.selectArtifactItem("SalesRepOrderComplete");
		filterAttributeItem = messageFilterView.findActivityTreeItem("SalesRepOrderComplete", "ns2:SalesRepOrderComplete_SalesRepOrderCompleteModel_OP/ns2:formoutputdata/ns2:Order_x0020_DetailsModel/ns3:GetOrder_x0020_DetailsObjectsForOrderIDResponse/ns3:tuple/ns3:old/ns3:Order_x0020_Details/ns3:Discount");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("SalesRepOrderComplete", "ns2:SalesRepOrderComplete_SalesRepOrderCompleteModel_OP/ns2:formoutputdata/ns2:OrdersModel/ns3:GetOrdersObjectResponse/ns3:tuple/ns3:old/ns3:Orders/ns3:OrderID");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("SalesRepOrderComplete", "ns2:SalesRepOrderComplete_SalesRepOrderCompleteModel_OP/ns2:formoutputdata/ns2:OrdersModel/ns3:GetOrdersObjectResponse/ns3:tuple/ns3:old/ns3:Orders/ns3:OrderDate");
		messageFilterView.createMessageFilter(filterAttributeItem);
		filterAttributeItem = messageFilterView.findActivityTreeItem("SalesRepOrderComplete", "ns2:UpdateOrdersOutput/ns2:UpdateOrdersResponse/ns2:tuple/ns2:new/ns2:Orders/ns2:ShippedDate");
		messageFilterView.createMessageFilter(filterAttributeItem);
		
		bpmDesigner.save();
		
	}
}

