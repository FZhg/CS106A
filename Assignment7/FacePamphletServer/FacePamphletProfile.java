/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import java.util.*;

import org.omg.CORBA.FREE_MEM;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
//import com.sun.xml.internal.ws.handler.HandlerProcessor.RequestOrResponse;

public class FacePamphletProfile {
	private  ArrayList<String> friends = new ArrayList<>();
	private String name;
	private String status;
	private String imgFileName;

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 */
	public FacePamphletProfile(String name) {
		this.name = name;
		this.status = "";
		this.imgFileName = "";
		
		// * delete *
		System.out.println(this.getName());
		System.out.println(this.getImageFileName());
		System.out.println(this.getStatus());
		System.out.println(this.getFriends().toString());
		
	}

	/** This method returns the name associated with the profile. */ 
	public String getName() {
		return this.name;
	}


	public String getImageFileName() {
		return this.imgFileName;
	}

	/** This method sets the image associated with the profile. */ 
	public void setImageFileName(String fileName) {
		this.imgFileName = fileName;
		/* delete */
		System.out.println("filename" + this.imgFileName);
	}

	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getStatus() {
		return this.status;
	}

	/** This method sets the status associated with the profile. */ 
	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 */
	public boolean addFriend(String friend) {
		return friends.add(friend);
	}

	/** 
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the 
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name 
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		return friends.remove(friend);
	}

	/** 
	 * This method returns an iterator over the list of friends 
	 * associated with the profile.
	 */ 
	public List<String> getFriends() {
		return friends;
	}

}
