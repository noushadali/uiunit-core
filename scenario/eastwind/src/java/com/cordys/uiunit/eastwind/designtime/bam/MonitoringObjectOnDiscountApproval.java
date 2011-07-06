package com.cordys.uiunit.eastwind.designtime.bam;

import org.junit.Test;

import com.cordys.bam.cwsutilities.moutils.IUIMO;
import com.cordys.bam.cwsutilities.moutils.IUIMOWizard;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class MonitoringObjectOnDiscountApproval extends WorkSpaceOrganizer{

	@Test
	@UIUnitTimeout(1200000)
	public void createDiscountApprovalMO() throws InterruptedException{
		
		String moName = "MonitoringObjectOnDiscountApproval";
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
				
				moWiz.selectBusinessProcessForMO(EastWindArtifacts.BPM_DISCOUNTAPPROVAL);
								
				//validation for checking the BPM Read only view is enabled or not. 
				moWiz.checkReadOnlyViewOfBPM();
				Assert.assertTrue(moWiz.getReadOnlyViewOfBPM());
				moWiz.uncheckReadOnlyViewOfBPM();
				Assert.assertFalse(moWiz.getReadOnlyViewOfBPM());
				
				moWiz.selectActivitybyName("SalesRepOrderComplete - end");					
				
				moWiz.clickNext();
				this.getContext().waitForIdle();
				// filling of attribute info in second screen.		
				// selecting attributes for Process Start
				moWiz.selectActivityForAttributeFilling("SalesRepOrderComplete - end");
				moWiz.selectAttributeInfo("OrderID");
				moWiz.selectAttributeInfo("Discount *");
				moWiz.selectAttributeInfo("OrderDate");
				moWiz.selectAttributeInfo("ShippedDate");
				
				moWiz.clickNext();		

				moWiz.clickFinish();
				
			}catch (Exception e) {
				//moWiz.close();
				Assert.fail("Creation of MO with Process attribtues is failed :- "+e.getMessage());
			}
	}
}
