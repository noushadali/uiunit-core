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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.DefaultSelenium;

public class ParallelSeleniumEnvironment// implements Selenium
{

	List<DefaultSelenium> seleniumEnvs ;//= new ArrayList<DefaultSelenium>();
	final GetActiveRCs activeRcs;
	String url;
	String serverHost;
	int serverPort;
	
	ParallelSeleniumEnvironment(String serverHost,int serverPort,String url)
	{
		this.activeRcs = new GetActiveRCs(serverHost,serverPort);
		this.seleniumEnvs = new ArrayList<DefaultSelenium>();
		this.url = url;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		createSeleniumObj();
	}
	
	/*public Selenium getSeleniumObject(String serverHost, int serverPort, String url)
	{
		//get object based on paraemeters
		//if not existts create 
		//and return
	}*/
	
	private void createSeleniumObj()
	{
		String[] activeRCEnvs = this.activeRcs.execute();
		
		if(activeRCEnvs != null)
		{
			for(String activeRCEnv : activeRCEnvs)
			{
				DefaultSelenium selenium = new DefaultSelenium(this.serverHost,this.serverPort,activeRCEnv,this.url);
				selenium.start();
//				setBrowserLogLevel("error");
				seleniumEnvs.add(selenium);
			}
		}
	}
	
//	@Override
//	public void addLocationStrategy(String arg0, String arg1) {
//		
//		
//	}
//
//	@Override
//	public void addScript(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addSelection(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void allowNativeXpath(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void altKeyDown() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void altKeyUp() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void answerOnNextPrompt(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void assignId(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void attachFile(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void captureEntirePageScreenshot(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String captureEntirePageScreenshotToString(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String captureNetworkTraffic(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void captureScreenshot(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String captureScreenshotToString() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void check(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void chooseCancelOnNextConfirmation() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void chooseOkOnNextConfirmation() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void click(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void clickAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void close() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void contextMenu(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void contextMenuAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void controlKeyDown() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void controlKeyUp() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void createCookie(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteAllVisibleCookies() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteCookie(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deselectPopUp() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void doubleClick(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void doubleClickAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dragAndDrop(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dragAndDropToObject(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dragdrop(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void fireEvent(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void focus(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getAlert() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAllButtons() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAllFields() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAllLinks() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAllWindowIds() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAllWindowNames() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAllWindowTitles() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getAttribute(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getAttributeFromAllWindows(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getBodyText() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getConfirmation() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getCookie() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getCookieByName(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getCursorPosition(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getElementHeight(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getElementIndex(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getElementPositionLeft(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getElementPositionTop(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getElementWidth(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getEval(String arg0) {
//		Iterator<DefaultSelenium> itr = seleniumEnvs.iterator();
//		while(itr.hasNext())
//		{
//			Selenium selenium = itr.next();
//			selenium.getEval(arg0);
//		}
//		return null;
//	}
//
//	@Override
//	public String getExpression(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getHtmlSource() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getLocation() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Number getMouseSpeed() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getPrompt() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getSelectOptions(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSelectedId(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getSelectedIds(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSelectedIndex(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getSelectedIndexes(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSelectedLabel(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getSelectedLabels(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSelectedValue(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String[] getSelectedValues(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getSpeed() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getTable(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getText(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getTitle() {		
//		Iterator<DefaultSelenium> itr = seleniumEnvs.iterator();
//		while(itr.hasNext())
//		{
//			Selenium selenium = itr.next();
//			selenium.getTitle();
//		}
//		return null;
//	}
//
//	@Override
//	public String getValue(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean getWhetherThisFrameMatchFrameExpression(String arg0,
//			String arg1) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean getWhetherThisWindowMatchWindowExpression(String arg0,
//			String arg1) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Number getXpathCount(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void goBack() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void highlight(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void ignoreAttributesWithoutValue(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean isAlertPresent() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isChecked(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isConfirmationPresent() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isCookiePresent(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isEditable(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isElementPresent(String arg0) {
//		Iterator<DefaultSelenium> itr = seleniumEnvs.iterator();
//		while(itr.hasNext())
//		{
//			Selenium selenium = itr.next();
//			selenium.isElementPresent(arg0);
//		}
//		return false;
//	}
//
//	@Override
//	public boolean isOrdered(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isPromptPresent() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isSomethingSelected(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isTextPresent(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isVisible(String arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void keyDown(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyDownNative(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyPress(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyPressNative(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyUp(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyUpNative(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void metaKeyDown() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void metaKeyUp() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseDown(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseDownAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseDownRight(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseDownRightAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseMove(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseMoveAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseOut(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseOver(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseUp(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseUpAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseUpRight(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseUpRightAt(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void open(String arg0) {
//		
//		Iterator<DefaultSelenium> itr = seleniumEnvs.iterator();
//		while(itr.hasNext())
//		{
//			Selenium selenium = itr.next();
//			selenium.open(arg0);
//		}
//	}
//
//	@Override
//	public void openWindow(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void refresh() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeAllSelections(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeScript(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeSelection(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String retrieveLastRemoteControlLogs() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void rollup(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void runScript(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void select(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void selectFrame(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void selectPopUp(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void selectWindow(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setBrowserLogLevel(String arg0) {
//		Iterator<DefaultSelenium> itr = seleniumEnvs.iterator();
//		while(itr.hasNext())
//		{
//			Selenium selenium = itr.next();
//			selenium.setBrowserLogLevel(arg0);
//		}
//		
//	}
//
//	@Override
//	public void setContext(String arg0) {
//		Iterator<DefaultSelenium> itr = seleniumEnvs.iterator();
//		while(itr.hasNext())
//		{
//			Selenium selenium = itr.next();
//			selenium.setContext(arg0);
//		}
//		
//	}
//
//	@Override
//	public void setCursorPosition(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setExtensionJs(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setMouseSpeed(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setSpeed(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setTimeout(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void shiftKeyDown() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void shiftKeyUp() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void showContextualBanner() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void showContextualBanner(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void shutDownSeleniumServer() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void start(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void start(Object arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void submit(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void type(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void typeKeys(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void uncheck(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void useXpathLibrary(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void waitForCondition(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void waitForFrameToLoad(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void waitForPageToLoad(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void waitForPopUp(String arg0, String arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void windowFocus() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void windowMaximize() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addCustomRequestHeader(String key, String value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getLog() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void open(String url, String ignoreResponseCode) {
//		// TODO Auto-generated method stub
//		
//	}

}
