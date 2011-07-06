package com.cordys.uiunit.eastwind.designtime;

import com.cordys.cws.uiunit.framework.junit.CWSUIUnitTestCaseCUSP;

public class WorkSpaceOrganizer extends CWSUIUnitTestCaseCUSP
{
	@Override
	public String getDefaultProjectName() {
		
		return "EastWind";
	}

	@Override
	public String getDefaultSolutionName() {		
		return "Sales";
	}

	@Override
	public String getDefaultWorkspaceName() {
		return "SalesWorkspace";
	}	
}
