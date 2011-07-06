package com.cordys.uiunit.eastwind.designtime.bam;

import org.junit.Test;

import com.cordys.bam.cwsutilities.beutils.IUIBE;
import com.cordys.bam.cwsutilities.beutils.IUIBEWizard;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.rule.cwsutilities.actions.Action;
import com.cordys.rule.cwsutilities.actions.ISendNotification;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class SalesRepresentativeDelayNotificationBER extends WorkSpaceOrganizer{

	@Test
	@UIUnitTimeout(1000000)
	public void createBEUsingDelayActivity()
	{
		IUIProject project = getProject();
		IUIBEWizard beWiz = null;
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.BAM_BER);
		project.refresh();
		
		String beName = "SalesCoordinatorDelayNotificationBER";
		
		try
		{
			beWiz = (IUIBEWizard)project.addDocumentWithWizard(IUIBE.class);
			//filling of first screen info
			beWiz.setBEName(beName);
			beWiz.setBEDescription(beName);
			beWiz.selectBusinessProcessForBE(EastWindArtifacts.BPM_ORDERENTRYPROCESS);
			
			String activityName = "Send Task To SalesRepresentative";
			beWiz.selectActivitybyName(activityName + " - start");
			beWiz.selectActivitybyName(activityName + " - end");

			beWiz.clickNext();

			//filling of attribute info in second screen.
			// selecting attributes for Process Start
			
			beWiz.selectActivityForAttributeFilling(activityName + " - start"	);

			beWiz.selectLeadtimeOfCurrentActivity(activityName + "leadtime");
			beWiz.clickNext();
			beWiz.clickNext();	

			beWiz.getContext().waitForIdle();
			
			beWiz.setDecisionTable();
			ISendNotification notification = Action.getNotificationAction("SalesCoordinatorNotification");
			notification.setHostingFrame(beWiz.getDecisionTableFrame());
			
			notification.setMessage("<xml/>");
			notification.setRoles("VPSales","SalesManager");
			notification.setUrl("http:\\www.cordys.com");
			notification.setDescription("Notification about task delay");
			notification.setSubject("Sales coordinator delayed task for more than 2 days");
			beWiz.addAction(notification, 1);
			
			beWiz.clickFinish();				
			
		}catch(Exception e)
		{
			beWiz.close();
			Assert.fail("Creation of BE with Email Model is failed with Error:- " +e.getMessage());
		}
	}
}
