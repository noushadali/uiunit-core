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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;


public class GetActiveRCs {
	
	String rcServerLocation;
	String pathToServlet;
	
	GetActiveRCs(String serverHost,int serverPort)
	{
		this.rcServerLocation = serverHost + ":" + Integer.toString(serverPort);
		this.pathToServlet = "http://" + this.rcServerLocation + "/getactiverc";
	}
	
	 //private final String targetURL="http://localhost:4444/getactiverc";
	
	 public String[] execute(){
	        HttpClient client = new HttpClient();
	        GetMethod getMethod = new GetMethod(this.pathToServlet);
	        try{
		        try {
					if(client.executeMethod(getMethod)==HttpStatus.SC_OK)
					{
					    InputStream responseBody = getMethod.getResponseBodyAsStream();
						String response = convertStreamToString(responseBody);
						if(!response.equals(""))
						{
							//response= response.substring(1, response.length()-1);
							String[] activeRcs = response.split("\\,");
							System.out.println(response + " "+ activeRcs.length);
							return activeRcs;						
						}
					}
				} catch (HttpException e) {
					throw new UIUnitRuntimeException(e);
				} catch (IOException e) {
					throw new UIUnitRuntimeException(e);
				}
	    	} finally {
	    		getMethod.releaseConnection();
			}
	        return null;
	    }
	 
	 private String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				throw new UIUnitRuntimeException(e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					throw new UIUnitRuntimeException(e);
				}
			}
			return sb.toString();
		}

	  /*  public String targetURL() {
	        return targetURL;
	    }*/

	  /*  public static void main(String args[])
	    {
	    	GetActiveRC obj = new GetActiveRC("localhost",4444);
	    	try {
				obj.execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }*/
}