/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import jdk.internal.dynalink.beans.StaticClass;
import sun.security.x509.X400Address;

import java.awt.*;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.sun.prism.shader.DrawCircle_Color_AlphaTest_Loader;
import com.sun.webkit.ThemeClient;

public class Target extends GraphicsProgram {	
	private static final double R_INNER = 72 * 0.3;
	private static final double R_WHITE = 72 * 0.65;
	private static final double R_OUTER = 72;
	private double BALL_RADIUS;
	public void run() {
		/*the coordinate of the cirle center*/
		double x = getWidth() / 2;
		double y = getHeight() / 2;
		GOval cir1 = drawCircle(x, y, R_OUTER, Color.RED);
		GOval cir2 = drawCircle(x, y, R_WHITE, Color.WHITE);
		GOval cir3 = drawCircle(x, y, R_INNER, Color.RED);
		
		BALL_RADIUS = 72 * 0.3;
		GObject cillided = getCollidingObject(cir3);
		println(cillided);
	}
	
	/*
	 * draw a circle according to the 
	 * center coordinate, the radius and color.
	 * preconditions: Argument 1: the x coordinate of the center;
	 * 				  Argument 2: the y coordinate of the center;
	 *                Argument 3: the radius of the circle;
	 *                Argument 4: the color of the circle, have to a color object
	 *postconditions: put the circle fo the radius at the center with the specific color.
	 */
	private GOval drawCircle(double center_x, double center_y, double radius, Color color) {
		/*the coordinate of the upper left corner of the circle*/
		double x = center_x - radius / 2;
		double y = center_y - radius / 2;
		/*the width and height are the radius since it is a circle*/
		GOval circle = new GOval(radius, radius);
		
		circle.setColor(color);
		circle.setFilled(true);
		circle.setFillColor(color);;
		add(circle, x, y);
		return circle;
	}
	
	private GObject getCollidingObject(GObject ball){
		/*the x and y coordinates of the ball up left corner*/
		double ulx = ball.getX();
		double uly = ball.getY();
		println("ulx: " + ulx + "uly: " + uly);
		/* the x and y coordinates of up right corner*/
		double urx = ball.getX() + BALL_RADIUS;
		double ury = ball.getY();
		
		/* the x and y coordinates of down left corner*/
		double dlx = ball.getX();
		double dly = ball.getY() + BALL_RADIUS;
		
		/* the x and y coordinates of down right corner*/
		double drx = ball.getX() + BALL_RADIUS;
		double dry = ball.getY() + BALL_RADIUS;
		

		/* the up left corner*/
		GObject collided = getElementAt(ulx, uly);
		if (collided != null) return collided;

		
		/* the up right corner */
		collided = getElementAt(urx, ury);
		if (collided != null) return collided;
		
		/* the down left corner*/
		collided = getElementAt(dlx, dly);
		if(collided != null) return collided;
		
		/* the down right corner return null if no collision happens*/
		collided = getElementAt(drx, dry);
		
		return collided;
	}
}
