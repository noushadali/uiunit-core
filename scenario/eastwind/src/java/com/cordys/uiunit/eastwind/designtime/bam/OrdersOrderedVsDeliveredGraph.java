package com.cordys.uiunit.eastwind.designtime.bam;

import org.junit.Test;

import com.cordys.bam.cwsutilities.bmutils.IUIBM;
import com.cordys.bam.cwsutilities.bmutils.IUIBMWizard;
import com.cordys.bam.cwsutilities.ccutils.internal.BusinessMeasureCompositeControlUtils;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;
import com.cordys.xform.cwsutilities.IXForm;
import com.cordys.xform.cwsutilities.internal.ControlsPropertyView;
import com.cordys.xform.cwsutilities.internal.XFormEditor;

public class OrdersOrderedVsDeliveredGraph extends WorkSpaceOrganizer{
	
	@Test
	@UIUnitTimeout(1000000)
	public void createOrdersOrderedVsDeliveredGraph() throws Exception
	{
		// creating bm for the orders where discount < 20 %
		String orderedBm = "OrdersOrderedByMonthMeasure";

		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BAM_BM);
		project.refresh();
		IUIBMWizard bmWizard = (IUIBMWizard) folder.addDocumentWithWizard(IUIBM.class);

		try
		{
			bmWizard.setMeasureName(orderedBm);
			bmWizard.setDescriptionText(orderedBm);
			bmWizard.selectMonitoringObject("MonitoringObjectOnDiscountApproval");
			bmWizard.selectMeasureType("graph");
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
			
			String selectClause = "Count(OrderID) TotalOrdered,Month(OrderDate) Month";
			bmWizard.setSelectClause(selectClause);
		
			String groupClause = "Month(OrderDate)";
			bmWizard.setGroubyField(groupClause);
			
			bmWizard.clickNext();
			bmWizard.getContext().waitForIdle();
			bmWizard.selectRollingdates("12", "Month(s)", "inprogress");
			bmWizard.clickFinish();			

		}catch (Exception e) {
			bmWizard.close();
			bmWizard.stopTime(10000);
			Assert.fail("Creating Measure of type Graph with rolling dates type inprogress on process dates of MO failed :"+e.getMessage());
		}
		finally
		{
			if(bmWizard!=null)
			{
				bmWizard.close();
			}
		}
		
		
		// creating bm for the orders where discount > 20 %
		String deliveredBm = "OrdersDeliveredByMonthMeasure";
		this.getContext().waitForIdle();
		project.refresh();
		bmWizard = (IUIBMWizard) folder.addDocumentWithWizard(IUIBM.class);
		try
		{
			bmWizard.setMeasureName(deliveredBm);
			bmWizard.setDescriptionText(deliveredBm);
			bmWizard.selectMonitoringObject("MonitoringObjectOnDiscountApproval");
			bmWizard.selectMeasureType("graph");
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
			
			String selectClause = "Count(OrderID) TotalDelivered,Month(ShippedDate) Month";
			bmWizard.setSelectClause(selectClause);
			String groupClause = "Month(ShippedDate)";
			bmWizard.setGroubyField(groupClause);
			bmWizard.clickNext();
			bmWizard.getContext().waitForIdle();
			bmWizard.selectRollingdates("12", "Month(s)", "inprogress");
			bmWizard.clickFinish();			

		}catch (Exception e) {
			bmWizard.close();
			bmWizard.stopTime(10000);
			Assert.fail("Creating Measure of type Graph with rolling dates type inprogress on process dates of MO failed :"+e.getMessage());
		}
		finally
		{
			if(bmWizard!=null)
			{
				bmWizard.close();
			}
		}
		
		
		//creating the composite bm
		String compositeName = "OrdersOrderedVsOrdersDeliveredCompositeMeasure";
		this.getContext().waitForIdle();
		project.refresh();
		bmWizard = (IUIBMWizard) folder.addDocumentWithWizard(IUIBM.class);
		try
		{
			bmWizard.setMeasureName(compositeName);
			bmWizard.setDescriptionText(compositeName);
			bmWizard.selectMeasureType("graph");
			String compsiteBMs[] = {orderedBm, deliveredBm};
			bmWizard.selectBusinessMeasures(compsiteBMs);
			
			bmWizard.clickNext();
			
			bmWizard.clickFinish();			

		}catch (Exception e) {
			bmWizard.close();
			bmWizard.stopTime(10000);
			Assert.fail("Creating Measure of type Graph with rolling dates type inprogress on process dates of MO failed :"+e.getMessage());
		}
		finally
		{
			if(bmWizard!=null)
			{
				bmWizard.close();
			}
		}
		
		
		
		//creatning dashboard on top BM
		try{
			
			IUICWSFolder dashboardFolder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_BAM_DASHBOARDS);
			IXForm xform= dashboardFolder.addDocument(IXForm.class);
			getProject().getCWSIDE().getWorkspaceDocumentsView().focus();
			XFormEditor editor = (XFormEditor) xform.openEditor();
			String elementId = editor.insertCompositeControl(compositeName);
			
			ControlsPropertyView element = null;
			BusinessMeasureCompositeControlUtils bmView =null;
			
			element = editor.getProperties(elementId);	
			bmView = new BusinessMeasureCompositeControlUtils(element,editor);
			
					
			bmView.selectChartType(EastWindArtifacts.GRAPH_TYPE_STACKED);
			bmView.selectXAxisField("Month",1);
						
			bmView.selectYAxisField("TotalDelivered", true);
			bmView.selectYAxisField("TotalOrdered", true);
					
				
			editor.save(compositeName+"Graph",compositeName);
			bmView.closePropertySheet();	
			editor.save();				
			bmView.closePropertySheet();
			editor.close();		
		
		
		}
		catch (Exception e) {
			// TODO: handle exception
			Assert.fail("Error while validating property sheet for BM Type Graph :"+" : "+e.getMessage());
		}
	}

}
