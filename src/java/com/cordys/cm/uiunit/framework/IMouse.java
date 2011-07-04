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

public interface IMouse
{

	public enum Speed
	{
		FAST(1), MEDIUM(2), SLOW(6), VERYSLOW(25);

		private final int delay;

		Speed(int delay)
		{
			this.delay = delay;
		}

		public int getDelay()
		{
			return delay;
		}
	}

	/**
	 * Method to do the click with the left mouse button
	 */
	public void click();

	/**
	 * Method to do the double click with the left mouse button
	 */
	public void doubleClick();

	/**
	 * Method to do the double click with the right mouse button
	 */
	public void rightClick();

	/**
	 * Method to click the left mouse button and hold it. 
	 * NOTE: you have to release it again, use releaseLeftButton to do that 
	 * @see releaseLeftButton
	 */
	public void pressLeftButton();

	/**
	 * Method to release the left mouse button. 
	 * @see pressLeftButton
	 */
	public void releaseLeftButton();

	/**
	 * Move the mouse to a new location
	 * @param xEnd End coordinate X
	 * @param yEnd End coordinate Y
	 */
	public void moveTo(int xEnd, int yEnd);

	/**
	 * Set the speed of the mouse movement
	 * @param speed new speed of the mouse
	 */
	public void setSpeed(Speed speed);

	/**
	 * Get the current mouse speed
	 * @return the speedsetting of the mouse
	 */
	public Speed getSpeed();

	public void moveRelative(int x, int y);
	/**
	 * Rotates the scroll wheel on wheel-equipped mice. 
	 * @param number of "notches" to move the mouse wheel Negative values indicate movement up/away from the user, positive values indicate movement down/towards the user.
	 */
	public void mouseWheel(int wheelAmt);

}
