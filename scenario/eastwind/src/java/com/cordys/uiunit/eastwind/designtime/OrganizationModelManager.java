package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.organizationmodel.uiunit.IUIOrgModel;
import com.cordys.organizationmodel.uiunit.IUIOrgModelDesigner;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitDesigntime;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitType;
import com.cordys.organizationmodel.uiunit.IUIOrgUnitTypeProperties;
import com.cordys.umfgraphicaldesigner.uiunit.designer.IUIShapeUMF;

@UIUnitTimeout(1200000)
public class OrganizationModelManager extends WorkSpaceOrganizer{
	IUIProject project = null; 
	@Test
	public void createOrganizationModelHirearchy()
	{
		project = getProject();
		IUICWSFolder organizationFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_ORGANIZATION);
		IUIOrgUnitType eastwindOUT = organizationFolder.getExistingChildDocument(IUIOrgUnitType.class,EastWindArtifacts.ORGANIZATIONUNITTYPE_EASTWIND);
		IUIOrgUnitTypeProperties eastWindPropertiesView = eastwindOUT.openProperties();
		eastWindPropertiesView.showInToolbar(true);
		eastWindPropertiesView.save();
		eastWindPropertiesView.close();
		IUIOrgModel eastOrgModel= (IUIOrgModel) organizationFolder.addDocument(IUIOrgModel.class);
		this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IUIOrgModelDesigner eastOrgModelDesignerView=eastOrgModel.openDesigner();
		//eastOrgModelDesignerView.maximize();
		//eastOrgModelDesignerView.maximize();
		//this.getCWSIDE().getWorkspaceDocumentsView().focus();
		//eastwindOUT.refresh();
		//eastwindOUT.dragAndDropToLocation(600,200);
		eastOrgModelDesignerView.focus();
		eastOrgModelDesignerView.addOrgUnitFromToolbarByClick(eastwindOUT.getName(), EastWindArtifacts.ORGANIZATIONUNITTYPE_EASTWIND, 500, 150);
		IUIShapeUMF eastwindOUL = eastOrgModelDesignerView.getLatestObject();
		eastOrgModelDesignerView.save(EastWindArtifacts.ORGANIZATIONMODEL_EAST,EastWindArtifacts.ORGANIZATIONMODEL_EAST);
		this.getCWSIDE().getWorkspaceDocumentsView().focus();
		IUIOrgUnitDesigntime eastwindorg = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class, "Organization Unit");
		eastwindorg.rename(EastWindArtifacts.ORGANIZATIONUNIT_EASTWINDORG);
		IUIOrgUnitDesigntime salesDivisionOU = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class,EastWindArtifacts.ORGANIZATIONUNIT_SALESDIVISION);
		salesDivisionOU.refresh();
		//salesDivisionOU.dragAndDropToLocation(500,300);
		eastOrgModelDesignerView.focus();
		eastOrgModelDesignerView.insertOrgUnitFromContextMenu(salesDivisionOU.getName(), 350, 300);
		 IUIShapeUMF salesDivisionOUL = eastOrgModelDesignerView.getLatestObject();
		 eastOrgModelDesignerView.makeNormalConnector(eastwindOUL,salesDivisionOUL);
		 eastOrgModelDesignerView.save();
		 IUIOrgUnitDesigntime productionDivisionOU = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class,EastWindArtifacts.ORGANIZATIONUNIT_PRODUCTDIVISION);
		 productionDivisionOU.refresh();
		 //productionDivisionOU.dragAndDropToLocation(750,300);
		 eastOrgModelDesignerView.insertOrgUnitFromContextMenu(productionDivisionOU.getName(), 650, 300);
		 IUIShapeUMF productionDivisionOUL = eastOrgModelDesignerView.getLatestObject();
		eastOrgModelDesignerView.makeNormalConnector(eastwindOUL,productionDivisionOUL);
		 IUIOrgUnitDesigntime eastRegionOU = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class,EastWindArtifacts.ORGANIZATIONUNIT_EASTERNREGION);
		 eastRegionOU.refresh();
		 //eastRegionOU.dragAndDropToLocation(500,400);
		 eastOrgModelDesignerView.insertOrgUnitFromContextMenu(eastRegionOU.getName(), 200, 450);
		 IUIShapeUMF eastRegionOUL = eastOrgModelDesignerView.getLatestObject();
		 eastOrgModelDesignerView.makeNormalConnector(salesDivisionOUL,eastRegionOUL);
		 IUIOrgUnitDesigntime southernRegionOU = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class,EastWindArtifacts.ORGANIZATIONUNIT_SOUTHERNREGION);
		 southernRegionOU.refresh();
		 //southernRegionOU.dragAndDropToLocation(650,400);
		 eastOrgModelDesignerView.insertOrgUnitFromContextMenu(southernRegionOU.getName(), 350, 450);
		 IUIShapeUMF southernRegionOUL = eastOrgModelDesignerView.getLatestObject();
		 eastOrgModelDesignerView.makeNormalConnector(salesDivisionOUL,southernRegionOUL);
		 IUIOrgUnitDesigntime northernRegionOU = organizationFolder.getExistingChildDocument(IUIOrgUnitDesigntime.class,EastWindArtifacts.ORGANIZATIONUNIT_NORTHENREGION);
		 northernRegionOU.refresh();
		 //northernRegionOU.dragAndDropToLocation(950,400);
		 eastOrgModelDesignerView.insertOrgUnitFromContextMenu(northernRegionOU.getName(), 500, 450);
		 IUIShapeUMF northernRegionOUL = eastOrgModelDesignerView.getLatestObject();
		 eastOrgModelDesignerView.makeNormalConnector(salesDivisionOUL,northernRegionOUL);
					 
		 eastOrgModelDesignerView.save();
		 eastOrgModelDesignerView.close();
	}

}
