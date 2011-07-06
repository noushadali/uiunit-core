package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.ITree;
import com.cordys.cm.uiunit.elements.cordys.ITreeItem;
import com.cordys.cm.uiunit.elements.html.IFrame;
import com.cordys.cm.uiunit.exceptions.UIUnitException;
import com.cordys.cm.uiunit.framework.IKeyboard;
import com.cordys.cm.uiunit.framework.IMouse;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cm.uiunit.message.Messages;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.cws.uiunit.util.project.IUIProject;
import com.cordys.datatransformation.cwsutilities.IDatamap;
import com.cordys.datatransformation.cwsutilities.IDatamapEditor;
import com.cordys.webservice.cwsutilities.DataTransformationWebServiceInterfaceProperties;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSetCreatorWizard;
import com.cordys.webservice.cwsutilities.SourceType;


@UIUnitTimeout(1200000)
public class DataTransformationManager extends WorkSpaceOrganizer{


	IUIProject project = null;  
	ITree sourcetree = null;
	ITree targettree = null;
	@Test
	public void createSalesProductionDT(){
		project = getProject();
		IUICWSFolder datamapFolder = project.getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_DATAMAP);
		IDatamap salesDataTrans=(IDatamap)datamapFolder.addDocument(IDatamap.class);
		
		IDatamapEditor datmapEditor = salesDataTrans.openEditor();
		salesDataTrans.openEditor();
		datmapEditor.save(EastWindArtifacts.DATAMAP_SALESPRODUCTIONDATAMAP,EastWindArtifacts.DATAMAP_SALESPRODUCTIONDATAMAP);
		datmapEditor.zoomSourceSchemaFragment(project.getName()+"\\Custom Schemas\\OrdersOne\\"+EastWindArtifacts.SCHEMA_FRAG_ORDERS,true);
		datmapEditor.zoomSourceSchemaFragment(project.getName()+"\\Custom Schemas\\ProductionOne\\"+EastWindArtifacts.SCHEMA_FRAG_PRODUCTION,false);
		IFrame modelerFrame =datmapEditor.findElement(IFrame.class, "modelerFrame");
		sourcetree = modelerFrame.findElement(ITree.class, "sourcetree_elem");
		targettree = modelerFrame.findElement(ITree.class, "targettree_elem");
		sourcetree.getRoot().expandAll();
		targettree.getRoot().expandAll();		
		
	   ITreeItem souOrderId=  sourcetree.findItem("sch:OrderID");
	   IMouse mouse= this.getContext().getRootContext().getTestEnvironment().getMouse();
	   IKeyboard kbd=this.getContext().getRootContext().getTestEnvironment().getKeyboard();
	   souOrderId.moveMouseToThis();
	   mouse.click();
	   
	   ITreeItem tarOrderId =  targettree.findItem("sch:OrderID");
	   kbd.controlKeyDown();
	   tarOrderId.moveMouseToThis();
	   mouse.click();
	   
	    datmapEditor.createLinkFromNodeToNode("sch:OrderID","sch:OrderID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:ProductID","sch:ProductID",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:UnitPrice","sch:UnitPrice",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:Quantity","sch:Quantity",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:Discount","sch:Discount",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:CustomerID","sch:CustomerID",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:EmployeeID","sch:EmployeeID",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:OrderDate","sch:OrderDate",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:RequiredDate","sch:RequiredDate",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:ShippedDate","sch:ShippedDate",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:ShipVia","sch:ShipVia",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:Freight","sch:Freight",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:ShipName","sch:ShipName",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:ShipAddress","sch:ShipAddress",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:ShipCity","sch:ShipCity",sourcetree,targettree);		
		datmapEditor.createLinkFromNodeToNode("sch:ShipRegion","sch:ShipRegion",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:ShipPostalCode","sch:ShipPostalCode",sourcetree,targettree);
		datmapEditor.createLinkFromNodeToNode("sch:ShipCountry","sch:ShipCountry",sourcetree,targettree);

		kbd.controlKeyUp();
	    datmapEditor.save();
        datmapEditor.close();
     }
	
     @Test
     public void generateSalesProductionDTWebService(){
          
            project = getProject();
          
      if(project == null)
             throw new UIUnitException(Messages.ELEMENT_NOT_FOUND_EXCEPTION,IUIProject.class,"Project SalesDemo not found..");
      
      IUICWSFolder folder = project.getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_WEBSERVICEDATAMAP);
      
      if(folder == null)
             throw new UIUnitException(Messages.ELEMENT_NOT_FOUND_EXCEPTION,IUICWSFolder.class,"folder webservicesdatamap not found..");
 
      IUIWebServiceDefinitionSetCreatorWizard wsdefsetcreationwizz = (IUIWebServiceDefinitionSetCreatorWizard)folder.addDocumentWithWizard(IUIWebServiceDefinitionSet.class);
      wsdefsetcreationwizz.setSourceType(SourceType.DATA_TRANSFORMATION);
      wsdefsetcreationwizz.setName(EastWindArtifacts.WS_DEFINITIONSET_DATATRANSFORMATION);
      
      wsdefsetcreationwizz.setNameSpace("http://schemas.cordys.com/saleproductionmapws");
      
      DataTransformationWebServiceInterfaceProperties dtwsiProperties = new DataTransformationWebServiceInterfaceProperties();
      dtwsiProperties.setDocumentName(EastWindArtifacts.DATAMAP_SALESPRODUCTIONDATAMAP);
      dtwsiProperties.setWebServiceInterfaceName(EastWindArtifacts.WS_INTERFACE_DATATRANSFORMATION);
      dtwsiProperties.setWebServiceOperationName(EastWindArtifacts.WS_OPERATION_DATATRANSFORMATION);
      wsdefsetcreationwizz.createWebServiceInterface( dtwsiProperties);
      wsdefsetcreationwizz.close();
    }


}



