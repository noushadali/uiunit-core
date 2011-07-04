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

import java.awt.Stroke;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cordys.cm.uiunit.config.browser.IBrowserType;
import com.cordys.cm.uiunit.config.identity.IIdentity;
import com.cordys.cm.uiunit.config.identity.SimpleIdentity;
import com.cordys.cm.uiunit.config.server.IServer;

/**
 * This class keeps the UI Unit configuration,
 * 
 * @author ddjong
 */
public final class ConfigurationManager
{
	private static ConfigurationManagerImpl s_configManagerImpl;

	private ConfigurationManager()
	{
		// class is factory class; do not instantiate
		
	}

	private static ConfigurationManagerImpl getInstance()
	{
		if( null == s_configManagerImpl )
		{
			s_configManagerImpl = new ConfigurationManagerImpl();
		}
		return s_configManagerImpl;
	}

	public static IConfiguration createConfig()
	{
		return getInstance().createConfig(null, false);
	}

	public static IConfiguration createConfig( boolean persistChanges )
	{
		return getInstance().createConfig(null, persistChanges);
	}

	public static IConfiguration createConfig( String configFile,
	        boolean persistChanges )
	{
		return getInstance().createConfig(configFile, persistChanges);
	}
}

/*
 * Class below is implementation for configuration file from properties file.
 * The most used situation is the c:\UIUNit\UIUnit.properties variant.
 */

final class ConfigurationManagerImpl
{

	/**
	 * Constructor
	 * @param configFile full path to properties file
	 * @param persistChanges save changes made to the config back to the properties file
	 * @return
	 */
	public IConfiguration createConfig( String configFile,
	        boolean persistChanges )
	{
		// if no configFile supplied
		if( null == configFile || "".equals(configFile) )
		{
			// start looking for the defaults
			
			// first check the VM argument -Duiunit.properties=...
			configFile = System.getProperty("uiunit.properties");
			if (null == configFile)
			{
				// next check for a file in the src/java folder
				URL url = ClassLoader.getSystemResource(IConfigurationConstants.UIUNIT_PROPERTIES_FILENAME);
				if (url != null)
				{
					configFile = url.getPath();
				}
				// else use the old c:\UIUnit\UIUnit.properties
				else
				{
					String osName = System.getProperty("os.name").toLowerCase(Locale.US);
					if(osName.contains("windows"))
					{
						configFile = IConfigurationConstants.WINDOWS_UIUNITFOLDER + IConfigurationConstants.UIUNIT_PROPERTIES_FILENAME;
					}
					else
					{
						configFile = IConfigurationConstants.LINUX_UIUNITFOLDER + IConfigurationConstants.UIUNIT_PROPERTIES_FILENAME;
					}
				}
			}
		}
		return new ConfigurationImpl(configFile, persistChanges);
	}
}

final class ConfigurationImpl implements IConfiguration, IConfigurationEdit
{
	private static final String TIMEOUT = "timeout";

	private static final String START_URL = "startURL";

	private static final String REQUESTMAXSIZEINBYTES = "requestmaxsizeinbytes";

	private static final String TRACE_ENABLED = "trace";

	private static final String HOSTNAME = "hostname";
	
	private static final String SELENIUMRCMACHINE = "seleniumserver";

	private static final String BROWSERTYPE = "browsertype";

	private static final String PASSWORD = "password";

	private static final String USERNAME = "username";
	
	private static final String ANONYMOUS = "anonymous";
	
	private static final String  BEPATHFINDER = "bepathfinder";
	
	private static final String FEPATHFINDER = "fepathfinder";
	
	private static final String  DETECTSCRIPTERROR = "detectscripterror";

	private Properties m_properties;

	private IIdentity m_identity;

	private IBrowserType m_browserType;

	private IServer m_server;

	private String m_propertiesFile;

	private final boolean m_persistChanges;
	
	private Map<String, String> m_properties_map = new HashMap<String, String>();
	
	private Logger m_logger = null;

	public ConfigurationImpl( String propertiesFile, boolean persistChanges )
	{
		setPropertiesFile(propertiesFile);
		m_persistChanges = persistChanges;
	}
	
	private Logger getLogger()
	{
		if(null==m_logger)
		{
			m_logger = Logger.getLogger(IConfiguration.class.getName());
		}
		return m_logger;
	}

	private boolean persistChanges()
	{
		return m_persistChanges;
	}

	private void setPropertiesFile( String propertiesFile )
	{
		m_properties = null;

		if( null == propertiesFile || "".equals(propertiesFile)
		        || !new File(propertiesFile).exists() )
		{
			throw new RuntimeException(
			        String.format(
			                "UI Unit configuration properties file %s does not exist or cannot be read.",
			                propertiesFile));
		}
		Properties prop = new Properties();
		try
		{
			prop.load(new FileInputStream(propertiesFile));
			getLogger().debug("Using config file: \""+propertiesFile+"\" ("+propertiesFile.hashCode()+")");
		}
		catch( IOException e )
		{
			throw new RuntimeException(
			        String.format(
			                "UI Unit configuration properties file %s does not exist or cannot be read.",
			                propertiesFile), e);

		}
		m_properties = prop;
		m_propertiesFile = propertiesFile;
	}

	public int getIntProperty( String name, int defaultValue, int minValue,
	        int maxValue )
	{
		int value = defaultValue;
		try
		{
			value = Integer.parseInt(getStringProperty(name,
			        String.valueOf(defaultValue)));
		}
		catch( Exception e )
		{
			value = defaultValue;
		}
		finally
		{
			if( value > maxValue )
			{
				value = defaultValue;
			}
			if( value < minValue )
			{
				value = defaultValue;
			}
		}
		return value;
	}
	
	public boolean getBooleanProperty( String name, boolean defaultValue )
	{
		boolean value = defaultValue;
		try
		{
			value = Boolean.parseBoolean(getStringProperty(name, String.valueOf(defaultValue)));
		}
		catch ( Exception e )
		{
			value = defaultValue;
		}
		
		return value;
	}
	
	public String getStringProperty( String name, String defaultValue )
	{
		String storedValue = m_properties_map.get(name);
		if( null == storedValue)
		{
  		// first check for VM argument: -Duiunit.<name>
  		String propValue = System.getProperty("uiunit." + name);
  		// if it doesn't exist or is empty
  		if (propValue == null || "".equals(propValue))
  		{
  			// use the property from the properties file
  			propValue = m_properties.getProperty(name);
  		}
  		// if even that is null or empty
  		if( null == propValue || "".equals(propValue) )
  		{
  			// use the default value
  			propValue = defaultValue;
  		}
  		m_properties_map.put(name, propValue);
  		storedValue = propValue;
		}
		return storedValue;
	}

	public IBrowserType getBrowserType()
	{
		if( null == m_browserType )
		{
			m_browserType = new BrowserType(getStringProperty(BROWSERTYPE,
			        IConfigurationConstants.IE_BROWSER));
		}
		return m_browserType;
	}

	public IIdentity getIdentity()
	{
		if( null == m_identity )
		{
			m_identity = new SimpleIdentity(getStringProperty(USERNAME,
			        ""), getStringProperty(PASSWORD, ""));
		}
		return m_identity;
	}

	public IServer getTargetMachine()
	{
		if( null == m_server )
		{
			String server = getStringProperty(HOSTNAME,
			        System.getenv("COMPUTERNAME")).toLowerCase();
			if( server.startsWith("localhost"))
			{
				server.replaceFirst("localhost", System.getenv("COMPUTERNAME"));
			}
			m_server = new Server(server,"");
		}
		return m_server;
	}
	
	public IServer getSeleniumRcMachine(){
		String rcserverName = getStringProperty(SELENIUMRCMACHINE, "COMPUTERNAME");
		if (rcserverName.contains(":"))
		{
			rcserverName = rcserverName.split(":")[0];
		}
		m_server.setRcServer(rcserverName);		
		return m_server;
	}

	public int getMaxRequestSizeInBytes()
	{
		return getIntProperty(REQUESTMAXSIZEINBYTES, 6553600, 0, 6553600);
	}

	public void setIdentity( IIdentity identity )
	{
		setProperty(USERNAME, identity.getUserName());
		setProperty(PASSWORD, identity.getPassword());
	}

	public boolean getAnonymous()
	{
		return getBooleanProperty(ANONYMOUS, false);
	}

	public void setStartUrl( String startUrl )
	{
		setProperty(START_URL, startUrl);
	}
	
	private synchronized void setProperty( String name, String value )
	{
		try
		{
			m_properties.setProperty(name, value);
			if( persistChanges() )
			{
				m_properties.store(new FileOutputStream(m_propertiesFile), "");
			}
		}
		catch( IOException e )
		{
			throw new RuntimeException(
			        String.format(
			                "Cannot write property %s (value=%s) to property file '%s'",
			                name, value, m_propertiesFile), e);
		}
	}

	public String getStartUrl()
	{
		return getStringProperty(START_URL,
		        "http://<default>/cordys/wcp/desktop.htm");
	}

	public int getTimeOut()
	{
		return getIntProperty(TIMEOUT, IConfigurationConstants.DEFAULT_TIMEOUT,
		        0, IConfigurationConstants.MAX_TIMEOUT);
	}

	public long getTimeOutInMiliseconds()
	{
		return getTimeOut() * 1000;
	}
	
	public int getIdentifier()
	{
		// if there is a properties file defined
		if (this.m_propertiesFile != null)
		{
			// take the hashCode of the properties file path as identifier
			return this.m_propertiesFile.hashCode();
		}
		else
		{
			// return 0 (same as "".hashCode())
			return 0;
		}
	}

	@Override
	public String getDetectScriptError() {
		return this.getStringProperty(DETECTSCRIPTERROR,"true").toLowerCase();	
	}
	
	@Override
	public String getFEPathFinder(){
		return this.getStringProperty(FEPATHFINDER, "false").toLowerCase();		
	}

	@Override
	public String getBEPathFinder(){
		return this.getStringProperty(BEPATHFINDER, "false").toLowerCase();		
	}
	
}

class BrowserType implements IBrowserType
{
	private final String m_name;

	BrowserType( String name )
	{
		m_name = name;
	}

	public String getName()
	{
		return m_name;
	}

	public boolean isInternetExplorer()
	{
		return IConfigurationConstants.IE_BROWSER.equalsIgnoreCase(getName());
	}

	/*public boolean isOpera()
	{
		return IConfigurationConstants.OPERA_BROWSER.equalsIgnoreCase(getName());
	}*/

	/*public boolean isSafari()
	{
		return IConfigurationConstants.SAFARI_BROWSER.equalsIgnoreCase(getName());
	}*/

	public boolean isMozilla()
	{
		return (IConfigurationConstants.MOZILLA_BROWSER.equalsIgnoreCase(getName()) || (!isInternetExplorer()));
	}
}

class Server implements IServer
{
	private final String m_name;
	
	private  String rc_m_name;

	/*Server( String name )
	{
		m_name = name;
	}*/
	
	Server (String name, String rcmname){
		m_name = name;
		rc_m_name= rcmname;
	}

	public String getName()
	{
		return m_name;
	}
	
	public void setRcServer(String serverName)
	{
		rc_m_name = serverName;
	}
	public String getRcServer(){
		return rc_m_name;
	}

}