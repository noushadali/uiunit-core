package com.cordys.uiunit.eastwind;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.cordys.uiunit.eastwind.runtime.AclForWebServices;
import com.cordys.uiunit.eastwind.runtime.AssignOrgTeamToUser;
import com.cordys.uiunit.eastwind.runtime.CageManagerSalesRepWL;
import com.cordys.uiunit.eastwind.runtime.CageSalesCoordinatorInbox;
import com.cordys.uiunit.eastwind.runtime.CustomerNotification;
import com.cordys.uiunit.eastwind.runtime.KingSalesRepInbox;
import com.cordys.uiunit.eastwind.runtime.LauraPersonalTaskInbox;
import com.cordys.uiunit.eastwind.runtime.PlaceOrder;
import com.cordys.uiunit.eastwind.runtime.ProjectRuntimeOrganizer;
import com.cordys.uiunit.eastwind.runtime.UserManager;

@RunWith(Suite.class)
@SuiteClasses({
	ProjectRuntimeOrganizer.class,
	AclForWebServices.class,
	UserManager.class,
	AssignOrgTeamToUser.class,
	PlaceOrder.class,
	CageSalesCoordinatorInbox.class,
	LauraPersonalTaskInbox.class,
	CageManagerSalesRepWL.class,
	KingSalesRepInbox.class,
	CustomerNotification.class
	
})

public class EastWindRuntimeAfterPublishToOrg {

}
