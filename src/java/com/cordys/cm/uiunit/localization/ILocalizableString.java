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

import java.util.Locale;

/**
 * Localizable string Interface
 * 
 * @author ppostma
 */
public interface ILocalizableString
{
	/**
	 * Get the code of the message
	 * 
	 * @return code
	 */
	String getCode();

	/**
	 * Get the message in the default locale
	 * 
	 * @return message in default locale
	 */
	String getMessage();

	/**
	 * Get the message with parameters in the default locale
	 * 
	 * @param parameters
	 * @return message in default locale
	 */
	String getMessage( Object... parameters );

	/**
	 * Get the message without parameters in the specified locale
	 * 
	 * @param locale
	 * @return message in the specified locale
	 */
	String getMessage( Locale locale );

	/**
	 * Get the message with parameters in the specified locale
	 * 
	 * @param locale
	 * @param parameters
	 * @return message in the specified locale
	 */
	String getMessage( Locale locale, Object... parameters );
}
