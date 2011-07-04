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


import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import static java.awt.event.KeyEvent.VK_BACK_SLASH;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_EQUALS;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_PERIOD;
import static java.awt.event.KeyEvent.VK_QUOTE;
import static java.awt.event.KeyEvent.VK_SEMICOLON;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;

import com.cordys.cm.uiunit.framework.IKeyboard;
import com.cordys.cm.uiunit.framework.selenium.ISeleniumEnvironment;

public class Keyboard implements IKeyboard
{
	
	private ISeleniumEnvironment environment;

	@Deprecated
	public Keyboard()
	{
	}
	
	public Keyboard(ISeleniumEnvironment environment)
	{
		this.environment = environment;
	}
	public void keyPress(int keyCode)
	{
		this.environment.getSeleniumObject().keyDownNative(String.valueOf(keyCode));
		
	}
	public void keyRelease(int keyCode)
	{
		this.environment.getSeleniumObject().keyUpNative(String.valueOf(keyCode));
	}
	public void keyPressAndRelease(int keyCode)
	{
		//this.keyPress(keyCode);
		this.environment.getSeleniumObject().keyPressNative(String.valueOf(keyCode));
	}
	
	/**
	 * Method to key in character
	 * special characters {} dont work
	 * @param p_char
	 */
	public void typeKey(char p_char)
	{
		int iCode = 0;
		if (p_char >= '0' && p_char <= '9')
		{
			iCode = Character.getNumericValue(p_char) + 48;
			keyPressAndRelease(iCode);
		}
		else if (p_char >= 'a' && p_char <= 'z')
		{
			iCode = Character.getNumericValue(p_char) + 55;
			keyPressAndRelease(iCode);
		}
		else if (p_char >= 'A' && p_char <= 'Z')
		{
			iCode = Character.getNumericValue(p_char) + 55;
			shiftPress(iCode);
		}
		else
		{
			switch(p_char)
			{
				case '-':
					keyPressAndRelease(VK_MINUS);
					break;
				case ',':
					keyPressAndRelease(VK_COMMA);
					break;
				case '/':
					keyPressAndRelease(VK_SLASH);
					break;
				case '.':
					keyPressAndRelease(VK_PERIOD);
					break;
				case '!':
					iCode = Character.getNumericValue('1') + 48;
					shiftPress(iCode);
					break;
				case '#':
					iCode = Character.getNumericValue('3') + 48;
					shiftPress(iCode);
					break;
				case '$':
					iCode = Character.getNumericValue('4') + 48;
					shiftPress(iCode);
					break;
				case '%':
					iCode = Character.getNumericValue('5') + 48;
					shiftPress(iCode);
					break;
				case '&':
					iCode = Character.getNumericValue('7') + 48;
					shiftPress(iCode);
					break;
				case '(':
					iCode = Character.getNumericValue('9') + 48;
					shiftPress(iCode);
					break;
				case ')':
					iCode = Character.getNumericValue('0') + 48;
					shiftPress(iCode);
					break;
				case '*':
					iCode = Character.getNumericValue('8') + 48;
					shiftPress(iCode);
					break;
				case ':':
					shiftPress(VK_SEMICOLON);
					break;
				case '<':
					shiftPress(VK_COMMA);
					break;
				case '>':
					shiftPress(VK_PERIOD);
					break;
				case '?':
					shiftPress(VK_SLASH);
					break;
				case '@':
					iCode = Character.getNumericValue('2') + 48;
					shiftPress(iCode);
					break;
				case '^':
					iCode = Character.getNumericValue('6') + 48;
					shiftPress(iCode);
					break;
				case '_':
					shiftPress(VK_MINUS);
					break;
				case '|':
					shiftPress(VK_BACK_SLASH);
					break;
				case '~':
					shiftPress(VK_BACK_QUOTE);
					break;
				case '`':
					keyPressAndRelease(VK_BACK_QUOTE);
					break;
				case '\'':
					keyPressAndRelease(VK_QUOTE);
					break;
				case '"':
					shiftPress(VK_QUOTE);
					break;
				case '+':
					shiftPress(VK_EQUALS);
					break;
				case '{':
					// TODO: fix
					// Robot dies when trying to type a '{' (VK_BRACELEFT, KeyCode: 161)
					// dirty fix: type shift+[
					// note: this only works on US Keyboard Layout. if the '{' is placed somewhere else it won't work.
					shiftPress('[');
					break;
				case '}':
					// TODO: fix
					// Robot dies when trying to type a '}' (VK_BRACERIGHT, KeyCode: 162)
					// dirty fix: type shift+]
					// note: this only works on US Keyboard Layout. if the '}' is placed somewhere else it won't work.
					shiftPress(']');
					break;
				default : 
					iCode = Character.toString(p_char).hashCode();
					keyPressAndRelease(iCode);
			}
		}

	}
	
	public void typeString(String text)
	{
		for (char c : text.toCharArray())
		{
			this.typeKey(c);
		}
	}
	
	/**
	 * Shift press + key + shift release
	 * @param keyCode
	 */
	public void shiftPress(int keyCode)
	{
		this.environment.getSeleniumObject().keyDownNative(String.valueOf(VK_SHIFT));
		keyPressAndRelease(keyCode);
		this.environment.getSeleniumObject().keyUpNative(String.valueOf(VK_SHIFT));
	}
	
	/**
	 * Ctrl press + key + Ctrl release
	 * @param keyCode
	 */
	public void ctrlPress(char key)
	{
		this.environment.getSeleniumObject().keyDownNative(String.valueOf(VK_CONTROL));
		typeKey(key);
		this.environment.getSeleniumObject().keyUpNative(String.valueOf(VK_CONTROL));
	}
	
	public void ctrlPress(int keyCode)
	{
		this.environment.getSeleniumObject().keyDownNative(String.valueOf(VK_CONTROL));
		keyPressAndRelease(keyCode);
		this.environment.getSeleniumObject().keyUpNative(String.valueOf(VK_CONTROL));
	}
	
	@Override
	public void controlKeyDown() {
		String browserType = this.environment.getConfiguration().getBrowserType().getName().toUpperCase();
		if( browserType.startsWith("SAFARI") || browserType.startsWith("IE"))
			this.environment.getSeleniumObject().keyDownNative(String.valueOf(VK_CONTROL));
		else
			this.environment.getSeleniumObject().controlKeyDown();

	}

	@Override
	public void controlKeyUp() {
		String browserType = this.environment.getConfiguration().getBrowserType().getName().toUpperCase();
		
		if( browserType.startsWith("SAFARI") || browserType.startsWith("IE"))
			this.environment.getSeleniumObject().keyUpNative(String.valueOf(VK_CONTROL));
		else
			this.environment.getSeleniumObject().controlKeyUp();
	}
}
