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

import java.io.UnsupportedEncodingException;

import com.cordys.cm.uiunit.event.IApplicationSelectEventObject;
import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;
import com.cordys.cm.uiunit.framework.IBrowser;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.XMLException;

public class ApplicationSelectEventObject implements IApplicationSelectEventObject
{
	private String javascriptForData;
	private String eventObjectType;
	private IBrowser browser;
	
	private boolean isXMLInitialized = false;
	private Document document;
	private Integer parseEventData;
	
	static
	{
		//Thread.currentThread().getContextClassLoader().setClassAssertionStatus(ApplicationSelectEventObject.class.toString(), true);
		Thread.currentThread().getContextClassLoader().setDefaultAssertionStatus(true);
	}
	
	public ApplicationSelectEventObject(String objectType, IBrowser browser, String javascriptForData)
	{
		this.javascriptForData = javascriptForData;
		this.eventObjectType = objectType;
		this.browser = browser;
	}
	
	@SuppressWarnings("unchecked")
	public String getContent() {
		
		return browser.executeJavascript(javascriptForData);
	}
	
	public int getContentAsXML()
	{
		switch(this.getType())
		{
			case XML:
				if(!isXMLInitialized)
				{
					document = new Document();
					try {
						parseEventData = document.parseString(getContent());
						isXMLInitialized = true;
					} catch (UnsupportedEncodingException e) {
						throw new UIUnitRuntimeException(e);
					} catch (XMLException e) {
						throw new UIUnitRuntimeException(e);
					}
				}
				return parseEventData;
			default:
				return 0;
		}
	}
	
	public ObjectType getType() {
		
		if (this.eventObjectType.equalsIgnoreCase("object"))
		{
			return ObjectType.OBJECT;
		}
		else if (this.eventObjectType.equalsIgnoreCase("xml"))
		{
			return ObjectType.XML;
		}
		else if (this.eventObjectType.equalsIgnoreCase("string"))
		{
			return ObjectType.STRING;
		}
		else if (this.eventObjectType.equalsIgnoreCase("undefined"))
		{
			return ObjectType.UNDEFINED;
		}
		else if (this.eventObjectType.equalsIgnoreCase("unknown"))
		{
			return ObjectType.UNKNOWN;
		}
		else if (this.eventObjectType.equalsIgnoreCase("function"))
		{
			return ObjectType.FUNCTION;
		}
		return null;
	}
	
	public String toString()
	{
		return "{type: \"" + this.eventObjectType+ "\"; data: \"" + this.javascriptForData + "\"}";
	}
	
	public static void main(String[] args)
	{
		/*ApplicationSelectEventObject eObject;
		
		eObject = new ApplicationSelectEventObject("test", "string");
		System.out.println(eObject.getType().toString());
		System.out.println(eObject.getContent(new String()));
		
		eObject = new ApplicationSelectEventObject("<test/>", "xml");
		System.out.println(eObject.getType().toString());
		System.out.println(Node.writeToString(eObject.getContent(new Integer(1)),true));
		
		eObject = new ApplicationSelectEventObject("{'test':'test'}", "object");
		System.out.println(eObject.getType().toString());
		System.out.println(eObject.getContent(new Object()));*/
		
		/*eObject = new ApplicationSelectEventObject("{\"name\":\"json\",\"bool\":true,\"int\":1}", "object");
		System.out.println(eObject.getType().toString());
		JSONObject test = new JSONObject(true);
		JSONObject json = eObject.getContent(new JSONObject());
		Iterator keyArray = json.keys();
		while(keyArray.hasNext())
		{
			System.out.println(keyArray.next());
		}*/
		
	}
}
