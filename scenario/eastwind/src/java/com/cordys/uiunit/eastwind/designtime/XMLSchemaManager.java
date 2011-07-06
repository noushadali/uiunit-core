package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.xmlschema.cwsutilities.IUISchemaFragment;
import com.cordys.xmlschema.cwsutilities.IUISchemaFragmentCreatorWizard;
import com.cordys.xmlschema.cwsutilities.IUISchemaFragmentEditor;
import com.cordys.xmlschema.cwsutilities.IUIXMLSchemaDefinition;
import com.cordys.xmlschema.cwsutilities.IUIXMLSchemaDefinitionCreatorWizard;
import com.cordys.xmlschema.cwsutilities.IUIXMLSchemaDefinitionEditor;
public class XMLSchemaManager extends WorkSpaceOrganizer
{

	@Test
	@UIUnitTimeout(8000000)
	public void createOrdersSchema()
	{
		//Get Folder foe The Schema Creation
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		//XMLNode of the schema definition
		String XmlNode = "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' elementFormDefault=\\\"qualified\\\"/>";
		//The Schema definition document
		IUIXMLSchemaDefinition schemaDef = createSchemaDefintion(folder,EastWindArtifacts.SCHEMA_DEF_ORDERS, XmlNode);
		schemaDef.refresh();
		//XML schema of the schema fragment
		String xml = "<xsd:element name='Orders' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><xsd:complexType><xsd:sequence><xsd:element name='OrderID' type='xsd:int' /><xsd:element name='OrderDetails'><xsd:complexType><xsd:sequence><xsd:element name='ProductID' type='xsd:int' /><xsd:element name='UnitPrice' type='xsd:double' /><xsd:element name='Quantity' type='xsd:int'/><xsd:element name='Discount' type='xsd:float' /></xsd:sequence></xsd:complexType></xsd:element><xsd:element name='CustomerID' type='xsd:string' /><xsd:element name='EmployeeID' type='xsd:int' /><xsd:element name='OrderDate' type='xsd:dateTime' /><xsd:element name='RequiredDate' type='xsd:dateTime' /><xsd:element name='ShippedDate' type='xsd:dateTime' /><xsd:element name='ShipVia' type='xsd:int' /><xsd:element name='Freight' type='xsd:string' /><xsd:element name='ShipName' type='xsd:string' /><xsd:element name='ShipAddress' type='xsd:string' /><xsd:element name='ShipCity' type='xsd:string' /><xsd:element name='ShipRegion' type='xsd:string' /><xsd:element name='ShipPostalCode' type='xsd:string' /><xsd:element name='ShipCountry' type='xsd:string' /></xsd:sequence></xsd:complexType></xsd:element>";
		//Creates schema fragment
		createSchemaFragment(schemaDef,EastWindArtifacts.SCHEMA_FRAG_ORDERS, xml);		
	}
	
	@Test
	@UIUnitTimeout(8000000)
	public void createOrderDetailsSchema()
	{
		//Get Folder foe The Schema Creation
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		//XMLNode of the schema definition
		String XmlNode = "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' elementFormDefault=\\\"qualified\\\" />";
		//The Schema definition document
		IUIXMLSchemaDefinition schemaDef = createSchemaDefintion(folder,EastWindArtifacts.SCHEMA_DEF_ORDERDETAILS, XmlNode);
		schemaDef.refresh();
		//XML schema of the schema fragment
		String xml = "<xsd:element name='OrderDetailsSalesOrd' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><xsd:complexType><xsd:sequence><xsd:element type='xsd:string' name='OrderID' /><xsd:element type='xsd:string' name='ProductID' /><xsd:element type='xsd:string' name='UnitPrice' /><xsd:element type='xsd:string' name='Quantity' /><xsd:element type='xsd:string' name='Discount' /><xsd:element type='xsd:string' name='DiscountRange' /></xsd:sequence></xsd:complexType></xsd:element>";
		//Creates schema fragment
		createSchemaFragment(schemaDef,EastWindArtifacts.SCHEMA_FRAG_ORDERDETAILS, xml);		
	}
	@Test
	@UIUnitTimeout(8000000)
	public void createProductionSchema()
	{
		//Get Folder foe The Schema Creation
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		//XMLNode of the schema definition
		String XmlNode = "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' elementFormDefault=\\\"qualified\\\"/>";
		//The Schema definition document
		IUIXMLSchemaDefinition schemaDef = createSchemaDefintion(folder,EastWindArtifacts.SCHEMA_DEF_PRODUCTION, XmlNode);
		schemaDef.refresh();
		//XML schema of the schema fragment
		String xml = "<xsd:element name='Production' xmlns:xsd='http://www.w3.org/2001/XMLSchema'><xsd:complexType><xsd:sequence><xsd:element name='OrderID' type='xsd:int' /><xsd:element name='OrderDetails'><xsd:complexType><xsd:sequence><xsd:element name='ProductID' type='xsd:int' /><xsd:element name='UnitPrice' type='xsd:double' /><xsd:element name='Quantity' type='xsd:int' /><xsd:element name='Discount' type='xsd:float' /></xsd:sequence></xsd:complexType></xsd:element><xsd:element type='xsd:string' name='CustomerID' /><xsd:element name='EmployeeID' type='xsd:int' /><xsd:element name='OrderDate' type='xsd:dateTime' /><xsd:element name='RequiredDate' type='xsd:dateTime' /><xsd:element name='ShippedDate' type='xsd:dateTime' /><xsd:element name='ShipVia' type='xsd:int' /><xsd:element type='xsd:string' name='Freight' /><xsd:element type='xsd:string' name='ShipName' /><xsd:element type='xsd:string' name='ShipAddress' /><xsd:element type='xsd:string' name='ShipCity' /><xsd:element type='xsd:string' name='ShipRegion' /><xsd:element type='xsd:string' name='ShipPostalCode' /><xsd:element type='xsd:string' name='ShipCountry' /></xsd:sequence></xsd:complexType></xsd:element>";
		//Creates schema fragment
		createSchemaFragment(schemaDef,EastWindArtifacts.SCHEMA_FRAG_PRODUCTION, xml);		
	}
			
	@Test
	@UIUnitTimeout(8000000)
	public void createOrdeEntryProcessSchema()
	{
		//Get Folder for The Schema Creation
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		//XMLNode of the schema definition		
		String XmlNode = "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' elementFormDefault='qualified' targetNamespace='http://schemas.cordys.com/' xmlns:tns='http://schemas.cordys.com/'>";
		//XML schema of the OrderInformation schema fragment
		String xml = "<xsd:element xmlns:xsd='http://www.w3.org/2001/XMLSchema' name='OrderInformation'><xsd:complexType><xsd:sequence><xsd:element name='Orders'><xsd:complexType/></xsd:element><xsd:element name='OrderDetails'><xsd:complexType/></xsd:element></xsd:sequence></xsd:complexType></xsd:element>";
		//XML schema of the Ordertuple schema fragment
		String xml1 = "<xsd:element xmlns:xsd='http://www.w3.org/2001/XMLSchema' name='Ordertuple'><xsd:complexType><xsd:sequence><xsd:element name='tuple'><xsd:complexType><xsd:sequence><xsd:element name='new'><xsd:complexType><xsd:sequence><xsd:element name='Order_x0020_Details' ><xsd:complexType><xsd:sequence><xsd:element name='OrderID'><xsd:complexType/></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element>";
		//XML schema of the EmployeeInfo schema fragment
		String xml2 = "<xsd:element xmlns:xsd='http://www.w3.org/2001/XMLSchema' name='EmployeeInfo'><xsd:complexType><xsd:sequence><xsd:element name='SalesRepDN'><xsd:complexType/></xsd:element></xsd:sequence></xsd:complexType></xsd:element>";
		//XML Schema
		String finalString = XmlNode+xml+xml1+xml2+"</xsd:schema>";
		//Create the Schema Definition
		IUIXMLSchemaDefinitionCreatorWizard schemaWizard = (IUIXMLSchemaDefinitionCreatorWizard)folder.addDocumentWithWizard(IUIXMLSchemaDefinition.class);
		//set the Name for the schema definition
		schemaWizard.setName(EastWindArtifacts.SCHEMA_ORDERENTRYPROCESS);	
		//paste the schema in the XMLSchemaGenerationWizard 
		schemaWizard.setXSDInTextArea(finalString);
		schemaWizard.finish();
		IUIXMLSchemaDefinition schemaDefEditor = folder.getExistingChildDocument(IUIXMLSchemaDefinition.class,EastWindArtifacts.SCHEMA_ORDERENTRYPROCESS);
		schemaDefEditor.openDesigner().close();
	}	
	
	@Test
	@UIUnitTimeout(8000000)
	public void createUpdateProductionDBSchema()
	{
		//Get Folder for The Schema Creation
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		//XMLNode of the schema definition
		String XmlNode = "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' elementFormDefault=\\\"qualified\\\"/>";
		//The Schema definition document
		IUIXMLSchemaDefinition schemaDef = createSchemaDefintion(folder, EastWindArtifacts.SCHEMA_UPDATEPRODUCTION, XmlNode);
		schemaDef.refresh();
		//XML schema of the schema fragment
		String xml = "<xsd:element xmlns:xsd='http://www.w3.org/2001/XMLSchema' name='OrderLinesInput'><xsd:complexType><xsd:sequence><xsd:element name='OrderDetails'><xsd:complexType><xsd:sequence><xsd:element name='tuple'><xsd:complexType><xsd:sequence><xsd:element name='new'><xsd:complexType><xsd:sequence><xsd:element name='Order_x0020_Details'><xsd:complexType><xsd:sequence><xsd:element name='ID'><xsd:complexType/></xsd:element><xsd:element name='ProductID'><xsd:complexType/></xsd:element><xsd:element name='UnitPrice'><xsd:complexType/></xsd:element><xsd:element name='Discount'><xsd:complexType/></xsd:element><xsd:element name='Quantity'><xsd:complexType/></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element>";
		//Creates schema fragment
		createSchemaFragment(schemaDef, EastWindArtifacts.SCHEMA_FRAG_ORDERLINESINPUT, xml);
			
	}	
	@Test
	@UIUnitTimeout(8000000)
	public void createDiscountApprovalSchema()
	{
		//Get Folder for The Schema Creation
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_CUSTOMSCHEMAS);
		//XMLNode of the schema definition
		String XmlNode = "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' elementFormDefault=\\\"qualified\\\"/>";
		//The Schema definition document
		IUIXMLSchemaDefinition schemaDef = createSchemaDefintion(folder, EastWindArtifacts.SCHEMA_DISCOUNTAPPROVAL, XmlNode);
		schemaDef.refresh();
		//Add the ORDERINFORMATION Schema Fragment
		//XML schema of the schema fragment
		String xml = "<xsd:element xmlns:xsd='http://www.w3.org/2001/XMLSchema' name='OrderInfo'><xsd:complexType><xsd:sequence><xsd:element name='OrderID'><xsd:complexType/></xsd:element><xsd:element name='DisocuntDetails'><xsd:complexType><xsd:sequence><xsd:element name='TotalDiscount'><xsd:complexType/></xsd:element></xsd:sequence></xsd:complexType></xsd:element></xsd:sequence></xsd:complexType></xsd:element>";
		//Creates schema fragment
		createSchemaFragment(schemaDef, EastWindArtifacts.SCHEMA_FRAG_ORDERINFO, xml);
				
	}
	
	private IUIXMLSchemaDefinition createSchemaDefintion(IUICWSFolder folder,String name,String xmlNode)
	{
		//Create the Schema Definition
		IUIXMLSchemaDefinitionCreatorWizard schemaWizard = (IUIXMLSchemaDefinitionCreatorWizard)folder.addDocumentWithWizard(IUIXMLSchemaDefinition.class);
		//set the Name for the schema definition
		schemaWizard.setName(name);		
		IUIXMLSchemaDefinition schemaDef = schemaWizard.finish();	
		//Open the Schema Definition Editor
		IUIXMLSchemaDefinitionEditor schemaDefEditor = schemaDef.openDesigner();
		//Set the Schema Node		
		schemaDefEditor.setXmlNode(xmlNode);
		//Save the Schema Definition
		//schemaDefEditor.save(name,name);
		schemaDefEditor.save();
		//Close the Schema Editor
		schemaDefEditor.close();
		
		return schemaDef;
	}
	private void createSchemaFragment(IUIXMLSchemaDefinition schemaDef,String name,String xml)
	{		
		//Create the Schema Definition
		IUISchemaFragmentCreatorWizard SchemaFragmentWizard = (IUISchemaFragmentCreatorWizard)schemaDef.addSchemaFragment();
		//Set the name of the Schema Definition
		SchemaFragmentWizard.setName(name);
		//Set the Description of the Schema Definition
		SchemaFragmentWizard.setDescription(name);
				
		IUISchemaFragment schemaFragment = SchemaFragmentWizard.finish();
		//Open the Schema Fragment Editor
		IUISchemaFragmentEditor schemaEditor = schemaFragment.openDesigner();
		//Set the Schema Text in the Text tab		
		schemaEditor.createInstanceUsingText(xml);
		//Select The Tree Tab
		schemaEditor.selectTreeTab();
		//Select The Instance Tab
		schemaEditor.selectInstanceTab();
		//Save the Schema Fragment
		//schemaEditor.save();
		//Close the Schema Fragment Editor
		schemaEditor.close();	
	}
}
