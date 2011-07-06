package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.config.IConfiguration;
import com.cordys.cm.uiunit.config.IConfigurationEdit;
import com.cordys.cm.uiunit.config.identity.IIdentity;
import com.cordys.cm.uiunit.config.identity.SimpleIdentity;
import com.cordys.cm.uiunit.elements.cordys.IApplication;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.elements.html.IListBox;
import com.cordys.cm.uiunit.framework.IConfigurationProvider;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.notification.cusputilities.Actions;
import com.cordys.notification.cusputilities.IMyInbox;
import com.cordys.notification.cusputilities.ITaskView;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;

@UIUnitTimeout(1200000)
@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class CageSalesCoordinatorInbox extends UIUnitTestCase implements IConfigurationProvider{

	@Override
	public IConfiguration getConfiguration() {
		
		IConfigurationEdit config = (IConfigurationEdit) ConfigurationManager
				.createConfig();
		//IIdentity identity = new SimpleIdentity(SystemName.substring(0, SystemName.indexOf(":"))+"/"+"cage","cordys");
		//IIdentity identity = new SimpleIdentity(SystemName+"/"+"cage","cordys");
		IIdentity identity = new SimpleIdentity(EastWindArtifacts.USERS_CAGE,EastWindArtifacts.USERS_PASSWD);
		config.setIdentity(identity);
		System.out.println("Changed Configuration");
		return (IConfiguration) config;
	}	
	//@UIUnitReloadBrowser
	@Test
	public void salesTest()
	{
		IMyInbox inbox = CordysRuntimeApplications.startFromCUSP(this.getContext(),IMyInbox.class);
		inbox.switchWorklist(EastWindArtifacts.WORKLISTS_SALESCOORDINATOR);
		inbox.selectTask("AssignSalesRep");
		ITaskView tad = inbox.openTask();
		//ApplicationFactory.findFirstApplicationWhereIDStartsWith(tad, "")
		IApplication activityForm = tad.getActivityForm();
		IHTMLElement order = activityForm.findElement(IHTMLElement.class, "OrdersGroup");
		IListBox emp =order.findElement(IListBox.class, "employeeid");
		Assert.assertNotNull(emp);
		emp.selectByText("Laura");
		tad.performAction(Actions.COMPLETE);
		inbox.close();	
		
	}
	
}