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
 
 package com.cordys.cm.uiunit.config.identity;

public class SimpleIdentity implements IIdentity
{
	private String userName;

	private String password;

	public SimpleIdentity(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}

	public String getPassword()
	{
		return this.password;
	}

	public String getUserName()
	{
		return this.userName;
	}
}
