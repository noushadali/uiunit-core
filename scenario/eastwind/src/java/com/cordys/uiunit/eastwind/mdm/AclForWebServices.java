package com.cordys.uiunit.eastwind.mdm;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.IWebServiceInterfaceExplorer;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class AclForWebServices extends WorkSpaceOrganizer{
	IManageSystemResources SRM = null;
	IWebServiceInterfaceExplorer explorer = null;
	
	@Before
	public void setup()
	{
		SRM = CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		SRM.waitForIdle();
	}
	
	@UIUnitTimeout(1200000)
	@Test
	public void AclForManageProductsWS(){

		explorer = SRM.getWebServiceInterfaceExplorer();
		explorer.waitForIdle();
		explorer.SecurityOnWebServiceInterface("mdm.databasemetadata.EastwindMdmSpoke1DB WebService.WebServiceInterfaceSpoke1DatabaseMetadata",MDMConstants.MDM_ADMIN_ROLE);
		explorer.close();
	}
}
