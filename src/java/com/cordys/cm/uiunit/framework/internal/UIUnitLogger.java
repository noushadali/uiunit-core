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
 
 package com.cordys.cm.uiunit.framework.internal;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.cordys.cm.uiunit.framework.ILogger;
import com.cordys.cm.uiunit.localization.ILocalizableString;

public class UIUnitLogger implements ILogger
{

	private void log(Level priority, Object instance, String message)
	{
		Logger logger = getLogger(instance);
		logger.log(priority, message);
	}

	private Logger getLogger(Object instance)
	{
		Logger logger = getLogger(instance.getClass());
		return logger;
	}

	private Logger getLogger(Class<?> clazz)
	{
		Logger logger = Logger.getLogger(clazz);
		return logger;
	}

	private void log(Level priority, Object instance, Throwable t, String message)
	{
		Logger logger = getLogger(instance);
		logger.log(priority, message, t);
	}

	public void debug(Object instance, String message, Object... insertions)
	{
		log(Level.DEBUG, instance, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void debug(Object instance, ILocalizableString message, Object... insertions)
	{
		log(Level.DEBUG, instance, message.getMessage(insertions));
	}

	public void info(Object instance, String message, Object... insertions)
	{
		log(Level.INFO, instance, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void info(Object instance, ILocalizableString message, Object... insertions)
	{

		log(Level.INFO, instance, message.getMessage(insertions));
	}

	public void warning(Object instance, String message, Object... insertions)
	{
		log(Level.WARN, instance, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void warning(Object instance, ILocalizableString message, Object... insertions)
	{
		log(Level.WARN, instance, message.getMessage(insertions));
	}

	public void error(Object instance, String message, Object... insertions)
	{
		log(Level.ERROR, instance, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void error(Object instance, ILocalizableString message, Object... insertions)
	{
		log(Level.ERROR, instance, message.getMessage(insertions));
	}

	public void error(Object instance, Throwable t, String message, Object... insertions)
	{
		log(Level.ERROR, instance, t, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void error(Object instance, Throwable t, ILocalizableString message, Object... insertions)
	{

		log(Level.ERROR, instance, t, message.getMessage(insertions));
	}

	public void fatal(Object instance, String message, Object... insertions)
	{
		log(Level.FATAL, instance, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void fatal(Object instance, ILocalizableString message, Object... insertions)
	{
		log(Level.FATAL, instance, message.getMessage(insertions));
	}

	public void fatal(Object instance, Throwable t, String message, Object... insertions)
	{

		log(Level.FATAL, instance, t, (insertions == null || insertions.length == 0) ? message : String.format(message, insertions));
	}

	public void fatal(Object instance, Throwable t, ILocalizableString message, Object... insertions)
	{

		log(Level.FATAL, instance, t, message.getMessage(insertions));
	}

	public void exception(Object instance, Throwable t)
	{
		log(Level.ERROR, instance, t, t.getMessage());
	}

	public boolean isDebugEnabled(Object instance)
	{
		return this.getLogger(instance).isDebugEnabled();
	}

	public boolean isInfoEnabled(Object instance)
	{
		return this.getLogger(instance).isInfoEnabled();
	}

	@Override
	public boolean isDebugEnabled(Class<?> clazz)
	{
		return this.getLogger(clazz).isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled(Class<?> clazz)
	{
		return this.getLogger(clazz).isInfoEnabled();
	}
}
