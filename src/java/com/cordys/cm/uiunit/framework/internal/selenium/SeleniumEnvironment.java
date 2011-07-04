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

import java.io.IOException;
import java.io.StringReader;
import java.util.EventListener;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cordys.cm.uiunit.config.ConfigurationManager;
import com.cordys.cm.uiunit.config.IConfiguration;
import com.cordys.cm.uiunit.elements.IElement;
import com.cordys.cm.uiunit.elements.finder.ElementFinder;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.exceptions.NoContextSetException;
import com.cordys.cm.uiunit.exceptions.UIUnitException;
import com.cordys.cm.uiunit.framework.IBrowser;
import com.cordys.cm.uiunit.framework.ICordysServer;
import com.cordys.cm.uiunit.framework.IKeyboard;
import com.cordys.cm.uiunit.framework.ILogger;
import com.cordys.cm.uiunit.framework.IMouse;
import com.cordys.cm.uiunit.framework.IRootHTMLContext;
import com.cordys.cm.uiunit.framework.ITestEnvironment;
import com.cordys.cm.uiunit.framework.IMouse.Speed;
import com.cordys.cm.uiunit.framework.internal.Keyboard;
import com.cordys.cm.uiunit.framework.internal.Mouse;
import com.cordys.cm.uiunit.framework.internal.UIUnitLogger;
import com.cordys.cm.uiunit.framework.selenium.ISeleniumEnvironment;
import com.cordys.cm.uiunit.junit4.ITestRunner;
import com.cordys.cm.uiunit.message.Messages;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumCommandTimedOutException;
import com.thoughtworks.selenium.SeleniumException;

public final class SeleniumEnvironment implements ISeleniumEnvironment
{
	
	private SeleniumRunListener runListener = null;
	
	private boolean listenerAlreadyRegistered = false;
	
	private int totalTests = 0;
	
	private String browserString;
	
	private IConfiguration config	=	null;
	
	private Selenium selenium;
	
	private IBrowser browser;
	
	private RootContext rootcontext;
	
	private IMouse mouse = null;
	
	private IKeyboard keyBoard = null;
	
	public SeleniumEnvironment()
	{
		this.runListener = new SeleniumRunListener(this);
		this.mouse =  new Mouse(this);
		this.keyBoard = new Keyboard(this);
		this.rootcontext = new RootContext(this);
		this.browser = new Browser(this);
	}
		
	@Override
	public Selenium getSeleniumObject()
	{
		if ( this.selenium == null )
		{
			String seleniumServer = null;
			if((seleniumServer = System.getProperty("seleniumserver"))==null){
				seleniumServer = getConfiguration().getStringProperty("seleniumserver", "localhost:4444");
			}
			
			String seleniumServerHost = seleniumServer;
			int seleniumServerPort = 4444;
			if (seleniumServer.contains(":"))
			{
				String temp[] = seleniumServer.split(":");
				seleniumServerHost = temp[0];
				seleniumServerPort = Integer.parseInt(temp[1]);
			}
			String machineName = getConfiguration().getTargetMachine().getName();
			this.selenium = new DefaultSelenium( seleniumServerHost, seleniumServerPort, browserString, "http://"+machineName);
			
			this.selenium.start();
			this.selenium.windowMaximize();
			this.selenium.setBrowserLogLevel("error");

		}
		return selenium;
	}
	
	
	@Override
	public void registerTestDescription(Description description) 
	{
		totalTests	+=	description.testCount();
		runListener.setTotalNumberOfTest(totalTests);
	}

	@Override
	public void registerToNotifier(RunNotifier notifier) 
	{
		if (!listenerAlreadyRegistered)
		{
			notifier.addListener(runListener);
			listenerAlreadyRegistered = true;
		}
	}
	
	@Override
	public void checkStopFramework()
	{
		runListener.checkStopFramework();
	}

	@Override
	public void setConfiguration(final IConfiguration configuration) 
	{
		this.config = configuration;
		if( null == config )
		{
			// use default config ... to provide backward compatibility
			this.config = ConfigurationManager.createConfig();
		}

		if((browserString=System.getProperty("browsertype"))==null)
		{
			browserString	= getConfiguration().getBrowserType().getName();
		}
		
		if(browserString.equalsIgnoreCase("firefox"))
		{
			browserString="*firefox";
		}
		else if(browserString.equalsIgnoreCase("IE")){
			browserString="*iexplore";
		}
		else if(browserString.equalsIgnoreCase("chrome"))
		{
			browserString="*googlechrome";
		}
		else if(browserString.equalsIgnoreCase("safari"))
		{
			browserString="*safari";
		}
	}

	@Override
	public void startTimeout(long ms)
	{
		runListener.startTimeout(ms);
	}
	@Override
	public void stopTimeout()
	{
		runListener.stopTimeout();
	}

	@Override
	public IBrowser getBrowser() 
	{
		return browser;
	}

	@Override
	public IConfiguration getConfiguration()
	{
		if( config == null )
		{
			throw new NoContextSetException();

		}
		return config;
	}

	@Override
	public ICordysServer getCordysServer() 
	{
		return null;
	}

	@Override
	public IKeyboard getKeyboard() 
	{
		return this.keyBoard;
	}

	@Override
	public IMouse getMouse() 
	{
		return this.mouse;
	}

	@Override
	public IRootHTMLContext getRootContext() 
	{
		return rootcontext;
	}

	@Override
	public ITestRunner getTestRunner() 
	{
		throw new RuntimeException("This is not support yet in SeleniumEnvironment");
	}
	
	final class Browser implements IBrowser
	{		
		
		private String url;
		private SeleniumEnvironment selenv;
		//private String token = null;
		
		private int TITLEBAR_HEIGHT_IE = 62;
		private int TITLEBAR_HEIGHT_CHROME = 56;
		private int TITLEBAR_HEIGHT_FireFox = 58;
		private int TITLEBAR_HEIGHT_Safari = 66;
		
		public Browser(SeleniumEnvironment environment)
		{			
			this.selenv	=	environment;
		}
		
		@Override
		public void addEventListener(EventListener listener) 
		{
		}

		@Override
		public String executeJavascript(String javascript) 
		{
			return selenv.getSeleniumObject().getEval(javascript);
		}

		@Override
		public String getStartUrl() 
		{
			return url;
		}
		
		@Override
		public int getTitleBarHeight(String browserType) 
		{
			int titleBarHeight = TITLEBAR_HEIGHT_IE;
			if ("chrome".equalsIgnoreCase(browserType)|| browserType.toLowerCase().contains("chrome") )
			{
				titleBarHeight = TITLEBAR_HEIGHT_CHROME;
			}
			else if ("firefox".equalsIgnoreCase(browserType)|| browserType.toLowerCase().contains("firefox"))
			{
				titleBarHeight = TITLEBAR_HEIGHT_FireFox;
			}
			else if ("safari".equalsIgnoreCase(browserType) || browserType.toLowerCase().contains("safari"))
			{
				titleBarHeight = TITLEBAR_HEIGHT_Safari;
			}
			return titleBarHeight;
		}
		
		@Override
		public void loadURL(String inputUrl)
		{
			String machineName = getConfiguration().getTargetMachine().getName();
			String newUrl = inputUrl.replaceAll("<default>", machineName);
			this.selenv.getSeleniumObject().setTimeout("0"); //Because standard browsers will block on .open(url); So do it async for all browsers and use waitForCondition
			if(getConfiguration().getBrowserType().getName().contains("firefox")&& getConfiguration().getDetectScriptError().equals("true"))
			{
				selenv.getSeleniumObject().getEval("this.doBeginJsErrorChecker()");
			}
			if (this.url == null)
			{
				this.url = newUrl;
				try
				{
					String token = "";
					if (getConfiguration().getAnonymous())
					{
						token = fillUrlWithAuthenticationToken(machineName);
					}
					this.selenv.getSeleniumObject().open(this.url + token);
					this.selenv.getSeleniumObject().setTimeout("30000"); //set back to 30 seconds

					makeSureCordysRootSet(newUrl);
					String javascript = "if (!CordysRoot.testDriverIsIdle) CordysRoot.application.addType(CordysRoot, \"wcp.library.util.UIUnitTestDriver\")";
					this.selenv.getSeleniumObject().getEval(javascript);
					getRootContext().waitForIdle();
				}
				catch (SeleniumException e)
				{
					getRootContext().getLogger().error(this, e, "Error during load/open");
					throw new UIUnitException(e, Messages.URL_NOT_LOADED_EXCEPTION);
				}
				catch (IOException e)
				{
					getRootContext().getLogger().error(this, e, "Error during load/open");
					throw new UIUnitException(e, Messages.URL_NOT_LOADED_EXCEPTION);
				}
				try 
				{
					if (!getRootContext().calibrateMouseCoordinateForCordysRootWindow())
					{
						getRootContext().getLogger().error(this, "calibrateMouseCoordinateForCordysRootWindow: Failed; Probably due to focus loss");
						throw new UIUnitException(Messages.CALIBRATION_FAILED);
					}
				}
				catch(SeleniumException e)
				{
					getRootContext().getLogger().error(this, e, "Error during Mouse Calliberation");
				}
			}
		}

		private void makeSureCordysRootSet(String newUrl) throws SeleniumException
		{
			if(newUrl.contains(".caf"))
			{
				selenv.getSeleniumObject().waitForCondition("selenium.browserbot.getCurrentWindow() && selenium.browserbot.getCurrentWindow().application && selenium.browserbot.getCurrentWindow().application.addType", "30000");
				selenv.getSeleniumObject().getEval("if ( typeof(CordysRoot) == \"undefined\" ) CordysRoot = selenium.browserbot.getCurrentWindow();");
			}
			else
			{
				selenv.getSeleniumObject().waitForCondition("selenium.browserbot.getCurrentWindow().frames[\"desktop\"] && selenium.browserbot.getCurrentWindow().frames[\"desktop\"].application && selenium.browserbot.getCurrentWindow().frames[\"desktop\"].application.addType", "30000");
				selenv.getSeleniumObject().getEval("if ( typeof(CordysRoot) == \"undefined\" ) CordysRoot = selenium.browserbot.getCurrentWindow().frames[\"desktop\"];");
			}
			selenv.getSeleniumObject().waitForCondition("CordysRoot && CordysRoot.application && CordysRoot.application.container && CordysRoot.application.container.applicationState == 'loaded';", "30000");
		}
		
		private String fillUrlWithAuthenticationToken(String machineName) throws IOException
		{
			SSOAuthenticationHandler ssoAuthenticationHandler = new SSOAuthenticationHandler();
			String token = ssoAuthenticationHandler.authenticate(machineName, getConfiguration().getStringProperty("username", ""), getConfiguration().getStringProperty("password", ""));
			String SAMLToken = "";
			if ( this.url.indexOf("?") == -1 )
			{
				SAMLToken = "?SAMLart=" + token;
			}
			else
			{
				SAMLToken = "&SAMLart=" + token;
			}
			this.selenv.getSeleniumObject().runScript("document.cookie='SAMLart="+ token+"; path=/cordys;'");
			return SAMLToken;
		}
		
		@Override
		public void refresh() 
		{
			this.selenv.getSeleniumObject().close();
			this.selenv.getSeleniumObject().stop();
			this.selenv.getSeleniumObject().start();
			this.selenv.getSeleniumObject().windowMaximize();

			String existingUrl = this.url;
			this.url = null; //reset
			loadURL(existingUrl);
		}

		@Override
		public void removeEventListener(EventListener listener) 
		{
		}
		
	}
	
	final class RootContext implements IRootHTMLContext
	{
		
		private ILogger logger = null;

		private ISeleniumEnvironment environment;
		
		private int calibratedCordysRootX = 0;	//The hardware position in pixels of the top edge of CordysRoot.document.body 
		
		private int calibratedCordysRootY = 0;	//The hardware position in pixels of the left edge of CordysRoot.document.body
		
		RootContext(ISeleniumEnvironment environment)
		{
			this.environment = environment;
		}
		
		@Override
		public ITestEnvironment getTestEnvironment() 
		{
			return environment;
		}

		@Override
		public void addElement(IElement element)
		{
			element._setContext(this);
		}

		
		/**
		 * Selenium has different ways to locate an element
		 * http://release.seleniumhq.org/selenium-remote-control/0.9.0/doc/java/com/thoughtworks/selenium/Selenium.html#locators
		 */
		@Override
		public boolean elementExistsById(String id) 
		{
			return this.environment.getSeleniumObject().isElementPresent("id="+id);
		}

		@Override
		public String executeJavascript(String javascript) 
		{
			return this.getTestEnvironment().getBrowser().executeJavascript(javascript);
		}

		@Override
		public <T extends IHTMLElement> T findElement(Class<T> type,String elementID) 
		{
			return ElementFinder.findElementById(type, this, elementID);
		}

		@Override
		public <T extends IHTMLElement> List<T> findElementsByName(Class<T> type, String name) 
		{
			return ElementFinder.findElementsByName(type, this, name);
		}

		@Override
		public <T extends IHTMLElement> List<T> findElementsByTagName(Class<T> type, String tagName) 
		{
			return ElementFinder.findElementsByTagName(type, this, tagName);
		}

		@Override
		public int getBrowserXCoordinate() 
		{
			return calibratedCordysRootX;
		}

		@Override
		public int getBrowserYCoordinate() 
		{
			return calibratedCordysRootY;
		}

		@Override
		public ILogger getLogger() 
		{
			if ( this.logger == null )
			{
				this.logger = new UIUnitLogger();
			}
			return this.logger;
		}

		@Override
		public IRootHTMLContext getRootContext() 
		{
			return this;
		}

		@Override
		public int getScreenXCoordinate() 
		{
			return calibratedCordysRootX;
		}

		@Override
		public int getScreenYCoordinate() 
		{
			return calibratedCordysRootY;
		}

		@Override
		public String getWindow() 
		{
			//TODO:: check what about having CordysRoot vs Window in case of independent test case
			return "CordysRoot";
		}
		
		protected void sleep( int ms )
		{
			// sleep ms miliseconds more
			try
			{
				Thread.sleep(ms);
			}
			catch( InterruptedException e )
			{
				throw new UIUnitException(e, Messages.WAIT_FOR_IDLE_EXCEPTION);
			}
		}
		
		@Override
		public long waitForIdle(int waitTime)
		{
			final long startTime = System.currentTimeMillis();
			int i;
			for (i = 1; i<=10; i++)
			{
				try
				{
					//Most likely the back-end is (almost) idle and some single tries will already succeed
					//(in IE getEval seems to be faster than waitForCondition )
					if ( getSeleniumObject().getEval("CordysRoot.testDriverIsIdle()").equals("true") ) break;
					if ( getSeleniumObject().getEval("CordysRoot.testDriverIsIdle()").equals("true") ) break;
					if ( getSeleniumObject().getEval("CordysRoot.testDriverIsIdle()").equals("true") ) break;
					getSeleniumObject().waitForCondition("CordysRoot.testDriverIsIdle()", "10000");
					break;
				}
				catch(SeleniumException e)
				{
					String result = getSeleniumObject().getEval("CordysRoot._UIUnitTestDriverStatus");
					getLogger().debug(this, "waitForIdle=" + i + " seconds   reason=" +result);
					if ( i == 10 )
					{
						logger.error(this, "Timeout occured while waitForIdle=" + i + " seconds   reason=" +result);
						throw new UIUnitException(Messages.TIMEOUT_EXCEPTION_DURING_WAITFORIDLE, (System.currentTimeMillis() - startTime));
					}
				}
			}
			if(this.environment.getConfiguration().getBrowserType().getName().contains("firefox")&& getConfiguration().getDetectScriptError().equals("true")){
				getSeleniumObject().getEval("this.doCheckForError()");
			}
			long endTime = System.currentTimeMillis();
			getLogger().debug(this, "waitForIdle waitTime: " + (endTime - startTime));
			return (endTime - startTime);
		}
		
		@Override
		public void waitForIdle()
		{
			this.waitForIdle(0);
		}
		
		/*
		 * This is a Browser independent way to calculate the exact coordinates in pixels of the CordysRoot window
		 * This is necessary because all browsers will have their position and own decoration of borders, titlebars etc.
		 * Also the absolute position can be influenced due to the native task bar on the Windows/Mac/Linux desktop.
		 * During some tests the mouse robot is used and there we need to use hardware mouse positions. The calculated coordinates
		 * can be used as correction offset to adjust relative coordinates for CordysRoot window to hardware screen coordinates.
		 */
		public boolean calibrateMouseCoordinateForCordysRootWindow()
		{
			String javascript = "if (!CordysRoot.showEventCaptureLayer) CordysRoot.application.addType(CordysRoot, \"wcp.library.util.HTMLUtil\");"
				+ "CordysRoot.__mouseCalibrateLayer = CordysRoot.showEventCaptureLayer(CordysRoot.document, true);" 
				+ "CordysRoot.cordys.addDOMListener(CordysRoot.__mouseCalibrateLayer,\"onclick\",function(evt){"
				+		"CordysRoot.CordysRootScreenX = evt.screenX - evt.clientX;"
				+		"CordysRoot.CordysRootScreenY = evt.screenY - evt.clientY;"
				+		"CordysRoot.cordys.removeDOMListener(CordysRoot.__mouseCalibrateLayer,\"onclick\",arguments.callee);"
				+ 		"CordysRoot.hideEventCaptureLayer(CordysRoot.document, true);"
				+		"CordysRoot.__mouseCalibrateLayer = null;"
				+ "});";
			executeJavascript(javascript);
			int almostCorrectCordysScreenX = Integer.parseInt(executeJavascript("('screenLeft' in CordysRoot) ? CordysRoot.screenLeft : CordysRoot.screenX"));
			int almostCorrectCordysScreenY = Integer.parseInt(executeJavascript("('screenTop' in CordysRoot) ? CordysRoot.screenTop : CordysRoot.screenY"));
			waitForIdle();
			IMouse mouse = this.getTestEnvironment().getMouse();
			mouse.moveTo(almostCorrectCordysScreenX+300, almostCorrectCordysScreenY+300); // Move somewhere in the middle of the screen; (we have a maximized browser and we are pretty sure to click somewhere on the CordysRoot context; 
			executeJavascript("CordysRoot.focus();"); //Ensure browser window has focus (e.g firefox showed a plug-in installer popup stealing the focus);
			mouse.click();
			waitForIdle();
			mouse.click();
			waitForIdle();
			boolean isCalibrated = "true".equals(executeJavascript("'CordysRootScreenX' in CordysRoot ? 'true' : 'false'"));
			if ( isCalibrated )
			{
				this.calibratedCordysRootX = Integer.parseInt(executeJavascript("CordysRoot.CordysRootScreenX"));
				this.calibratedCordysRootY = Integer.parseInt(executeJavascript("CordysRoot.CordysRootScreenY"));
				getLogger().info(this, "CalibratedCordysRootX = " + this.calibratedCordysRootX + "  Adjustment due to Browser decoration: " + (this.calibratedCordysRootX - almostCorrectCordysScreenX));
				getLogger().info(this, "CalibratedCordysRootY = " + this.calibratedCordysRootY + "  Adjustment due to Browser decoration: : " + (this.calibratedCordysRootY - almostCorrectCordysScreenY));
			}
			return isCalibrated;
		}
	}
	
	class SSOAuthenticationHandler
	{
		
		private final static String FIRSTTOKEN = "<SOAP:Envelope xmlns:SOAP='http://schemas.xmlsoap.org/soap/envelope/'>" + 
						        "<SOAP:Header>" +
								    "<wsse:Security xmlns:wsse='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd'>" +
									   "<wsse:UsernameToken xmlns:wsse='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd'>";
		private final static String SECONDTOKEN =
								"</wsse:UsernameToken></wsse:Security>" +
									 "</SOAP:Header>" + 
									"<SOAP:Body>" + 
									"<samlp:Request xmlns:samlp='urn:oasis:names:tc:SAML:1.0:protocol' MajorVersion='1' MinorVersion='1' IssueInstant='2009-09-11T05:14:37Z' RequestID='ac954e0e3b-9694-e12a-ace2-7137c457c7d'>" +
									   "<samlp:AuthenticationQuery>" +
									      "<saml:Subject xmlns:saml='urn:oasis:names:tc:SAML:1.0:assertion'>";
		
		private final static String THIRDTOKEN = "</saml:Subject>" +
												    "</samlp:AuthenticationQuery>" +
												  "</samlp:Request>" +
											"</SOAP:Body>" +
											"</SOAP:Envelope>";
    
		
		public String authenticate(String machineName, String userName, String password) throws HttpException, IOException
		{
			String url = "http://" + machineName + "/cordys/com.eibus.web.soap.Gateway.wcp?messageOptions=0";
			String request = FIRSTTOKEN +
			  					"<wsse:Username>"+ userName +"</wsse:Username>" +
	          					"<wsse:Password>"+ password +"</wsse:Password>" +
	          				 SECONDTOKEN +	
	          			        "<saml:NameIdentifier Format='urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified'>"+userName+"</saml:NameIdentifier>" +
						     THIRDTOKEN;
	          				

		       // Prepare HTTP post
		    PostMethod post = new PostMethod(url);
		    post.setRequestHeader("Accept-Language", "en-us");	
			post.setRequestHeader("Content-Type", "text/xml; charset=UTF-8");
			post.setRequestBody( request );
		      
		    HttpClient httpclient = new HttpClient();
		    String response;
		       // Execute request
		    try
		    {
				httpclient.executeMethod(post);
				response = post.getResponseBodyAsString();
		    }
			finally 
			{
		        post.releaseConnection();
		    }
		
			Document doc = null;
		       
			try
			{
				DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource inputSource = new InputSource();
		    inputSource.setCharacterStream(new StringReader(response));			    
				doc = documentBuilder.parse(inputSource);
				NodeList nodes = doc.getElementsByTagName("samlp:AssertionArtifact");
				Node assertionId = nodes.item(0);
				return assertionId.getFirstChild().getNodeValue();		    
			}
			catch (SAXException e)
			{
				getRootContext().getLogger().error(this, e.getMessage());
			}
			catch (IOException e)
			{
				getRootContext().getLogger().error(this, e.getMessage());
			}
			catch (ParserConfigurationException e) 
			{
				getRootContext().getLogger().error(this, e.getMessage());
			}
			return null;
		}
	}
}
