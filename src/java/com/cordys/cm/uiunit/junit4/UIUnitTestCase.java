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
 
 package com.cordys.cm.uiunit.junit4;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cordys.cm.uiunit.framework.IContextConsumer;
import com.cordys.cm.uiunit.framework.IHTMLContext;
import com.cordys.cm.uiunit.framework.ITestEnvironment;
import com.cordys.cm.uiunit.framework.ITestEnvironmentConsumer;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.runners.UIUnitRunner;

@RunWith(UIUnitRunner.class)
@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class UIUnitTestCase implements IContextConsumer, ITestEnvironmentConsumer {

	private IHTMLContext context;
	private static ITestEnvironment testEnvironment;
	
	@Override
	public void _setContext(IHTMLContext newContext) {
		this.context = newContext;
	}

	@Override
	public IHTMLContext getContext() {
		return this.context;
	}

	@Override
	public void _setTestEnvironment(ITestEnvironment newTestEnvironment)
	{
		UIUnitTestCase.testEnvironment = newTestEnvironment;
	}

	@Override
	public ITestEnvironment getTestEnvironment()
	{
		return testEnvironment;
	}	
	
	@AfterClass
	public static void runTestClassFinalizers(){
		//UIUnitTestCase.testEnvironment.getBrowser().refresh();
	}
	
	
	public void logDebug(String message, Object... insertions )
	{
		this.getContext().getLogger().debug(this, message, insertions);
	}
	
	public void logInfo(String message, Object... insertions )
	{
		this.getContext().getLogger().info(this, message, insertions);
	}
	
	public void logWarning(String message, Object... insertions )
	{
		this.getContext().getLogger().warning(this, message, insertions);
	}
	
	public void logError(String message, Object... insertions )
	{
		this.getContext().getLogger().error(this, message, insertions);
	}
	
	public void logFatal(String message, Object... insertions )
	{
		this.getContext().getLogger().fatal(this, message, insertions);
	}
}
