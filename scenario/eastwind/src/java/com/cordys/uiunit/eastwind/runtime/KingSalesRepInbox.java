package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.config.IConfiguration;
import com.cordys.cm.uiunit.config.IConfigurationEdit;
import com.cordys.cm.uiunit.config.identity.IIdentity;
import com.cordys.cm.uiunit.config.identity.SimpleIdentity;
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
public class KingSalesRepInbox extends UIUnitTestCase implements IConfigurationProvider{	

		@Override
		public IConfiguration getConfiguration() {
			IConfigurationEdit config = (IConfigurationEdit) ConfigurationManager
					.createConfig();
			IIdentity identity = new SimpleIdentity(EastWindArtifacts.USERS_KING,EastWindArtifacts.USERS_PASSWD);
			config.setIdentity(identity);
			return (IConfiguration) config;
		}
		 @Test
			
			public void kingSalesRepWL()

			{
			IMyInbox inbox = CordysRuntimeApplications.startFromCUSP(this.getContext(),IMyInbox.class);
			inbox.switchWorklist(EastWindArtifacts.WORKLISTS_SALESREPRESENTATIVE);
			inbox.selectTask("SalesRepOrderComplete");
			ITaskView openTask = inbox.openTask();
			openTask.performAction(Actions.COMPLETE);
			inbox.close();
			}
}
