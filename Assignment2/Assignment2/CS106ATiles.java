/*
 * File: CS106ATiles.java
 * Name: 
 * Section Leader: 
 * ----------------------
 * I decompose the problem into draw a tile and copy the tile to 
 * a grid form. Encapsulation and information hiding really made the code
 * more readable.
 */

import acm.graphics.*;
import acm.program.*;
import sun.net.www.content.audio.x_aiff;

import java.awt.*;

import javax.sound.sampled.LineUnavailableException;

import com.sun.webkit.ThemeClient;

public class CS106ATiles extends GraphicsProgram {
	
	/** Amount of space (in pixels) between tiles */
	private static final int TILE_SPACE = 20;
	private static final double TILE_HEIGHT = 60;
	private static final double TILE_WIDTH = 140;

	/**
	 * run the createLable method at grid corner coordinate
	 * , which is to say copy the tile into grid form
	 */
	public void run() {

        /* the distances to move to the coordinate of next tile upperleft corner*/
		double x_dis = TILE_WIDTH + TILE_SPACE / 2.0;
		double y_dis = TILE_HEIGHT + TILE_SPACE / 2.0;
		/*the coordinate of the first corner of the grid*/
		double x = getWidth() / 2.0 - x_dis ;
		double y = getHeight() / 2.0 - y_dis;
		
		createTile(x, y);
		createTile(x + x_dis, y);
		createTile(x, y + y_dis);
		createTile(x+ x_dis, y + y_dis);
	}
	
	/**
	 * Create the tile at (x, y)
	 * preconditions: Argument 1: the x coordinate of the upperleft corner of the tile;
	 * 				  Argument 2: the y coordinate of the upperleft corner of the tile;
	 * post conditions£ºdraw a rectangle at (x,y) with the height and width specified as frame;
	 * 					center the text in the frame;
	 */
	private void createTile(double x, double y) {
		GLabel label = new GLabel("CS106A");
		GRect frame = new GRect(TILE_WIDTH, TILE_HEIGHT);
		add(frame, x, y);
		double label_x = x + frame.getWidth() / 2.0 - label.getWidth() / 2.0;
		double label_y = y + frame.getHeight() / 2.0 + label.getAscent() / 2.0;
		add(label, label_x, label_y);
				
	}
}

