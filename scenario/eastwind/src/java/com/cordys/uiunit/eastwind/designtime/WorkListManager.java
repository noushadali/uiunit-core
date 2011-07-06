package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.notification.cwsutilities.IWorklist;
import com.cordys.notification.cwsutilities.IWorklistEditor;

@UIUnitTimeout(1200000)
public class WorkListManager  extends WorkSpaceOrganizer {
	IUIProject project = null;
	IUICWSFolder worklistsFolder=null; 
	IUICWSFolder organizationFolder = null;	
	
	@Test
	public void testSalesCoordinatorWorkList()
	{
	 project = getProject();
	 worklistsFolder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_WORKLISTS);
	 IWorklist salesCoordinatorWLCW= (IWorklist) worklistsFolder.addDocument(IWorklist.class);
	 IWorklistEditor salesCoordinatorWorkListEditor=salesCoordinatorWLCW.openEditor();
	 salesCoordinatorWorkListEditor.maximize();
	 String salesDiv = "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION;
	 salesCoordinatorWorkListEditor.addTeamFromTree(salesDiv,EastWindArtifacts.ORGANIZATIONMODEL_EAST);	 

	 /*
	  //Sample code to set worklistmanager
	 organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
	 IUIOrgUnitDesigntime 	salesDivision = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class,EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION);	 
	 salesCoordinatorWorkListEditor.addWorklistManager(salesDivision,"President");
	 */	 
	 salesCoordinatorWorkListEditor.save(EastWindArtifacts.WORKLISTS_SALESCOORDINATOR,EastWindArtifacts.WORKLISTS_SALESCOORDINATOR);
	 salesCoordinatorWorkListEditor.close();	
	}
	
	@Test
	public void testSalesRepresentativeWorkList()
	{
		project = getProject();
		 worklistsFolder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_WORKLISTS);
		IWorklist salesRepresentativeWLCW= (IWorklist) worklistsFolder.addDocument(IWorklist.class);
		IWorklistEditor salesRepresentativeWorkListEditor=salesRepresentativeWLCW.openEditor();
		salesRepresentativeWorkListEditor.maximize();
		String easternRegion = "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION + "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION ;
		salesRepresentativeWorkListEditor.addTeamFromTree(easternRegion,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		String southernRegion =  "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION;
		salesRepresentativeWorkListEditor.addTeamFromTree(southernRegion,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		String northernRegion = "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION + "/" + EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION;
		salesRepresentativeWorkListEditor.addTeamFromTree(northernRegion,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		salesRepresentativeWorkListEditor.save(EastWindArtifacts.WORKLISTS_SALESREPRESENTATIVE,EastWindArtifacts.WORKLISTS_SALESREPRESENTATIVE);
		salesRepresentativeWorkListEditor.close();		 
	}
	@Test
	public void testManagerWorkList()
	{
		project = getProject();
	    worklistsFolder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_WORKLISTS);
		IWorklist managerWLCW= (IWorklist) worklistsFolder.addDocument(IWorklist.class);
		IWorklistEditor managerWorkListEditor=managerWLCW.openEditor();
		managerWorkListEditor.maximize();		
		String easternRegion = "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION + "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION ;
		managerWorkListEditor.addTeamFromTree(easternRegion,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		String southernRegion =  "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION;
		managerWorkListEditor.addTeamFromTree(southernRegion,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		String northernRegion = "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION + "/" + EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION;
		managerWorkListEditor.addTeamFromTree(northernRegion,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		managerWorkListEditor.save(EastWindArtifacts.WORKLISTS_MANAGER,EastWindArtifacts.WORKLISTS_MANAGER);
		managerWorkListEditor.close();
	}
	@Test
	public void testVPSalesWL()
	{
		project = getProject();
		worklistsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS);
		IWorklist vpSalesWLCW= (IWorklist) worklistsFolder.addDocument(IWorklist.class);
		IWorklistEditor vpSalesWorkListEditor = vpSalesWLCW.openEditor();
		vpSalesWorkListEditor.maximize();
		String salesDiv = "/" + EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG + "/" + EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION;
		vpSalesWorkListEditor.addTeamFromTree(salesDiv,EastWindArtifacts.ORGANIZATIONMODEL_EAST);		
		vpSalesWorkListEditor.save(EastWindArtifacts.WORKLISTS_VPSALES,EastWindArtifacts.WORKLISTS_VPSALES);
		vpSalesWorkListEditor.close();
	}
	@Test
	public void testCustomerWL()
	{
		project = getProject();
	    worklistsFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS);
		IWorklist customerWLCW = (IWorklist) worklistsFolder.addDocument(IWorklist.class);
		IWorklistEditor customerWorkListEditor=customerWLCW.openEditor();
		customerWorkListEditor.maximize();
	    String customerDiv = "/" + EastWindArtifacts.ORGANIZATIONUNIT_CUSTOMER;
	    customerWorkListEditor.addTeamFromTree(customerDiv,null);
		 customerWorkListEditor.save(EastWindArtifacts.WORKLISTS_CUSTOMER,EastWindArtifacts.WORKLISTS_CUSTOMER);
		 customerWorkListEditor.close();
	}
	

}
