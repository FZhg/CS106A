/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	/**
	 * deal wtih general world first,
	 * which is more than a column
	 * then deal with a single column and a single corner world
	 */
	public void run() {
		if (frontIsClear()) { 
			leaveTrace();
			while (leftIsClear()) {
				moveUp();
				fillRow();
			}
		}
		else {
			singleCol();
		}
	}
	
	/*
	 * single column
	 * preconditions: Karel facese east;
	 *                there is only one colum
	 * postconditons: return the pattern considering just one corner
	 */
	private void singleCol() {
		turnLeft();
		if (frontIsClear()) {
			leaveTrace();
		}
		else {
			putBeeper();
		}
	}
	
	/**	 * fill a row
	 * preconditions: Karel is at the first corner of the street,
	 * facing east;
	 * postConditions: return a row fill with checkerboard pattern;
	 * karel return to starting point facing east.
	 */
	private void fillRow() {
		if (beeperIsDown()) {
			move();
			leaveTrace();
		}
		else {
			leaveTrace();
		}
	}
	
	/**
	 * put a beeper and move
	 * repeate until hit a wall
	 * preconditions: Karel start facing the direction 
	 * where trace will be left
	 * postConditions: make the pattern and leaving Karel
	 * at the the first corner facing the same direction as before
	 */
	private void leaveTrace() {
		while (frontIsClear()) {
			putBeeper();
			safeMove();
			safeMove();
		}
		if (noBeeperIsBack()) {
			putBeeper();
		}
		turnAround();
		moveToWall();
		turnAround();
	}
	
	/**
	 * safe move
	 */
	private void safeMove() {
		if (frontIsClear()) {
			move();
		}
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
	 * face to east
	 */
	private void faceEast() {
		if (facingNorth()) {
			turnRight();
		}
		if (facingSouth()) {
			turnLeft();
		}
		if (facingWest()) {
			turnAround();
		}
	}
	
	/**
	 * move up
	 */
	private void moveUp() {
		faceNorth();
		move();
		turnRight();
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
	 * check if beeper is down
	 */
	private boolean beeperIsDown() {
		faceSouth();
		if (beeperIsFront()) {
			faceEast();
			return true;
		}
		else {
			faceEast();
			return false;
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
	
	

}
