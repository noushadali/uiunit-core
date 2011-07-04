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
 
 package com.cordys.cm.uiunit.config;

import com.cordys.cm.uiunit.config.browser.IBrowserType;
import com.cordys.cm.uiunit.config.identity.IIdentity;
import com.cordys.cm.uiunit.config.server.IServer;

public interface IConfiguration
{
	/**
	 * Get a property value. First checks for a VM argument
	 * {@code -Duiunit.<propertyName>} , if not found it checks for a value
	 * from the properties that are loaded. lastly it returns the default value
	 * if neither is found.
	 * 
	 * @param propertyName
	 * @param defaultValue
	 * @return property value
	 */
	public String getStringProperty( String propertyName, String defaultValue );

	/**
	 * Get a property value as an integer. First checks for a VM argument
	 * {@code -Duiunit.<propertyName>} , if not found it checks for a value
	 * from the properties that are loaded. If the property does not exist,
	 * can't be converted to int or is not between the minValue and maxValue, the
	 * defaultValue is returned.
	 * 
	 * @param name
	 * @param defaultValue
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public int getIntProperty( String name, int defaultValue, int minValue,
	        int maxValue );

	/**
	 * Get a property value as a boolean. First checks for a VM argument
	 * {@code -Duiunit.<propertyName>} , if not found it checks for a value
	 * from the properties that are loaded. If the property does not exist or
	 * can't be converted to boolean the defaultValue is returned.
	 * 
	 * @param name
	 * @param defaultValue
	 * @return true/false
	 */
	public boolean getBooleanProperty( String name, boolean defaultValue );

	/**
	 * Get an identity, specifying a username and password.
	 * 
	 * @return identity
	 */
	public IIdentity getIdentity();

	/**
	 * Get browser type as defined in IConfigurationConstants
	 * 
	 * @see {@link com.cordys.com.uiunit.config.IConfigurationConstants}
	 * @return browser type
	 */
	public IBrowserType getBrowserType();

	/**
	 * Get the start URL for the environment
	 * 
	 * @return start url
	 */
	public String getStartUrl();

	/**
	 * Get the name of the target machine. if the target is "localhost", the own
	 * machine name is returned ( taken from System.getEnv("COMPUTERNAME") )
	 * 
	 * @return Target Server
	 */
	public IServer getTargetMachine();

	/**
	 * Get the default configured timeout in seconds
	 * 
	 * @return timeout in seconds
	 */
	public int getTimeOut();
	
	/**
	 * Check if the framework should use anonymous access to the webserver
	 */
	public boolean getAnonymous();

	/**
	 * Get the default configured timeout in miliseconds
	 * 
	 * @return timeout in miliseconds
	 */
	public long getTimeOutInMiliseconds();

	/**
	 * Get the maximum request size the proxy server can handle. default 6.5MB
	 * 
	 * @return maximum request size
	 */
	public int getMaxRequestSizeInBytes();

	/**
	 * Unique identifier for the configuration
	 * 
	 * @return identifier
	 */
	public int getIdentifier();
	
	/**
	 * Check whether script error detector is enabled
	 * 
	 * @return true/false
	 */
	public String getDetectScriptError();
	
	/**
	 * Check whether front-end Pathfinder property is enabled
	 * 
	 * @return true/false
	 */
	public String getFEPathFinder();
	
	/**
	 * Check whether back-end Pathfinder property is enabled
	 * 
	 * @return true/false
	 */
	public String getBEPathFinder();

}
