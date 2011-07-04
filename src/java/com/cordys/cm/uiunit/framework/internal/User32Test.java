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
 
 package com.cordys.cm.uiunit.framework.internal;

public class User32Test
{
	

  	static {
  	try{
  	System.loadLibrary("User32");
  	} catch(Exception e)
  	{
  	e.printStackTrace();
  	}
  	}
  	public static void main(String args[])
  	{
  		User32Test t1=new User32Test();
  		t1.MessageBeep(4);
  	}
  	
  	public native int MessageBoxA(int i,String a,String b,String c,int j);
  	public native int MessageBeep(int i);

}
