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
 
 package com.cordys.cm.uiunit.framework;

import com.cordys.cm.uiunit.elements.IElement;

public abstract class BaseContextImpl implements IHTMLContext, IElement {

	
	private IHTMLContext context; 
	
	public void _setContext(IHTMLContext context)
	{
		this.context = context;
	}
	
	public IHTMLContext getContext()
	{
		return context;
	}

	@Override
	public String executeJavascript(String javascript) {
		return this.getContext().executeJavascript(javascript);
	}

	@Override
	public void waitForIdle() {
		this.getContext().waitForIdle();
	}

	@Override
	public long waitForIdle(int waitTime) {
		return this.getContext().waitForIdle(waitTime);
	}
	
	@Override
	public IRootHTMLContext getRootContext() {
		return this.getContext().getRootContext();
	}

}
