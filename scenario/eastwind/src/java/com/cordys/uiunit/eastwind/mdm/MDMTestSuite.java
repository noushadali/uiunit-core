package com.cordys.uiunit.eastwind.mdm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
				ProjectEnvironment.class,	
				CreateSoapProcessors.class,
				WebServicesGeneration.class,
				MDMModel.class,	
				CreateUI.class,
				AddingTaskToRole.class,				
				SynchBuildAndPublish.class,
				AssignRoles.class,
				AclForWebServices.class,	
				ModifyProductName.class,
				VerifyProductName.class,
				ResetProductNameToOriginal.class
				})
public class MDMTestSuite {

}
