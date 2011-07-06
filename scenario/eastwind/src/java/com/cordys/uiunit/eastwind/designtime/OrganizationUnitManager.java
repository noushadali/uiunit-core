package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;


import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitDesigntime;

import com.cordys.organizationmodel.uiunit.IUIOrgUnitDesigntimeProperties;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitType;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitTypeProperties;

@UIUnitTimeout(1200000)
public class OrganizationUnitManager extends WorkSpaceOrganizer{
	IUIProject project=null;
	IUIOrgUnitType productionOrgUnit = null;
	IUICWSFolder organizationFolder = null;
	@Test
	public void testSalesDivisionOrgUnit()
	{
		project =getProject();
		organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
		IUIOrgUnitType salesUnit = (IUIOrgUnitType) organizationFolder.addDocument(IUIOrgUnitType.class);
		salesUnit.rename(EastWindArtifacts.ORGANIZATIONUNITTYPE_SALES);
		IUIOrgUnitTypeProperties salesproperties = salesUnit.openProperties();
		salesproperties.save();
		salesproperties.close();
		IUIOrgUnitDesigntime salesDivision = (IUIOrgUnitDesigntime)organizationFolder.addDocument(IUIOrgUnitDesigntime.class);
		salesDivision.rename(EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION);
		IUIOrgUnitDesigntimeProperties salesdivisionProperties = salesDivision.openProperties();
		salesdivisionProperties.setOrgUnitType(salesUnit);
		salesdivisionProperties.save();
		salesdivisionProperties.close();

	}
	@Test
	public void testEasternRegionOrgUnit()
	{

		project =getProject();
		organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
		
		IUIOrgUnitDesigntime easternRegion = (IUIOrgUnitDesigntime)organizationFolder.addDocument(IUIOrgUnitDesigntime.class);
		easternRegion.rename(EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION);
		IUIOrgUnitDesigntimeProperties easternRegionProperties=(IUIOrgUnitDesigntimeProperties)easternRegion.openProperties();
		easternRegionProperties.setOrgUnitType(EastWindArtifacts.ORGANIZATIONUNITTYPE_SALES);
		easternRegionProperties.save();
		easternRegionProperties.close();

	}
	@Test
	public void testSouthernRegionOrgUnit()
	{
		project =getProject();
		organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);


		IUIOrgUnitDesigntime southernRegion = (IUIOrgUnitDesigntime)organizationFolder.addDocument(IUIOrgUnitDesigntime.class);
		southernRegion.rename(EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION);
			
		IUIOrgUnitDesigntimeProperties southernRegionProperties=(IUIOrgUnitDesigntimeProperties)southernRegion.openProperties();
		southernRegionProperties.setOrgUnitType(EastWindArtifacts.ORGANIZATIONUNITTYPE_SALES);
		southernRegionProperties.save();
		southernRegionProperties.close();
	}
	@Test
	public void testNorthernRegionOrgUnit()
	{
		project =getProject();
		organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);

		IUIOrgUnitDesigntime northernRegion = (IUIOrgUnitDesigntime)organizationFolder.addDocument(IUIOrgUnitDesigntime.class);
		
		northernRegion.rename(EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION);
		IUIOrgUnitDesigntimeProperties northernRegionProperties=(IUIOrgUnitDesigntimeProperties)northernRegion.openProperties();
		northernRegionProperties.setOrgUnitType(EastWindArtifacts.ORGANIZATIONUNITTYPE_SALES);
		northernRegionProperties.save();
		northernRegionProperties.close();
	}
	@Test
	public void testProductDivisionOrgUnit()
	{


		project =getProject();
		organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
		IUIOrgUnitType productionOrgUnit = (IUIOrgUnitType) organizationFolder.addDocument(IUIOrgUnitType.class);
		productionOrgUnit.rename(EastWindArtifacts.ORGANIZATIONUNIT_TYPE_PRODUCTION);
		IUIOrgUnitTypeProperties production = productionOrgUnit.openProperties();
		production.save();
		production.close();
		
		IUIOrgUnitDesigntime productDivision = (IUIOrgUnitDesigntime)organizationFolder.addDocument(IUIOrgUnitDesigntime.class); 
		productDivision.rename(EastWindArtifacts.ORGANIZATIONUNIT_PRODUCTDIVISION);
			
		IUIOrgUnitDesigntimeProperties productDivisionProperties = productDivision.openProperties();
		productDivisionProperties.setOrgUnitType(productionOrgUnit);
		productDivisionProperties.save();
		productDivisionProperties.close();

	}
	@Test
	public void testCustomerOrgUnit()
	{
		project =getProject();
		organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
		IUIOrgUnitType eastwind = (IUIOrgUnitType)organizationFolder.addDocument(IUIOrgUnitType.class);
		eastwind.rename(EastWindArtifacts.ORGANIZATIONUNITTYPE_EASTWIND);
		IUIOrgUnitTypeProperties eastwindProperties = eastwind.openProperties();
		eastwindProperties.save();
		IUIOrgUnitDesigntime customer = (IUIOrgUnitDesigntime)organizationFolder.addDocument(IUIOrgUnitDesigntime.class);
		customer.rename(EastWindArtifacts.ORGANIZATIONUNIT_CUSTOMER);
		IUIOrgUnitDesigntimeProperties eastWindProperties = customer.openProperties();
		eastWindProperties.setOrgUnitType(eastwind);
		eastWindProperties.save();
		eastWindProperties.close();


	}




}
