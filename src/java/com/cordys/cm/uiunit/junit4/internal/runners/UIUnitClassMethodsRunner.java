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
 
 package com.cordys.cm.uiunit.junit4.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.config.IConfiguration;
import com.cordys.cm.uiunit.framework.IConfigurationProvider;
import com.cordys.cm.uiunit.framework.IContextConsumer;
import com.cordys.cm.uiunit.framework.ITestEnvironmentConsumer;
import com.cordys.cm.uiunit.framework.internal.EnvironmentFactory;
import com.cordys.cm.uiunit.framework.selenium.ISeleniumEnvironment;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;


/**
 * Internal runner for calling test methods.
 */
public class UIUnitClassMethodsRunner extends JUnit4ClassRunner
{
	private static ISeleniumEnvironment environment;		
	
	public UIUnitClassMethodsRunner( Class<?> clazz ) throws InitializationError
	{
		super(clazz);		
		environment = (ISeleniumEnvironment)EnvironmentFactory.getEnvironment();
		environment.registerTestDescription(this.getDescription());			
	}

	@Override
	protected Object createTest() throws Exception
	{
		Object testobject = super.createTest();

		IConfiguration config = null;
		if( testobject instanceof IConfigurationProvider )
		{
			IConfigurationProvider provider = (IConfigurationProvider) testobject;
			config = provider.getConfiguration();
		}
		else
		{
			config = ConfigurationManager.createConfig();
		}
		environment.setConfiguration(config);
		
		String startUrl = "";
		UIUnit uiunit = testobject.getClass().getAnnotation(UIUnit.class);
		if( uiunit != null )
		{
			startUrl = uiunit.startUrl();
		}
		if( startUrl != "" )
		{
			if( ! startUrl.equals( environment.getBrowser().getStartUrl() ) )
			{
				environment.getBrowser().loadURL(startUrl);
			}
		}	
		if( testobject instanceof ITestEnvironmentConsumer )
		{
			ITestEnvironmentConsumer envConsumer = (ITestEnvironmentConsumer) testobject;
			envConsumer._setTestEnvironment(environment);
		}

		if( testobject instanceof IContextConsumer )
		{
			IContextConsumer consumer = (IContextConsumer) testobject;
			consumer._setContext(environment.getRootContext());
		}
		return testobject;
	}

	@Override
	public void run( RunNotifier notifier )
	{
		environment.registerToNotifier(notifier);
		super.run(notifier);
		environment.checkStopFramework();
	}

	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {
		Description description= methodDescription(method);
		Object test;
		//notifier.addListener(flistener);
		try {
			test= createTest();
		} catch (InvocationTargetException e) {
			notifier.testAborted(description, e.getCause());
			return;			
		} catch (Exception e) {
			notifier.testAborted(description, e);
			return;
		}
		TestMethod testMethod= wrapMethod(method);
		new UIUnitTestMethodRunner(test, testMethod, notifier, description,method).run();
	}
}
