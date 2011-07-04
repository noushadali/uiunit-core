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

/**
 * Message Bundle factory. Set of localizable messages.
 * 
 * @author ppostma
 */
public class MessageBundle
{

	private String bundleName;

	/**
	 * Private constructor
	 * 
	 * @param bundleName
	 */
	private MessageBundle( String bundleName )
	{
		this.bundleName = bundleName;
	}

	/**
	 * Get the bundleName
	 * 
	 * @return bundleName
	 */
	public String getBundleName()
	{
		return this.bundleName;
	}

	/**
	 * Create a Message Object for the specified key.
	 * 
	 * @param key
	 * @return
	 */
	public ILocalizableString getMessage( String key )
	{
		return new Message(this, key);
	}

	/**
	 * Instantiate a message bundle with the given bundle name
	 * 
	 * @param bundleName
	 * @return MessageBundle
	 */
	public static MessageBundle getMessageBundle( String bundleName )
	{
		return new MessageBundle(bundleName);
	}
}
