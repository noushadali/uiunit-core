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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import sun.misc.Service;

import com.cordys.cm.uiunit.elements.finder.internal.DefaultElement;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.exceptions.UIUnitException;
import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;
import com.cordys.cm.uiunit.framework.IHTMLContext;
import com.cordys.cm.uiunit.framework.ILogger;
import com.cordys.cm.uiunit.message.Messages;

/**
 * Factory class for finding elements
 * 
 * @author ppostma
 */
public class ElementFinder
{
	private static final ElementFinder                      dummyElementFinder = new ElementFinder();

	private static Class<? extends IUnQualifiedHTMLElement> elementImplementor;

	/**
	 * Find a typed HTML element inside the context based on the HTML Pointer
	 * 
	 * @param type
	 * @param context
	 * @param htmlPointer
	 * @return the element
	 */
	public static <T extends IHTMLElement> T findElementByHTMLPointer( Class<T> type, IHTMLContext context,
	        String htmlPointer )
	{
		IHTMLElement element = findElementAndIdentify(type, context, createUnqualifiedElement(context, htmlPointer), true);

		if( element == null )
		{
			return null;
		}
		else if( type.isInstance(element) )
		{
			return type.cast(element);
		}
		else
		{
			throw new UIUnitException(Messages.ELEMENT_TYPE_EXCEPTION, htmlPointer, type.getName(),
			        element.getClass().getName());
		}
	}

	/**
	 * Find an untyped HTML element inside the context based on the HTML Pointer
	 * 
	 * @param context
	 * @param htmlPointer
	 * @return the element
	 */
	public static IHTMLElement findElementByHTMLPointer( IHTMLContext context, String htmlPointer )
	{
		return findElementAndIdentify(IHTMLElement.class, context, createUnqualifiedElement(context, htmlPointer), false);
	}

	private static IUnQualifiedHTMLElement createUnqualifiedElement(IHTMLContext context, String htmlPointer)
	{
		IUnQualifiedHTMLElement element = getElementImplInstance();
		element._setHTMLPointer(htmlPointer);
		element._setContext(context);
		return element;
	}

	private static IUnQualifiedHTMLElement createUnqualifiedElementByID(IHTMLContext context, String id)
	{
		IUnQualifiedHTMLElement element = getElementImplInstance();
		element._setId(id);
		element._setContext(context);
		return element;
	}

	/**
	 * Internal method used to create an implementation object of the specific
	 * type of the element, according to the describer.
	 * 
	 * @param context
	 * @param element
	 * @return
	 */
	private static IHTMLElement findElementAndIdentify(Class<? extends IHTMLElement> type,
																										 IHTMLContext context,
																										 IUnQualifiedHTMLElement element,
																										 boolean withFallBack)
	{

		if( !element.exists() )
		{
			return null;
		}

		List<IHTMLElementDescriber> possibleDescribers = new ArrayList<IHTMLElementDescriber>();

		Iterator<IHTMLElementDescriber> describersList = getDescribersByClass(type).iterator();
		while( describersList.hasNext() )
		{
			IHTMLElementDescriber describer = describersList.next();
			if( describer.canDescribe(element) )
			{
				possibleDescribers.add(describer);
			}
		}

		if( possibleDescribers.size() > 0 )
		{
			// always assign the first one as realDescriber to start with
			// (possibleDescribers.size() == 1)
			IHTMLElementDescriber realDescriber = possibleDescribers.get(0);

			// if there are more then 1 describers, go look for most specific
			// one instead
			if( possibleDescribers.size() > 1 )
			{
				List<String> baseClassList = new ArrayList<String>();
				for( IHTMLElementDescriber describer : possibleDescribers )
				{
					if( describer.getBaseType() == null )
					{
						// add a string instead of a real null to not get a
						// nullpointer later on
						baseClassList.add("null");
					}
					else
					{
						baseClassList.add(describer.getBaseType().getName());
					}
				}
				for( IHTMLElementDescriber describer : possibleDescribers )
				{
					// if the describer isn't in the list...
					if( !baseClassList.contains(describer.getClass().getName()) )
					{
						// ... it's the most specific one, stop searching
						realDescriber = describer;
						break;
					}
				}
			}

			ILogger logger = context.getLogger();
			// print a list of found describers for this element to system.out
			// (the one marked with a * is the `realdescriber`)
			if( logger.isDebugEnabled(dummyElementFinder) && getConfigDebugSetting( context ) )
			{
				String debugString = "";
				debugString += "ElementFinder.findElement(" + element.getHTMLPointer() + ")" + " FOUND:"
				        + possibleDescribers.size() + " ( ";
				for( IHTMLElementDescriber d : possibleDescribers )
					debugString += d.getClass().getSimpleName()
					        + (d.getClass().getName().equals(realDescriber.getClass().getName()) ? "*" : "") + " ";
				debugString += ")";
				logger.debug(dummyElementFinder, debugString);
				// end of debug
			}

			IHTMLElement htmlElement = realDescriber.getNewHTMLElement();
			context.addElement(htmlElement);
			htmlElement._setHTMLPointer(element.getHTMLPointer());
			return htmlElement;
		}
		if(withFallBack && type != IHTMLElement.class)
		{
			context.getLogger().debug(dummyElementFinder, "Fallback for expected type '"
																			 + type.getCanonicalName()
																			 + "' of element '"
																			 + element.toString()
																			 + "'");
			return findElementAndIdentify(IHTMLElement.class, context, element, false);
		}
		// if no describer can match (not even AnyElementDescriber), return
		// null
		return null;
	}
	
	private static boolean getConfigDebugSetting (IHTMLContext context)
	{
		return context.getRootContext().getTestEnvironment().getConfiguration().getBooleanProperty("elementfinder.debug", true);
	}

	private static List<IHTMLElementDescriber> allDescribers = null;
	
	private static Map<IHTMLElementDescriber, Class<? extends IHTMLElement>> allDescribersByClass = null;

	private static List<IHTMLElementDescriber> getAllDescribers()
	{
		if( null == allDescribers )
		{
			allDescribers = new ArrayList<IHTMLElementDescriber>();
			Iterator<?> describersList = Service.providers(IHTMLElementDescriber.class);
			while( describersList.hasNext() )
			{
				IHTMLElementDescriber describer = (IHTMLElementDescriber) describersList.next();
				allDescribers.add(describer);
			}
		}
		return allDescribers;
	}
	
	private static Map<IHTMLElementDescriber, Class<? extends IHTMLElement>> getAllDescribersByClass()
	{
		if( null == allDescribersByClass )
		{
			List<IHTMLElementDescriber> describers = getAllDescribers();
			allDescribersByClass = new HashMap<IHTMLElementDescriber, Class<? extends IHTMLElement>>(describers.size());

			//multiple describers can result in the same class instances
			for (IHTMLElementDescriber describer : describers)
			{
				IHTMLElement newHTMLElement = describer.getNewHTMLElement();
				allDescribersByClass.put( describer, newHTMLElement.getClass());
			}
		}
		return allDescribersByClass;
	}
	
	private static List<IHTMLElementDescriber> getDescribersByClass(Class<? extends IHTMLElement> type)
	{
		final List<IHTMLElementDescriber> list = new ArrayList<IHTMLElementDescriber>();
		final Map<IHTMLElementDescriber, Class<? extends IHTMLElement>> describersByClass = getAllDescribersByClass();
		
		for (Entry<IHTMLElementDescriber, Class<? extends IHTMLElement>> entry : describersByClass.entrySet())
		{
			final Class<? extends IHTMLElement> possibleClass = entry.getValue();
			if(type.isAssignableFrom(possibleClass))
			{
				list.add(entry.getKey());
			}
		}
		return list;
	}

	public static void _logSystemInformation( Logger logger )
	{
		if( logger.isInfoEnabled() )
		{
			List<IHTMLElementDescriber> describers = getAllDescribers();
			logger.info("Number of describers registered: " + describers.size());

			if( logger.isDebugEnabled() )
			{
				logger.debug("All describers registered:");
				for( IHTMLElementDescriber describer : describers )
				{
					logger.debug(describer.getClass().getCanonicalName());
				}
			}
		}
	}

	/**
	 * Find an element by ID in the given context
	 * 
	 * @param context
	 * @param id
	 * @return generic HTMLElement object
	 */
	public static IHTMLElement findElementById( IHTMLContext context, String id )
	{
		return findElementAndIdentify(IHTMLElement.class, context, createUnqualifiedElementByID(context, id), false);
	}

	/**
	 * Find an element by ID in the given context of a specific type.
	 * 
	 * @param type
	 * @param context
	 * @param id
	 * @return specific HTMLElement object
	 */
	public static <T extends IHTMLElement> T findElementById(Class<T> type, IHTMLContext context, String elementID)
	{
		IHTMLElement element = findElementAndIdentify(type, context, createUnqualifiedElementByID(context, elementID), true);

		if( element == null )
		{
			return null;
		}
		else if( type.isInstance(element) )
		{
			return type.cast(element);
		}
		else
		{
			throw new UIUnitException(Messages.ELEMENT_TYPE_EXCEPTION, elementID, type.getName(),
			        element.getClass().getName());
		}
	}

	/**
	 * Method to set your own implementation of an Unqualified Element for
	 * testing purposes
	 * 
	 * @param elementClass
	 */
	public static void setElementImpl( Class<? extends IUnQualifiedHTMLElement> elementClass )
	{
		elementImplementor = elementClass;
	}

	/**
	 * Get the Unqualified Element Implementation, DefaultElement by default but
	 * can be set through setElementImpl() for testing purposes
	 * 
	 * @see com.cordys.cm.uiunit.elements.finder.ElementFinder#setElementImpl(Class)
	 * @return
	 */
	public static Class<? extends IUnQualifiedHTMLElement> getElementImpl()
	{
		if( elementImplementor == null )
		{
			elementImplementor = DefaultElement.class;
		}
		return elementImplementor;
	}
	
	public static IUnQualifiedHTMLElement getElementImplInstance()
	{
		IUnQualifiedHTMLElement element = null;
		try
		{
			element = getElementImpl().newInstance();
		}
		catch( IllegalArgumentException e )
		{
			throw new UIUnitRuntimeException(e);
		}
		catch( InstantiationException e )
		{
			throw new UIUnitRuntimeException(e);
		}
		catch( IllegalAccessException e )
		{
			throw new UIUnitRuntimeException(e);
		}
		return element;
	}

	/**
	 * Find a list of elements based on tagname of a specific type
	 * 
	 * @param type
	 * @param context
	 * @param tagName
	 * @return list of elements with a specific tagname, or an empty list if
	 *         none found.
	 */
	public static <T extends IHTMLElement> List<T> findElementsByTagName( Class<T> type, IHTMLContext context,
	        String tagName )
	{
		
		String jsScript = "";
		jsScript += "var x = " + context.getWindow() + ".document.getElementsByTagName(\"" + tagName + "\");";
		jsScript += "var list = \"\";";
		jsScript += "for (i = 0; i < x.length; i++)";
		jsScript += "{";
		jsScript += "  if (x[i].id == \"\" || typeof(x[i].id) == \"undefined\")";
		jsScript += "    x[i].id = x[i].uniqueID || CordysRoot.cordys.getUniqueID(x[i]);";
		jsScript += "  list += x[i].id + \"@#@\";";
		jsScript += "}";
		jsScript += "list;";

		String[] result = context.executeJavascript(jsScript).split("@#@");

		List<T> finalList = new ArrayList<T>();
		for( int i = 0; i < result.length; i++ )
		{
			IHTMLElement ele = findElementAndIdentify(type, context, createUnqualifiedElementByID(context, result[i]), false);
			if( ele != null && type.isInstance(ele))
			{
					finalList.add(type.cast(ele));
			}
		}
		return finalList;
	}

	/**
	 * Find a list of elements based on name ( &lt;t name="X"&gt; ) of a
	 * specific type
	 * 
	 * @param type
	 * @param context
	 * @param name
	 * @return list of elements with a specific name, or an empty list if none
	 *         found.
	 */
	public static <T extends IHTMLElement> List<T> findElementsByName( Class<T> type, IHTMLContext context, String name )
	{
		/*
		 * IE is very selective to which elements are allowed to have a 'name'
		 * attribute: A, APPLET, attribute, BUTTON, EMBED, FORM, IMG, INPUT
		 * type=button, INPUT type=checkbox, INPUT type=file, INPUT type=hidden,
		 * INPUT type=image, INPUT type=password, INPUT type=radio, INPUT
		 * type=reset, INPUT type=submit, INPUT type=text, LINK, MAP, OBJECT,
		 * RT, RUBY, SELECT, TEXTAREA FRAME, FRAMESET, IFRAME uniqueID is IE
		 * only
		 */

		String jsScript = "";
		jsScript += "var x = document.getElementsByName(\"" + name + "\");";
		jsScript += "var list = \"\";";
		jsScript += "for (i = 0; i < x.length; i++)";
		jsScript += "{";
		jsScript += "  if (x[i].id == null || typeof(x[i].id) == \"undefined\")";
		jsScript += "    x[i].id = x[i].uniqueID || CordysRoot.cordys.getUniqueID(x[i]);";
		jsScript += "  list += x[i].id + \"@#@\";";
		jsScript += "}";
		jsScript += "list;";

		String[] result = context.executeJavascript(jsScript).split("@#@");

		List<T> finalList = new ArrayList<T>();

		for( int i = 0; i < result.length; i++ )
		{
			IHTMLElement ele = findElementAndIdentify(type, context, createUnqualifiedElementByID(context, result[i]), false);
			if( ele != null && type.isInstance(ele))
			{
					finalList.add(type.cast(ele));
			}
		}
		return finalList;
	}
}
