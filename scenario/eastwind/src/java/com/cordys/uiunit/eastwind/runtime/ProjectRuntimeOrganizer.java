package com.cordys.uiunit.eastwind.runtime;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.ISoapNodeProperties;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class ProjectRuntimeOrganizer extends UIUnitTestCase{
	IManageSystemResources manageResources=null;
	
	@UIUnitTimeout(1200000)
	@Before
	public void setup()
	{
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.maximize();
		manageResources.waitForIdle();
	}
	
	@UIUnitTimeout(1200000)
	@Test
	public void attachMethodSetToDataTransformation()
	{
		ISoapNodeProperties SNpropertiesPage= (ISoapNodeProperties)manageResources.getSoapNodeProperties("Data Transformation");
		Assert.assertNotNull("not found",SNpropertiesPage);
		SNpropertiesPage.addMethodSet("webservicesdatatransformation.SalesProductionDataTransformationWebService.SalesProductionDataTransformation_WebserviceInterface","system");
		manageResources.close();
	}
	
	@Test
	@UIUnitTimeout(1200000)
	public void attachMethodSetToRuleRepositoryService()
	{
		ISoapNodeProperties SNpropertiesPage= (ISoapNodeProperties)manageResources.getSoapNodeProperties("Rule Management");
		Assert.assertNotNull("not found",SNpropertiesPage);
		SNpropertiesPage.addMethodSet("webservicesdecisiontable.DiscountDecision_Web_Service.DiscountDecision_WebserviceInterface","system");
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
