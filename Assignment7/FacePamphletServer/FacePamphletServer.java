/*
 * File: FacePamphletServer.java
 * ------------------------------
 * This program runs a server which hosts the data for a 
 * FacePamphlet internet application. The server stores all 
 * of the data and contains the logic for creating, deleting 
 * profiles and getting and setting profile properties. When 
 * the server receives a requests (which often come from the 
 * client), it updates its internal data, and sends back a string. 
 */

import java.security.KeyStore.PrivateKeyEntry;
import java.util.*;

//import com.sun.xml.internal.ws.handler.HandlerProcessor.RequestOrResponse;

import acm.program.*;
//import sun.security.provider.JavaKeyStore.CaseExactJKS;
//import sun.security.provider.JavaKeyStore.CaseExactJKS;

public class FacePamphletServer extends ConsoleProgram 
					implements SimpleServerListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* The internet port to listen to requests on */
	private static final int PORT = 8000;
	
	/* The server object. All you need to do is start it */
	private SimpleServer server = new SimpleServer(this, PORT);
	private static String NAME = "name";
	private static String STATUS = "status";
	private static String IMG = "fileName";
	private static String FRI = "friends";
	private static String NAME1 = "name1";
	private static String NAME2 = "name2";
	private static HashMap<String, FacePamphletProfile> profiles = new HashMap<String, FacePamphletProfile>();

	/**
	 * Starts the server running so that when a program sends
	 * a request to this computer, the method requestMade is
	 * called.
	 */
	public void run() {
		println("Starting server on port " + PORT);
		server.start();
	}

	/**
	 * When a request is sent to this computer, this method is
	 * called. It must return a String.
	 */
	public String requestMade(Request request) {
		String cmd = request.getCommand();
		println(request.toString());
		switch (cmd) {
		case "ping":
			return "Hello, internet";
		case "addProfile":
			return addProfile(request);
		case "containsProfile":
			return containsProfile(request);
		case "deleteProfile":
			return deleteProfile(request);
		case "getStatus":
			return getStatus(request);
		case "setStatus":
			return setStatus(request);
		case "getImgFileName":
			return getImgFileName(request);
		case "setImgFileName":
			return setImgFileName(request);
		case "getFriends":
			return getFriends(request);
		case "addFriend":
			return addFriend(request);

		default:
			return "Error: Unknown command " + cmd + ".";
		}
	}
	
	/**
	 * Method: addProfile(String name)
	 * @param name
	 * @return "success" or, if the profile exitsts, return an error message
	 */
	private String addProfile(Request request) {
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			return "Error:" + name + " already have a profile!";
		} else {
			FacePamphletProfile profile = new FacePamphletProfile(name);
			profiles.put(name, profile);
			return "success";
		}
	}
	
	/**
	 * Method: containsProfile(String name)
	 * @param name
	 * @return "true" if the profile exists
	 *         "false" if the profile does not.
	 */
	private String containsProfile(Request request) {
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			return "true";
		} else {return "false";}	
	}
	
	/**
	 * Method: deleteProfile(String name)
	 * @param name
	 * @return "success" and remove the profile 
	 *         Error message if the profile does not exist.
	 */
	private String deleteProfile(Request request) {
		String friendName = request.getParam(NAME);
		if(profiles.containsKey(friendName)) {
			profiles.remove(friendName);
			deletefriend(friendName);
			return "success";
		} else {return "Error: the profile for" + friendName + " didn't exist.";}
	}
	/**
	 * Method: getStatus(String name)
	 * @param name
	 * @return the status of the profile
	 *         Error message if the profile doesn't exist.
	 */
	private String getStatus(Request request) {
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			return profiles.get(name).getStatus();
		} else {return "Error: the profile didn't exist.";}
	}
	
	/**
	 * Method: setStatus(String name)
	 * @param name
	 * @return "success" and set the status to be the parameter.
	 *         Error message if the profile desn't exist.
	 */
	private String setStatus(Request request) {
		String status = request.getParam(STATUS);
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			profiles.get(name).setStatus(status);
			return "success";
		} else {return "Error: the profile didn't exist.";}
	}
	/**
	 * Method: getImgFileName(String name)
	 * @param name
	 * @return the image file name if the profile exists;
	 *         Error message if the profile desn't exist.
	 */
	private String getImgFileName(Request request) {
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			return profiles.get(name).getImageFileName();
		} else {return "Error: the profile didn't exist.";}
	}
	/**
	 * Method: setStatus(String name)
	 * @param name
	 * @return "success" and set the image filename to be the parameter.
	 *         Error message if the profile desn't exist.
	 */
	private String setImgFileName(Request request) {
		String fileName = request.getParam(IMG);
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			profiles.get(name).setImageFileName(fileName);
			return "success";
		} else {
			return "Error: the profile didn't exist.";}
	}
	
	/**
	 * Method: getFriends(String name)
	 * @param name
	 * @return the friends of the name profile
	 *         Error message if the profile doesn't exist.
	 */
	private String getFriends(Request request) {
		String name = request.getParam(NAME);
		if (profiles.containsKey(name)) {
			return profiles.get(name).getFriends().toString();
		} else {return "Error: the profile didn't exist.";}
	}
	
	private String addFriend(Request request) {
		String  name1 = request.getParam(NAME1);
		String name2 = request.getParam(NAME2);
		if (name1.equals(name2)) {
			System.out.println("screw 3");
			return "Error " + name1 + ", you can't be your own friend.";
		} else {
			if (profiles.containsKey(name2) && profiles.containsKey(name2)) {
				FacePamphletProfile profile1 = profiles.get(name1);
				FacePamphletProfile profile2 = profiles.get(name2);
				if (profile1.getFriends().contains(name2)) {
					return "Error: they are already friends.";
				} else {
					profile1.addFriend(name2);
					profile2.addFriend(name1);
					return "success";
				}
			} else {
				System.out.println("screw 1");
				return "Error: either " + name1 + "or " + name2 + " are not in the database.";
			}
		}
	}
	private void deletefriend(String friend) {
		for (String name: profiles.keySet()) {
			FacePamphletProfile profile = profiles.get(name);
			profile.removeFriend(friend);
		}
	}
}
