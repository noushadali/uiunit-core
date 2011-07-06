package com.cordys.uiunit.eastwind;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.cordys.uiunit.eastwind.designtime.AddingTaskToRole;
import com.cordys.uiunit.eastwind.designtime.BPMManager;
import com.cordys.uiunit.eastwind.designtime.BPMOrderEntryProcess;
import com.cordys.uiunit.eastwind.designtime.BuildAndPublish;
import com.cordys.uiunit.eastwind.designtime.CreateRuntimeISVP;
import com.cordys.uiunit.eastwind.designtime.DBSchemaManager;
import com.cordys.uiunit.eastwind.designtime.DataTransformationManager;
import com.cordys.uiunit.eastwind.designtime.DeliveryModelManager;
import com.cordys.uiunit.eastwind.designtime.DocumentOrganizer;
import com.cordys.uiunit.eastwind.designtime.OrganizationModelManager;
import com.cordys.uiunit.eastwind.designtime.OrganizationUnitManager;
import com.cordys.uiunit.eastwind.designtime.OrganizationUnitRolesManager;
import com.cordys.uiunit.eastwind.designtime.RolesManager;
import com.cordys.uiunit.eastwind.designtime.RuleGroupAndDesicionCaseManager;
import com.cordys.uiunit.eastwind.designtime.UIInterfaceManager;
import com.cordys.uiunit.eastwind.designtime.WorkListManager;
import com.cordys.uiunit.eastwind.designtime.WsAppPackageManagerNorthwind;
import com.cordys.uiunit.eastwind.designtime.WsAppPackageManagerProduction;
import com.cordys.uiunit.eastwind.designtime.XMLSchemaManager;
import com.cordys.uiunit.eastwind.designtime.bam.BAMTestSuite;
import com.cordys.uiunit.eastwind.runtime.ProjectEnvironmentOrganizer;


@RunWith(Suite.class)
@SuiteClasses({
				ProjectEnvironmentOrganizer.class,
				DocumentOrganizer.class,
				XMLSchemaManager.class,
				DataTransformationManager.class,
				RuleGroupAndDesicionCaseManager.class,
				DBSchemaManager.class,
				RolesManager.class,				
				OrganizationUnitManager.class,
				OrganizationModelManager.class,
				OrganizationUnitRolesManager.class,
				WorkListManager.class,
				WsAppPackageManagerNorthwind.class,
				WsAppPackageManagerProduction.class,
				UIInterfaceManager.class,
				DeliveryModelManager.class,
				AddingTaskToRole.class,
				BPMManager.class,
				BPMOrderEntryProcess.class,
				BAMTestSuite.class,
				BuildAndPublish.class,
				CreateRuntimeISVP.class
				})
public class AEastWindTestSuite {

}
