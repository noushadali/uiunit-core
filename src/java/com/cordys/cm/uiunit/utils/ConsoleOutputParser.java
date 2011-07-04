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
 
 package com.cordys.cm.uiunit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleOutputParser
{
	// Example data
	// [junit] 09:48:29 INFO com.cordys.cm.uiunit.framework.TestFrameworkInformation - Test IGNORED: 007/011 -
	// testFolderQNRReload(com.cordys.cws.uiunit.test.project.QualifiedNameRootTestCase)
	// [junit] 09:48:29 INFO com.cordys.cm.uiunit.framework.TestFrameworkInformation - IGNORE Reason: Test ignored due to
	// issues in UIUnit framework with refreshing browser. This issue is logged as ticket
	// http://srv-ind-scrat/dsolver/show_bug.cgi?id=60978. Get the issue fixed and reactivate this test case.

	private static String TEST_IGNORED = "Test IGNORED:.*-(.*)";

	private static String IGNORE_REASON = "IGNORE Reason";

	private static String METHOD_CLASS = "(.*)\\((.*)\\)";

	private static Pattern patternTestIgnored = Pattern.compile(TEST_IGNORED);

	private static Pattern patternIgnoreReason = Pattern.compile(IGNORE_REASON);

	private static Pattern patternMethodClass = Pattern.compile(METHOD_CLASS);

	private static Map<String, TestCase> results = new HashMap<String, TestCase>();
	
	private static String NEWLINE = System.getProperty("line.separator");

	public static void printinfo(URLConnection u) throws IOException
	{
		String standardOut = "";
		// Display the URL address, and information about it.
		BufferedReader in = new BufferedReader(new InputStreamReader(u.getInputStream()));
		String line = in.readLine();
		boolean appendNextLineIfReason = false;
		
		while (line != null)
		{
			if(appendNextLineIfReason)
			{
				if(patternIgnoreReason.matcher(line).find())
				{
					standardOut += line.trim() + NEWLINE + NEWLINE;
				}
				appendNextLineIfReason=false;
			}

			Matcher matcher = patternTestIgnored.matcher(line);
			if (matcher.find())
			{
				String group = matcher.group(1);
				// System.out.println(group);
				Matcher methodClassMatcher = patternMethodClass.matcher(group);
				if (methodClassMatcher.find())
				{
					String method = methodClassMatcher.group(1);
					String className = methodClassMatcher.group(2);
					String key = className + "." + method;
					if (!results.containsKey(key))
					{
						TestCase testCase = new TestCase(className, method);
						results.put(key, testCase);
						standardOut += line.trim() + NEWLINE;
						appendNextLineIfReason=true;
					}
				}
			}
			line = in.readLine();
		}

		// <testsuite failures="0" time="1.155" errors="0" skipped="3" tests="18"
		// name="org.antlr.stringtemplate.js.JSTemplateTest">
		// <testcase time="0.296" classname="org.antlr.stringtemplate.js.JSTemplateTest" name="simpleTextTest"/>
		// <testcase time="0.296" classname="org.antlr.stringtemplate.js.JSTemplateTest" name="elseifTest">
		// <skipped/>
		// </testcase>
		// </testsuite>

		int size = results.size();
		System.out.println("<testsuite failures=\"\" time=\"0.0\" errors=\"0\" skipped=\""
											 + size
											 + "\" tests=\""
											 + size
											 + "\" name=\"com.cordys.skippedtests.Suite\">");
		for (TestCase testCase : results.values())
		{
			System.out.println("   <testcase time=\"0.0\" classname=\""
												 + testCase.getClassName()
												 + "\" name=\""
												 + testCase.getMethod()
												 + "\">");
			System.out.println("      <skipped/>");
			System.out.println("   </testcase>");
		}
		System.out.println("<system-out><![CDATA[");
		System.out.println(standardOut);
		System.out.println("]]></system-out>");
		System.out.println("</testsuite>");
	}

	// Create a URL from the specified address, open a connection to it,
	// and then display information about the URL.
	public static void main(String[] args) throws MalformedURLException, IOException
	{
		URL url = new URL(args[0]);
		URLConnection connection = url.openConnection();
		printinfo(connection);
	}

	private static class TestCase
	{
		private final String m_className;

		private final String m_method;

		public TestCase(String className, String method)
		{
			this.m_className = className;
			this.m_method = method;
		}

		public String getClassName()
		{
			return m_className;
		}

		public String getMethod()
		{
			return m_method;
		}
	}
}
