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

import java.util.EventListener;

public interface IBrowser {
	
	/**
	 * Use this method to execute javascript in the browser. The answer will be returned as string value.
	 * @param javascript
	 * @return
	 */
	public String executeJavascript(String javascript);
	
	
	/**
	 * Get the startUrl defined for the browser
	 * @return the startUrl
	 */
	public String getStartUrl();
	
	/**
	 * This method returns the title bar height of the browser. 
	 * This is required to calculate the offset of the element w.r.t desktop. This way of offset calculation is required to use Robot. 
	 * @return Browser title bar height
	 */
	public int getTitleBarHeight(String browserType);
	
	/**
	 * This method will cause the browser to reload with the given URL. This URL will be the new startUrl
	 * @param url
	 */
	public void loadURL(String url);
	
	/**
	 * Based on the current startURL the browser will be reloaded.
	 */
	public void refresh();
	
	
	//TODO extend this info
	/**
	 * You can add custom listeners to the browser. 
	 * See com.webrenderer.event.* to see the listener which can be registered.
	 * For some event there are already some helper methods like for prompts, application etc.
	 * @param listener
	 */
	public void addEventListener(EventListener listener);
	
	/**
	 * Remove the added listener. See addEventListener for more information
	 * @see addEventListener
	 * @param listener
	 */
	public void removeEventListener(EventListener listener);

}
