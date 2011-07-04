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

import com.cordys.cm.uiunit.localization.ILocalizableString;

public interface ILogger
{

	public void debug(Object instance, String message, Object... insertions);
	
	public void debug(Object instance, ILocalizableString message, Object... insertions);

	public void info(Object instance, String message, Object... insertions);

	public void info(Object instance, ILocalizableString message, Object... insertions);

	public void warning(Object instance, String message, Object... insertions);

	public void warning(Object instance, ILocalizableString message, Object... insertions);

	public void error(Object instance, String message, Object... insertions);

	public void error(Object instance, ILocalizableString message, Object... insertions);

	public void error(Object instance, Throwable t, String message, Object... insertions);

	public void error(Object instance, Throwable t, ILocalizableString message, Object... insertions);

	public void fatal(Object instance, String message, Object... insertions);

	public void fatal(Object instance, ILocalizableString message, Object... insertions);

	public void fatal(Object instance, Throwable t, String message, Object... insertions);

	public void fatal(Object instance, Throwable t, ILocalizableString message, Object... insertions);

	public void exception(Object instance, Throwable t);

	public boolean isDebugEnabled(Object instance);
	
	public boolean isDebugEnabled(Class<?> clazz);

	public boolean isInfoEnabled(Object instance);
	
	public boolean isInfoEnabled(Class<?> clazz);

}
