package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;

public class CodeCoverage  extends UIUnitTestCase{

	private  IManageSystemResources manageResources=null;
	
	@Test
	@UIUnitTimeout(6000000)
	public void setCoberturaInformation(){
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.setCoberturaInformation("ProductionWSApps");
		manageResources.setCoberturaInformation("NorthwindWSApps");
	}	
}
