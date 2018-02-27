/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.shape.Line;

import java.awt.event.*;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.*;

import com.sun.scenario.effect.impl.prism.ps.PPSTwoSamplerPeer;

import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	private HashMap<String, NameSurferEntry> entriesSearched = new HashMap<String, NameSurferEntry>();
	private static double POINT_RADIUS= 2;

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		drawLable(20, 20, "test");
		}
	
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		this.entriesSearched.clear();
	}
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		this.entriesSearched.put(entry.getName(), entry);
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		drawBackGround();
		plotNameSufer();
	}
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
    /**
     *  draw the background
     */
	private void drawBackGround() {
		double width = getWidth();
		double height = getHeight();
		for (int decade = 0; decade < NDECADES; decade ++) {
			double x0 = decade  * width / NDECADES;
			double y0 = 0, x1 = x0, y1 = height;
			int year = START_DECADE + decade * 10;
			drawLine(x0, y0, x1, y1, Color.BLACK);
			//draw the vertical lines for the background grid
			
			drawLable(x1, y1, Integer.toString(year)); // draw the year labels
		} 
		drawLine(0, GRAPH_MARGIN_SIZE, width, GRAPH_MARGIN_SIZE, Color.BLACK);
		drawLine(0, height - GRAPH_MARGIN_SIZE, width, height - GRAPH_MARGIN_SIZE, Color.BLACK); 
		//draw the horizontal lines
		
	}
	
	/**
	 * This Method draw line given  its starting and ending  points.
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	private void drawLine(double x0, double y0, double x1, double y1, Color color) {
		GLine line = new GLine(x0, y0, x1, y1);
		line.setColor(color);
		add(line);
	}
	
	private void drawLable(double x, double y, String str) {
		GLabel lable = new GLabel(str);
		add(lable, x, y);
	}
	
	private void drawPoint(double x, double y, Color color) {
		double xul = x - POINT_RADIUS;
		double yul = y - POINT_RADIUS;
		GOval pt = new GOval(2 * POINT_RADIUS, 2 * POINT_RADIUS);
		pt.setFilled(true);
		pt.setFillColor(color);
		pt.setColor(color);
		add(pt, xul, yul);
	}

	/**
	 * draw the lines given all entries searched
	 */
	private void plotNameSufer() {
		double width = getWidth();
		double height = getHeight();
		double deltaX = width / NDECADES;
		double deltaY = (height - GRAPH_MARGIN_SIZE * 2) / MAX_RANK;
		int i = 1;
		for (String name : entriesSearched.keySet()) {
			Color color = changeColor(i);
			i ++;
			for (int decade = 0; decade < NDECADES - 1; decade ++) {
				NameSurferEntry entry = entriesSearched.get(name);
				double x0 = decade * deltaX;
				double y0 = height - entry.getRank(decade) * deltaY - GRAPH_MARGIN_SIZE;
				double x1 = (decade + 1) * deltaX;
				double y1 =height - entry.getRank(decade + 1) * deltaY - GRAPH_MARGIN_SIZE;
				drawLine(x0, y0, x1, y1, color);
				drawPoint(x0, y0, color);
				drawPoint(x0, y0, color);
				drawLable(x0, y0, entry.getName() + Integer.toString(entry.getRank(decade)));
				if (decade == NDECADES - 2) {
					drawLable(x1, y1, entry.getName() + Integer.toString(entry.getRank(decade + 1)));
				} //the last label for 2000
			}
		}
	}
	
	private Color changeColor(int i) {
		i = i % 4;
		Color color = Color.BLACK;
		switch (i) {
		case 1:
			color = Color.BLACK;
			break;
		case 2:
			color = Color.RED;
			break;
		case 3:
			color = Color.BLUE;
			break;
		case 4:
			color = Color.MAGENTA;
		}
		return color;
	}
}
