/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import jdk.nashorn.internal.ir.WithNode;
import sun.reflect.generics.tree.IntSignature;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.NumericShaper.Range;
import java.time.Year;
import java.util.function.DoubleToLongFunction;

import javax.swing.text.StyledEditorKit.BoldAction;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;
import com.sun.rowset.internal.Row;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;
import com.sun.webkit.ThemeClient;
import com.sun.xml.internal.stream.util.BufferAllocator;


public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels.  IMPORTANT NOTE:
  * ON SOME PLATFORMS THESE CONSTANTS MAY **NOT** ACTUALLY BE THE DIMENSIONS
  * OF THE GRAPHICS CANVAS.  Use getWidth() and getHeight() to get the 
  * dimensions of the graphics canvas. */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board.  IMPORTANT NOTE: ON SOME PLATFORMS THESE 
  * CONSTANTS MAY **NOT** ACTUALLY BE THE DIMENSIONS OF THE GRAPHICS
  * CANVAS.  Use getWidth() and getHeight() to get the dimensions of
  * the graphics canvas. */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	
/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 2;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Paddle instance to follow the mouse */
	GRect paddle = null;

/** The bouncing ball instance */
    GOval ball = null;

/** the pause between each frame */
    private static final int DELAY = 18;
    
/** The velocity of the ball in x and y direction */
    private double  vx, vy;
    
/** the color of the  bouncing ball */
    private static final Color BALL_COLOR = Color.BLACK;

/** the ramdom generator instance for initializing the velocity */
    private RandomGenerator rgen = RandomGenerator.getInstance();

/**  the scores instance variable keep scores */
    private int scores = 0;
    
/** the Gobj instance variable that show the scores */
    private GLabel scoreTable = null;

/** the sound file for collision */
    AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au"); 
/** 
/* Method: run() */
/** Runs the Breakout program. */
 	public void run() {
		setUp();
		play();
	}
	
	/**
	 * the set up for the game:
	 * create the initial bricks;
	 * create the paddle;
	 * create the initial balls;
	 */
	private void setUp( )  {
		setUpBricks();
		setUpPaddle();
	}
	
	/*
	 * set up the bricks according to different color
	 * and with the same speration while centered in the window;
	 */
	private void setUpBricks() {
		Color color = Color.RED;
		for (int nRow = 0; nRow < NBRICK_ROWS; nRow ++) {
			color = changeColor(color, nRow);
			for (int nBrick = 0; nBrick < NBRICKS_PER_ROW; nBrick ++) {
				addBrick(nRow, nBrick, color);
			}
		}
		
	}
	
	private void addBrick(int nRow, int nBrick, Color color) {
		/*the x,y coordinates of  the first brick of the first row 
		 * to center the game board*/
		double xFirstBrick = (getWidth() - WIDTH  + BRICK_SEP / 2.0) / 2.0;
		double yFirstBrick = BRICK_Y_OFFSET + BRICK_HEIGHT / 2.0;

		/*the x,y coordinates nth row, nth column brick*/
		double x = xFirstBrick +  nBrick * (BRICK_WIDTH + BRICK_SEP);
		double y = yFirstBrick + nRow * (BRICK_HEIGHT + BRICK_SEP);
		
		GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		
		brick.setColor(color);
		brick.setFilled(true);
		brick.setFillColor(color);
		
		add(brick, x, y);
	}
	
	/*
	 * change the color of bricks acoording to the rows
	 * 1rst and 2nd row are red; 3th and 4th row are orange;
	 * 5th and 6th row are yellow; 7th and 8th are green; 
	 * 9th and 10th row are cyan.
	 */
	private Color changeColor(Color color, int nRow) {
		switch (nRow) {
		case 3: color = Color.ORANGE;
		case 5: color = Color.YELLOW;
		case 7: color = Color.CYAN;
		}
		return color;
	}
	
	/*
	 * add the paddle to the initial position,
	 * and centered,
	 *  then the paddle will move with the mouse 
	 */
	private void setUpPaddle() {
		paddle = makePaddle();
		centerPaddle();
		addMouseListeners();
	}
	/**
	 * @return return the Paddle instance as a specified size
	 * and black
	 */
	private GRect makePaddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		
		paddle.setFillColor(Color.BLACK);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
		
		return paddle;
	}
	/**
	 * the center paddle method will center the 
	 * paddle for x coordinates, but remain the same y coordinate
	 */
	private void centerPaddle() {
		double x  = (getWidth() - PADDLE_WIDTH) / 2.0;
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT / 2.0;
		add(paddle, x, y);
	}
	/**
	 * this method is used to set the paddle move with the mouse
	 * but stay still if the paddle hit either left or right wall.
	 */
	public void mouseMoved(MouseEvent e) {
		double x = e.getX() - PADDLE_WIDTH / 2.0;
		double y = paddle.getY();
		
		/**the moving range of the paddle*/
		double left = 0;
		double right = getWidth() - PADDLE_WIDTH;
		
		if (x > right) {
			paddle.setLocation(right, y);
		} else if (x < left) {
			paddle.setLocation(left, y);
		} else {
			paddle.setLocation(x, y);
		}
	}
	
	/**
	 * create the bouncing  ball:
	 * the ball starts bouncing at the center of the canvas
	 * with a random velocity between (1.0, 3.0) and a random direction.
	 * then when the ball hit wall, the respective velocity will reverse.
	 */
	private void play() {
		/*set up the bouncing ball and score table*/
		ball = makeBall();
		centerBall();
		giveVelocity();
		setScoreTable();
		
		waitForClick();
		while(true) {
			/* lose */
			if (hitBottom(ball)) {
				lose();
				break;
			}
			
			/* win */
			if (scores == 101) {
				win();
				break;
			}
			ball.move(vx, vy);
			updateVelocity();
			updateGame();
			updateScoreTable();
			pause(DELAY);
		}
	}
	/**
	 * @return the ball with specified radious and color
	 */
	private GOval makeBall() {
		ball = new GOval(BALL_RADIUS, BALL_RADIUS);
		ball.setColor(BALL_COLOR);
		ball.setFilled(true);
		ball.setColor(BALL_COLOR);
		return ball;
	}
	
	/**
	 * move the ball to the center of the canvas
	 */
	private void centerBall() {
		double x = (getWidth() - BALL_RADIUS) / 2.0;
		double y = (getHeight() - BALL_RADIUS) / 2.0;
		add(ball, x, y);
	}
	
	/**
	 * initialize the velocity of the ball to start bouncing
	 */
	private void giveVelocity() {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = rgen.nextDouble(6.0, 8.0);
	}
	/**
	 * @param ball: the bouncing ball instance
	 * @param left: the left wall coordinates
	 * @param right: the right wall coordinates
	 * when the ball hit left or right wall,
	 * the x velocity will reverse the direction.
	 */
	private void hitX(GOval ball, double left, double right) {
		double x = ball.getX();
		if(x <= left || x >= right) {
			vx = - vx;
		}
	}
	
	/**
	 * @param ball: the bouncing ball instance
	 * @param up: the upper wall limit
	 * @param down: the dowm wall limit
	 * when the ball hit the up or down wall, 
	 * the y velocity will reverse its direction.
	 * modified: remove the bottom wall limit
	 *           since the player loses.
	 */
	private void hitY(GOval ball, double up, double down) {
		double y = ball.getY();
		if ( y <= up) {
			vy = -vy;
		}

	}
	/**
	 * update velocity when the ball hit a wall
	 */
	private void updateVelocity() {
		double left = 0;
		double up = 0;
		double right = getWidth() - BALL_RADIUS;
		double down = getHeight() - BALL_RADIUS;
		hitX(ball, left, right);
		hitY(ball, up, down);
	}
	
	/**
	 *Get Colliding Object Method
	 *---------------------------
	 * @return the GObeject instance that collides with
	 *         the bouncing ball or anyohter GObject.
	 */
	private GObject findCollided(GObject colliding){
		/*the x and y coordinates of the ball up left corner*/
		double ulx = colliding.getX();
		double uly = colliding.getY();
		
		/* the x and y coordinates of up right corner*/
		double urx = colliding.getX() + BALL_RADIUS;
		double ury = colliding.getY();
		
		/* the x and y coordinates of down left corner*/
		double dlx = colliding.getX();
		double dly = colliding.getY() + BALL_RADIUS;
		
		/* the x and y coordinates of down right corner*/
		double drx = colliding.getX() + BALL_RADIUS;
		double dry = colliding.getY() + BALL_RADIUS;

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
    
	/**
     * The update game method
     * -----------------------
     * check for each frame:
     * if the ball collides the peddale, reverse the y velocity
     * if the ball hit the brick, remove the brick
     */
	private void updateGame() {
    	GObject collided = findCollided(ball);
    	if (collided == paddle) {
    		vy = -vy;
    		bounceClip.play();
    	}
    	else if (collided != null) {
    		scores ++;
			vy = - vy;
			bounceClip.play();
			remove(collided);
		}
    }
	/**
	 * The Win Method
	 * --------------
	 * the feedback for the player if the player win.
	 * Display "Bravo!"
	 */
	private void win() {
		GLabel winLable = new GLabel("Bravo!");
		winLable.setColor(Color.RED);
		winLable.setFont("Courier-42");
//		double x = (getWidth() - winLable.getX()) / 2.0;
//		double y = (getHeight() - winLable.getAscent()) / 2.0;
		add(winLable, (WIDTH - winLable.getWidth()) / 2.0, 300);
	}
	/**
	 * The Lose method
	 *-----------------
	 *the feedback for the player if the player loses.
	 *Dislay " Game over!"
	 */
	private void lose() {
		GLabel loseLable =  new GLabel("Game Over!");
		loseLable.setColor(Color.RED);
		loseLable.setFont("Courier-42");
//		double x = (getWidth() - loseLable.getX()) / 2.0;
//		double y = (getHeight() - loseLable.getAscent()) / 2.0;
		add(loseLable, (WIDTH - loseLable.getWidth()) / 2.0, 300);
	}
	/**
	 * The hit Bottom Method
	 * -----------------------
	 * @param obj: the bouncing ball in this game
	 * @return true if the ball hit the bottom wall
	 *         false if doesn't
	 */
	private boolean hitBottom(GObject obj) {
		return obj.getY() >= getHeight();
	}
    /**
     * The set Score Table Method
     * --------------------------
     * set the initial scores table and
     * position the table in the up left corner
     */
	private void setScoreTable() {
    	scoreTable = new GLabel("Bricks: " + scores);
    	scoreTable.setFont("Courier-36");
    	add(scoreTable, 20, 30);
    }
    private void updateScoreTable() {
    	scoreTable.setLabel("Bricks: " + scores);
    }
}

