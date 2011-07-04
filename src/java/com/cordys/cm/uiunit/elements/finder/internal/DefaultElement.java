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
 
 package com.cordys.cm.uiunit.elements.finder.internal;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import com.cordys.cm.uiunit.elements.finder.IUnQualifiedHTMLElement;
import com.cordys.cm.uiunit.framework.IHTMLContext;

/**
 * Implementation of an Unqualified Element
 * 
 * @author ppostma
 */
public class DefaultElement implements IUnQualifiedHTMLElement
{

	private String id;

	private String htmlPointer;

	private IHTMLContext context;
	
	private String tagName = null;
	
	private String className = null;
	
	private String nameSpace = null;
	
	private HashMap<String, String> attributes = null;
	
	private HashMap<String, String> hasAttributes = null;
	
	private List<String> librariesList = null;

	public DefaultElement()
	{

	}

	/**
	 * Get the html pointer of the element
	 * 
	 * @return html pointer
	 */
	@Override
	public String getHTMLPointer()
	{
		if( this.htmlPointer != null )
		{
			return this.htmlPointer;
		}
		return this.context.getWindow()
		        + String.format(".document.getElementById(\"%s\")",
		                this.getId());
//		return this.context.getWindow() + "." + this.getId();
	}

	/**
	 * Get the value of the specified attribute.
	 * 
	 * @param attributeName
	 * @return value of the attribute, or empty string if it doesn't exists
	 */
	@Override
	public String getAttribute( String attributeName )
	{
		if ( this.attributes == null )
		{
			this.attributes = new HashMap<String, String>();
		}
		String attrValue = this.attributes.get(attributeName);
		if (attrValue == null)
		{
			attrValue = this.context.executeJavascript(this.getHTMLPointer() + "." + attributeName );
			if ( attrValue == null )
			{
				attrValue = this.context.executeJavascript(this.getHTMLPointer()
				        + ".getAttribute(\"" + attributeName + "\")+\"\"");
			}
			this.attributes.put(attributeName, attrValue);
		}
		return attrValue;
	}

	/**
	 * Check if a certain attribute exists on the element
	 * 
	 * @param attributeName
	 * @return true/false
	 */
	@Override
	public boolean hasAttribute( String attributeName )
	{
		if (this.hasAttributes == null)
		{
			this.hasAttributes = new HashMap<String, String>();
		}
		String attr = this.hasAttributes.get(attributeName);
		if (attr == null)
		{
			String searchStr1 = this.getHTMLPointer() + "."  + attributeName;
			String searchStr2 = this.getHTMLPointer() + ".getAttribute(\"" + attributeName + "\")";
			String jsScript = "";
			jsScript += "var exists = false;";
			jsScript += "if (typeof(" + searchStr1 + ") != \"undefined\" && " + searchStr1 + " != null) exists = true;";
			jsScript += "if ( !exists && typeof(" + searchStr2 + ") != \"undefined\" && " + searchStr2 + " != null) exists = true;";
			jsScript += "exists;";
			attr = this.context.executeJavascript(jsScript);		
			this.hasAttributes.put(attributeName, attr);
		}
		boolean test = "true".equalsIgnoreCase(attr);
		return test;
	}

	/**
	 * Get the namespace of the element. Namespace of regular HTML elements is
	 * 'html'. For cordys elements this is 'eibus'.
	 * 
	 * @return namespace
	 */
	@Override
	public String getNamespace()
	{
		if (this.nameSpace == null)
		{
			this.nameSpace = this.context.executeJavascript(this.getHTMLPointer()
			        + ".scopeName");
		}
		return this.nameSpace;
	}

	/**
	 * Get the classname ( &lt;t class="X"&gt; ) of the element
	 * 
	 * @return .className
	 */
	@Override
	public String getClassName()
	{
		// TODO: Should check whether caching the className is creating any problem.
		// In a scenario where the classname is changed in runtime, then the cached property woould give the old value.
		if (this.className == null)
		{
			this.className = this.context.executeJavascript(this.getHTMLPointer()
		        + ".className");
		}
		return this.className;
	}

	/**
	 * Get the InnerHTML of the element
	 * 
	 * @return .innerHTML
	 */
	@Override
	public String getInnerHTML()
	{
		return this.context.executeJavascript(this.getHTMLPointer()
		        + ".innerHTML");
	}

	/**
	 * Get the OuterHTML of the element
	 * 
	 * @return .outerHTML
	 */
	@Override
	public String getOuterHTML()
	{
		return this.context.executeJavascript(this.getHTMLPointer()
		        + ".outerHTML");
	}

	/**
	 * Get the tagname of the element
	 * 
	 * @return tagname of the element
	 */
	@Override
	public String getTagName()
	{
		if (this.tagName == null)
		{
    		this.tagName = this.context.executeJavascript(this.getHTMLPointer()
    		        + ".tagName");
		}
		return this.tagName;
	}

	/**
	 * Set the context of this element. This method should only be called on
	 * creation of the object.
	 * 
	 * @param context
	 */
	@Override
	public void _setContext( IHTMLContext context )
	{
		this.context = context;
	}

	/**
	 * Get the context the element is living in
	 * 
	 * @return context
	 */
	@Override
	public IHTMLContext getContext()
	{
		return this.context;
	}

	/**
	 * Set the ID of the element. This method should only be called on creation
	 * of the object.
	 * 
	 * @param id
	 */
	@Override
	public void _setId( String id )
	{
		this.id = id;
	}

	/**
	 * Get the ID of the element
	 * 
	 * @return id of the element
	 */
	@Override
	public String getId()
	{
		if( this.id == null )
		{
			this.id = this.context.executeJavascript(this.getHTMLPointer() + ".id");
		}
		return this.id;
	}

	/**
	 * Set the HTML Pointer of the element. This method should only be called on
	 * creation of the object.
	 * 
	 * @param htmlPointer
	 */
	@Override
	public void _setHTMLPointer( String htmlPointer )
	{
		this.htmlPointer = htmlPointer;
	}

	/**
	 * Check whether the element exists in the DOM Tree
	 * 
	 * @return true/false
	 */
	@Override
	public boolean exists()
	{
		return "true".equalsIgnoreCase(this.context.executeJavascript(this.getHTMLPointer()+ " != null;"));
	}
	
	public List<String> getLibrariesAttached()
	{
		if (this.librariesList == null)
		{
			this.librariesList = new Vector<String>();
			String htmlPointer = this.getHTMLPointer();
			String javaScript = "";
			javaScript += "var ele = " + htmlPointer + ";";
			javaScript += "var libraries = ele.ownerDocument.defaultView.application.getLibrariesAttached(ele);";
			javaScript += "var text = '';";
			javaScript += "for (var behaviorId in libraries)";
			javaScript += "{";
			javaScript += "		text += behaviorId + ' : ';";
			javaScript += "}";
			javaScript += "text = text.substring(0, text.length-3);";
			javaScript += "text;";
			String result = this.getContext().executeJavascript( javaScript );
			
			StringTokenizer st = new StringTokenizer(result, " : ");
			while(st.hasMoreTokens())
			{
				this.librariesList.add(st.nextToken());
			}
		}
		return this.librariesList;
	}
	
	@Override
	public String toString()
	{
		if( id != null)
		{
			return "[DefaultElement] ID: " + id;
		}
		if( htmlPointer != null)
		{
			return "[DefaultElement] HTMLPointer: " + htmlPointer;
		}
		return super.toString();
	}
}
