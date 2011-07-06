package com.cordys.uiunit.eastwind.designtime;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.IWebServiceInterfaceExplorer;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;

public class AclForWebServices extends WorkSpaceOrganizer {
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
	public void AclForProductionDBWS(){

		explorer = SRM.getWebServiceInterfaceExplorer();
		explorer.waitForIdle();
		explorer.SecurityOnWebServiceInterface("databasemetadata.WSAppServerPackageProductionDatabaseMetadata.WebServices ProductionDatabaseMetadata.ProductionDatabaseWebService.WebServiceInterfaceProductionDatabaseMetadata","SalesRepresentative");
		explorer.close();
	}
	@UIUnitTimeout(1200000)
	@Test
	public void AclForNorthwindDBWS(){
		explorer = SRM.getWebServiceInterfaceExplorer();
		explorer.waitForIdle();
		explorer.SecurityOnWebServiceInterface("databasemetadata.WSAppServerPackageNorthwindDatabaseMetadata.WebServices NorthwindDatabaseMetadata.NorthwindDatabaseWebService.WebServiceInterfaceNorthwindDatabaseMetadata","SalesCoordinator");
		explorer.SecurityOnWebServiceInterface("databasemetadata.WSAppServerPackageNorthwindDatabaseMetadata.WebServices NorthwindDatabaseMetadata.NorthwindDatabaseWebService.WebServiceInterfaceNorthwindDatabaseMetadata","SalesManager");
		explorer.SecurityOnWebServiceInterface("databasemetadata.WSAppServerPackageNorthwindDatabaseMetadata.WebServices NorthwindDatabaseMetadata.NorthwindDatabaseWebService.WebServiceInterfaceNorthwindDatabaseMetadata","SalesRepresentative");
		explorer.close();
	}
	@UIUnitTimeout(1200000)
	@Test
	public void AclForNorthwindDataTransformationAndDecisionCaseWS(){
		explorer = SRM.getWebServiceInterfaceExplorer();
		explorer.waitForIdle();
		explorer.SecurityOnWebServiceInterface("webservicesdatatransformation.SalesProductionDataTransformationWebService.SalesProductionDataTransformation_WebserviceInterface","SalesRepresentative");
		explorer.SecurityOnWebServiceInterface("webservicesdecisiontable.DiscountDecision_Web_Service.DiscountDecision_WebserviceInterface","SalesManager");
		explorer.SecurityOnWebServiceInterface("webservicesdecisiontable.DiscountDecision_Web_Service.DiscountDecision_WebserviceInterface","SalesCoordinator");
		explorer.close();
	}
	
	
}
