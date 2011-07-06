package com.cordys.uiunit.eastwind.designtime;

public class EastWindArtifacts {
 //XForms Related
 public static final String FOLDER_XFORMS = "XForms";
 public static final String FOLDER_BPM = "bpm";
 public static final String FOLDER_DBSCHEMA = "databasemetadata";
 public static final String FOLDER_WEBSERVICEDATAMAP = "webservicesdatatransformation";
 public static final String FOLDER_WEBSERVICEDECISIONCASE = "webservicesdecisiontable";
 public static final String FOLDER_ROLES = "Roles";
 public static final String FOLDER_ORGANIZATION = "Organization";
 public static final String FOLDER_WORKLISTS = "Worklists";
 public static final String FOLDER_DATAMAP = "datatransformation";
 public static final String FOLDER_CUSTOMSCHEMAS = "Custom Schemas";
 public static final String FOLDER_OBJETTEMPLATES = "Object Templates";
 public static final String FOLDER_RULES = "rules";
 public static final String FOLDER_BAM_PMO = "BAM_PMO";
 public static final String FOLDER_BAM_KPI = "BAM_KPI";
 public static final String FOLDER_BAM_BM = "BAM_BM";
 public static final String FOLDER_BAM_BER="BAM_BER";
 public static final String FOLDER_BAM_DASHBOARDS="BAM_DASHBOARDS";

  //Xform Artifacts
 public static final String XFORM_ASSIGNSALESREP = "AssignSalesRep";
 public static final String XFORM_ORDERREGISTRATION ="OrderRegistration";
 public static final String XFORM_SALESORDERAPPROVAL ="SalesOrdApproval";
 public static final String XFORM_SALESORDERCOMPLETE = "SalesRepOrderComplete";
 public static final String XFORM_NOTIFYAPPLICATIONSERVICE = "NotifyApplicationService";
 public static final String XFORM_DISCOUNTAPPROVAL = "DiscountApproval";
 public static final String XFORM_UPDATEPRDUCTIONDB = "UpdateProductionDB";
 
 public static final String DELIVERYMODEL_ASSIGNSALESREP = "AssignSalesRepModel";
 public static final String DELIVERYMODEL_ORDERREGISTRATION ="OrderRegistrationModel";
 public static final String DELIVERYMODEL_SALESORDERAPPROVAL ="SalesOrdApprovalModel";
 public static final String DELIVERYMODEL_SALESORDERCOMPLETE = "SalesRepOrderCompleteModel";
 public static final String DELIVERYMODEL_NOTIFYAPPLICATIONSERVICE = "NotifyApplicationServiceModel";


 //Organization related
 public static final String ORGANIZATIONUNITTYPE_EASTWIND = "Eastwind";
 public static final String ORGANIZATIONUNITTYPE_SALES = "Sales";
 public static final String ORGANIZATIONUNIT_TYPE_PRODUCTION = "Production";
 public static final String ORGANIZATIONUNIT_SALESDIVISION = "SalesDivision";
 public static final String ORGANIZATIONUNIT_EASTERNREGION = "EasternRegion";
 public static final String ORGANIZATIONUNIT_SOUTHERNREGION = "SouthernRegion";
 public static final String ORGANIZATIONUNIT_NORTHENREGION = "NorthernRegion";
 public static final String ORGANIZATIONUNIT_PRODUCTDIVISION = "ProductDivision";
 public static final String ORGANIZATIONUNIT_CUSTOMER = "Customer";
 public static final String ORGANIZATIONUNIT_EASTWINDORG= "EastwindOrg";
 public static final String ORGANIZATIONMODEL_EAST = "EastOrgModel";

 //WorkList related
 public static final String WORKLISTS_SALESCOORDINATOR= "SalesCoordinatorWL";
 public static final String WORKLISTS_SALESREPRESENTATIVE = "SalesRepresentativeWL";
 public static final String WORKLISTS_MANAGER = "ManagerWL";
 public static final String WORKLISTS_VPSALES = "VPSalesWL";
 public static final String WORKLISTS_CUSTOMER = "CustomerWL";

 //DataMap Related
 public static final String DATAMAP_SALESPRODUCTIONDATAMAP = "SalesProductionDataTransformation";

 //schema Artifacts
 public final static String SCHEMA_DEF_ORDERS = "OrdersOne";
 public final static String SCHEMA_FRAG_ORDERS = "Orders";
 public final static String SCHEMA_DEF_PRODUCTION = "ProductionOne";
 public final static String SCHEMA_FRAG_PRODUCTION = "Production";
 public final static String SCHEMA_DEF_ORDERDETAILS = "OrderDetailsSalesOrdOne";
 public final static String SCHEMA_FRAG_ORDERDETAILS = "OrderDetailsSalesOrd";

 //Object Template Artifacts
 public final static String OBJECT_TEMPLATE_ORDERS = "Orders";
 public final static String OBJECT_TEMPLATE_PRODUCTION = "Production";
 public final static String OBJECT_TEMPLATE_ORDERDETAILS = "OrderDetailsSalesOrder";

 //Rule Group Artifact
 public final static String RULE_GROUP_ORDERS = "Discountrulegroup";

 //Decision Case Artifact
 public final static String DECISION_CASE = "DiscountDecision";

 //BPM Artifacts
public static final String BPM_ORDERENTRYPROCESS = "OrderEntryProcess";
public static final String BPM_DISCOUNTAPPROVAL = "DiscountApproval";
public static final String BPM_UPDATEPRODUCTION = "ProductionDBUpdate";

//BAM Artifacts
public static final String BAM_PMO = "PmoOnOrders";
public static final String BAM_KPI = "KPIOnOrders";
public static final String BAM_BM = "BMOnOrders";
public static final String BAM_BER="BEROnOrders";

public static final String KPI_TYPE_LINER = "Linear Gauge";
public static final String KPI_TYPE_ANGULAR = "Angular Gauge";
public static final String KPI_TYPE_VERTICAL = "Vertical Bullet";
public static final String KPI_TYPE_HORIZANTAL = "Horizontal Bullet";


public static final String GRAPH_TYPE_LINE = "Line Chart";
public static final String GRAPH_TYPE_BAR = "Bar Chart";
public static final String GRAPH_TYPE_PYRAMID = "Pyramid";
public static final String GRAPH_TYPE_PIE = "Pie Chart";
public static final String GRAPH_TYPE_STACKED = "Stacked Bar Chart";
public static final String GRAPH_TYPE_COMBINATIONAL = "Combinational Chart";
public static final String GRAPH_TYPE_DUALY = "Combinational Dual-Y Chart";

//Roles Related
public static final String ROLES_SALESMANAGER="SalesManager";
public static final String ROLES_VPSALES="VPSales";
public static final String ROLES_CUSTOMER="Customer";
public static final String ROLES_SALESREPRESENTATIVE="SalesRepresentative";
public static final String ROLES_PRESIDENT="President";
public static final String ROLES_SALESCOORDINATOR="SalesCoordinator";

//DBSchema Artifact

 public static final String NORTHWIND_DBSCHEMA = "NorthwindDatabaseMetadata";
 public static final String PRODUCTION_DBSCHEMA = "ProductionDatabaseMetadata";

 //WsAppS Package Artifact

 public static final String WSAPPS_PACKAGE_FOLDER_NORTHWIND = "WSAppServerPackageNorthwindDatabaseMetadata";
 public static final String WSAPPS_PACKAGE_FOLDER_PRODUCTION = "WSAppServerPackageProductionDatabaseMetadata";

 public static final String WSAPPS_PACKAGE_NORTHWIND = "NorthwindDatabaseMetadata";
 public static final String WSAPPS_PACKAGE_PRODUCTIONDB = "ProductionDatabaseMetadata";

 //WebService Definition Set Artifact(under dbschema Folder)

 public static final String WS_FOLDER_NORTHWIND = "WebServices NorthwindDatabaseMetadata";
 public static final String WS_FOLDER_PRODUCTIONDB = "WebServices ProductionDatabaseMetadata";

 public static final String WS_DEFINITIONSET_NORTHWIND = "NorthwindDatabaseWebService";
 public static final String WS_DEFINITIONSET_PRODUCTION = "ProductionDatabaseWebService";

 public static final String WS_INTERFACE_NORTHWIND = "WebServiceInterfaceNorthwindDatabaseMetadata";
 public static final String WS_INTERFACE_PRODUCTIONDB = "WebServiceInterfaceProductionDatabaseMetadata";

 //webservice Definition set Artifact (under webservicedatamap Folder)

 public static final String WS_DEFINITIONSET_DATATRANSFORMATION ="SalesProductionDataTransformationWebService";
 public static final String WS_INTERFACE_DATATRANSFORMATION = "SalesProductionDataTransformation_WebserviceInterface";
 public static final String WS_OPERATION_DATATRANSFORMATION = "SalesProductionDataTransformation_WebserviceOperation"; 

//webservice Definition set on Decision Case Artifact (under webservicesdecisioncase Folder)

 public static final String WS_DEFINITIONSET_DECISIONCASE ="DiscountDecision_Web_Service";
 public static final String WS_INTERFACE_DECISIONCASE = "DiscountDecision_WebserviceInterface";
 public static final String WS_OPERATION_DECISIONCASE = "DiscountDecision_WebserviceOperation";
 
 //BPMSchemas
 public final static String SCHEMA_ORDERENTRYPROCESS = "OrdeEntryProcess";
 public final static String SCHEMA_FRAG_ORDERINFORMATION = "OrderInformation";
 public final static String SCHEMA_FRAG_ORDERLINESINPUT_OEP = "OrderLinesInput";//EMPTY
 public final static String SCHEMA_FRAG_ORDERTUPLE = "Ordertuple";
 public final static String SCHEMA_FRAG_EMPLOYEEINFO = "EmployeeInfo";
 
 public final static String SCHEMA_DISCOUNTAPPROVAL = "DiscountApproval";
 public final static String SCHEMA_FRAG_ORDERINFO = "OrderInfo";
 
 
 public final static String SCHEMA_UPDATEPRODUCTION = "UpdateProductionDB";
 public final static String SCHEMA_FRAG_ORDERLINESINPUT = "OrderLinesInput";
 
 
 public final static String SCHEMA_BPM_COMMON = "BpmSchemas";//common schemas
 public final static String SCHEMA_FRAG_ORDERMAPINPUT = "OrderMapInput";
 public final static String SCHEMA_FRAG_DUMMY = "dummy";

//Users related

public static final String USERS_ANNE="Anne";
public static final String USERS_SUYAMA="Suyama";
public static final String USERS_KING="King";
public static final String USERS_STEVEN="Steven";
public static final String USERS_CAGE="Cage";
public static final String USERS_JONES="Jones";
public static final String USERS_FULLER="Fuller";
public static final String USERS_LAURA="Laura";
public static final String USERS_PASSWD="cordys";

}
