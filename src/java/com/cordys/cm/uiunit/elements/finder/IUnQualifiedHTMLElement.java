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
 
 package com.cordys.cm.uiunit.elements.finder;

import java.util.List;

import com.cordys.cm.uiunit.framework.IHTMLContext;

/**
 * Unqualified HTML Element. An element which type has not yet been determined
 * (not even generic HTMLElement). Used by the element finder as a base to
 * identify the type with.
 * 
 * @author ppostma
 */
public interface IUnQualifiedHTMLElement
{
	/**
	 * Get the ID of the element
	 * 
	 * @return id of the element
	 */
	public String getId();

	/**
	 * Get the tagname of the element
	 * 
	 * @return tagname of the element
	 */
	public String getTagName();

	/**
	 * Get the OuterHTML of the element
	 * 
	 * @return .outerHTML
	 */
	public String getOuterHTML();

	/**
	 * Get the InnerHTML of the element
	 * 
	 * @return .innerHTML
	 */
	public String getInnerHTML();

	/**
	 * Get the classname ( &lt;t class="X"&gt; ) of the element
	 * 
	 * @return .className
	 */
	public String getClassName();

	/**
	 * Get the value of the specified attribute.
	 * 
	 * @param attributeName
	 * @return value of the attribute, or empty string if it doesn't exists
	 */
	public String getAttribute( String attributeName );

	/**
	 * Check if a certain attribute exists on the element
	 * 
	 * @param attributeName
	 * @return true/false
	 */
	public boolean hasAttribute( String attributeName );

	/**
	 * Get the namespace of the element. Namespace of regular HTML elements is
	 * 'html'. For cordys elements this is 'eibus'.
	 * 
	 * @return namespace
	 */
	public String getNamespace();

	/**
	 * Get the html pointer of the element
	 * 
	 * @return html pointer
	 */
	public String getHTMLPointer();

	/**
	 * Get the context the element is living in
	 * 
	 * @return context
	 */
	public IHTMLContext getContext();

	/**
	 * Set the context of this element. This method should only be called on
	 * creation of the object.
	 * 
	 * @param context
	 */
	public void _setContext( IHTMLContext context );

	/**
	 * Set the ID of the element. This method should only be called on creation
	 * of the object.
	 * 
	 * @param id
	 */
	public void _setId( String id );

	/**
	 * Set the HTML Pointer of the element. This method should only be called on
	 * creation of the object.
	 * 
	 * @param htmlPointer
	 */
	public void _setHTMLPointer( String htmlPointer );

	/**
	 * Check whether the element exists in the DOM Tree
	 * 
	 * @return true/false
	 */
	public boolean exists();
	
	public abstract List<String> getLibrariesAttached();

}
