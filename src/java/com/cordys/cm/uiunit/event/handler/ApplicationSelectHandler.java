/**
 * Copyright 2005 Cordys R&D B.V. 
 * 
 * This file is part of the UIUnit framework. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
 package com.cordys.cm.uiunit.event.handler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import com.cordys.cm.uiunit.event.IApplicationDefinition;
import com.cordys.cm.uiunit.event.IApplicationSelectEventObject;
import com.cordys.cm.uiunit.event.IApplicationSelectListener;
import com.cordys.cm.uiunit.framework.IBrowser;
import com.cordys.cm.uiunit.framework.IHTMLContext;
import com.cordys.cm.uiunit.utils.ResourceLoader;

public class ApplicationSelectHandler {
	
	private static Collection<IApplicationSelectListener>  APPLICATIONSELECTLISTENERS = new ArrayList<IApplicationSelectListener>();
	private static IBrowser CORDYSBROWSER = null;
	
	private static boolean ISREGISTERED = false;
	
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private static boolean registerOnApplicationSelect(IBrowser browser)
	{
		if(!ISREGISTERED)
		{
			CORDYSBROWSER = browser;
//			CORDYSBROWSER.addEventListener
//			(
//					
//				new BrowserAdapter()
//				{
//		            public void onBeforeNavigate(BrowserEvent e)
//		            {
//		                    //Catching the WRUI communication
//		                    if(e.getURL().startsWith("wrui://applicationSelect/"))
//		                    {
//		                            e.blockLoad();
//		                            notifyApplicationSelectListeners();
//		                    }
//		            }
//				}
//			);
			
			if(CORDYSBROWSER.executeJavascript("var res;if(typeof(CordysRoot) !=\"undefined\" && CordysRoot && CordysRoot.system && CordysRoot.system.select){res=true;}else{res=false;}res;").equalsIgnoreCase("false"))
			{
				return false;
			}
			
			CORDYSBROWSER.executeJavascript("CordysRoot.system._select = CordysRoot.system.select");
			
			String jsonLibrary = "";
			try {
				jsonLibrary = ResourceLoader.loadText("/com/cordys/cm/uiunit/event/handler/json.js");
			} catch (FileNotFoundException e1) {
				org.junit.Assert.fail("Unable to load the json javascript library which is required for Applicationlistener");
			}
			CORDYSBROWSER.executeJavascript(jsonLibrary);
			
			CORDYSBROWSER.executeJavascript("" +
					"CordysRoot.system.select = function (applicationDefinition, srcApplication, eventData, eventCallbackMethod)" + NEWLINE +
					"{" + NEWLINE + 
					" 	var JSon = {'applicationDefinition':applicationDefinition.xml, 'scrApplication':srcApplication.container.applicationId, 'eventData':eventData, 'eventCallbackMethod':eventCallbackMethod};" + NEWLINE +
					"   CordysRoot.system.uiunitApplicationSelect = JSon;" +
					"   if(srcApplication.container.applicationId != 'Menu')" +
					" 		CordysRoot.document.location = 'wrui://applicationSelect/'; " + NEWLINE +
					" 	return CordysRoot.system._select(applicationDefinition, srcApplication, eventData, eventCallbackMethod);" + NEWLINE +
					"};");
			
			CORDYSBROWSER.executeJavascript("window.status = 'Registration succeeded';");
		}
		ISREGISTERED = true;
		return true;
	}
	
	private synchronized static void notifyApplicationSelectListeners()
	{
		String applicationDefinition = CORDYSBROWSER.executeJavascript("CordysRoot.system.uiunitApplicationSelect.applicationDefinition");
		IApplicationDefinition appDefinition = new ApplicationDefinition(applicationDefinition);
		
		String eventObjectType = "";
		String javascript ="var res" +
				"if(typeof(CordysRoot.system.uiunitApplicationSelect.eventData)==\"undefined\")" +
				"{" +
				"res = \"undefined\";" +
				"}" +
				"else" +
				"{" +
				"res=CordysRoot.JSON.getType(CordysRoot.system.uiunitApplicationSelect.eventData);" +
				"}" +
				"res;";
		eventObjectType = CORDYSBROWSER.executeJavascript(javascript);
		IApplicationSelectEventObject eventObject = new ApplicationSelectEventObject(eventObjectType, CORDYSBROWSER, "CordysRoot.JSON.getData(CordysRoot.system.uiunitApplicationSelect.eventData);");	
		
		String sourceApplicationId = CORDYSBROWSER.executeJavascript("CordysRoot.system.uiunitApplicationSelect.scrApplication");
		String callbackHandler = CORDYSBROWSER.executeJavascript("CordysRoot.system.uiunitApplicationSelect.eventCallbackMethod");
				
		
		synchronized (APPLICATIONSELECTLISTENERS)
		{
			for(IApplicationSelectListener listener : APPLICATIONSELECTLISTENERS)
			{
				try 
				{
					listener.onApplicationSelect(appDefinition, sourceApplicationId, eventObject,callbackHandler);
				}
				catch (Throwable t)
				{
					//listener.getTestCase().handleException(t);
				}
			}
		}
		
	}

	private void unregisterOnApplicationSelect()
	{
		CORDYSBROWSER.executeJavascript("CordysRoot.system.select = CordysRoot.system._select");
		ISREGISTERED = false;
	}
	
	public void finalize() throws Throwable
	{
		if(ISREGISTERED)
		{
			unregisterOnApplicationSelect();
		}
		super.finalize();
	}
	
	public static boolean addApplicationSelectListener(IApplicationSelectListener listener, IHTMLContext context)
	{
		if(registerOnApplicationSelect(context.getRootContext().getTestEnvironment().getBrowser()))
		{
			if(!APPLICATIONSELECTLISTENERS.contains(listener))
			{
				synchronized (APPLICATIONSELECTLISTENERS)
				{
					APPLICATIONSELECTLISTENERS.add(listener);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean removeApplicationSelectListener(IApplicationSelectListener listener)
	{
		
		if(APPLICATIONSELECTLISTENERS.contains(listener))
		{
			synchronized (APPLICATIONSELECTLISTENERS)
			{
				return APPLICATIONSELECTLISTENERS.remove(listener);
			}
		}
		return false;
	}
	
	

}
