package com.cordys.uiunit.eastwind.mdm;
import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.uiunit.eastwind.designtime.WorkSpaceOrganizer;

public class SynchBuildAndPublish extends WorkSpaceOrganizer
{
	@Test
	@UIUnitTimeout(600000)
	public void SynchronizeBuildAndPublishContent()
	{
		getCWSIDE().synchronize();
		this.getContext().waitForIdle();
		IUIProject project = getProject();
		IUICWSFolder mdm = project.getExistingChildDocument(IUICWSFolder.class, MDMConstants.MDM_FOLDER_MDM);
		mdm.doBuild();
		this.getContext().waitForIdle();
		mdm.doPublish();
		this.getContext().waitForIdle();			
	}
		
}
