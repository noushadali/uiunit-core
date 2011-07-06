package com.cordys.uiunit.eastwind.designtime.bam;


import org.junit.Test;
import com.cordys.bam.cwsutilities.beutils.IUIBE;
import com.cordys.bam.cwsutilities.beutils.IUIBEWizard;
import com.cordys.cm.uiunit.elements.cordys.ApplicationFactory;
import com.cordys.cm.uiunit.elements.cordys.IApplication;
import com.cordys.cm.uiunit.elements.cordys.ICordysContextMenu;
import com.cordys.cm.uiunit.elements.cordys.ITree;
import com.cordys.cm.uiunit.elements.cordys.ITreeItem;
import com.cordys.cm.uiunit.elements.cordys.internal.ArtifactViewer;
import com.cordys.cm.uiunit.elements.html.IButton;
import com.cordys.cm.uiunit.elements.html.IFrame;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.rule.cwsutilities.actions.Action;
import com.cordys.rule.cwsutilities.actions.ISendNotification;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class SalesCoordinatorDelayNotificationBE extends WorkSpaceOrganizer{
	
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
			IApplication workspace=ApplicationFactory.findFirstApplicationWhereIDStartsWith(this.getContext(),"WorkspaceDocumentsView");
			IFrame viewFrame=workspace.findElement(IFrame.class,"explorerViewFrame");
			ITree solutionTree=viewFrame.findElement(ITree.class, "StudioIDESolutionExplorerTree");
			ITreeItem scenario=solutionTree.findTreeItemByName("EastWind","BAM_BER");
			//scenario.rightClick();
			
			//Choosing Condition Template from Context Menu
			ICordysContextMenu contextMenu=scenario.getContextMenu(ICordysContextMenu.class);
			if(contextMenu.isMenuItemAvailable("Add Runtime Reference/Condition Template"))
				contextMenu.clickOnMenuItem("Add Runtime Reference/Condition Template");
			else{
				contextMenu.clickOnMenuItem("Add Runtime Reference/Other...");
				IApplication templateApp=ApplicationFactory.findFirstApplicationWhereIDStartsWith(this.getContext(),"NewRuntimeReferenceArtifactView");
				
				templateApp.getContext().waitForIdle();
				ArtifactViewer art=templateApp.findElement(ArtifactViewer.class, "publicDocumentTypesViewer");
				art.getArtifactOnName("Condition Template").click();
			   	this.getContext().waitForIdle();
			}
				
			//finding ConditionTemplateRuntimeWizardView
			IApplication templateApp=ApplicationFactory.findFirstApplicationWhereIDStartsWith(this.getContext(),"ConditionTemplateRuntimeWizardView");
			IFrame explorer=templateApp.findElement(IFrame.class, "frameRuntimeExplorer");
			ITree tree=explorer.findElement(ITree.class,"BinaryReferenceRuntimeExplorerTree");
			
			//getting first level tree items from the wizard
			tree.getAllFirstLevelItems();
				
			ITreeItem child=tree.findTreeItemByName("Cordys Business Activity Monitoring", "LeadTimeGreaterthanEqualto (/BAMConditionTemplates/LeadTimeGreaterthanEqualto)");
			child.click();
			templateApp.findElement(IButton.class, "buttonFinish").click();
			this.getContext().waitForIdle();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		try
		{
			beWiz = (IUIBEWizard)project.addDocumentWithWizard(IUIBE.class);
			//filling of first screen info
			beWiz.setBEName(beName);
			beWiz.setBEDescription(beName);
			beWiz.selectBusinessProcessForBE(EastWindArtifacts.BPM_ORDERENTRYPROCESS);
			
			String activityName = "AssignSalesRep";
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
			notification.setApplication(beWiz);
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
