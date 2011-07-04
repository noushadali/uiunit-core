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
 
 package com.cordys.cm.uiunit.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.cordys.cm.uiunit.elements.html.IHTMLElement;

public class Screendumper
{
	
	/**
	 * Create a Image of a HTMLElement
	 * @param aElement element to creat an image from
	 * @return Image of HTMLElement
	 */
	private static String imagesFolder = "images";
	static
	{
		File imgFolder = new File(imagesFolder);
		imgFolder.mkdirs();
	}
	
	public BufferedImage dumpScreenToImage(IHTMLElement aElement) {
		return dumpScreenToImage(aElement.getScreenRect());
	}
	
	/**
	 * Create an image of part of the screen
	 * @param dumpRect in screen coordinates
	 * @return BufferedImage
	 */
	public BufferedImage dumpScreenToImage(Rectangle dumpRect) {
		// get Image of browser
		try {
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(dumpRect);
			return image;
		} catch (Exception ex) {
			throw new RuntimeException("error creating Screendump :"+ex.getMessage(),ex);
		}
	}
	
	/**
	 * Create an image of the part on the screen that can hold both HTMLelements
	 * @param aElement
	 * @param aElement2
	 * @return an image
	 */
	public BufferedImage dumpScreenToImage(IHTMLElement aElement, IHTMLElement aElement2){
		return dumpScreenToImage(aElement.getScreenRect(),aElement2.getScreenRect());
	}

	/**
	 * Create an image of the part on the screen that can hold both Rectangles
	 * @param dumpRect
	 * @param dumpRect2
	 * @return an Image
	 */
	public BufferedImage dumpScreenToImage(Rectangle dumpRect,Rectangle dumpRect2){
		// get Image of browser, where both rectangles fit
		Rectangle r = new Rectangle(dumpRect);
		r.add(dumpRect2); // get new Rectangle where both rectangles fit
		return dumpScreenToImage(r);
	}
	
	public void savePageToFile(BufferedImage image, String ftype, String outFileName)  {

		try {
			File newFile = new File(imagesFolder+File.separator+outFileName + "." + ftype);
			
			Logger.getLogger(Screendumper.class.getName()).info("Screenshot taken and stored at:" + newFile.getAbsolutePath());
			ImageIO.write(image, ftype, newFile);
		} catch (Exception ex) {
			throw new RuntimeException("Error saving Image to file :"+ex.getMessage(),ex);
		}
	}
	public void savePageToPngFile(BufferedImage im, String outFileName) {
		savePageToFile(im, "png", outFileName);
	}
	
	public void savePageToBmpFile(BufferedImage im, String outFileName) {
		savePageToFile(im, "bmp", outFileName);
	}


}
