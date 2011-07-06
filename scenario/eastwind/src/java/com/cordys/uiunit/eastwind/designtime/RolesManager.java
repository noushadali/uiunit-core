package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.role.uiunit.IUIRole;
import com.cordys.role.uiunit.IUIRoleEditor;


public class RolesManager extends WorkSpaceOrganizer {

	
     IUIProject project = null;
     IUIRole roleDocument=null;
     IUICWSFolder rolesFolder;
     IUIRoleEditor roleEditor=null;
     
     @UIUnitTimeout(12000000)
     @Test
     public void createAllFunctionalRoles()
     {
    	  project=getProject();
    	  rolesFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ROLES);
    	  CreateRoles(EastWindArtifacts.ROLES_SALESMANAGER);
          CreateRoles(EastWindArtifacts.ROLES_VPSALES);
          CreateRoles(EastWindArtifacts.ROLES_CUSTOMER);
          CreateRoles(EastWindArtifacts.ROLES_SALESREPRESENTATIVE);
          CreateRoles(EastWindArtifacts.ROLES_PRESIDENT);
          CreateRoles(EastWindArtifacts.ROLES_SALESCOORDINATOR);
     }
     
     public void CreateRoles(String RoleName)
     {
    	 roleDocument = rolesFolder.addDocument(IUIRole.class);
    	 roleEditor=roleDocument.openDesigner();
    	 roleEditor.save(RoleName, RoleName);
    	 roleEditor.close();
     }
     
}
