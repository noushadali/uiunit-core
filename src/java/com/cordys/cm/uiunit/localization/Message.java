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
 
 package com.cordys.cm.uiunit.localization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

/**
 * Implementation of a localizable string
 * 
 * @author ppostma
 *
 */
public class Message implements ILocalizableString
{
	private MessageBundle messageBundle;

	private String key;
	

	@Override
	public String getCode()
	{
		return key;
	}

	public Message(MessageBundle messageBundle, String key)
	{
		this.messageBundle = messageBundle;
		this.key = key;
	}

	@Override
	public String getMessage()
	{
		return this.getMessage(Locale.getDefault(), "");
	}

	@Override
	public String getMessage(Object... parameters)
	{
		return this.getMessage(Locale.getDefault(), parameters);
	}

	@Override
	public String getMessage(Locale locale)
	{
		return this.getMessage(locale, "");
	}

	@Override
	public String getMessage(Locale locale, Object... parameters)
	{
		ResourceBundle resourcebundle = ResourceBundle.getBundle(this.messageBundle.getBundleName(),
																														 locale,
																														 ResourceBundle.Control.getControl(FORMAT_DEFAULT));
		String message = resourcebundle.getString(this.key);
		if (message != null)
		{
			if (parameters != null && parameters.length > 0)
			{
				return MessageFormat.format(message, parameters);
			}
			return message;
		}
		return "";
	}

}
