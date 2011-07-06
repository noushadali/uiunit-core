package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.notification.cusputilities.Actions;
import com.cordys.notification.cusputilities.Containers;
import com.cordys.notification.cusputilities.IMyInbox;
import com.cordys.notification.cusputilities.INotificationView;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class CustomerNotification extends UIUnitTestCase {
	IMyInbox inbox = null;

	@Test
	@UIUnitTimeout(3000000)
	public void CustomerInbox()
	{
		 inbox = CordysRuntimeApplications.startFromCUSP(this.getContext(),IMyInbox.class);
		inbox.switchContainer(Containers.NOTIFICATIONS);
		inbox.selectNotification("Notify Customer Discount Details");
		INotificationView notifView = inbox.openNotification();
		notifView.performAction(Actions.DELETE_NOTIFICATION);
	}

}
