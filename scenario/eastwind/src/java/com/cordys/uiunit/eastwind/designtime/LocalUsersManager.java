package com.cordys.uiunit.eastwind.designtime;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageUsers;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;

public class LocalUsersManager extends UIUnitTestCase{
	private IManageUsers manageUsers=null;
	
	@Before
	public void setup()
	{
		manageUsers=CordysRuntimeApplications.startFromMenu(this.getContext(),IManageUsers.class);
	}
	
	@UIUnitTimeout(12000000)
	@Test
	public void createAllLocalUsers() 
	{
				
		manageUsers.addCustomUser(EastWindArtifacts.USERS_ANNE,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_SUYAMA,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_KING,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_STEVEN, "cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_CAGE, "cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_JONES,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_FULLER,"cordys");
		manageUsers.addCustomUser(EastWindArtifacts.USERS_LAURA,"cordys");
	}
	
	
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers1()
	{
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_ANNE, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_ANNE, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_ANNE, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_ANNE, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_ANNE, "everyoneInCordys User Assignment");
		}
	
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers2()
	{
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_CAGE, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_CAGE, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_CAGE, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_CAGE, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_CAGE, "everyoneInCordys User Assignment");
	
	}
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers3()
	{
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_FULLER, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_FULLER, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_FULLER, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_FULLER, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_FULLER, "everyoneInCordys User Assignment");
	}
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers4()
	{
		
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_JONES, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_JONES, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_JONES, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_JONES, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_JONES, "everyoneInCordys User Assignment");
	
	}
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers5()
	{
		
	    manageUsers.addRoleToUser(EastWindArtifacts.USERS_KING, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_KING, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_KING, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_KING, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_KING, "everyoneInCordys User Assignment");
	}
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers6()
	{
		
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "Rule Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_LAURA, "everyoneInCordys User Assignment");
	}
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers7()
	{
		
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_STEVEN, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_STEVEN, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_STEVEN, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_STEVEN, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_STEVEN, "everyoneInCordys User Assignment");
	}
	@UIUnitTimeout(120000000)
	@Test
	public void assignRolesTOUsers8()
	{
		
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_SUYAMA, "systemAdmin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_SUYAMA, "Notification Admin");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_SUYAMA, "Notification Developer");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_SUYAMA, "everyoneInCordys Organization Model Runtime");
		manageUsers.addRoleToUser(EastWindArtifacts.USERS_SUYAMA, "everyoneInCordys User Assignment");
	}
	}
	
	
	

