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
 
 package com.cordys.cm.uiunit.elements.html;

import java.awt.Point;
import java.awt.Rectangle;

import com.cordys.cm.uiunit.elements.IElement;
import com.cordys.cm.uiunit.exceptions.UIUnitException;

/**
 * Generic HTML Element Interface
 * 
 * @author ppostma
 */
public interface IHTMLElement extends IElement
{
	/**
	 * Get the ID of the element
	 * @return
	 */
	public String getId();

	/**
	 * Get the HTML Pointer of the element
	 * @return
	 */
	public String getHTMLPointer();

	/**
	 * Set the HTML Pointer of the element. Only used at creation of the object.
	 * 
	 * @param htmlPointer
	 */
	public void _setHTMLPointer( String htmlPointer );

	/**
	 * Find an element with the given ID in the subtree of this element
	 * 
	 * @param sID
	 * @return
	 */
	public IHTMLElement findElement(String sID);
	
	/**
	 * Find an element with the given ID of the given type in the subtree of this element
	 * 
	 * @param type
	 * @param sID
	 * @return
	 * 
	 * @see IHTMLElement#findElement(String)
	 */
	public <T extends IHTMLElement> T findElement(Class<T> type, String sID);
	
	/**
	 * Find a nearby element based on the given DOM String
	 * 
	 * @param domString
	 * @return generic html element
	 */
	public IHTMLElement findNearByElement( String domString );

	/**
	 * Find a nearby element based on the given DOM String of a specific type.
	 * @param type
	 * @param domString
	 * @return specific type html element
	 */
	public <T extends IHTMLElement> T findNearByElement( Class<T> type,
	        String domString );

	/**
	 * get inner HTML
	 * 
	 * @return String
	 */
	public String getInnerHTML();

	/**
	 * get outer HTML
	 * 
	 * @return String
	 */
	public String getOuterHTML();
	
	/**
	 * Get the tagname of the element
	 * 
	 * @return
	 */
	public String getTagName();

	/**
	 * Move the mouse pointer to the center of the element
	 * @return the element to which the mouse is moved
	 */
	public IHTMLElement moveMouseToThis();

	/**
	 * clicks the HTML Element by using mouse
	 */
	public void doClickWithMouse();
	/**
	 * double clicks the HTML Element by using mouse
	 */
	public void doDoubleClickWithMouse();
	/**
	 * right clicks the HTML Element by using mouse
	 */
	public void doRightClickWithMouse();
	/**
	 * right clicks the HTML Element by using mouse at the anchor point relative to the HTML Element's top left corner.
	 */
	public void	doRightClickWithMouse(Point anchorPoint);
	/**
	 * double clicks the HTML Element by using mouse at the anchor point relative to the HTML Element's top left corner.
	 */	
	public void doDoubleClickWithMouse(Point anchorPoint);
	/**
	 * clicks the HTML Element by using mouse at the anchor point relative to the HTML Element's top left corner.
	 */	
	public void doClickWithMouse(Point anchorPoint);
	/**
	 * clicks the HTML Element
	 */
	public void click() throws UIUnitException;
	/**
	 * clicks the HTML Element with out giving Focus
	 */
	public void clickWithoutFocus() throws UIUnitException;
	/**
	 * right clicks the HTML Element
	 */
	public void rightClick() throws UIUnitException;

	/**
	 * Get the offset Height
	 * 
	 * @see <a href="http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).aspx">http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).asp</a>
	 * 
	 * @return int
	 */
	public int getoffsetHeight();

	/**
	 * Get the offset width
	 * 
	 * @see <a href="http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).aspx">http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).asp</a>
	 * 
	 * @return int
	 */
	public int getoffsetWidth();

	/**
	 * Get offset top
	 * 
	 * @see <a href="http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).aspx">http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).asp</a>
	 * 
	 * @return int
	 */
	public int getoffsetTop();

	/**
	 * Get the offset left
	 * 
	 * @see <a href="http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).aspx">http://msdn2.microsoft.com/en-us/library/ms530302(VS.85).asp</a>
	 * 
	 * @return int
	 */
	public int getoffsetLeft();

	/**
	 * Method to get attribute
	 * 
	 * @param sAttrName
	 * @return String
	 */
	public String getAttribute( String sAttrName );

	/**
	 * Method to see if a certain attribute exists on the element
	 * 
	 * @param String
	 *            attributeName
	 * @return boolean exists
	 */
	public boolean hasAttribute( String attributeName );

	/**
	 * Method to get the xml namespace of an element
	 * 
	 * @return String namespace
	 */
	public String getNamespace();

	/**
	 * Get style property value. Returns "null" if property is not defined
	 * 
	 * @param sStyleAttrName
	 * @return style property value
	 */
	public String getStyleAttribute( String sStyleAttrName );

	/**
	 * Method to scroll the element into view if not visible already
	 */
	public void scrollIntoView();

	/**
	 * Method to get the x screen coordinate of the element
	 * 
	 * @return int
	 */
	public int getScreenXCoordinate();

	/**
	 * Method to get the y screen coordinate of the element
	 * 
	 * @return int
	 */
	public int getScreenYCoordinate();

	/**
	 * Method to return the screen coordinate x in the middle of the Element
	 * 
	 * @return Screen Coordinate X at middel of element
	 */
	public int getScreenXCenter();

	/**
	 * Method to return the screen coordinate y in the middle of the Element
	 * 
	 * @return Screen Coordinate Y at middel of element
	 */
	public int getScreenYCenter();

	/**
	 * Get a Rectangle object representing the screen position and size of the element
	 * 
	 * @return Rectangle
	 */
	public Rectangle getScreenRect();

	/**
	 * Method to double click on a element
	 * 
	 * @throws UIUnitException
	 */
	public void doubleClick() throws UIUnitException;
	
	/**
	 * Method to make the object loose focus (fires onblur event)
	 * 
	 */
	public void blur();

	/**
	 * Method to drag and drop HTML Elements on other HTML Elements
	 * 
	 * @param endElement
	 * @throws UIUnitException
	 */
	public void dragAndDrop( IHTMLElement endElement ) throws UIUnitException;

	/**
	 * Method to drag and drop HTML Element on the given target HTML Element. The anchorPoints are used to get relative position from the HTML Elements' top left corner, to perform the mouse press and release. 
	 * 
	 * @param destinationElement
	 * @offset sourceAnchorPoint,anchorPoint relative to the top left corner of sourceElement.
	 * @offset destinationAnchorPoint,anchorPoint relative to the top left corner of destinationElement.
	 * @throws UIUnitException
	 */
	public void dragAndDrop(IHTMLElement destinationElement, Point sourceAnchorPoint, Point destinationAnchorPoint) throws UIUnitException;
	
	/**
	 * Method to drag and drop HTML Elements at specified screen location
	 * 
	 * @param startElement
	 * @param xEnd
	 *            relative to the top left corner of the browser
	 * @param yEnd
	 *            relative to the top left corner of the browser
	 * @throws UIUnitException
	 */
	public void dragAndDrop( int xEnd, int yEnd ) throws UIUnitException;

	/**
	 * This method gives the center X coordinate of element relative to the
	 * context (frame/browser/application)
	 * 
	 * @return x coordinate of the element center
	 */
	public int getXCenter();

	/**
	 * This method gives the center Y coordinate of element relative to the
	 * context (frame/browser/application)
	 * 
	 * @return y coordinate of the element center
	 */
	public int getYCenter();

	/**
	 * Checks if element is enabled or disabled
	 * 
	 * @return true/false
	 */
	public boolean isEnabled();

	/**
	 * Checks if element is ReadOnly
	 * 
	 * @return true/false
	 */
	public boolean isReadOnly();

	/**
	 * Checks if element is visible or not.
	 * 
	 * @return true/false
	 */
	public boolean isVisible();

	/**
	 * Open context menu (right click) on this element and return the
	 * IContextMenu. After the context menu is clicked, or looses focus, this
	 * function has to be called again to open it anew. the old object can't be
	 * reused!
	 * 
	 * @return IContextMenu
	 * @deprecated Use ICordysContextMenu contextMenu = getContextMenu(ICordysContextMenu.class) instead
	 * @see <T extends IContextMenu >T getContextMenu(Class<T> contextMenuClass);
	 */
	@Deprecated
	public IContextMenu getContextMenu();
	
	/**
	 * Open context menu (right click) on this element and return the
	 * ICordysContextMenu. After the context menu is clicked, or looses focus, this
	 * function has to be called again to open it anew. the old object can't be
	 * reused!
	 * 
	 * @return ICordysContextMenu
	 */
	public <T extends IContextMenu >T getContextMenu(Class<T> contextMenuClass);

	/**
	 * @return xPosition CordysRoot relative to Desktop
	 */
	public int getCordysRootStartX();
	
	/**
	 * @return yPosition CordysRoot relative to Desktop
	 */
	public int getCordysRootStartY();
	
	/**
	 * @return element offset left in the current application window
	 */
	public int getElementOffsetLeft();
	
	/**
	 * @return element offset top in the current application window
	 */
	public int getElementOffsetTop();
	
	/**
	 * @return element width
	 */
	public int getElementOffsetWidth();
	
	/**
	 * @return element height
	 */
	public int getElementOffsetHeight();
	
	/**
	 * @return element offsetLeft 
	 *  HTMLUtil.getAbsoluteOffset(element, CordysRoot.document.body).offsetLeft
	 */
	public int getElementOffsetLeftRelativeToCordysRoot();

	/**
	 * @return element offsetTop relative to CordysRoot 
	 *  HTMLUtil.getAbsoluteOffset(element, CordysRoot.document.body).offsetTop
	 */
	public int getElementOffsetTopRelativeToCordysRoot();

	/**
	 * @return element offsetLeft relative to Desktop 
	 *  HTMLUtil.getAbsoluteOffset(element, CordysRoot.document.body).offsetLeft + screenLeft
	 */
	public int getElementOffsetLeftRelativeToDesktop();
	
	/**
	 * @return element offsetTop relative to Desktop 
	 *  HTMLUtil.getAbsoluteOffset(element, CordysRoot.document.body).offsetTop + screenTop
	 */
	public int getElementOffsetTopRelativeToDesktop();

	void check() throws UIUnitException;
}
