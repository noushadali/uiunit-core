package com.cordys.uiunit.eastwind.designtime.bam;

import junit.framework.TestCase;

import org.junit.Test;

import com.cordys.bam.cwsutilities.bmutils.IUIBM;
import com.cordys.bam.cwsutilities.bmutils.IUIBMWizard;
import com.cordys.bam.cwsutilities.ccutils.internal.KPICompositeControlUtils;
import com.cordys.bam.cwsutilities.kpiutils.IUIKPI;
import com.cordys.bam.cwsutilities.kpiutils.IUIKPIWizard;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;
import com.cordys.xform.cwsutilities.IXForm;
import com.cordys.xform.cwsutilities.internal.ControlsPropertyView;
import com.cordys.xform.cwsutilities.internal.XFormEditor;

public class OrdersByPeriodTrendToVP extends WorkSpaceOrganizer{

	@Test
	@UIUnitTimeout(1000000)
	public void createOrdersByPeriodTrendToVP() throws Exception
	{
		String bmName = "OrdersByPeriodBusinessMeasureOfTypeKPI";
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BAM_BM);
		project.refresh();
		IUIBMWizard bmWizard = (IUIBMWizard) folder.addDocumentWithWizard(IUIBM.class);
		bmWizard.setMeasureName(bmName);
		bmWizard.setDescriptionText("No of orders by week or month or year business measure");
		bmWizard.selectMonitoringObject("MonitoringObjectOnOrderEntryProcess");
		bmWizard.selectMeasureType("kpi");
		bmWizard.checkProcessDate();
		if(!bmWizard.isProcessStartTimeSelected())
		{
			bmWizard.checkProcessStartTime();
		}
		if(!bmWizard.isProcessEndTimeSelected())
		{
			bmWizard.checkProcessEndTime();
		}
			
		bmWizard.clickNext();
		
		String selectClause = "Count(OrderID) TotalOrders";
		bmWizard.setSelectClause(selectClause);
		
		bmWizard.clickNext();
		bmWizard.getContext().waitForIdle();
		bmWizard.selectRollingdates("12", "Month(s)", "inprogress");
		bmWizard.clickFinish();			

		bmWizard.close();
		bmWizard.stopTime(10000);
		//Assert.fail("Creating Measure of type graph with rolling dates type inprogress on process dates of MO failed :"+e.getMessage());


		if(bmWizard!=null)
		{
			bmWizard.close();
		}
		
		this.getContext().waitForIdle();
		IUICWSFolder kpiFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BAM_KPI);
		IUIKPIWizard wiz = (IUIKPIWizard)kpiFolder.addDocumentWithWizard(IUIKPI.class);
		String kpiName = "TotalOrdersTrendKPI";
		wiz.setName(kpiName);
		
		wiz.setGoal("my goal");
		wiz.setUnitOfMeasure("Integer");
		wiz.setTargetValue("100");
		wiz.insertRange(1, "Low", "10", "50");
		wiz.insertRange(2, "Moderate", "50", "100");
		wiz.insertRange(3, "High", "100", "200");
			
		wiz.clickNext();
    	try 
		{
			  Thread.sleep(4000);
		} 
		catch (InterruptedException e) 
		{
		  TestCase.fail();
		}
		wiz.addMeasure(bmName);			
		wiz.clickNext();
			
		try 
		{
		  Thread.sleep(4000);
		} 
		catch (InterruptedException e) 
		{
		  TestCase.fail();
		}
		
		wiz.clickOnMeasure(bmName+"/TotalOrders");
			/*wiz.checkRadioForExistingWebServiceDefinitionSet();
			wiz.clickNext();
			try 
			{
			  Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
			  TestCase.fail();
			}
			wiz.setScheduleTypeAsWeekly();
			wiz.fillDaysHoursMinutesForWeeklySchedule("Saturday", "10", "30");
			wiz.checkDefineActions();
			wiz.checkEmailModelCheckBox();
			wiz.clickNext();
			
			
			this.getContext().waitForIdle();			
			wiz.doubleClickOnBody();
			wiz.dropProcessedValueOnEmailTemplate(kpiName);
			this.getContext().waitForIdle();		
			wiz.fillSendToReceipient("V Vijay");
		    wiz.fillSendToEmailAddress("vvijay@cordys.com");
			wiz.clickNext();
			
			this.getContext().waitForIdle();
			wiz.clickOnAddRule();
			wiz.configureNotificationAction("VPSales");
			wiz.uncheckAction("Invoke Business Operation");
			wiz.uncheckAction("Call Web Service Operation");*/		
			
			wiz.clickFinish();

		
		//creatning dashboard on top of KPI
			this.getContext().waitForIdle();
			IUICWSFolder dashboardFolder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_BAM_DASHBOARDS);
			IXForm xform= dashboardFolder.addDocument(IXForm.class);
			getProject().getCWSIDE().getWorkspaceDocumentsView().focus();
			XFormEditor editor = (XFormEditor) xform.openEditor();
			String elementId = editor.insertCompositeControl(kpiName);
			
			ControlsPropertyView element = null;
			KPICompositeControlUtils kpiView =null;
			
			element = editor.getProperties(elementId);	
			kpiView = new KPICompositeControlUtils(element,editor);
			
					
			kpiView.selectViewType(EastWindArtifacts.KPI_TYPE_ANGULAR);
					
			//kpiView.setRollingDateType("inprogress");
			
			editor.save(kpiName+"Graph",kpiName);
			kpiView.closePropertySheet();	
			editor.save();				
			kpiView.closePropertySheet();
			editor.close();		
		
		
		}
}
