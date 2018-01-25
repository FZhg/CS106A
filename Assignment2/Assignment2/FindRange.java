/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import com.sun.scenario.effect.impl.state.LinearConvolveRenderState.PassType;

import acm.program.*;

public class FindRange extends ConsoleProgram {
	/**
	 * take input while the user don't hit Enter
	 * compare to previous min and max and update to the new ones;
	 * When the user hit Enter print the max and min.
	 */
	public void run() {
		println("This program finds the largest and smallest number you input.\n");
		println("type \" end \" (without the quotation mark) when you have input all the numbers");
		double max = -1.0;
		double min = 0.0;
		String enter = "initial";
		/*loop until the user input end*/
		while (enter.compareTo("end") != 0) {
			enter = readLine("?");
			
			/*when the user hit enter, the compare process is skipped*/
			if (enter.equals("end")) {
				println("The search for minium and maxium has ended.");
			} else {
				double num = Double.parseDouble(enter);
				/*when the user enter the first num while max and min are still initials*/
				if (max < min) {
					max = num;
					min = num;
				} else {
					if (max < num) {
						max = num;
					}
					if (min > num) {
						min = num;
					}
				}
			}
		}
		
		/*when max and min are still initials, the 
		 * user did not input any numbers print the error message*/
		if (max < min) {
			println("Please input at least one number!");
		} else {
			println("Smallest: " + min + "\n");
			println("Largest: " + max + "\n");
		}
	}
}

