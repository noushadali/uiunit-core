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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import junit.framework.Assert;

import com.cordys.cm.uiunit.exceptions.UIUnitRuntimeException;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.XMLException;

/**
 * Resource loader specific for SOAPUnit.
 */
public class ResourceLoader
{
	private static final String NEWLINE = System.getProperty("line.separator");
	
    public static int loadXML(String resourceName, Class<?> baseClass, 
            String baseDirName, String baseFileName, Document document) throws Exception
    {
        String baseClassName = null;
        if (baseClass != null)
        {
            baseClassName = baseClass.getName();
        }
        
        String absoluteResourcePath = ResourceLoader.getAbsoluteResourcePath(
                resourceName, baseClassName, baseDirName, baseFileName);
        if (absoluteResourcePath == null)
        {
            if (baseClassName != null)
            {
                Assert.fail("'" + resourceName + "' does not exist as resource for Class : " + baseClassName + " or as file");
            }
            else
            {
                Assert.fail("'" + resourceName + "' does not exist as resource or as file");
            }
        }

        return ResourceLoader.loadXML(absoluteResourcePath, document);
    }
    
    public static String getAbsoluteResourcePath(String resourceName, 
            String baseClassName, String baseDirName, String baseFileName)
    {
        resourceName = resourceName.replace('\\', '/');
        InputStream is = null;
        
        /* 1. Load resource with absolute resource path. */
        if (resourceName.startsWith("/"))
        {                        
            is = performResourceInputStreamCreation(resourceName);
            if (is != null)
            {                
                performSafeInputStreamClose(is);
                return resourceName;
            }
        }
        /* 2. Load resource relative to another resource file. */
        else if (baseFileName != null && baseFileName.startsWith("/"))
        {
            String resourcePath = getAbsolutePathRelativeToFile(resourceName, baseFileName);
            is = performResourceInputStreamCreation(resourcePath);
            if (is != null)
            {
                performSafeInputStreamClose(is);
                return resourcePath;
            }
        }
        /* 3. Load resource relative to a java class. */
        else if (baseClassName != null)
        {
            String resourcePath = getAbsolutePathRelativeToClass(resourceName, baseClassName);
            is = performResourceInputStreamCreation(resourcePath);
            if (is != null)
            {
                performSafeInputStreamClose(is);
                return resourcePath;
            }
        }
        
        // 4. Load file with absolute file path
        is = performFileInputStreamCreation(resourceName);
        if (is != null)
        {
            performSafeInputStreamClose(is);
            return resourceName.replace('\\', '/');
        }
        
        if (!resourceName.startsWith("/"))
        {
            // 5. Load file wrt to base file
            if (baseFileName != null)
            {
                String resourcePath = getAbsolutePathRelativeToFile(resourceName, baseFileName);
                is = performFileInputStreamCreation(resourcePath);
                if (is != null)
                {
                    performSafeInputStreamClose(is);
                    return resourcePath.replace('\\', '/');
                } 
            }

            // 6. Load file wrt to base directory
            if (baseDirName != null && baseDirName.trim().length() > 1)
            {
                String resourcePath = baseDirName + "/" + resourceName;
                is = performFileInputStreamCreation(resourcePath);
                if (is != null)
                {
                    performSafeInputStreamClose(is);
                    return resourcePath.replace('\\', '/');
                }
            }
        }
        
        return null;
    }    
    
    public static int loadXML(String absoluteResourcePath, Document document) throws Exception
    {
        absoluteResourcePath = absoluteResourcePath.replace('\\', '/');
        InputStream is = performResourceInputStreamCreation(absoluteResourcePath);        
        if (is == null)
        {
            try 
            {
                is = new FileInputStream(absoluteResourcePath);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("FileNotFoundException: " + e.getMessage());
                throw e;
            }
        }        

        byte[] content = new byte[4 * 1024 * 1024]; //4Mb
        int length = new BufferedInputStream(is).read(content);
        performSafeInputStreamClose(is);

        try
        {
            return document.load(content, length);
        }
        catch (XMLException e)
        {
            System.out.println("CorruptXML: " + new String(content, 0, length));
            throw e;
        }
    }
    
	public static String loadText(String absoluteResourcePath) throws FileNotFoundException
	{
		absoluteResourcePath = absoluteResourcePath.replace('\\', '/');
		InputStream is = performResourceInputStreamCreation(absoluteResourcePath);
		if (is == null)
		{
			try
			{
				is = new FileInputStream(absoluteResourcePath);
			}
			catch (FileNotFoundException e)
			{
				System.out.println("FileNotFoundException: " + e.getMessage());
				throw e;
			}
		}

		BufferedReader d = new BufferedReader(new InputStreamReader(is));
		String result = "";

		String nextLine = "";
		try
		{
			while ((nextLine = d.readLine()) != null)
			{
				result += nextLine + NEWLINE;

			}
			return result;
		}
		catch (IOException e)
		{
			throw new UIUnitRuntimeException(e);
		}
		finally
		{
			performSafeInputStreamClose(is);
		}
	}
    
    static String getAbsolutePathRelativeToFile(String relResourceName, String fileName)
    {
        fileName = fileName.replace('\\', '/');
        int lastSlashIndex = fileName.lastIndexOf('/');
        if (lastSlashIndex > -1)
        {
            return fileName.substring(0, lastSlashIndex) + "/" + relResourceName;
        }
        return relResourceName;
    }
    
    /**
     * Internal function for SOAPUnit.
     */
    static String getAbsolutePathRelativeToClass(String relResourceName, String className)
    {
        String packageName = getJavaPackageName(className);
        if ("".equals(packageName))
        {
            return "/" + relResourceName;
        }
        
        String packagePath = packageName.replaceAll("\\.", "/");
        return "/" + packagePath + "/" + relResourceName;
    }
    
    static String getJavaPackageName(String className)
    {
        int lastDotIndex = className.lastIndexOf(".");
        if (lastDotIndex != -1)
        {            
            return className.substring(0, lastDotIndex);
        }
        return "";
    }
    
    static InputStream performFileInputStreamCreation(String resourceName)
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream(resourceName);
        }
        catch (FileNotFoundException e)
        {            
        }        
        return is;
    }
    
    static InputStream performResourceInputStreamCreation(String resourceName)
    {
        InputStream is = ResourceLoader.class.getResourceAsStream(resourceName);
        if (is == null)
        {
            is = ClassLoader.getSystemResourceAsStream(resourceName);
        }
        return is;
    }
    
    static void performSafeInputStreamClose(InputStream is)
    {
        try
        {
            is.close();
        }
        catch (IOException e)
        {           
        }
    }
    
    static void performSafeOutputStreamClose(OutputStream os)
    {
        try
        {
            os.close();
        }
        catch (IOException e)
        {           
        }
    }
}
