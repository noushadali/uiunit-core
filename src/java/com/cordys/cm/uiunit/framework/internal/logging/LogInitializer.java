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
 
 package com.cordys.cm.uiunit.framework.internal.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.cordys.cm.uiunit.config.IConfigurationConstants;
import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;
import com.cordys.cm.uiunit.framework.SystemInformation;

public class LogInitializer
{
	private static final Object lockObject = new Object();

	private static boolean initialized = false;

	public static void initialize()
	{
		if (!initialized)
		{
			synchronized (lockObject)
			{
				if (initialized)
					return;

				Properties properties = new Properties();
				boolean configFound = false;

				String configFileName = System.getProperty(IConfigurationConstants.LOG4J_PROPERTIES_FILENAME);
				if (null == configFileName)
				{
					// next check for a file in the src/java folder
					URL url = ClassLoader.getSystemResource(IConfigurationConstants.LOG4J_PROPERTIES_FILENAME);
					if (url != null)
					{
						configFileName = url.getPath();
					}
					// else use the old c:\UIUnit\log4j.properties
					else
					{
						String osName = System.getProperty("os.name").toLowerCase(Locale.US);
						if(osName.contains("windows"))
						{
							configFileName = IConfigurationConstants.WINDOWS_UIUNITFOLDER + IConfigurationConstants.LOG4J_PROPERTIES_FILENAME;
						}
						else
						{
							configFileName = IConfigurationConstants.LINUX_UIUNITFOLDER + IConfigurationConstants.LOG4J_PROPERTIES_FILENAME;
						}
					}
				}

				File configFile = new File(configFileName);
				if (configFile.exists())
				{
					FileInputStream fileInputStream = null;
					try
					{
						fileInputStream = new FileInputStream(configFile);
						properties.load(fileInputStream);
						configFound = true;
					}
					catch (FileNotFoundException e)
					{
						throw new UIUnitRuntimeException(e);
					}
					catch (IOException e)
					{
						throw new UIUnitRuntimeException(e);
					}
					finally
					{
						if (null != fileInputStream)
						{
							try
							{
								fileInputStream.close();
							}
							catch (IOException e)
							{
								throw new UIUnitRuntimeException(e);
							}
							fileInputStream = null;
						}
					}
				}

				if (!configFound)
				{
					// load default config
					InputStream is = LogInitializer.class.getResourceAsStream("log4j_uiunit.properties");
					try
					{
						properties.load(is);
					}
					catch (IOException e)
					{
						throw new UIUnitRuntimeException(e);
					}
				}

				PropertyConfigurator.configure(properties);
				SystemInformation sysInfo = new SystemInformation();
				sysInfo.log();
				initialized = true;
			}
		}
	}
}
