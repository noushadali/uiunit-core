package com.cordys.uiunit.eastwind.mdm;

import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageUsers;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;

public class AssignRoles extends UIUnitTestCase{
	IManageUsers manageUsers = null;
	
	 
	@Test
	@UIUnitTimeout(1200000)
	public void assignRoles()
	{
	 	manageUsers=CordysRuntimeApplications.startFromCUSP(this.getContext(),IManageUsers.class);
		Assert.assertNotNull("Manage users page not found",manageUsers);
		manageUsers.maximize();
		manageUsers.waitForIdle();
		manageUsers.addRoleToUser(getContext().getRootContext().getTestEnvironment().getConfiguration().getIdentity().getUserName(), MDMConstants.MDM_ADMIN_ROLE);
	}
	/*
	@UIUnitTimeout(1200000)
	//@Test
	public void attachMethodSetToRunTimeWSApp()
	{
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.maximize();
		manageResources.waitForIdle();
		ISoapNodeProperties SNpropertiesPage= (ISoapNodeProperties)manageResources.getSoapNodeProperties(MDMConstants.MDM_RUNTIME_WSAPP);
		Assert.assertNotNull("not found",SNpropertiesPage);
		SNpropertiesPage.addMethodSet("mdm.databasemetadata.RuntimeDatabaseWebService.WebServiceInterfaceRunTimeDatabaseMetaData","system");
		manageResources.close();
	}
	*/
}
