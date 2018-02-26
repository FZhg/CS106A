/*
  * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class NameSurferEntry implements NameSurferConstants {
	
/* what make up the NameSuferEntry */
	private String name; // the name for the entry
	private int[] ranks; // an array of integers for 1 ranks

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		String[] entry = line.split(" ");
		this.name = entry[0];
		this.ranks = toNumberRanks(entry);
		
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return this.name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return this.ranks[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		return this.name + " " +  Arrays.toString(this.ranks);
	}
	
/* Method: toNumberRanks(String[] entry) */
/**
 * Returns an array of integers as ranks with the indexes as decade number.
 * Take an array of strings with the 0th element as the name 
 * and rest of strings as string ranks;
 * 
 */
	private int[] toNumberRanks(String[] entry) {
		int[] ranks = new int[NDECADES];
		for (int i = 1; i < 12; i ++) {
			ranks[i - 1] = Integer.parseInt(entry[i]);
		}
		return ranks;
	}
}

