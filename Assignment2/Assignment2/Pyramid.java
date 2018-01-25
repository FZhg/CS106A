/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This class build a pyramid using graphicProgram. 
 * The pyramid should be centered in bottom of the window.
 * I decompose the task into three layers: build the pyramid,
 * build a layer, and put a brick on the right spot.
 * the position of the bricks is calculated by the layer and brick index.
 * I write the code with help of ChekerBorad Problem.
 * the coordinate is so confusing, the  is origin and the y axis points downward.
 */

import acm.graphics.*;
import acm.program.*;
import sun.net.www.content.audio.x_aiff;

import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Height of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	/**
	 * layer by layer; brick by brick.
	 * (x,y) is the coordinate of the rect's upper left corner
	 */
	public void run() {
		/* the first brick is half base layer away from the origin in x axis.*/
		double firstBrickX = getWidth() / 2 - BRICK_WIDTH * BRICKS_IN_BASE / 2;
		/* */
		double firsrBrickY = getHeight() - BRICK_HEIGHT ;
		for (double layerIndex = 0; layerIndex < BRICKS_IN_BASE; layerIndex ++) {
			
			/*the height is dependent only on layer index */
			double y = firsrBrickY - BRICK_HEIGHT * layerIndex;
			
			for (double brickIndex = 0;  brickIndex < (BRICKS_IN_BASE - layerIndex); brickIndex ++) {
				
				/*the first two term is x coordinate of the first brick in the layer*/
				double x = firstBrickX + BRICK_WIDTH / 2 * layerIndex + brickIndex * BRICK_WIDTH;
				
				GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				add(brick);
			}
		}
	}
}

