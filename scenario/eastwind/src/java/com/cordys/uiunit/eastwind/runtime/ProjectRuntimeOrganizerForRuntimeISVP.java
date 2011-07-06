package com.cordys.uiunit.eastwind.runtime;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.IProcessorProperties;
import com.cordys.ciui.cusputilities.ISoapNodeProperties;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.eibus.util.system.EIBProperties;
@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class ProjectRuntimeOrganizerForRuntimeISVP extends UIUnitTestCase {

IManageSystemResources manageResources=null;
	
	@UIUnitTimeout(1200000)
	@Before
	public void setup()
	{
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		//manageResources.maximize();
		manageResources.waitForIdle();
	}
	
	@UIUnitTimeout(1200000)
	@Test
	public void attachMethodSetToDataTransformation()
	{
		ISoapNodeProperties SNpropertiesPage= (ISoapNodeProperties)manageResources.getSoapNodeProperties("Data Transformation");
		Assert.assertNotNull("not found",SNpropertiesPage);
		SNpropertiesPage.addMethodSet("webservicesdatatransformation.SalesProductionDataTransformationWebService.SalesProductionDataTransformation_WebserviceInterface","Cordys EastWind");
		manageResources.close();
	}
	
	@Test
	@UIUnitTimeout(1200000)
	public void attachMethodSetToRuleRepositoryService()
	{
		ISoapNodeProperties SNpropertiesPage= (ISoapNodeProperties)manageResources.getSoapNodeProperties("Rule Management");
		Assert.assertNotNull("not found",SNpropertiesPage);
		SNpropertiesPage.addMethodSet("webservicesdecisiontable.DiscountDecision_Web_Service.DiscountDecision_WebserviceInterface","Cordys EastWind");
		manageResources.close();
	}
	
	@UIUnitTimeout(1200000)
	@Test
	public void setClassPathForNorthwind()
	{
		String directory=EIBProperties.getInstallDir();
		IProcessorProperties SPProperties=(IProcessorProperties)manageResources.getProcessorProperties("NorthwindWSApps");
		SPProperties.setClassPath(directory+'\\'+"databasemetadata\\Java Archive NorthwindDatabaseMetadata\\NorthwindDatabaseMetadata.jar");
		manageResources.close();
	}
	
	@UIUnitTimeout(1200000)
	@Test
	public void setClassPathForProduction()
	{
		String directory=EIBProperties.getInstallDir();
		IProcessorProperties SPProperties=(IProcessorProperties)manageResources.getProcessorProperties("ProductionWSApps");
		SPProperties.setClassPath(directory+'\\'+"databasemetadata\\Java Archive ProductionDatabaseMetadata\\ProductionDatabaseMetadata.jar");
		manageResources.close();
	}
		
	@UIUnitTimeout(1200000)
	@Test
	public void restartProcessors()
	{
		manageResources.restartProcessor("Data Transformation");
		manageResources.restartProcessor("Rule Management");
		manageResources.close();
	}
	
}
