/*
 * File: StoneMasonKarel.java
 * --------------------------
 * Put Karel in the start point, 1st Street, 1st Avenue
 * start one columns a time, stop when hit the right wall
 * when scan the columns, put the beeper where there no beepers.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	
	/**
	 * Move to the bottom
	 */
	private void moveToBottom() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
	}

	/**
	 *move to the bottom
	 * move from column to column in the bottom of the column.
	 * when hit the right wall, stop.
	 */
	private void moveToNextCol() {
		moveToBottom();
		// move to next column
		turnLeft();
		for (int i = 0; i < 4; i++) {
			if (frontIsClear()) {
				move();
			}
		}
	}
	
	/**
	 * go up and put a beeper when there is no beeper
	 */
	private void repair() {
		turnLeft();
		while (frontIsClear()) {
			checkForRepair();
			move();
		}
	}
	
	/**
	 * fix for the fencepost error
	 * check for the top position of the column
	 */
	private void checkForRepair() {
		if (noBeepersPresent()) {
			putBeeper();
		}
	}
	
	/**
	 * repair the column, also check for fencePost problem
	 * move to next column
	 */
	private void mansionStone() {
		repair();
		checkForRepair();
		moveToNextCol();
	}
	
	/**
	 * iterate repair and move to next column
	 * until hit a wall
	 * check for the last column
	 */
	public void run() {
		while (frontIsClear()) {
			mansionStone();
		}
		mansionStone();
	}

}
