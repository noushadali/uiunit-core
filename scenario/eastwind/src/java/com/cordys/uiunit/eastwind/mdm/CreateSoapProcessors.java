package com.cordys.uiunit.eastwind.mdm;

import org.junit.Before;
import org.junit.Test;

import com.cordys.ciui.cusputilities.IManageSystemResources;
import com.cordys.ciui.cusputilities.WSAppsSoapService;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.mdm.cusputilities.CreateMDMSoapNodes;
import com.cordys.mdm.cusputilities.MDMService;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class CreateSoapProcessors extends UIUnitTestCase{
	private  IManageSystemResources manageResources=null;	

	@UIUnitTimeout(1200000)
	@Before
	public void setup()
	{
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
	}
	@UIUnitTimeout(120000000)
	@Test
	public void createWsAppForSpoke1(){
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();
		WSAppsSoapService myService=new WSAppsSoapService();
		myService.setSoapNodeName(MDMConstants.MDM_SPOKE1_WSAPP);
		myService.setSoapProcessorName(MDMConstants.MDM_SPOKE1_WSAPP);
		myService.setConnectorName("WS-AppServer");
		myService.setDsoName(MDMConstants.MDM_SPOKE1_DSO);
		myService.setSetSpecialCharacterSupport(true);
		manageResources.createNewService(myService);
		this.getContext().waitForIdle();
		
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createWsAppForSpoke2(){
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();	
		WSAppsSoapService myService=new WSAppsSoapService();
		myService.setSoapNodeName(MDMConstants.MDM_SPOKE2_WSAPP);
		myService.setSoapProcessorName(MDMConstants.MDM_SPOKE2_WSAPP);
		myService.setConnectorName("WS-AppServer");
		myService.setDsoName(MDMConstants.MDM_SPOKE2_DSO);
		myService.setSetSpecialCharacterSupport(true);
		manageResources.createNewService(myService);	
		this.getContext().waitForIdle();
	}	
	
	@UIUnitTimeout(120000000)
	@Test
	public void createSpoke1Publisher(){
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();
		CreateMDMSoapNodes mdmService = new CreateMDMSoapNodes();
		mdmService = new CreateMDMSoapNodes();		
		mdmService.setConnectorName("MDM Publisher");
		mdmService.setHubPublisher(false);
		mdmService.setDifferentApplicationDatabase(true);
		mdmService.setApplicationDSO(MDMConstants.MDM_SPOKE1_DSO);
		mdmService.setRepositoryDSO("Cordys System");		
		mdmService.setSoapNodeName(MDMConstants.MDM_SPOKE1_PUBLISHER);
		mdmService.setSoapProcessorName(MDMConstants.MDM_SPOKE1_PUBLISHER);
		mdmService.setStartAutomatically(true);
		manageResources.createNewService(mdmService);
		this.getContext().waitForIdle();
		
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createSpoke2Publisher(){
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();
		CreateMDMSoapNodes mdmService = new CreateMDMSoapNodes();
		mdmService.setConnectorName("MDM Publisher");
		mdmService.setHubPublisher(false);
		mdmService.setDifferentApplicationDatabase(true);
		mdmService.setApplicationDSO(MDMConstants.MDM_SPOKE2_DSO);
		mdmService.setRepositoryDSO("Cordys System");		
		mdmService.setSoapNodeName(MDMConstants.MDM_SPOKE2_PUBLISHER);
		mdmService.setSoapProcessorName(MDMConstants.MDM_SPOKE2_PUBLISHER);
		mdmService.setStartAutomatically(true);
		manageResources.createNewService(mdmService);
		this.getContext().waitForIdle();
		
	}
	
	@UIUnitTimeout(120000000)
	@Test
	public void createHubPublisher(){
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();
		CreateMDMSoapNodes mdmService = new CreateMDMSoapNodes();
		mdmService.setConnectorName("MDM Publisher");
		mdmService.setHubPublisher(true);
		mdmService.setDifferentApplicationDatabase(true);
		mdmService.setApplicationDSO(MDMConstants.MDM_HUB_DSO);
		mdmService.setRepositoryDSO("Cordys System");
		mdmService.setSoapNodeName(MDMConstants.MDM_HUB_PUBLISHER);
		mdmService.setSoapProcessorName(MDMConstants.MDM_HUB_PUBLISHER);
		mdmService.setStartAutomatically(true);
		manageResources.createNewService(mdmService);
		this.getContext().waitForIdle();
		
	}	
	
	@UIUnitTimeout(120000000)
	@Test
	public void createMDMService()
	{
		IManageSystemResources manageResources=null;
		manageResources=CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageSystemResources.class);
		Assert.assertNotNull("not found",manageResources);
		manageResources.waitForIdle();
		MDMService mdmService = new MDMService();
		mdmService.setConnectorName("MDM Service");
		mdmService.setHubPublisherGroup(MDMConstants.MDM_HUB_PUBLISHER);
		mdmService.setSoapNodeName(MDMConstants.MDM_SERVICE_PROCESSOR);
		mdmService.setSoapProcessorName(MDMConstants.MDM_SERVICE_PROCESSOR);
		mdmService.setStartAutomatically(true);
		mdmService.setConnectorName("MDM Service");
		manageResources.createNewService(mdmService);		
	}

}
