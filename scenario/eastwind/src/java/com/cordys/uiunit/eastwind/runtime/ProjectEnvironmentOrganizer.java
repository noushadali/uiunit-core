package com.cordys.uiunit.eastwind.runtime;


import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.SqlDSO;
import com.cordys.ciui.cusputilities.WSAppsSoapService;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.uiunit.eastwind.Util;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class ProjectEnvironmentOrganizer  extends UIUnitTestCase{
	private  IManageSystemResources manageResources=null;
	
	@UIUnitTimeout(1200000)
	@Before
	public void setup()
	{
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createNorthwindDSOAndWSApp()
	{
		//manageResources.maximize();
		manageResources.waitForIdle();
		SqlDSO dsoObject=new SqlDSO();
		dsoObject.setDsoName("DSONorthwind");
		dsoObject.setDescription("DSO for Northwind");
		dsoObject.setDataBase("Northwind");		
		dsoObject.setDabaseVendor(Util.getEastwindProperty("DBVendor"));
		dsoObject.setProvider(Util.getEastwindProperty("DBProvider"));		
		dsoObject.setServerName(Util.getEastwindProperty("DBServerName"));		
		dsoObject.setUserName(Util.getEastwindProperty("DBUserName"));		
		dsoObject.setPassword(Util.getEastwindProperty("DBUserPassword"));
		manageResources.createDSO(dsoObject);
		
		WSAppsSoapService myService=new WSAppsSoapService();
		myService.setSoapNodeName("NorthwindWSApps");
		myService.setSoapProcessorName("NorthwindWSApps");
		myService.setConnectorName("WS-AppServer");
		myService.setDsoName("DSONorthwind");
		myService.setSetSpecialCharacterSupport(true);
		manageResources.createNewService(myService);		
		manageResources.close();
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createProductionDSOAndWSApp()
	{
		SqlDSO dsoObject=new SqlDSO();
		dsoObject.setDsoName("DSOProduction");
		dsoObject.setDescription("DSO for ProductionDB");
		dsoObject.setDataBase("EastWindProduction");		
		dsoObject.setProvider(Util.getEastwindProperty("DBProvider"));		
		dsoObject.setServerName(Util.getEastwindProperty("DBServerName"));
		dsoObject.setServerName(Util.getEastwindProperty("DBServerName"));		
		dsoObject.setUserName(Util.getEastwindProperty("DBUserName"));		
		dsoObject.setPassword(Util.getEastwindProperty("DBUserPassword"));
		
		
		manageResources.createDSO(dsoObject);
		WSAppsSoapService myService=new WSAppsSoapService();
		myService.setSoapNodeName("ProductionWSApps");
		myService.setSoapProcessorName("ProductionWSApps");
		myService.setConnectorName("WS-AppServer");
		myService.setDsoName("DSOProduction");
		myService.setSetSpecialCharacterSupport(true);
		manageResources.createNewService(myService);
		manageResources.close();
	}
	@Test
	@UIUnitTimeout(6000000)
	public void startRuleProcessor(){
		manageResources.startProcessor("Rule Management");
		manageResources.close();
	}
	
	
}
