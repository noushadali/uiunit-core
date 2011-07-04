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
 
 package com.cordys.cm.uiunit.exceptions;

import com.cordys.cm.uiunit.message.Messages;

public class NoContextSetException extends UIUnitException
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7145841002449042740L;

	public NoContextSetException()
	{
		super(Messages.NO_CONTEXT_SET);
	}

}
