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

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

public class BetterMouse extends Mouse
{
	private Component component;
	private int lastX;
	private int lastY;
	
	/**
   * The coordinate where the last event was fired.
   */
  private static Point s_last = new Point(0, 0);

  /**
   * The EventQueue on which all events are processed.
   */
  private EventQueue m_queue;

  /**
   * The popup that appeared in the previous call.
   */
  private boolean m_lastPopup = false;

  /** Last number of clicks. */
  private int m_lastClicks;

  /**
   * The mouse buttons that were pressed in the previous call.
   */
  private int m_lastMouseModifiers = 0;

  /**
   * The mouse buttons that were pressed in the previous call.
   */
  private long m_lastPressed = 0;
	
  
	public BetterMouse(Component component)
	{
		super();
		this.component = component;
		m_queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
	}
	
	/*private void doClickInRectangle( int xEnd, int yEnd,
                                        boolean useRightClick ) {
    int modifiers = useRightClick ? MouseEvent.BUTTON3_MASK : MouseEvent.BUTTON1_MASK;
    final int nbClicks = 1;
    final int x = xEnd;
    final int y = yEnd;
    this.component.dispatchEvent(new MouseEvent(this.component, MouseEvent.MOUSE_PRESSED, 1, modifiers, x, y, nbClicks, false));
    this.component.dispatchEvent(new MouseEvent(this.component, MouseEvent.MOUSE_RELEASED, 1, modifiers, x, y, nbClicks, useRightClick));
  }*/
	
	@Override
	public void click()
	{
    int modifiers = MouseEvent.BUTTON1_MASK;
    final int nbClicks = 1;
    //this.component.dispatchEvent(new MouseEvent(this.component, MouseEvent.MOUSE_PRESSED, 1, modifiers, x, y, nbClicks, false));
    //this.component.dispatchEvent(new MouseEvent(this.component, MouseEvent.MOUSE_RELEASED, 1, modifiers, x, y, nbClicks, false));
    
    this.mousePressed(this.component, modifiers, nbClicks, false);
    this.mouseReleased(this.component, modifiers, nbClicks, false);
    
	}

	@Override
	public void moveTo(int end, int end2)
	{
		lastX = end;
		lastY = end2;
		s_last = new Point(end, end2);
		super.moveTo(end, end2);
	}
	
	/**
   * Process a mouse move event on a component.
   *
   * @param   ultimate    The ultimate parent Component
   * @param   x           The x coordinate of the point where the mouse is being moved to.
   * @param   y           The y coordinate of the point where the mouse is being moved to.
   */
  protected void mouseMoved(final Component ultimate, final int x, final int y) {
      Point dest = new Point(x, y);
      dest.translate(-ultimate.getLocationOnScreen().x,
          -ultimate.getLocationOnScreen().y);

      int mouseEventType = MouseEvent.MOUSE_MOVED;

      if (m_lastPressed != 0) {
          mouseEventType = MouseEvent.MOUSE_DRAGGED;
      }

      while ((s_last.x != dest.x) || (s_last.y != dest.y)) {
          s_last = calcNextPoint(
                  s_last,
                  dest,
                  getStep());
          postEvent(
              new MouseEvent(
                  ultimate,
                  mouseEventType,
                  System.currentTimeMillis(),
                  m_lastMouseModifiers,
                  s_last.x,
                  s_last.y,
                  0,
                  m_lastPopup));
      }
  }
  
  /**
   * Calculate the direction of travel given the src
   * and dest point.
   * @param src Source
   * @param dest Destination
   * @return -1 if dest < src, 0 if dest = src, and 1 if dest > src
   */
  private static int getDir(final int src, final int dest) {
      if (src == dest) {
          return 0;
      } else if (src < dest) {
          return 1;
      } else {
          return -1;
      }
  }
  
  /**
   * Calculate the next point along the path to the
   * destingation point.
   *
   * @param src Source point.
   * @param dest Dest point.
   * @param step Number of pixels to step.
   * @return src point.
   */
  public static Point calcNextPoint(final Point src, final Point dest,
      final int step) {
      Point dir = new Point(
              getDir(src.x, dest.x),
              getDir(src.y, dest.y));

      if (isBounded(src.x + (dir.x * step), dest.x, src.x)) {
          dir.x = (dir.x * step);
      }

      if (isBounded(src.y + (dir.y * step), dest.y, src.y)) {
          dir.y = dir.y * step;
      }

      dir.translate(src.x, src.y);

      return dir;
  }
  
  /**
   * Check to see if the value is bounded by the min and max.
   * @param val Value to be checked.
   * @param vMin minimum value of range.
   * @param vMax maximum value of range.
   * @return true if the value is between vMin and vMax.
   */
  protected static boolean isBounded(final int val, final int vMin,
      final int vMax) {
      int min = Math.min(vMin, vMax);
      int max = Math.max(vMin, vMax);

      if ((min < val) && (val < max)) {
          return true;
      }

      return false;
  }

  private int getStep()
	{
		return 10;
	}

	/**
   * Process a mouse press event on a component.
   *
   * @param   ultimate          The ultimate parent Component
   * @param   modifiers         The modifiers associated with this mouse event.
   * @param   click             The number of clicks associated with this mouse event.
   * @param   isPopupTrigger    Whether this mouse event will generate a popup.
   */
  protected void mousePressed(final Component ultimate, final int modifiers,
      final int click, final boolean isPopupTrigger) {
      m_lastPressed            = System.currentTimeMillis();
      m_lastPopup              = isPopupTrigger;
      m_lastMouseModifiers     = modifiers;

      if (modifiers != 0) {
          postEvent(
              new MouseEvent(ultimate, MouseEvent.MOUSE_PRESSED,
                  m_lastPressed, modifiers, s_last.x, s_last.y, click,
                  isPopupTrigger));
      }
  }

  /**
   * Process a mouse release event on a component.
   *
   * @param   ultimate          The ultimate parent Component
   * @param   modifiers         The modifiers associated with this mouse event.
   * @param   click             The number of clicks associated with this mouse event.
   * @param   isPopupTrigger    Whether this mouse event will generate a popup.
   */
  protected void mouseReleased(final Component ultimate, final int modifiers,
      final int click, final boolean isPopupTrigger) {
      m_lastMouseModifiers     = modifiers;
      m_lastPopup              = isPopupTrigger;
      m_lastClicks             = click;

      if (modifiers != 0) {
          postEvent(
              new MouseEvent(
                  ultimate,
                  MouseEvent.MOUSE_RELEASED,
                  System.currentTimeMillis(),
                  modifiers,
                  s_last.x,
                  s_last.y,
                  click,
                  isPopupTrigger));

          long delta = m_lastPressed - System.currentTimeMillis();

          if ((click > 0) && (delta < 100)) {
              postEvent(
                  new MouseEvent(
                      ultimate,
                      MouseEvent.MOUSE_CLICKED,
                      System.currentTimeMillis(),
                      modifiers,
                      s_last.x,
                      s_last.y,
                      click,
                      isPopupTrigger));
          }
      }

      m_lastPressed = 0;
  }

	
	/**
   * This method is just present so as to put debug statements in one central place,
   * without repeating everywhere.
   *
   * @param evt    The event to be posted.
   */
  private void postEvent(final AWTEvent evt) {
      m_queue.postEvent(evt);
  }
  
	
	
	
}
