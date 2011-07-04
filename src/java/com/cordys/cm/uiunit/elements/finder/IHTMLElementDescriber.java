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

import com.cordys.cm.uiunit.elements.html.IHTMLElement;

/**
 * Element Describer Interface
 * 
 * @author ppostma
 */
public interface IHTMLElementDescriber
{

	/**
	 * Get the tagname of the element this describer describes. Used for the
	 * initial check of an element (if this doesn't match, don't bother with the
	 * rest)
	 * 
	 * @return The tagname on which this describer matches
	 */
	public abstract String getTagName();

	/**
	 * Get the base type of the element this describer describes. The base type
	 * is a describer that describes a more generic element of the same type
	 * this describer describes.
	 * 
	 * @return the class which is its base type, null if this is the base and
	 *         this does not override a certain type
	 */
	public abstract Class<? extends IHTMLElementDescriber> getBaseType();

	/**
	 * This method is called to do the exact determination if this type matches
	 * with the element. Initial check is always on tagname.
	 * 
	 * @param element
	 *            The element to check if it can be described by this describer
	 * @return The result if the determination has succeeded or not
	 */
	public abstract boolean canDescribe( IUnQualifiedHTMLElement element );

	/**
	 * Get an implementation of the element this describer describes
	 * 
	 * @return Return a new instance of the specified HTMLElement
	 */
	public abstract IHTMLElement getNewHTMLElement( );

}
