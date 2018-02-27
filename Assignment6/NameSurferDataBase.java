/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;
import sun.security.util.Length;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.*;

import javax.naming.NameParser;
import javax.print.attribute.standard.PrinterLocation;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
import com.sun.xml.internal.bind.v2.runtime.Name;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class NameSurferDataBase implements NameSurferConstants {
	private HashMap<String, NameSurferEntry> database = new HashMap<String, NameSurferEntry>();
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		try (BufferedReader rd = new BufferedReader(new FileReader(filename))) {
			String line;
			while (( line = rd.readLine()) != null ) {
			NameSurferEntry entry = new NameSurferEntry(line);
			this.database.put(entry.getName(), entry);
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		String nameProcessed = toName(name);
		return this.database.get(nameProcessed);
	}

/* Method: toName(name) */
/**
 * make the user's input into the name form 
 * the fisrt letter is capital and the rest of letters are lowercase.
 */
	private String toName(String name) {
		char cap = Character.toUpperCase(name.charAt(0));
		String nameProcessed = "" + cap;
		for (int i  = 1; i < name.length(); i ++) {
			nameProcessed += Character.toLowerCase(name.charAt(i));
		}
		return nameProcessed.toString();
	}
}

