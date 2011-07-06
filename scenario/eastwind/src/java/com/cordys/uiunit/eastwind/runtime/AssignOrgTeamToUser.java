package com.cordys.uiunit.eastwind.runtime;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageUsers;
import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class AssignOrgTeamToUser extends UIUnitTestCase{

	
private IManageUsers manageUsers=null;
	
	private String ntdomUser=ConfigurationManager.createConfig().getIdentity().getUserName();
	private String userName=(String)ntdomUser.subSequence(ntdomUser.lastIndexOf('\\')+1, ntdomUser.length());
	@Before
	public void setup()
	{
		manageUsers=CordysRuntimeApplications.startFromCUSP(this.getContext(),IManageUsers.class);
		manageUsers.maximize();
		manageUsers.waitForIdle();
	}
	
	@UIUnitTimeout(1200000)
	@Test
	public void assignUserToTeam()
	{
		manageUsers.addUserToTeam(userName, EastWindArtifacts.ORGANIZATIONUNIT_CUSTOMER, "Customer(Lead)", false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_LAURA,EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION,"SalesCoordinator(Lead)",false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_FULLER,EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION,EastWindArtifacts.ROLES_VPSALES,false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_CAGE,EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION,"SalesManager(Lead)",false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_KING,EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION,EastWindArtifacts.ROLES_SALESREPRESENTATIVE,false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_STEVEN,EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION,"SalesManager(Lead)",false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_SUYAMA,EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION,EastWindArtifacts.ROLES_SALESREPRESENTATIVE,false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_JONES,EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION,"SalesManager(Lead)",false);
		manageUsers.addUserToTeam(EastWindArtifacts.USERS_ANNE,EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION,EastWindArtifacts.ROLES_SALESREPRESENTATIVE,false);
		manageUsers.close();
	}
	
}
