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

public class NameSurfer extends ConsoleProgram implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the top of the window.
 */
	public void init() {
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
			println(nameField.getText());
			entry = lastCentury.findEntry(nameField.getText());
			println(entry.toString());
		} if (source == clearButton) {
			println("Clear");
		}
	}
/* Method: createController() */
	public void createController() {
		setFont("Courier-24");
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
	
	
	/* private instance variable */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private JLabel nameLable;
	private static int MAX_NAME = 25;
	private NameSurferDataBase lastCentury;
	private NameSurferEntry entry;
	
}
