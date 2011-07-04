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
 
 package com.cordys.cm.uiunit.framework.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;

import javax.swing.JFrame;

public class SimpleGui extends OldGUI implements IGui {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7562939751636482308L;

	public SimpleGui()
	{
		super();
		
		Image icon= loadFrameIcon();
		if (icon != null)
			this.setIconImage(icon);
		
		this.setTitle("Application Test Framework");
		
		//get the screen size and set height and width to be slightly less than that
	
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenDimension.height-25;
		int width = screenDimension.width-25;
		
		//compare it with the size that we want to set and decide if it is too big
		height = height<780 ? height : 780;
		width = width<940 ? width : 940;
		this.setBounds(0, 0, width, height);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	@Override
	public void destroy() {
		this.destroy();
	}

	@Override
	public void setBrowser(Canvas browserComponent) {
		Container contain = this.getContentPane();
		contain.add(BorderLayout.CENTER, browserComponent);
		contain.repaint();
	}
	
	@Override
	public Container getContainer()
	{
		return this.getContentPane();
	}

	@Override
	public void setBrowserStatusMessage(String message) {
		if (message == null)
			return;
		this.getJStatusField().setText(message);
	}

	@Override
	public void setNumberOfConnection(int numberOfConnections) {
		this.getJnumConnectionBar().setValue(numberOfConnections);
		this.getJnumConnectionBar().setString(Integer.toString(numberOfConnections));
	}

	@Override
	public void setTestStatusMessage(String message) {
		if (message == null)
			return;
		this.getJTestStatusField().setText(message);
	}

	@Override
	public void setTimer(int timeToGo) {
		// TODO Auto-generated method stub

	}
	
	private Image loadFrameIcon() {
		Toolkit toolkit= Toolkit.getDefaultToolkit();
		try {
			java.net.URL url= SimpleGui.class.getResource("uiunit.png");
			return toolkit.createImage((ImageProducer) url.getContent());
		} catch (Exception ex) {
		}
		return null;
	}

}
