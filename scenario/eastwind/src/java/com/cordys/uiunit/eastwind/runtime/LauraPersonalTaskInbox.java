package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.config.IConfiguration;
import com.cordys.cm.uiunit.config.IConfigurationEdit;
import com.cordys.cm.uiunit.config.identity.IIdentity;
import com.cordys.cm.uiunit.config.identity.SimpleIdentity;
import com.cordys.cm.uiunit.elements.cordys.IApplication;
import com.cordys.cm.uiunit.elements.cordys.internal.Grid;
import com.cordys.cm.uiunit.elements.html.IButton;
import com.cordys.cm.uiunit.elements.html.ICheckBox;
import com.cordys.cm.uiunit.elements.html.IRow;
import com.cordys.cm.uiunit.elements.html.ITextInput;
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

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
@UIUnitTimeout(1200000)
public class LauraPersonalTaskInbox extends UIUnitTestCase implements IConfigurationProvider{

	@Override
	public IConfiguration getConfiguration() {
		// TODO Auto-generated method stub
		IConfigurationEdit config = (IConfigurationEdit) ConfigurationManager
				.createConfig();
		//IIdentity identity = new SimpleIdentity(SystemName.substring(0, SystemName.indexOf(":"))+"/"+"laura","cordys");
		IIdentity identity = new SimpleIdentity(EastWindArtifacts.USERS_LAURA,EastWindArtifacts.USERS_PASSWD);
		//IIdentity identity = new SimpleIdentity(SystemName+"/"+"laura","cordys");
		config.setIdentity(identity);
		return (IConfiguration) config;
	}	
	//@UIUnitReloadBrowser
	@Test
	public void salesTest()
	{
		IMyInbox inbox = CordysRuntimeApplications.startFromCUSP(this.getContext(),IMyInbox.class);
		//inbox.selectTask("SalesRepOrderComplete");
		inbox.selectTask("Send Task To SalesRepresentative");
		ITaskView tad = inbox.openTask();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//IFrame iframe = tad.findElement(IFrame.class,"formFrame");
		IApplication activityForm = tad.getActivityForm();
		activityForm.findElement(IButton.class, "button1").click();
		ITextInput itext = activityForm.findElement(ITextInput.class, "discountRangeText");
		Assert.assertNotNull(itext);
									
		Grid order = activityForm.findElement(Grid.class, "Order_x0020_DetailsTable");
		IRow pro = order.findRow(2);
		ITextInput prodId =  pro.getCellElement(ITextInput.class,5);
		prodId.setValue("0.18");
		ICheckBox checkBox =  pro.getCellElement(ICheckBox.class,1);
		checkBox.check();
		tad.performAction(Actions.COMPLETE);
		inbox.close();
		
	

}
}
