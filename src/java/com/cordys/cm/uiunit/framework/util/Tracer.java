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
 
 package com.cordys.cm.uiunit.framework.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * @author jverhaar and cbos 
 * 
 */

// Is for internal exception and used in command-line tool
public abstract class Tracer
{
	private static final String INDENT = "  ";

	private static final int INDENT_LENGTH = INDENT.length();

	private static ILoggerLevelItem OUT_OF_CATEGORY = new OutOfCategoryLoggerLevelItem();

	private static long s_lastTime = System.currentTimeMillis();

	private static String s_levelIndent = "";

	private static long s_lastTraceTime = s_lastTime;

	private static int s_loadLevel = 0;

	private static LinkedList<ILoggerLevelItem> s_loggerItems = new LinkedList<ILoggerLevelItem>();

	private volatile static ArrayList<Object> s_errors;

	private static StringBuilder s_sb = new StringBuilder(256);

	private static long s_startAt = s_lastTime;

	private static Logger s_logger = Logger.getLogger("PERFORMANCE-TRACE");
	
	private static boolean traceIndentStyle = false;

	static
	{
		s_loggerItems.add(OUT_OF_CATEGORY);
		traceIndentStyle = Boolean.getBoolean("uiunit.trace.indentstyle");
	}

	/*
	 * ___________________________________________ Declaration and definition of
	 * constructors
	 */

	/**
	 * Default constructor.
	 */
	private Tracer()
	{
		super();
	}

	//was public
	private static void error(String message, Throwable exception)
	{
		// Increment the number of errors
		cacheError(exception);

		if (null == message)
		{
			message = exception.toString();
		}
		else
		{
			message += " : " + exception.toString();
		}

		s_logger.error(message);

		Throwable traceException = exception;
		String stackPrefix = "  ";
		while (null != traceException)
		{
			StackTraceElement[] oStack = traceException.getStackTrace();

			for (int i = 0; i < oStack.length; ++i)
			{
				//trace(stackPrefix + oStack[i].toString(), false);
				s_logger.error(stackPrefix + oStack[i].toString());
			}
			traceException = traceException.getCause();
			if (null != traceException)
			{
				stackPrefix += " ";
				//trace(stackPrefix + "Caused by " + traceException, false);
				s_logger.error(stackPrefix + "Caused by " + traceException);
			}
		}
	}

	//was public
	private static void error(Throwable exception)
	{
		error(null, exception);
	}

	//was public
	private static void error(String errorMessage)
	{
		// Increment the number of errors
		cacheError(errorMessage);

		trace("ERROR: " + errorMessage);

		StackTraceElement[] oStack = Thread.currentThread().getStackTrace();

		for (int i = 1; i < oStack.length; ++i)
		{
			trace("  " + oStack[i].toString(), false);
		}
	}

	//was public
	private static int getErrorCount()
	{
		if (null == s_errors)
		{
			return 0;
		}
		return s_errors.size();
	}

	public static void endMarker()
	{
		if (s_loggerItems.isEmpty())
		{
			trace("Invalid level! (" + s_loadLevel + ")");
			s_loadLevel = 0;
			s_levelIndent = "";
			s_sb.setLength(0);
		}
		else
		{
			ILoggerLevelItem oLoggerLevel = s_loggerItems.removeLast();

			if (oLoggerLevel != OUT_OF_CATEGORY)
			{
				--s_loadLevel;
				s_sb.setLength(s_loadLevel * INDENT_LENGTH);
				s_levelIndent = s_sb.toString();

				oLoggerLevel.end();

				s_lastTraceTime = System.currentTimeMillis();
			}
		}
	}

	// Exception used for internal check
	public static void startMarker(String message)
	{
		s_loggerItems.add(new LoggerLevelItem(newTime(), message));
		if(traceIndentStyle)
		{
			trace("{ " + message);
		}
		++s_loadLevel;
		s_sb.append(INDENT);
		s_levelIndent = s_sb.toString();

		if (s_loadLevel > 32)
		{
			Tracer.error(new RuntimeException("Possible overflow in Logger. system is stopping"));
			//System.exit(-32);
		}
		s_lastTraceTime = System.currentTimeMillis();
	}

	//was public
	private static void total()
	{
		s_levelIndent = "";
		if (0 == getErrorCount())
		{
			trace("Total: " + (newTime() - s_startAt) + " ms");
		}
		else
		{
			trace("Total: "
						+ (newTime() - s_startAt)
						+ " ms with "
						+ getErrorCount()
						+ " error(s)");

			Iterator<Object> it = s_errors.iterator();

			while (it.hasNext())
			{
				trace(it.next().toString(), false);
			}
		}
	}

	//was public
	private static void trace(String message)
	{
		trace(message, true);
	}

	// Used for console testing
	private static void trace(String message, boolean updateTime)
	{
		if (updateTime)
		{
			long lastTime = s_lastTraceTime;
			s_lastTraceTime = System.currentTimeMillis();
			s_logger.trace(s_levelIndent
												 + message
												 + " ..."
												 + (s_lastTraceTime - lastTime)
												 + " ms");
		}
		else
		{
			s_logger.trace(s_levelIndent + message);
		}
	}

	/*
	 * _______________________________________ The methods below belong to this
	 * class
	 */
	private static void cacheError(Object errorObject)
	{
		if (null == s_errors) // NOPMD Is thread safe
		{
			synchronized (INDENT)
			{
				if (null == s_errors)
				{
					s_errors = new ArrayList<Object>();
				}
			}
		}

		s_errors.add(errorObject);
	}

	/**
	 * 
	 */
	protected static long newTime()
	{
		long currentTime = System.currentTimeMillis();
		s_lastTime = currentTime;

		return currentTime;
	}

	/**
	 * Prints the elapsed time
	 * 
	 * @param aStream
	 *          The PrintStream to write on. If null nothing is written
	 * @param aMessage
	 *          The message to print below the elapsed time protected static void
	 *          printElapsedTime(PrintStream aStream, String aMessage) { long
	 *          newTime = System.currentTimeMillis() ; long duration = newTime -
	 *          lastTime ; lastTime = newTime ; if ((aStream != null) &&
	 *          (s_verbose)) { aStream.println(" -- Duration: " + duration + " ms
	 *          --\n" + aMessage) ; } }
	 */
	interface ILoggerLevelItem
	{
		public void end();
	}

	/**
	 * Class will maintain the time and value for the level
	 */
	static class LoggerLevelItem implements ILoggerLevelItem
	{
		private String m_message;

		// The starttime
		private long m_startAt;

		/**
		 * Default constructor
		 * 
		 * @param aInt
		 *          The start-value of the internal integer
		 */
		public LoggerLevelItem(long startAt, String message)
		{
			m_startAt = startAt;
			m_message = message;
		}

		public void end()
		{
			long lDuration = Tracer.newTime() - m_startAt;
			if(traceIndentStyle)
			{
				Tracer.trace("} " + m_message + "(" + (lDuration) + " ms)");
			}
			else
			{
				s_logger.trace(String.format("PEFORMANCE-TRACE, %s, %s, %s", new Date().toString(), m_message, lDuration));
			}
		}			
	}

	static class OutOfCategoryLoggerLevelItem implements ILoggerLevelItem
	{
		public OutOfCategoryLoggerLevelItem()
		{
		}

		public void end()
		{
		}
	}

}
