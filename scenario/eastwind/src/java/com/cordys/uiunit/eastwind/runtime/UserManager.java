package com.cordys.uiunit.eastwind.runtime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageUsers;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class UserManager extends UIUnitTestCase
{
	IManageUsers manageUsers=null;
	
	@Before
	public void setup()
	{
		manageUsers=CordysRuntimeApplications.startFromCUSP(this.getContext(),IManageUsers.class);
		Assert.assertNotNull("Manage users page not found",manageUsers);
		manageUsers.maximize();
		manageUsers.waitForIdle();
	}
	
	@Test
	@UIUnitTimeout(1200000)
	public void createUserAddRoles()
	{
		
		//manageUsers.addAuthenticatedUser(EastWindArtifacts.USERS_ANNE);
		manageUsers.addCustomUser(EastWindArtifacts.USERS_ANNE,"cordys");
		String roleName[]={"systemAdmin","Notification Admin","Notification Developer"};
		manageUsers.addRolesToUser(EastWindArtifacts.USERS_ANNE, roleName);
		manageUsers.close();
	}
	
	@Test
	@UIUnitTimeout(12000000)
	public void createAllUsers()
	{
		manageUsers.addCustomUser(EastWindArtifacts.USERS_CAGE,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_FULLER,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_JONES,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_KING,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_LAURA,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_STEVEN,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_SUYAMA,"cordys");
		String roleName[]={"systemAdmin","Notification Admin","Notification Developer"};
		String users[] = {EastWindArtifacts.USERS_CAGE,EastWindArtifacts.USERS_FULLER,EastWindArtifacts.USERS_JONES,EastWindArtifacts.USERS_KING,EastWindArtifacts.USERS_LAURA,EastWindArtifacts.USERS_STEVEN,EastWindArtifacts.USERS_SUYAMA};
		manageUsers.addRolesToUsers(users, roleName);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "Rule Admin");
		manageUsers.close();
	
	}
	
	@Test
	@UIUnitTimeout(1200000)
	public void addRoles()
	{
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, EastWindArtifacts.ROLES_SALESCOORDINATOR);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_ANNE, EastWindArtifacts.ROLES_SALESREPRESENTATIVE);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_CAGE, EastWindArtifacts.ROLES_SALESMANAGER);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_FULLER, EastWindArtifacts.ROLES_VPSALES);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_JONES, EastWindArtifacts.ROLES_SALESMANAGER);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_KING, EastWindArtifacts.ROLES_SALESREPRESENTATIVE);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_STEVEN, EastWindArtifacts.ROLES_SALESMANAGER);
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_SUYAMA, EastWindArtifacts.ROLES_SALESREPRESENTATIVE);
		manageUsers.close();
	}
}
