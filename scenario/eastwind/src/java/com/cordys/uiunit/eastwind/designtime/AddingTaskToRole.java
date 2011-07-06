package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.role.uiunit.IUIRole;
import com.cordys.role.uiunit.IUIRoleEditor;
import com.cordys.xform.cwsutilities.IXForm;

public class AddingTaskToRole extends WorkSpaceOrganizer
{
	
	 IUIProject project = null;
     IUICWSFolder rolesFolder=null;
     IUICWSFolder taskFolder=null;
     @Test
     public void addTaskToRole()
     {
    	 project=getProject();
    	 rolesFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ROLES);
    	 IUIRole customer=rolesFolder.getExistingChildDocument(IUIRole.class, EastWindArtifacts.ROLES_CUSTOMER);
    	 IUIRoleEditor roleEditor = customer.openDesigner();
    	 taskFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS);
    	 taskFolder.getExistingChildDocument(IXForm.class, EastWindArtifacts.XFORM_ORDERREGISTRATION);
    	 roleEditor.AddSubTask(EastWindArtifacts.XFORM_ORDERREGISTRATION);
    	 //IArtifactViewer roleViewer =roleEditor.getTasks();
    	 //orderRegistration.dragAndDropOnElement(roleViewer);
    	 roleEditor.save();
    	 roleEditor.close();
    }

}
