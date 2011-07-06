package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.project.IUIProject;

public class DocumentOrganizer extends WorkSpaceOrganizer{	
	@Test
	@UIUnitTimeout(1200000)
	public void createFolderStructure(){		
		IUIProject project = getProject();
		project.addFolder(EastWindArtifacts.FOLDER_XFORMS);//Folder for XForms
		project.addFolder(EastWindArtifacts.FOLDER_DBSCHEMA);//Folder for WSApps
		project.addFolder(EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP);//Folder for WSApps		
		project.addFolder(EastWindArtifacts.FOLDER_WEBSERVICEDECISIONCASE);//Folder for WSApps	
		project.addFolder(EastWindArtifacts.FOLDER_ROLES);//Folder for CIUI
		project.addFolder(EastWindArtifacts.FOLDER_ORGANIZATION);//Folder for BPMNL
		project.addFolder(EastWindArtifacts.FOLDER_WORKLISTS);//Folder for WorkFlow
		project.addFolder(EastWindArtifacts.FOLDER_DATAMAP);//Folder for WorkFlow
	    project.addFolder(EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);//Folder for Repository	
		project.addFolder(EastWindArtifacts.FOLDER_OBJETTEMPLATES);//Folder for Repository
		project.addFolder(EastWindArtifacts.FOLDER_RULES);//Folder for Repository
		project.addFolder(EastWindArtifacts.FOLDER_BPM);//Folder for BPM
		project.addFolder(EastWindArtifacts.FOLDER_BAM_PMO);//Folder for BAM
		project.addFolder(EastWindArtifacts.FOLDER_BAM_BM);//Folder for BAM
		project.addFolder(EastWindArtifacts.FOLDER_BAM_BER);//Folder for BAM
		project.addFolder(EastWindArtifacts.FOLDER_BAM_KPI);//Folder for BAM
		project.addFolder(EastWindArtifacts.FOLDER_BAM_DASHBOARDS);//Folder for BAM
	}
}
