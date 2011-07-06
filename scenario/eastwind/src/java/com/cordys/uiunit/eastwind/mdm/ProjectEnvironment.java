package com.cordys.uiunit.eastwind.mdm;


import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.SqlDSO;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.uiunit.eastwind.Util;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class ProjectEnvironment  extends UIUnitTestCase{
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
	public void createSpoke1DSO()
	{
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();			
		SqlDSO dsoObject=new SqlDSO();
		dsoObject.setDsoName(MDMConstants.MDM_SPOKE1_DSO);
		dsoObject.setDescription("Spoke1 for MDM synchronization");
		dsoObject.setDataBase(MDMConstants.MDM_SPOKE1_DBNAME);				
		dsoObject.setProvider(Util.getEastwindProperty("DBProvider"));		
		dsoObject.setDabaseVendor(Util.getEastwindProperty("DBVendor"));
		dsoObject.setServerName(Util.getEastwindProperty("DBServerName"));		
		dsoObject.setUserName(Util.getEastwindProperty("DBUserName"));		
		dsoObject.setPassword(Util.getEastwindProperty("DBUserPassword"));
		manageResources.createDSO(dsoObject);
		this.getContext().waitForIdle();
		manageResources.close();
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createSpoke2DSO()
	{
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();			
		SqlDSO dsoObject=new SqlDSO();
		dsoObject.setDsoName(MDMConstants.MDM_SPOKE2_DSO);
		dsoObject.setDescription("Spoke2 for MDM synchronization");
		dsoObject.setDataBase(MDMConstants.MDM_SPOKE2_DBNAME);				
		dsoObject.setProvider(Util.getEastwindProperty("DBProvider"));		
		dsoObject.setDabaseVendor(Util.getEastwindProperty("DBVendor"));
		dsoObject.setServerName(Util.getEastwindProperty("DBServerName"));		
		dsoObject.setUserName(Util.getEastwindProperty("DBUserName"));		
		dsoObject.setPassword(Util.getEastwindProperty("DBUserPassword"));
		manageResources.createDSO(dsoObject);
		this.getContext().waitForIdle();
		manageResources.close();
	}	
	
}
