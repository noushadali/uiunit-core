package com.cordys.uiunit.eastwind.designtime.bam;

import org.junit.Test;
import com.cordys.bam.cwsutilities.moutils.IUIMO;
import com.cordys.bam.cwsutilities.moutils.IUIMOWizard;
import com.cordys.bam.cwsutilities.bmutils.IUIBM;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;
import com.cordys.xform.cwsutilities.IXForm;


public class MonitoringObjectOnOrderEntryProcess extends WorkSpaceOrganizer{
	IUIProject project;
	IUICWSFolder BamFolder;
	IUIMO pmo;
	IUIBM bm;
	IXForm xform;
	
	
	@Test
	@UIUnitTimeout(1200000)
	public void createOrdersMO() throws InterruptedException{
		
		String moName = "MonitoringObjectOnOrderEntryProcess";
		String moDesc = "Attributes of different activities are selected to monitor";
		
		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BAM_PMO);
		project.refresh();
		IUIMOWizard moWiz = (IUIMOWizard)folder.addDocumentWithWizard(IUIMO.class);
		
			// Creation of MO
			try
			{
				//filling of first screen info 
				moWiz.setMOName(moName);
				moWiz.setMODescription(moDesc);
				
				moWiz.selectBusinessProcessForMO(EastWindArtifacts.BPM_ORDERENTRYPROCESS);
								
				//validation for checking the BPM Read only view is enabled or not. 
				moWiz.checkReadOnlyViewOfBPM();
				Assert.assertTrue(moWiz.getReadOnlyViewOfBPM());
				moWiz.uncheckReadOnlyViewOfBPM();
				Assert.assertFalse(moWiz.getReadOnlyViewOfBPM());
				
				moWiz.selectActivitybyName("UpdateOrders - start");
				moWiz.selectActivitybyName("UpdateOrders - end");
				moWiz.selectActivitybyName("UpdateOrder_x0020_Details - start");
				moWiz.selectActivitybyName("UpdateOrder_x0020_Details - end");
				moWiz.selectActivitybyName("AssignSalesRep - start");
				moWiz.selectActivitybyName("AssignSalesRep - end");
				moWiz.selectActivitybyName("Send Task To SalesRepresentative - start");
				moWiz.selectActivitybyName("Send Task To SalesRepresentative - end");
				moWiz.selectActivitybyName("ProductionDBUpdate - start");
				moWiz.selectActivitybyName("ProductionDBUpdate - end");				
				
				moWiz.clickNext();
				this.getContext().waitForIdle();
				// filling of attribute info in second screen.		
				// selecting attributes for Process Start
				String attrOfUpdateOrders[] = {"OrderID *", "CustomerID *", "EmployeeID *", "OrderDate *", "ShippedDate *"};
				moWiz.selectActivityForAttributeFilling("UpdateOrders - end");
				moWiz.selectAttributeInfo(attrOfUpdateOrders);
				
				// selecting attributes for Update order Details 
				moWiz.selectActivityForAttributeFilling("UpdateOrder_x0020_Details - end");
				String attrOfUpdateOrderDetails[] = {"ProductID *"};
				moWiz.selectAttributeInfo(attrOfUpdateOrderDetails);
				
				moWiz.selectActivityForAttributeFilling("AssignSalesRep - start");
				moWiz.selectStatusOfCurrentActivity("AssignSalesRepStatus");
				
				moWiz.selectActivityForAttributeFilling("Send Task To SalesRepresentative - start");
				moWiz.selectStatusOfCurrentActivity("SalesRepresentativeStatus");
				
				moWiz.selectActivityForAttributeFilling("ProductionDBUpdate - start");
				moWiz.selectStatusOfCurrentActivity("ProductionDBUpdateStatus");
				
				
				moWiz.clickNext();		

				moWiz.clickFinish();
		
			}catch (Exception e) {
				//moWiz.close();
				Assert.fail("Creation of MO with Process attribtues is failed :- "+e.getMessage());
			}
	}
}
