package com.cordys.uiunit.eastwind;

import java.io.IOException;
import java.util.Properties;

public class Util {
	private static final String EASTWIND_PROPERTY_FILE = "eastwind.properties";
	public static String getEastwindProperty(String property){
		Properties properties = new Properties();
		try {
			properties.load(Util.class.getResourceAsStream((EASTWIND_PROPERTY_FILE)));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(property);
	}
}
