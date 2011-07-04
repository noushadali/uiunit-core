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

import com.cordys.cm.uiunit.event.IApplicationDefinition;
import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.nom.XMLException;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;
import com.eibus.xml.xpath.XPath;

public class ApplicationDefinition  implements IApplicationDefinition{
	
	private String applicationDefinition;
	private boolean isInitialized = false;
	private Document document;
	private int parseApplicationDefintion;
	
	public ApplicationDefinition(String applicationDefinition)
	{
		this.applicationDefinition = applicationDefinition;
	}
	
	private void initialize()
	{
		if(!isInitialized)
		{
		document = new Document();
			try {
				parseApplicationDefintion = document.parseString(applicationDefinition);
				isInitialized = true;
			} catch (UnsupportedEncodingException e) {
				throw new UIUnitRuntimeException(e);
			} catch (XMLException e) {
				throw new UIUnitRuntimeException(e);
			}
		}
	}
	
	private String getByXPath(String xpathExpression)
	{
		if(!isInitialized)
		{
			initialize();
		}
		return getByXPath(parseApplicationDefintion, xpathExpression);
	}

	public String getApplicationId() {
		return getByXPath("//Application/id");
	}

	public String getCaption() {
		return getByXPath("//Application/caption");
	}

	public boolean isAutomatic() {
		return getByXPath("//Application/automatic") == null ? true : Boolean.getBoolean(getByXPath("//Application/automatic")) ;
	}
	
	public int getData() {
		
		if(!isInitialized)
		{
			initialize();
		}
		
		XPath xpath = null;
		
		
		
		xpath = XPath.getXPathInstance("//Application/data");
		NodeSet oNodeSet = xpath.selectNodeSet(parseApplicationDefintion);
		if (oNodeSet.hasNext())
		{
			long iResultNode = oNodeSet.next();
		    return ResultNode.getElementNode(iResultNode);
		}
		return 0;
	}

	public String getDescription() {
		return getByXPath("//Application/description");
	}

	public String getFrame() {
		return getByXPath("//Application/frame");
	}

	public String getIcon() {
		return getByXPath("//Application/icon");
	}

	public String getURL() {
		return getByXPath("//Application/url");
	}

	public boolean hasFocus() {
		return Boolean.parseBoolean(getByXPath("//Application/attribute::focus"));
	}

	public boolean showTitle() {
		return Boolean.parseBoolean(getByXPath("//Application/attribute::title"));
	}

	public boolean showInTaskbar() {
		return Boolean.parseBoolean(getByXPath("//Application/attribute::taskbar"));
	}

	public boolean showInToolbar() {
		return Boolean.parseBoolean(getByXPath("//Application/attribute::toolbar"));
	}
	
	public String toString()
	{
		if(!isInitialized)
		{
			initialize();
		}
		return Node.writeToString(parseApplicationDefintion, true);
	}
	
	private String getByXPath(int node, String xpathExpression)
	{
		XPath xpath = XPath.getXPathInstance(xpathExpression);
		NodeSet oNodeSet = xpath.selectNodeSet(node);
		if (oNodeSet.hasNext())
		{
			long iResultNode = oNodeSet.next();
			if (ResultNode.isAttribute(iResultNode)){
			      return ResultNode.getStringValue(iResultNode);
		    }
			else
			{
			    int iNode = ResultNode.getElementNode(iResultNode);
			    return Node.getData(iNode);
		    }
		}
		return null;
	}
	
	public static void main (String[]args)
	{
		String testAppDef = "<Application focus=\"true\" taskbar=\"true\" display=\"visible\" toolbar=\"false\" title=\"true\" menu=\"true\"><description>Restart Process Instances</description><caption>Restart Process Instances</caption><id>RestartProcessInstancesTue Apr 10 16:14:33 UTC+0200 2007</id><icon>/cordys/cas/xforms/images/form.gif</icon><url>/_bpm_pimrestprogress.caf</url><data><ProcessName>first.vcmdemo10.bpm</ProcessName><ProcessDescription>First BPM</ProcessDescription><ProcessVersion></ProcessVersion><OriginalNrOfInstances>1</OriginalNrOfInstances><Action>Restart</Action><Status>ABORTED</Status><Instances/><Filter databaseTable=\"PROCESS_INSTANCE\"><_CurrOrg_ databaseColumn=\"USER_NAME\"><Selection1 type=\"Like\">%o=myOrganization,cn=cordys,o=vanenburg.com%</Selection1></_CurrOrg_></Filter></data><frame docked=\"false\" height=\"321\" width=\"810\">main</frame></Application>";
		ApplicationDefinition appDef = new ApplicationDefinition(testAppDef);
		System.out.println(appDef.getApplicationId());
		System.out.println(appDef.getFrame());
		System.out.println(appDef.hasFocus());
		System.out.println(Node.writeToString(appDef.getData(), true));
		
		System.out.println("Boolean Test");
		System.out.println(Boolean.parseBoolean(""));
		System.out.println(Boolean.parseBoolean(null));
	}

}
