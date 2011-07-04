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

import java.awt.event.KeyEvent;

public interface IKeyboard {
	
	/*** Key event constants ***/
	public static final int KEY_SHIFT = KeyEvent.VK_SHIFT;
	public static final int KEY_CONTROL = KeyEvent.VK_CONTROL;
	public static final int KEY_ALT = KeyEvent.VK_ALT;
	public static final int KEY_ALTGR = KeyEvent.VK_ALT_GRAPH;
	public static final int KEY_INSERT = KeyEvent.VK_INSERT;
	public static final int KEY_HOME = KeyEvent.VK_HOME;
	public static final int KEY_PAGEUP = KeyEvent.VK_PAGE_UP;
	public static final int KEY_PAGEDOWN = KeyEvent.VK_PAGE_DOWN;
	public static final int KEY_END = KeyEvent.VK_END;
	public static final int KEY_DELETE = KeyEvent.VK_DELETE;
	public static final int KEY_BACKSPACE = KeyEvent.VK_BACK_SPACE;
	public static final int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
	public static final int KEY_ENTER = KeyEvent.VK_ENTER;
	public static final int KEY_TAB = KeyEvent.VK_TAB;
	public static final int KEY_CAPSLOCK = KeyEvent.VK_CAPS_LOCK;
	
	//arrow keys
	public static final int KEY_LEFTARROW = KeyEvent.VK_KP_LEFT;
	public static final int KEY_RIGHTARROW = KeyEvent.VK_KP_RIGHT;
	public static final int KEY_DOWNARROW = KeyEvent.VK_KP_DOWN;
	public static final int KEY_UPARROW = KeyEvent.VK_KP_UP;
	
	//function keys
	public static final int KEY_F1 = KeyEvent.VK_F1;
	public static final int KEY_F2 = KeyEvent.VK_F2;
	public static final int KEY_F3 = KeyEvent.VK_F3;
	public static final int KEY_F4 = KeyEvent.VK_F4;
	public static final int KEY_F5 = KeyEvent.VK_F5;
	public static final int KEY_F6 = KeyEvent.VK_F6;
	public static final int KEY_F7 = KeyEvent.VK_F7;
	public static final int KEY_F8 = KeyEvent.VK_F8;
	public static final int KEY_F9 = KeyEvent.VK_F9;
	public static final int KEY_F10 = KeyEvent.VK_F10;
	public static final int KEY_F11 = KeyEvent.VK_F11;
	public static final int KEY_F12 = KeyEvent.VK_F12;

	public void keyPress(int keyCode);

	public void keyRelease(int keyCode);
	
	public void keyPressAndRelease(int keyCode);

	/**
	 * Method to key in character
	 * special characters {} dont work
	 * @param p_char
	 */
	public void typeKey(char p_char);
	
	/**
	 * Method to type a string of characters
	 * @param string text
	 */
	public void typeString(String text);
	
	/**
	 * Shift press + key + shift release
	 * @param keyCode
	 */
	public void shiftPress(int keyCode);
	
	/**
	 * Ctrl press + key + Ctrl release
	 * @param keyCode
	 */
	public void ctrlPress(char key);
	
	/**
	 * CTRL press + key (identified by key code) + CTRL release
	 * @param keyCode
	 */
	public void ctrlPress(int keyCode);
	
	public void controlKeyDown();
	
	public void controlKeyUp();

}
