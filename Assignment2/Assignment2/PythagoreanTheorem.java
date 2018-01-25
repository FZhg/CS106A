/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * I find the problem could not be decomposed anymore.
 */

import com.sun.accessibility.internal.resources.accessibility_de;

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	/**
	 * preconditions: input two positive number(doubles)
	 *                as the two arms of the pythagorean.
	 * postconditions: print the third leg of the triangle using the theorem.
	 */
	public void run() {
		println(" Enter the lengths of two rectangular sides.");
		double a = readDouble("a: ");
		double b = readDouble("b: ");
		double c = Math.sqrt(a*a + b*b);
		println("c = " + c);
		
		}
}
