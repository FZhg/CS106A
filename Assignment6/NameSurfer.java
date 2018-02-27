/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.GLabel;
import acm.program.*;
import acmx.export.javax.swing.JLabel;

import java.awt.event.*;
import javax.swing.*;
import javax.xml.transform.Source;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the top of the window.
 */
	public void init() {
		graph = new NameSurferGraph();
		add(graph);
 		lastCentury = new NameSurferDataBase(NAMES_DATA_FILE);
	    createController();
	    addActionListeners();
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// Detect both click and ENTER for graph
		if(source == graphButton || source == nameField) {
			if(!nameField.getText().equals("")) {
				entry = lastCentury.findEntry(nameField.getText());
				ClickGraphButton(entry);
			}
		} if (source == clearButton) {
			ClickClearButton();
		}
	}
	
/* Method: createController() */
	public void createController() {
		nameField = new JTextField(MAX_NAME);
		nameField.addActionListener(this); //Detects ENTER key pressed
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		nameLable = new JLabel("Name");
		add(nameLable, NORTH);
		add(nameField, NORTH);
		add(graphButton, NORTH);
		add(clearButton,NORTH);
	}
	
	private void ClickClearButton() {
		graph.clear();
		graph.update();
	}
	
	private void ClickGraphButton(NameSurferEntry entry) {
		if (entry != null) {
			graph.addEntry(entry);
			graph.update();
		} 
	}
	
	/* private instance variable */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private JLabel nameLable;
	private static int MAX_NAME = 25;
	private NameSurferDataBase lastCentury;
	private NameSurferEntry entry;
	private NameSurferGraph graph;
	
}
