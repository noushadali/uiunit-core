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
 
 package com.cordys.cm.uiunit.exceptions;

import com.cordys.cm.uiunit.localization.ILocalizableString;

/**
 * Standard exception used within the framework. Messages can be localized.
 * 
 * @author ppostma
 */
public class UIUnitException extends RuntimeException
{

	private static final long serialVersionUID = -2636940903552255771L;

	/**
	 * Constructor of A UIUnit Exception
	 * 
	 * @see com.cordys.cm.uiunit.exceptions.UIUnitException#UIUnitException(ILocalizableString, Object...)
	 * @param aCode
	 * @param aMessage
	 * @deprecated
	 */
	@Deprecated
	public UIUnitException( String aCode, String aMessage )
	{
		super("\n UIUnit Exception \n\n Code:\t" + aCode + "\n Message  :\t"
		        + aMessage);
	}

	/**
	 * Constructor of a UIUnit Exception
	 * 
	 * @see com.cordys.cm.uiunit.exceptions.UIUnitException#UIUnitException(Exception, ILocalizableString, Object...)
	 * @param aCode
	 * @param aMessage
	 * @param aOriginalException
	 * @deprecated
	 */
	@Deprecated
	public UIUnitException( String aCode, String aMessage,
	        Exception aOriginalException )
	{
		super("\n UIUnit Exception \n\n Code:\t" + aCode + "\n Message  :\t"
		        + aMessage, aOriginalException);
	}

	/**
	 * Constructor of a UIUnit Exception using a localizable message
	 * 
	 * @param message
	 *            ILocalizableString error code
	 */
	public UIUnitException( ILocalizableString message )
	{
		super(formatMessage(null, message));
	}

	/**
	 * Constructor of a UIUnit Exception using a localizable message with an
	 * undefined numbers of parameters
	 * 
	 * @param message
	 *            ILocalizableString error code
	 * @param parameters
	 *            Object additional parameters
	 */
	public UIUnitException( ILocalizableString message, Object... parameters )
	{
		super(formatMessage(null, message, parameters));
	}

	/**
	 * Constructor of a UIUnit Exception using a localizable message, with
	 * encapsulated Exception and an undefined number of parameters
	 * 
	 * @param exception
	 *            Exception original exception
	 * @param message
	 *            ILocalizableString error code
	 * @param parameters
	 *            Object additional parameters
	 */
	public UIUnitException( Exception exception, ILocalizableString message,
	        Object... parameters )
	{
		super(formatMessage(exception, message, parameters));
	}

	/**
	 * static method to format the exception message
	 * 
	 * @param exception
	 *            Exception possible exception to encapsulate within this
	 *            exception
	 * @param message
	 *            ILocalizableString localizable exception message
	 * @param parameters
	 *            Object additional parameters
	 * @return String
	 */
	private static String formatMessage( Exception exception,
	        ILocalizableString message, Object... parameters )
	{
		String msg = "\n UIUnit Exception Code:\t" + message.getCode()
		        + "\n Message  :\t" + message.getMessage(parameters);

		if( exception != null )
		{
			msg += "\n Original Exception  :\t" + exception;
		}

		return msg;
	}

	/**
	 * Get the code of the exception. This method is deprecated. Always returns
	 * "default code" string.
	 * 
	 * @deprecated
	 */
	@Deprecated
	public String getCode()
	{
		return "default code";
	}

}
