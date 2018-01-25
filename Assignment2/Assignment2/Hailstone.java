/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;
import sun.net.www.content.audio.x_aiff;

public class Hailstone extends ConsoleProgram {
	/**
	 * take a number from input;
	 * if the number is odd, multiply the number  by 3 and add 1, update
	 * if the number is even, divide by 2, update
	 * repeate the process until the number deceases to one;
	 * preconditions: enter an positive interger;
	 * postconditions: count the step for the interger decease to one.
	 */
	public void run() {
		int num = readInt("Enter a number: ");
		int count = 0;
		while (num != 1) {
			if (num % 2 == 0) {
				println(num + " is even, so I take half: " + num / 2 + ".\n");
				num = num / 2;
			} else {
				println(num + "is odd, so I take half: " + 3 * num + 1 + ".\n");
				num = 3 * num + 1;
			}
			count ++;
		}
		println("The process took "+ count + " steps to reach 1. \n");
	}
}

