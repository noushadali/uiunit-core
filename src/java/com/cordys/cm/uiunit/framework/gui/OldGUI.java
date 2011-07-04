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

/**
 * Copyright 2005 Cordys, derived from Xebic source and McGinkel private archive
 * Created on Nov 3, 2005
 * @author kvginkel
 *
 * This class implements an frame that will hold the embedde browser
 * - frame with 2 status bars
 * - frame with 2 progress bars
 * 
 * All intelligence is in the derived class TestBrowser. 
 * Use this file only to change visual appearance
 * 
 * Class created with eclipse layout manager
 * 
 */


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OldGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8261958238340466599L;
	private JPanel jContentPane = null;
	private JPanel control = null;
	private JPanel StatusPanel = null;
	private JProgressBar jTimeoutBar = null;
	private JTextField jStatusField = null;
	private JTextField jTestStatusField = null;
	private JTextArea jTextArea = null;
	private JProgressBar jnumConnectionBar = null;
	/**
	 * This is the default constructor
	 */
	public OldGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addComponentListener(resizeListener);
	}

	private ComponentListener resizeListener = new ComponentListener() {
		public void componentHidden( java.awt.event.ComponentEvent i_e ) { }
		public void componentMoved( ComponentEvent i_e ) { }
		public void componentResized( ComponentEvent i_e ) {}
	    public void componentShown( ComponentEvent i_e ) { }
	};
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getControl(), java.awt.BorderLayout.EAST);
			jContentPane.add(getStatusPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes control	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControl() {
		if (control == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			control = new JPanel();
			control.setLayout(flowLayout);
			control.add(getJTextArea(), null);
			control.add(getJnumConnectionBar(), null);
			control.add(getJTimeoutBar(), null);
		}
		return control;
	}

	/**
	 * This method initializes StatusPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStatusPanel() {
		if (StatusPanel == null) {
			StatusPanel = new JPanel();
			StatusPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,5));
			StatusPanel.add(getJStatusField(), null);
			StatusPanel.add(getJTestStatusField(), null);
		}
		return StatusPanel;
	}

	/**
	 * This method initializes jTimeoutBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	protected JProgressBar getJTimeoutBar() {
		if (jTimeoutBar == null) {
			jTimeoutBar = new JProgressBar();
			jTimeoutBar.setPreferredSize(new java.awt.Dimension(14,200));
			jTimeoutBar.setName("TimeoutStatus");
			jTimeoutBar.setToolTipText("Time left");
			jTimeoutBar.setOrientation(javax.swing.JProgressBar.VERTICAL);
			jTimeoutBar.setForeground(java.awt.Color.yellow);
			jTimeoutBar.setValue(5);
			jTimeoutBar.setStringPainted(true);
		}
		return jTimeoutBar;
	}

	/**
	 * This method initializes jStatusField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJStatusField() {
		if (jStatusField == null) {
			jStatusField = new JTextField();
			jStatusField.setEditable(false);
			jStatusField.setPreferredSize(new java.awt.Dimension(400,18));
			jStatusField.setHorizontalAlignment(javax.swing.JTextField.LEADING);
			jStatusField.setText("Browser Status");
			jStatusField.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}
		return jStatusField;
	}

	/**
	 * This method initializes jTestStatusField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTestStatusField() {
		if (jTestStatusField == null) {
			jTestStatusField = new JTextField();
			jTestStatusField.setPreferredSize(new java.awt.Dimension(400,20));
			jTestStatusField.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jTestStatusField.setText("Test Status");
			jTestStatusField.setEditable(false);
		}
		return jTestStatusField;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	protected JProgressBar getJnumConnectionBar() {
		if (jnumConnectionBar == null) {
			jnumConnectionBar = new JProgressBar();
			jnumConnectionBar.setMaximum(5);
			jnumConnectionBar.setOrientation(javax.swing.JProgressBar.VERTICAL);
			jnumConnectionBar.setPreferredSize(new java.awt.Dimension(14,200));
			jnumConnectionBar.setStringPainted(true);
			jnumConnectionBar.setToolTipText("Number of active backend connections");
			jnumConnectionBar.setForeground(java.awt.Color.green);
			jnumConnectionBar.setString("0");
			jnumConnectionBar.setName("numConnection");
		}
		return jnumConnectionBar;
	}

}  //  @jve:decl-index=0:visual-constraint="20,17"

