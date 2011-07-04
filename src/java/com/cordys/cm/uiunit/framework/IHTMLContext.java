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
 
 package com.cordys.cm.uiunit.framework;

import java.util.List;

import com.cordys.cm.uiunit.elements.IElement;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.exceptions.UIUnitException;

public interface IHTMLContext
{
	public IRootHTMLContext getRootContext();

	/**
	 * add element to this context (by applying this context on the element)
	 * @param IElement element to apply the context to / add to this context
	 */
	public void addElement(IElement element);

	public int getScreenXCoordinate();

	public int getScreenYCoordinate();
	
	public int getBrowserXCoordinate();

	public int getBrowserYCoordinate();

	public String getWindow();

	public String executeJavascript(String javascript);

	public void waitForIdle();
	
	public long waitForIdle(int waitTime);

	/**
	 * Find a specified HTML element type inside the application based on the id
	 * <BR>
	 * <BR>
	 * Example:<BR>
	 * &nbsp;&nbsp;&nbsp;Span span = findHTMLElement(Span.class, "spanID"); 
	 * 
	 * @param type of the element which needs to be found
	 * @param elementID the id of the element
	 * @return the element
	 * @throws UIUnitException when the found element is not of the right type.
	 */
	public <T extends IHTMLElement> T findElement(Class<T> type, String elementID);

	public <T extends IHTMLElement> List<T> findElementsByName(Class<T> type, String name);

	public <T extends IHTMLElement> List<T> findElementsByTagName(Class<T> type, String tagName);
	
	/**
	 * Check if an element exists with the given ID within this context
	 * @param String Id of the element
	 * @return boolean
	 */
	public boolean elementExistsById(String id);
	
	/**
	 * Get the reference to the logger
	 * @return the logger class
	 */
	public ILogger getLogger();


}
