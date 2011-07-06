package com.cordys.uiunit.eastwind.mdm;

import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageUsers;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.role.uiunit.IUIRole;
import com.cordys.role.uiunit.IUIRoleEditor;
import com.cordys.uiunit.eastwind.designtime.EastWindArtifacts;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class AddingTaskToRole extends WorkSpaceOrganizer{
	
	IManageUsers manageUsers = null;
	 IUIProject project = null;
     IUICWSFolder folder=null;
     IUICWSFolder taskFolder=null;     
    
     
     
     @UIUnitTimeout(12000000)
     @Test
     public void createRole()
     {
		 IUICWSFolder mdm = null;
	     IUIRole roleDocument=null;
	     IUIRoleEditor roleEditor=null;
    	 mdm = getProject().getExistingChildDocument(IUICWSFolder.class,MDMConstants.MDM_FOLDER_MDM);    
    	 IUICWSFolder roles = mdm.addFolder("Roles");
    	 roleDocument = roles.addDocument(IUIRole.class);
     	 roleEditor=roleDocument.openDesigner();
     	 roleEditor.save(MDMConstants.MDM_ADMIN_ROLE, MDMConstants.MDM_ADMIN_ROLE);
     	 roleEditor.close();
     	roles.doPublish();
     }
     /*
    @Test
 	@UIUnitTimeout(1200000)
 	public void assignRoles()
 	{
 	 	manageUsers=CordysRuntimeApplications.startFromCUSP(this.getContext(),IManageUsers.class);
 		Assert.assertNotNull("Manage users page not found",manageUsers);
 		manageUsers.maximize();
 		manageUsers.waitForIdle();
 		manageUsers.addRoleToUser(getContext().getRootContext().getTestEnvironment().getConfiguration().getIdentity().getUserName(), MDMConstants.MDM_ADMIN_ROLE);
 	}
 	*/
     @Test
     @UIUnitTimeout(1200000)
     public void assignTaskToRole()
     {
    	 project=getProject();
    	 IUICWSFolder mdm = project.getExistingChildDocument(IUICWSFolder.class, MDMConstants.MDM_FOLDER_MDM);
    	 IUICWSFolder roles = mdm.getExistingChildDocument(IUICWSFolder.class, "Roles");
    	 IUIRole admin=roles.getExistingChildDocument(IUIRole.class, MDMConstants.MDM_ADMIN_ROLE);
    	 IUIRoleEditor roleEditor = admin.openDesigner();
    	 roleEditor.AddSubTask(MDMConstants.MDM_XFORM_MANAGEPRODUCT);
    	 roleEditor.save();
    	 roleEditor.close();
    }

}
