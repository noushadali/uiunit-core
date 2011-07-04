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
 
 package com.cordys.cm.uiunit.message;

import com.cordys.cm.uiunit.localization.ILocalizableString;
import com.cordys.cm.uiunit.localization.MessageBundle;

public class Messages
{
	public static final MessageBundle MESSAGEBUNDLE = MessageBundle.getMessageBundle("com.cordys.cm.uiunit.message.messages");

//script error in the browser
	public static final ILocalizableString EXCEPTION_OCCURED = MESSAGEBUNDLE.getMessage("exceptionOccured");
		
	// function not implemented
	public static final ILocalizableString NOT_IMPLEMENTED_EXCEPTION = MESSAGEBUNDLE.getMessage("notImplementedException");
	
	// illegal reference to element
	public static final ILocalizableString ILLEGAL_REFERENCE_EXCEPTION = MESSAGEBUNDLE.getMessage("illegalReferenceException");
	
	// exception during wait for idle
	public static final ILocalizableString WAIT_FOR_IDLE_EXCEPTION = MESSAGEBUNDLE.getMessage("waitForIdleException"); 
	
	//loading a url a browser failed
	public static final ILocalizableString URL_NOT_LOADED_EXCEPTION = MESSAGEBUNDLE.getMessage("urlNotLoadedException");

	//findElement inside a browser failed
	public static final ILocalizableString ELEMENT_NOT_FOUND_EXCEPTION = MESSAGEBUNDLE.getMessage("elementNotFoundException");
	
	//UFO findElement inside a browser failed
	public static final ILocalizableString UFO_ELEMENT_NOT_FOUND_EXCEPTION = MESSAGEBUNDLE.getMessage("ufoElementNotFoundException");

	//findElement is successful but element is not of expected type
	public static final ILocalizableString ELEMENT_TYPE_EXCEPTION = MESSAGEBUNDLE.getMessage("elementTypeMismatchException");

	//cannot create element object. May be not present in the hashmap elementCodes
	public static final ILocalizableString ELEMENT_INSTANTIATION_EXCEPTION = MESSAGEBUNDLE.getMessage("elementInstantiationException");

	//script error in the browser
	public static final ILocalizableString JAVASCRIPT_ERROR = MESSAGEBUNDLE.getMessage("javascriptError");

	//error during robot creation
	public static final ILocalizableString ROBOT_AWT_EXCEPTION = MESSAGEBUNDLE.getMessage("robotAWTException");

	//sleep is interrupted
	public static final ILocalizableString THREAD_INTERRUPT_EXCEPTION = MESSAGEBUNDLE.getMessage("threadInterruptException");

	//the response given for a expected dialog during register is not valid for this type of dialog
	public static final ILocalizableString INVALID_DIALOGRESPONSE = MESSAGEBUNDLE.getMessage("invalidDialogResponse");

	//an unregistered dialog occured
	public static final ILocalizableString UNEXPECTED_DIALOG = MESSAGEBUNDLE.getMessage("unexpectedDialog");

	//dialog registered for one type and another type appeared
	public static final ILocalizableString WRONG_DIALOG_TYPE = MESSAGEBUNDLE.getMessage("wrongDialogType");
	
	//dialog registered as one type, but is accessed as if it's another type
	public static final ILocalizableString WRONG_DIALOG_TYPE_UNREGISTER = MESSAGEBUNDLE.getMessage("wrongDialogTypeUnregister");

	//time out
	public static final ILocalizableString TIMEOUT_EXCEPTION = MESSAGEBUNDLE.getMessage("timeoutException");
	
//time out
	public static final ILocalizableString TIMEOUT_EXCEPTION_DURING_WAITFORIDLE = MESSAGEBUNDLE.getMessage("timeoutExceptionDuringWaitForIdle");

	//simulation file not found
	public static final ILocalizableString SIM_FILE_NOTFOUND_EXCEPTION = MESSAGEBUNDLE.getMessage("simFileNotFoundException");

	// method is deprecated and no longer works, so throw an exception instead.
	public static final ILocalizableString METHOD_DEPRECATED = MESSAGEBUNDLE.getMessage("methodDeprecated");
	
	
	//cordys exception codes*********
	//application not found in cordys browser
	public static final ILocalizableString APPLICATION_NOT_FOUND = MESSAGEBUNDLE.getMessage("applicationNotFound");

	//exception messages
	public static final ILocalizableString ELEMENT_INSTANTIATION_EXCEPTION_MESSAGE = MESSAGEBUNDLE.getMessage("elementInstallationException");

	public static final ILocalizableString ELEMENT_DISABLED = MESSAGEBUNDLE.getMessage("elementDisabled");

	public static final ILocalizableString ELEMENT_READONLY = MESSAGEBUNDLE.getMessage("elementReadOnly");

	public static final ILocalizableString ELEMENT_NOTVISIBLE = MESSAGEBUNDLE.getMessage("elementNotVisible");
	
	//find element exception
	public static final ILocalizableString FIND_ELEMENT_EXCEPTION = MESSAGEBUNDLE.getMessage("findElementException");
	
	//tree item not found
	public static final ILocalizableString TREEITEM_NOT_FOUND = MESSAGEBUNDLE.getMessage("treeItemNotFound");
	
	//tree item not checkable
	public static final ILocalizableString TREEITEM_NOT_CHECKABLE = MESSAGEBUNDLE.getMessage("treeItemNotCheckable");
	//tree item not checkable
	public static final ILocalizableString TREEITEM_NOT_UPDATEABLE = MESSAGEBUNDLE.getMessage("treeItemNotUpdateable");
	
	//	tree has no root element
	public static final ILocalizableString TREE_NO_ROOT_EXCEPTION = MESSAGEBUNDLE.getMessage("treeNoRoot");

	//tree node is not visible
	public static final ILocalizableString TREENODE_NOT_VISIBLE = MESSAGEBUNDLE.getMessage("treenodeNotVisible");

	//no item selected and getselected item called
	public static final ILocalizableString NO_ITEM_SELECTED = MESSAGEBUNDLE.getMessage("treeNoItemSelected");

	//more than one item seleted an d getselecteditem is called
	public static final ILocalizableString MORE_THAN_ONE_ITEM_SELECTED = MESSAGEBUNDLE.getMessage("treeMoreThenOneItemSelected");

	// generic invalid selection exception
	public static final ILocalizableString INVALID_SELECTION_EXCEPTION = MESSAGEBUNDLE.getMessage("invalidSelectionException");
	
	//text to select is not found in the selectBox
	public static final ILocalizableString INVALID_SELECT_TEXT = MESSAGEBUNDLE.getMessage("invalidSelectText");

	//INDEX to select is not found in the selectBox
	public static final ILocalizableString INVALID_SELECT_INDEX = MESSAGEBUNDLE.getMessage("invalidSelectIndex");

	//grid messages
	public static final ILocalizableString TABLE_ROW_NOT_FOUND = MESSAGEBUNDLE.getMessage("tableRowNotFound");
	public static final ILocalizableString TABLE_ROW_NOT_FOUND_BY_CELL_VALUE = MESSAGEBUNDLE.getMessage("tableRowNotFoundByCellvalue");

	public static final ILocalizableString CELL_NOT_FOUND = MESSAGEBUNDLE.getMessage("tableCellNotFound");

	public static final ILocalizableString HEADER_NOT_FOUND = MESSAGEBUNDLE.getMessage("tableHeaderNotFound");
	
	public static final ILocalizableString HEADER_COLUMN_NOT_FOUND = MESSAGEBUNDLE.getMessage("tableHeaderColumnNotFound");

	public static final ILocalizableString FOOTER_NOT_FOUND = MESSAGEBUNDLE.getMessage("tableFooterNotFound");

	//context menu messages
	public static final ILocalizableString CONTEXT_MENU_NOT_FOUND = MESSAGEBUNDLE.getMessage("contextMenuNotFound");

	public static final ILocalizableString MENU_ITEM_NOT_FOUND = MESSAGEBUNDLE.getMessage("menuItemNotFound");

	public static final ILocalizableString MENU_ITEM_DISABLED = MESSAGEBUNDLE.getMessage("menuItemDisabled");

	public static final ILocalizableString CALIBRATION_FAILED = MESSAGEBUNDLE.getMessage("calibrationFailed");

	public static final ILocalizableString SOAP_FAULT_OCCURED = MESSAGEBUNDLE.getMessage("soapFaultOccurred");

	public static final ILocalizableString DIALOG_TEST_FAILURE = MESSAGEBUNDLE.getMessage("dialogTestFailure");

	public static final ILocalizableString INVALID_METHOD_NAME = MESSAGEBUNDLE.getMessage("invalidMethodName");

	public static final ILocalizableString INVALID_PROPERTY_NAME = MESSAGEBUNDLE.getMessage("invalidPropertyName");

	public static final ILocalizableString INVALID_PROPERTY_TYPE = MESSAGEBUNDLE.getMessage("invalidPropertyType");
	
	public static final ILocalizableString INVALID_PROPERTY_VALUE = MESSAGEBUNDLE.getMessage("invalidPropertyValue");

	public static final ILocalizableString INVALID_DATE_FORMAT = MESSAGEBUNDLE.getMessage("invalidDateFormat");

	public static final ILocalizableString INVALID_DAY_FORMAT = MESSAGEBUNDLE.getMessage("invalidDayFormat");
	
	public static final ILocalizableString NO_CONTEXT_SET = MESSAGEBUNDLE.getMessage("noContextSet");
}
