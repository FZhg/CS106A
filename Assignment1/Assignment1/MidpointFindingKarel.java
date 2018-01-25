/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	
	/**
	 * face to North
	 */
	private void faceNorth() {
		if (facingEast()) {
			turnLeft();
		}
		if (facingSouth()) {
			turnAround();
		}
		if (facingWest()) {
			turnRight();
		}
	}
	
	/**
	 * face to south
	 */
	private void faceSouth() {
		if (facingNorth()) {
			turnAround();
		}
		if (facingEast()) {
			turnRight();
		}
		if (facingWest()) {
			turnLeft();
		}
	}
	
	/**
	 * move up
	 */
	private void moveUp() {
		faceNorth();
		move();
	}
	
	/**
	 * move down
	 */
	private void moveDown() {
		faceSouth();
		move();
	}
	/**
	 * move backward a step
	 */
	private void moveBack() {
		turnAround();
		move();
		turnAround();
	}
	/**
	 * check if a beeper is in front
	 */
	private boolean beeperIsFront() {
		move();
		if (beepersPresent()) {
			moveBack();
			return true;
		}
		else {
			moveBack();
			return false;
		}
	}
	
	/**
	 * the opposite of beeperIsFront
	 */
	private boolean noBeeperIsFront() {
		if (beeperIsFront()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * check if beeper is in back
	 */
	private boolean beeperIsBack() {
		turnAround();
		if (beeperIsFront()) {
			turnAround();
			return true;
		}
		else {
			turnAround();
			return false;
		}
	}
	
	/**
	 * the opposite of beeperIsBack
	 */
	private boolean noBeeperIsBack() {
		if (beeperIsBack()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 *  mvoe until hit a beeper
	 * put a beeper as mark and turn around
	 */
	private void shrinkRange() {
		while (noBeeperIsFront()) {
			move();
		}
		putBeeper();
		turnAround();
	}
	
	/**
	 * move to the wall
	 */
	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}
	
	/**
	 * move to the 2nd street
	 * put a beeper in leftest and rightest corner
	 */
	private void prepare() {
		moveUp();
		putBeeper();
		turnRight();
		moveToWall();
		putBeeper();
		turnAround();
		move();
	}
    /**
     * for worlds wider than a single columns
     * move to the 2nd street
     * put a beeper in the leftest and rightest corner
     * shrink the range
     * stop when right and left are beepers
     * then go down to the 1st street and put a beeper
     * finally clear all beepers in 2nd street.
     * 
     * for single columns world, stay where Karel are
     */
	private void moveToMidpoint() {
		if (frontIsClear()) {
			prepare();
			
			while(noBeeperIsFront() || noBeeperIsBack()) {
				shrinkRange();
			}
			
			moveDown();
		}
	}
	
	/**
	 * pick a beeper when a beeper is present
	 */
	private void safePickUp() {
		if (beepersPresent()) {
			pickBeeper();
		}
	}
	/**
	 * clear all beepers in the 2nd street
	 */
	private void clearBeepers() {
		moveUp();
		turnLeft();
		moveToWall();
		turnAround();
		while (frontIsClear()) {
			safePickUp();
			move();
		}
		safePickUp();
	}
	
	
	/**
	 * move to the midpoint of 1st street, drop a beeper
	 * clear the beepers used to locate the midpoint
	 */
	public void run() {
		moveToMidpoint();
		putBeeper();
		clearBeepers();
	}
}
