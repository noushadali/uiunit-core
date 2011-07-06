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

public class OrdersByCustomerBusinessMeasure extends WorkSpaceOrganizer{

	@Test
	@UIUnitTimeout(1000000)
	public void createOrdersByCustomerBusinessMeasure() throws Exception
	{
		String bmName = "OrdersByCustomerBusinessMeasure";
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BAM_BM);
		project.refresh();
		IUIBMWizard bmWizard = (IUIBMWizard) folder.addDocumentWithWizard(IUIBM.class);

		try
		{
			bmWizard.setMeasureName(bmName);
			bmWizard.setDescriptionText("No of orders by Customer ID for graph");
			bmWizard.selectMonitoringObject("MonitoringObjectOnOrderEntryProcess");
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
			
			String selectClause = "Count(OrderID) TotalNoOfOrders,CustomerID";
			bmWizard.setSelectClause(selectClause);
			String groupClause = "CustomerID";
			bmWizard.setGroubyField(groupClause);
			
			bmWizard.clickNext();
			bmWizard.getContext().waitForIdle();
			bmWizard.selectRollingdates("6", "Week(s)", "inprogress");
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
		try{
			this.getContext().waitForIdle();
			IUICWSFolder dashboardFolder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_BAM_DASHBOARDS);
			IXForm xform= dashboardFolder.addDocument(IXForm.class);
			getProject().getCWSIDE().getWorkspaceDocumentsView().focus();
			XFormEditor editor = (XFormEditor) xform.openEditor();
			String elementId = editor.insertCompositeControl(bmName);
			
			ControlsPropertyView element = null;
			BusinessMeasureCompositeControlUtils bmView =null;
			
			element = editor.getProperties(elementId);	
			bmView = new BusinessMeasureCompositeControlUtils(element,editor);
			
					
			bmView.selectChartType(EastWindArtifacts.GRAPH_TYPE_STACKED);
			bmView.selectXAxisField("customerid",1);
			
			bmView.selectYAxisField("totalnoofOrders", true);
					
				
			editor.save(bmName+"Graph","OrdersByCustomerBusinessMeasure");
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
