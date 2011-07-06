package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.config.IConfiguration;
import com.cordys.cm.uiunit.config.IConfigurationEdit;
import com.cordys.cm.uiunit.config.identity.IIdentity;
import com.cordys.cm.uiunit.config.identity.SimpleIdentity;
import com.cordys.cm.uiunit.elements.cordys.IApplication;
import com.cordys.cm.uiunit.elements.html.IButton;
import com.cordys.cm.uiunit.framework.IConfigurationProvider;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.notification.cusputilities.Actions;
import com.cordys.notification.cusputilities.IMyInbox;
import com.cordys.notification.cusputilities.ITaskView;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;

@UIUnitTimeout(180000)
@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class CageManagerSalesRepWL extends UIUnitTestCase implements IConfigurationProvider{


	@Override
	public IConfiguration getConfiguration() {
		// TODO Auto-generated method stub
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
	public void cagemanagerwl()
	{
		IMyInbox inbox = CordysRuntimeApplications.startFromCUSP(this.getContext(),IMyInbox.class);
		inbox.switchWorklist(EastWindArtifacts.WORKLISTS_MANAGER);
		inbox.selectTask("Manager Discount Approval");
		ITaskView tad = inbox.openTask();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//IFrame iframe = tad.findElement(IFrame.class,"formFrame");
		IApplication activityForm = tad.getActivityForm();
		activityForm.findElement(IButton.class, "approveButton").click();
		tad.performAction(Actions.COMPLETE);
		inbox.close();
		
		
	}  
}
