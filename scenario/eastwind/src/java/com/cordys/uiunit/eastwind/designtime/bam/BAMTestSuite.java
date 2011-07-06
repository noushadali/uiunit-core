package com.cordys.uiunit.eastwind.designtime.bam;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	MessageFilteringOfProcessForBAM.class,
	MonitoringObjectOnOrderEntryProcess.class,
	MonitoringObjectOnDiscountApproval.class,	
	BMOfTypeWebServiceOnProducts.class,
	OrdersByCustomerBusinessMeasure.class,
	OrdersByPeriodBusinessMeasure.class,
	OrdersByPeriodTrendToVP.class,
	OrdersByProductBusinessMeasure.class,
	OrdersDiscountPercentageGraph.class,
	OrdersOrderedVsDeliveredGraph.class,
	SalesCoordinatorDelayNotificationBE.class,
	SalesRepresentativeDelayNotificationBER.class,
	TasksScheduledVsOutstandingVsCompletedForSalesCoordinator.class,
	TasksScheduledVsOutstandingVsCompletedForSalesRepresentative.class
})
public class BAMTestSuite {

}
