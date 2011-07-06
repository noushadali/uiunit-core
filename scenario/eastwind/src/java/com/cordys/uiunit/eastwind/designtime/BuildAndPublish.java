package com.cordys.uiunit.eastwind.designtime;
import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIPackageGeneralInfo;
import com.cordys.cws.uiunit.util.project.IUIPackagePropertiesView;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceInterface;
import com.cordys.webservice.cwsutilities.IUIWebServiceOperation;

public class BuildAndPublish extends WorkSpaceOrganizer
{
	@Test
	@UIUnitTimeout(600000)
	public void synchronizeContent()
	{
		//Synchronize the content
		getCWSIDE().synchronize();				
		IUIProject project = getProject();
		IUIPackagePropertiesView projectPropertiesView = project.openPackageProperties();
		IUIPackageGeneralInfo packageGeneralInfo = projectPropertiesView.getGeneralInfo();
		packageGeneralInfo.setPackageForStaging();
		projectPropertiesView.clickOK();
	}
	//Build the content at folder level
	@Test
	@UIUnitTimeout(600000)
	public void buildContent()
	{
		IUIProject project = getProject();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ROLES).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS).doBuild();
		
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_OBJETTEMPLATES).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_RULES).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DATAMAP).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS).doBuild();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BPM).doBuild();		
	}
	@Test
	@UIUnitTimeout(600000)
	public void publishContentToOrganisation()
	{
		IUIProject project = getProject();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DBSCHEMA).doPublish();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ROLES).doPublish();
		IUICWSFolder organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
		organizationFolder.doPublish();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS).doPublish();
		
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_OBJETTEMPLATES).doPublish();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_RULES).doPublish();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DATAMAP).doPublish();
		IUICWSFolder wsDataMapFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP);
		wsDataMapFolder.doPublish();
		IUIWebServiceDefinitionSet wsDefSet = wsDataMapFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_DATATRANSFORMATION);
		wsDefSet.doPublish();
		IUIWebServiceInterface wsInf = wsDefSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_DATATRANSFORMATION);
		IUIWebServiceOperation wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, EastWindArtifacts.WS_OPERATION_DATATRANSFORMATION);
		wsOprn.doPublish();		
		
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP).doPublish();
		
		IUICWSFolder wsDecisionTableFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDECISIONCASE);
		wsDecisionTableFolder.doPublish();
		wsDefSet = wsDecisionTableFolder.getExistingChildDocument(IUIWebServiceDefinitionSet.class, EastWindArtifacts.WS_DEFINITIONSET_DECISIONCASE);
		wsDefSet.doPublish();
		wsInf = wsDefSet.getExistingChildDocument(IUIWebServiceInterface.class, EastWindArtifacts.WS_INTERFACE_DECISIONCASE);
		wsOprn = wsInf.getExistingChildDocument(IUIWebServiceOperation.class, EastWindArtifacts.WS_OPERATION_DECISIONCASE);
		wsOprn.doPublish();
				
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_XFORMS).doPublish();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WORKLISTS).doPublish();
		project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_BPM).doPublish();
	}	
}
