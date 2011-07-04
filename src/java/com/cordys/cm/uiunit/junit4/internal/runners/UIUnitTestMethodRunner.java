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

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.cordys.cm.uiunit.exceptions.UIUnitException;
import com.cordys.cm.uiunit.exceptions.UIUnitTimeoutException;
import com.cordys.cm.uiunit.framework.ITestEnvironment;
import com.cordys.cm.uiunit.framework.ITestEnvironmentEdit;
import com.cordys.cm.uiunit.framework.internal.EnvironmentFactory;
import com.cordys.cm.uiunit.junit4.IRunningTest;
import com.cordys.cm.uiunit.junit4.annotation.Prerequisite;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitReloadBrowser;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cm.uiunit.message.Messages;

/**
 * Internal runner for running the test methods
 * 
 * @author ppostma
 */
public class UIUnitTestMethodRunner extends MethodRoadie implements	IRunningTest
{

	private Object test;

	private TestMethod method;

	private Method internalMethod;

	private RunNotifier notifier;

	private Description description;
	
	private static boolean forceReload = false;

	/**
	 * Constructor
	 * 
	 * @param test
	 * @param method
	 * @param notifier
	 * @param description
	 */
	public UIUnitTestMethodRunner(Object test,
																TestMethod method,
																RunNotifier notifier,
																Description description,
																Method internalMethod)
	{
		super(test, method, notifier, description);
		this.test = test;
		this.method = method;
		this.notifier = notifier;
		this.description = description;
		this.internalMethod = internalMethod;

	}
	
	protected void addFailure(Throwable e) {
		//ignore ThreadDeath exception, this is reased by the JVM because of thread.stop()
		if(!(e instanceof ThreadDeath))
		{
			super.addFailure(e);
		}
	}

	@Override
	public void run()
	{

		UIUnitReloadBrowser reload = internalMethod.getAnnotation(UIUnitReloadBrowser.class);
		if (reload != null || forceReload)
		{
			forceReload = false;
			ITestEnvironment environment = EnvironmentFactory.getEnvironment();
			environment.getBrowser().refresh();
		}

		// check for Prerequisite annotation
		Prerequisite prereq = internalMethod.getAnnotation(Prerequisite.class);
		if (prereq != null)
		{

			boolean fulfilled = new PrerequisiteHandler().handleAnnotation(description,
																																		 test,
																																		 prereq,
																																		 notifier);
			if (!fulfilled)
			{
				return;
			}
		}

		// get the timeout duration from the annotation on method level
		UIUnitTimeout timeout = internalMethod.getAnnotation(UIUnitTimeout.class);

		long timeoutValue;
		// if the annotation exists

		if (timeout == null)
		{
			//if no annotation exists on method level
			timeout = test.getClass().getAnnotation(UIUnitTimeout.class);
		}
		if (timeout != null)
		{
			// if the timeout is set to 0 or lower (negative)
			if (timeout.value() <= 0)
			{
				timeoutValue = -1;
			}
			else
			{
				// set timeout to value
				timeoutValue = timeout.value();
			}
		}
		else
		{
			timeoutValue = EnvironmentFactory.getEnvironment()
																			 .getConfiguration()
																			 .getTimeOutInMiliseconds();
		}

		runWithTimeout(timeoutValue);
	}

	private void doRun()
	{
		super.run();
	}

	private String uiunitThreadName;
	private ExecutorService service;
	private Future<Object> result;
	private MyTask myTask;

	private void runWithTimeout(long timeout)
	{

		ITestEnvironmentEdit environment = EnvironmentFactory.getEnvironment();

		environment.startTimeout(timeout);
		
		service = Executors.newSingleThreadExecutor();
		Callable<Object> callable = new Callable<Object>()
		{
			public Object call() throws Exception
			{
				uiunitThreadName = Thread.currentThread().getName();
				doRun();
				return null;
			}
		};
		myTask = new MyTask(callable);
		result = service.submit(myTask, null);
		boolean terminated = true;
		service.shutdown();
		try
		{
//			if (timeout > 0)
//			{
//				terminated = service.awaitTermination(timeout, TimeUnit.MILLISECONDS);
//			}
//			else
//			{
//				terminated = service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//			}
			
			if (timeout > 0)
			{
				result.get(timeout, TimeUnit.MILLISECONDS); // throws the exception if one occurred during the invocation
			}
			else
			{
				result.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
			}

			//result.get(0, TimeUnit.MILLISECONDS); // throws the exception if one occurred during the invocation
		}
		catch (TimeoutException e)
		{
			Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();

			Exception ex = null;
			Thread uiunitThread = getUIUnitThread(map);
			if(null != uiunitThread)
			{
				ex = new UIUnitTimeoutException(timeout, map.get(uiunitThread));
			}
			if (ex != null)
			{
				addFailure(ex);
			}
			else
			{
				addFailure(e);
			}
			stopThread();
		}
		catch (InterruptedException e)
		{
			addFailure(e);
		}
		catch (ExecutionException e)
		{
			addFailure(e);
		}
		finally
		{
			
			environment.stopTimeout(); // set the timer to 0
			
			if (!terminated)
			{
				service.shutdownNow();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reportFailure(Exception exception, boolean forceToReload)
	{
		forceReload = forceToReload;
		
		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		Exception ex = null;
		Thread uiunitThread = getUIUnitThread(map);
		if(null != uiunitThread)
		{
			
				ex = new UIUnitException(Messages.EXCEPTION_OCCURED);
				ex.setStackTrace(map.get(uiunitThread));
				ex.initCause(exception);
				
				addFailure(ex);
				stopThread();
		}
		if (ex == null)
		{
			addFailure(exception);
		}
	}
	
	private Thread getUIUnitThread(Map<Thread, StackTraceElement[]> map)
	{
		for (Thread t : map.keySet())
		{
			if (t.getName().equalsIgnoreCase(uiunitThreadName))
			{
				return t;
			}
		}
		return null;
	}
	
	class MyTask<V> extends FutureTask<V>
	{

		public MyTask(Callable<V> callable)
		{
			super(callable);
		}
		
		public void reportFailure(Exception exception, boolean forceToReload)
		{
			this.setException(exception);
			this.cancel(true);
			forceReload = forceToReload;
		}
	}
	
	public boolean stopThread()
	{
		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		Thread uiunitThread = getUIUnitThread(map);
		if(null != uiunitThread)
		{
			try{
				uiunitThread.interrupt();
				uiunitThread.stop();
			}
			catch(Throwable thr)
			{
				System.out.println(thr.toString());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean resume()
	{
		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		Thread uiunitThread = getUIUnitThread(map);
		if(null != uiunitThread)
		{
			uiunitThread.resume();
			return true;
		}
		return false;
	}

	@Override
	public boolean suspend()
	{
		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		Thread uiunitThread = getUIUnitThread(map);
		if(null != uiunitThread)
		{
			uiunitThread.suspend();
			return true;
		}
		return false;
	}
}
