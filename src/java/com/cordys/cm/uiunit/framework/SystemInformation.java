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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.cordys.cm.uiunit.elements.finder.ElementFinder;
import com.eibus.xml.nom.Document;

public class SystemInformation
{
	public void log()
	{
		Logger logger = Logger.getLogger(SystemInformation.class.getName());
		testNom(logger);
		ElementFinder._logSystemInformation(logger);
	}
	
	private void testNom(Logger logger)
	{
		try
		{
			new Document();
		}
		catch(UnsatisfiedLinkError linkError)
		{
			logger.warn(linkError.getMessage() + " || Some features may not work now.");
		}
	}
	
	public static void main(String[] args)
	{
		
		BasicConfigurator.configure();
		
		SystemInformation sysinfo = new SystemInformation();
		sysinfo.log();
		
		System.out.println("SystemInformation.main()");
	}
}
