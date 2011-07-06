package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitDesigntime;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitDesigntimeProperties;

public class OrganizationUnitRolesManager extends WorkSpaceOrganizer{

    IUIProject project = null;
    IUICWSFolder folder=null;
 
    @UIUnitTimeout(120000000)
    @Test
    public void assigningRolesToOrganizationUnit()
    {
   	  project=getProject();
   	  folder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
   	  
   	  IUIOrgUnitDesigntime salesDivision=folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION);
   	  IUIOrgUnitDesigntimeProperties divisionProperties=salesDivision.openProperties();
   	  divisionProperties.addRole(EastWindArtifacts.ROLES_SALESCOORDINATOR, true);
   	  divisionProperties.addRole(EastWindArtifacts.ROLES_VPSALES);
   	  divisionProperties.addRole(EastWindArtifacts.ROLES_PRESIDENT);
   	  divisionProperties.save();
   	  divisionProperties.close();
   	  
   	  IUIOrgUnitDesigntime productDivision=folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_PRODUCTDIVISION);
 	  divisionProperties=productDivision.openProperties();
 	  divisionProperties.addRole(EastWindArtifacts.ROLES_PRESIDENT,true);
 	  divisionProperties.save();
 	  divisionProperties.close();
 	  
 	  IUIOrgUnitDesigntime easrtwindOrg = folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG);
	  divisionProperties=easrtwindOrg.openProperties();
	  divisionProperties.addRole(EastWindArtifacts.ROLES_PRESIDENT,true);
	  divisionProperties.save();
	  divisionProperties.close();
   	  
   	  IUIOrgUnitDesigntime easternRegion=folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION);
   	  IUIOrgUnitDesigntimeProperties easternProperties=easternRegion.openProperties();
   	  easternProperties.addRole(EastWindArtifacts.ROLES_SALESMANAGER,true);
   	  easternProperties.addRole(EastWindArtifacts.ROLES_SALESREPRESENTATIVE);   	  
   	  easternProperties.save();
   	  easternProperties.close();
   	  
   	  IUIOrgUnitDesigntime southernRegion=folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION);
   	  IUIOrgUnitDesigntimeProperties southernProperties=southernRegion.openProperties();
   	  southernProperties.addRole(EastWindArtifacts.ROLES_SALESMANAGER,true);
 	  southernProperties.addRole(EastWindArtifacts.ROLES_SALESREPRESENTATIVE);
 	  southernProperties.save();
 	  southernProperties.close();
 	  
 	  IUIOrgUnitDesigntime northenRegion=folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION);
 	  IUIOrgUnitDesigntimeProperties northenProperties=northenRegion.openProperties();
 	  northenProperties.addRole(EastWindArtifacts.ROLES_SALESMANAGER,true);
 	  northenProperties.addRole(EastWindArtifacts.ROLES_SALESREPRESENTATIVE);
 	  northenProperties.save();
 	  northenProperties.close();
 	  
	  IUIOrgUnitDesigntime customer=folder.getExistingChildDocument(IUIOrgUnitDesigntime.class, EastWindArtifacts.ORGANIZATIONUNIT_CUSTOMER);
	  IUIOrgUnitDesigntimeProperties customerProperties=customer.openProperties();
	  customerProperties.addRole(EastWindArtifacts.ROLES_CUSTOMER,true);
	  customerProperties.save();
	  customerProperties.close();
    }
    
}
