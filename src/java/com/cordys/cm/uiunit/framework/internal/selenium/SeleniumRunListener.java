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
 
package com.cordys.cm.uiunit.framework.internal.selenium;

import static java.util.regex.Pattern.compile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import sun.misc.BASE64Decoder;

import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;
import com.cordys.cm.uiunit.framework.TestFrameworkInformation;
import com.cordys.cm.uiunit.framework.selenium.ISeleniumEnvironment;
import com.cordys.cm.uiunit.utils.Screendumper;
import com.thoughtworks.selenium.SeleniumException;

public class SeleniumRunListener extends RunListener{

	private ISeleniumEnvironment seleniumEnv;
	
	private static final String TIMEOUT_TEXT_LAYOUT = "Timeout: %s";
	
	private static final String TIMELEFT_TEXT_LAYOUT = "Timeout settings: %s  Time left: %s";
	
	private static final String CLASSREGEX = "^(\\w+?\\()?(\\w+?(\\.\\w+?)+?)[\\)]?$";
	
	private static final Pattern CLASSNAME_PATTERN = compile(CLASSREGEX);
	
	private static final String METHODREGEX = "^(\\w+?)\\(\\w+(\\.\\w+)+?\\)$";
	
	private static final Pattern METHODNAME_PATTERN = Pattern.compile(METHODREGEX);

	private int totalTests = 0;
	
	private long timeoutTime = 0;
	
	private long startTime = 0;

	private int runnedTests = 0;

	private int totalFailures = 0;

	private int totalIgnored = 0;

	private int totalErrors = 0;

	private Logger logger;
	
	public SeleniumRunListener(ISeleniumEnvironment seleniumEnv )
	{
		this.seleniumEnv = seleniumEnv;
		this.logger = Logger.getLogger(TestFrameworkInformation.class.getName());
	}
	
	public void setTotalNumberOfTest(int total)
	{
		totalTests = total;
	}
	
	public void startTimeout(long time)
	{
		this.startTime = System.currentTimeMillis();
		if (time == -1)
		{
			this.timeoutTime = -1;
		}
		else
		{
			this.timeoutTime = time;
			logger.debug(String.format(TIMEOUT_TEXT_LAYOUT, formatDuration(this.timeoutTime)));
		}
	}

	public void stopTimeout()
	{
		if(this.timeoutTime >0)
		{
			long left = (startTime + timeoutTime) - System.currentTimeMillis();
			logger.debug(String.format(TIMELEFT_TEXT_LAYOUT, formatDuration(timeoutTime), formatDuration(left)));
		}
		this.timeoutTime = 0;
	}
	
	private static String formatDuration(long durationInMs)
	{
		long milliSecondsPerHour = 60 * 60 * 1000;
		long hours = durationInMs / milliSecondsPerHour;
		long minutes = (durationInMs / 60 / 1000) % 60;
		long seconds = (durationInMs / 1000) % 60;
		long miliseconds = durationInMs % 1000;

		return String.format("%02d:%02d:%02d.%03d ",
												 hours,
												 minutes,
												 seconds,
												 miliseconds);
	}
	
	@Override
	public void testFailure(Failure failure)
	{
		if (failure.getException() instanceof AssertionError)
		{
			totalFailures++;
		}
		else
		{
			totalErrors++;
		}

		logger.error(String.format( "Test FAILED: %03d/%03d - %s", runnedTests, totalTests , failure.getDescription()), failure.getException() );
		dumpScreen(failure);
	}

	@Override
	public void testFinished(Description description) throws Exception
	{
		this.log("Test finished: %03d/%03d - %s", runnedTests, totalTests , description.getDisplayName());
	}

	@Override
	public void testIgnored(Description description)
	{
		totalIgnored++;
		runnedTests++;
		this.log("Test IGNORED:  %03d/%03d - %2s", runnedTests, totalTests , description.getDisplayName());
		logIgnoreReason(description);
	}

	private void logIgnoreReason(Description description)
	{
		String testClassName = getTestClass(description);
		String testName = getTestName(description);
		try
		{
			Ignore ignore;
			Class<?> testClass = Class.forName(testClassName); // get class instance
			if (testName.length() > 0) // if a testname is specified (ignore on test method)
			{
				// get the method
				Method method = testClass.getMethod(testName);
				// get @Ignore from the method
				ignore = method.getAnnotation(Ignore.class);
			}
			else // if no testname is specified (ignore on class)
			{
				// get @Ignore from the class
				ignore = testClass.getAnnotation(Ignore.class);
			}
			//Due to prerequisite testing, it is possible to ignore without Ignore annotation
			if(null != ignore)
			{
    		String ignoreReason = ignore.value();
    		
    		if("".equals(ignoreReason))
    		{
    			ignoreReason = "No reason provided, please use '@Ignore(\"Reason why to ignore the test\")'";
    		}
    		this.log("IGNORE Reason: %s", ignoreReason);
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new UIUnitRuntimeException(e);
		}
		catch (SecurityException e)
		{
			throw new UIUnitRuntimeException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new UIUnitRuntimeException(e);
		}
	}

	@Override
	public void testRunFinished(Result result)
	{
		this.log("Testrun finished");
		this.log("Tests run: %s, Failures: %s, Errors: %s, Ignored: %s, Time elapsed: %s",
						 result.getRunCount(),
						 totalFailures,
						 totalErrors,
						 result.getIgnoreCount(),
						 result.getRunTime());
		stopFramework();
	}

	@Override
	public void testRunStarted(Description description)
	{
		this.log("Testrun started: %s", description.getDisplayName());
	}

	@Override
	public void testStarted(Description description)
	{
		runnedTests++;
		this.log("Test started:  %03d/%03d - %s", runnedTests, totalTests, description.getDisplayName());
	}
	
	private String getTestName(Description description)
	{
		Matcher matcher = METHODNAME_PATTERN.matcher(description.getDisplayName());
		if (matcher.find())
		{
			return matcher.group(1);
		}
		return "";
	}
	
	private String getTestClass(Description description)
	{
		Matcher matcher = CLASSNAME_PATTERN.matcher(description.getDisplayName());
		if(matcher.find())
		{
			return matcher.group(2);
		}
		return "";
	}
	
	public void checkStopFramework()
	{
		if(runnedTests == totalTests)
		{
			this.log(String.format("Total runned tests: %03d. End of suite is reached, stopping framework", runnedTests));
			stopFramework();
		}
	}
	
	private void stopFramework()
	{
		//close the browser window
		this.log("Browser will stop");
		try
		{
		this.seleniumEnv.getSeleniumObject().close();
		this.seleniumEnv.getSeleniumObject().stop();
		}
		catch(SeleniumException e)
		{
			//Ignore
		}
	}

	private void log(String message, Object... parameters)
	{
		logger.info(String.format(message, parameters));
	}
	
	private void dumpScreen(Failure failure)
	{
		try
		{
  		Screendumper dumper = new Screendumper();
  		String filename = String.format("%03d - %s", runnedTests, failure.getDescription().getDisplayName());
  		
  		String screenshotString = seleniumEnv.getSeleniumObject().captureScreenshotToString();
  		BufferedImage image = null;
  		try
  		{
  			BASE64Decoder decoder = new BASE64Decoder();
  			byte[] imageBytes = decoder.decodeBuffer(screenshotString);
  			image = ImageIO.read(new ByteArrayInputStream(imageBytes));
  		}
  		catch (UnsupportedEncodingException e)
  		{
  			throw new RuntimeException("Error during creation of screendump: "+e.getMessage(),e);
  		}
  		catch (IOException e)
  		{
  			throw new RuntimeException("Error during creation of screendump: "+e.getMessage(),e);
  		}
  
  		dumper.savePageToPngFile(image, filename);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}

	
}
