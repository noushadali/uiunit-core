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

public interface IConfigurationConstants
{
	public static final String WINDOWS_UIUNITFOLDER = "C:\\uiunit\\";
	
	public static final String LINUX_UIUNITFOLDER = "/usr1/uiunit";
	
	public static final String UIUNIT_PROPERTIES_FILENAME = "UIUnit.properties";
	/**
	 * Location of the UIUnit Properties file
	 */
	//public static final String UIUNIT_PROPERTIES_FILE = UIUNITFOLDER + UIUNIT_PROPERTIES_FILENAME;
	
	public static final String LOG4J_PROPERTIES_FILENAME = "log4j.properties";
	
	/**
	 * Definition for IE Browser Type
	 */
	public static final String IE_BROWSER = "IE";
	
	/**
	 * Definition for Mozilla Browser Type
	 */
	public static final String MOZILLA_BROWSER = "GECKO";
	
	/**
	 * Definition for Opera Browser Type. This type is not yet supported.
	 */
	@Deprecated
	public static final String OPERA_BROWSER = "OPERA";
	
	/**
	 * Definition for Safari Browser Type. This type is not yet supported.
	 */
	@Deprecated
	public static final String SAFARI_BROWSER = "SAFARI";
	
	/**
	 * Default Test Case Timeout (in seconds)
	 */
	public static final int DEFAULT_TIMEOUT = 60;

	/**
	 * Maximum Test Case Timeout (1 day)
	 */
	public static final int MAX_TIMEOUT = 60*60*24;

}