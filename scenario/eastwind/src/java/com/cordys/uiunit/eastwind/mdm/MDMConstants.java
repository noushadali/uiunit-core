package com.cordys.uiunit.eastwind.mdm;

public class MDMConstants {
	
	
	public static final String MDM_FOLDER_MDM = "mdm";
	
	public static final String MDM_DT_SPOKE2_TO_HUB = "Spoke2ToHubDT";
	public static final String MDM_DT_SPOKE1_TO_HUB = "Spoke1ToHubDT";
	public static final String MDM_AUDIT_KEYFIELD = "ProductID";
	public static final String MDM_AUDIT_KEYFIELD_TYPE = "Numeric";
	public static final String MDM_MODEL = "MDM Eastwind Model";
	public static final String MDM_ADMIN_ROLE = "Admin";
	public static final String MDM_XFORM_MANAGEPRODUCT = "ManageProduct";

	public static final String MDM_WS_OPERATION_PRODUCTS_COUNT = "ProductsCount";
	public static final String MDM_SPOKE1_WS_FOLDER_PATH = "EastWind/mdm/databasemetadata";
	public static final String MDM_RUNTIME_NS = "http://schemas.cordys.com/Products/MDMRuntimeWSApp";
	//HUB Related
	public static final String MDM_HUB_DSO = "DSONorthwind";
	public static final String MDM_HUB_PUBLISHER = "Eastwind MDM Hub Publisher";
	public static final String MDM_HUB_DATASTORE = "Eastwind MDM Hub Data Store";
	public static final String MDM_HUB_ENTITY = "Eastwind MDM Hub Entity";
	public static final String MDM_HUB_READ_WS_PATH = "EastWind/mdm/databasemetadata/WSAppServerPackageNorthwindDatabaseMetadata/WebServices NorthwindDatabaseMetadata/NorthwindDatabaseWebService/WebServiceInterfaceNorthwindDatabaseMetadata/GetProductsObjects";
	public static final String MDM_HUB_UPDATE_WS_PATH = "EastWind/mdm/databasemetadata/WSAppServerPackageNorthwindDatabaseMetadata/WebServices NorthwindDatabaseMetadata/NorthwindDatabaseWebService/WebServiceInterfaceNorthwindDatabaseMetadata/Update";
	public static final String MDM_HUB_BUS_OBJECT_PATH = "EastWind/mdm/databasemetadata/WSAppServerPackageNorthwindDatabaseMetadata/WebServices NorthwindDatabaseMetadata/NorthwindDatabaseWebService/Products.xsd/Products";
	public static final String MDM_WS_INTERFACE_HUB = "WebServiceInterfaceNorthwindDatabaseMetadata";
	public static final String MDM_WS_DEFINITIONSET_HUB = "NorthwindDatabaseWebService";
	public static final String MDM_HUB_AUDITTABLE = "Products_Hub_Audit";
	
	
	//      +******************Spoke1 Related*******************
	public static final String MDM_SPOKE1_DSO = "DSOSpoke1";
	public static final String MDM_SPOKE1_DBNAME = "EastwindMdmSpoke1DB";
	public static final String MDM_SPOKE1_PUBLISHER = "Eastwind MDM Spoke1 Publisher";
	public static final String MDM_SPOKE1_DATASTORE = "Eastwind MDM Spoke1 Data Store";
	public static final String MDM_SPOKE1_WSAPP = "Spoke1WSApps";	
	public static final String MDM_WS_INTERFACE_SPOKE1 = "WebServiceInterfaceSpoke1DatabaseMetadata";
	public static final String MDM_SPOKE1_READ_WS_PATH = "EastWind/mdm/databasemetadata/"+MDM_SPOKE1_DBNAME+" WebService/WebServiceInterfaceSpoke1DatabaseMetadata/GetProductsObjects";
	
	public static final String MDM_SPOKE1_UPDATE_WS_PATH = "EastWind/mdm/databasemetadata/"+MDM_SPOKE1_DBNAME+" WebService/WebServiceInterfaceSpoke1DatabaseMetadata/Update";
	public static final String MDM_SPOKE1_BUS_OBJECT_PATH = "EastWind/mdm/databasemetadata/"+MDM_SPOKE1_DBNAME+" WebService/Products.xsd/Products";
	public static final String MDM_SPOKE1_AUDITTABLE = "Products_Spoke_Audit";
	
	public static final String MDM_WS_DEFINITIONSET_SPOKE1 = "EastwindMdmSpoke1DB WebService";
	//Spoke2 Related
	public static final String MDM_SPOKE2_DSO = "DSOSpoke2";	
	public static final String MDM_SPOKE2_DBNAME = "EastwindMdmSpoke2DB";
	public static final String MDM_SPOKE2_PUBLISHER = "Eastwind MDM Spoke2 Publisher";	
	public static final String MDM_SPOKE2_DATASTORE = "Eastwind MDM Spoke2 Data Store";
	public static final String MDM_SERVICE_PROCESSOR = "Eastwind MDM Service";
	public static final String MDM_SPOKE1_ENTITY = "Eastwind MDM Spoke1 Entity";
	public static final String MDM_SPOKE2_ENTITY = "Eastwind MDM Spoke2 Entity";	
	public static final String MDM_SPOKE2_WSAPP = "Spoke2WSApps";
	public static final String MDM_WS_DEFINITIONSET_SPOKE2 = "Spoke2DatabaseWebService";
	public static final String MDM_WS_INTERFACE_SPOKE2 = "WebServiceInterfaceSpoke2DatabaseMetadata";
	public static final String MDM_SPOKE2_READ_WS_PATH = "EastWind/mdm/databasemetadata/"+MDM_SPOKE2_DBNAME+" WebService/WebServiceInterfaceSpoke2DatabaseMetadata/GetProductsObjects";
	public static final String MDM_SPOKE2_UPDATE_WS_PATH = "EastWind/mdm/databasemetadata/"+MDM_SPOKE2_DBNAME+" WebService/WebServiceInterfaceSpoke2DatabaseMetadata/Update";
	public static final String MDM_SPOKE2_BUS_OBJECT_PATH = "EastWind/mdm/databasemetadata/"+MDM_SPOKE2_DBNAME+" WebService/Products.xsd/Products";
	public static final String MDM_SPOKE2_AUDITTABLE = "Products_Spoke2_Audit";
	
	
	
	

}
