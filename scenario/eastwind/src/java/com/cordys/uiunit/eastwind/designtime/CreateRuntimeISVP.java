package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.project.IUIPackageGeneralInfo;
import com.cordys.cws.uiunit.util.project.IUIPackagePropertiesView;
import com.cordys.cws.uiunit.util.project.IUIProject;

public class CreateRuntimeISVP extends WorkSpaceOrganizer{
	@Test
	@UIUnitTimeout(600000)
	public void synchronizeContent()
	{
		IUIProject project = getProject();
		IUIPackagePropertiesView projectPropertiesView = project.openPackageProperties();
		IUIPackageGeneralInfo packageGeneralInfo = projectPropertiesView.getGeneralInfo();
		packageGeneralInfo.setPackageForRuntime();
		projectPropertiesView.clickOK();
		getProject().doPackage();
	}
}
