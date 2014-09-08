package data.persistant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * static class for keeping track of preferences, file paths etc
 * @author SamSchofield
 *
 */
public class Persistent {
	
	private static Preferences prefs = Preferences.userRoot().node("/Fitr");
	
	
	/**
	 * sets the FilePath preference 
	 * @param filePath
	 */
	public static void setFilePath(String filePath) {
		// filePath will be obtained from a file chooser so it will always be an existing file path so doesn't need to be checked
		prefs.put("FilePath", filePath);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
	 * gets the file path form preferences 
	 * @return FilePath 
	 */
	public static String getFilePath() {
		return prefs.get("FilePath", null);
	}
	
	/**
	 * returns the file path to the current users profile
	 * @return profileFilePath
	 */
	public static String getProfileFilePath() {
		return prefs.get("FilePath", null) + "/Users/" + getCurrentUser() + "/" + getCurrentUser() + ".fitr";
	}
	
	/**
	 * returns the file path to the current users activity data
	 * @return ActivityFilePath
	 */
	public static String getActivityFilePath() {
		return prefs.get("FilePath", null) + "/Users/" + getCurrentUser() + "/" + getCurrentUser() + "Activity.fitr";
	}
	
	/**
	 * checks if a valid file path has been set
	 * @return pathSet
	 */
	public static boolean filePathSet() {
		boolean pathSet = true;
		File fp = new File((prefs.get("FilePath", null)));
		
		if(fp == null || !fp.exists()) {
			pathSet = false;
		} else {
			pathSet = true;
		}
		return pathSet;
	}
	
	/**
	 * sets up a new Fitr directory with a users sub directory at the file path 
	 */
	public static void setupDirectory() {
		String filePath = prefs.get("FilePath", null);
		if(filePath != null) {
			new File(filePath + "/Fitr//Users").mkdirs();
		}
	}
	
	/**
	 * creates a new directory in users with name user to store information in 
	 * also adds a user.json and activity.json file to save data to
	 * @param userName
	 */
	public static void newUser(String userName) {
		if (! getUsers().contains(userName)) {
			new File(prefs.get("FilePath", null) + "/Fitr/Users/" + userName).mkdir();
			
			try {
				new File(prefs.get("FilePath", null) + "/Fitr/Users/" + userName + "/" + userName + ".fitr").createNewFile();
				// should go in its own try catch block?
				new File(prefs.get("FilePath", null) + "/Fitr/Users/" + userName + "/" + userName + "Activity.fitr").createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Sorry that user already exists");
		}
	}
	
	/**
	 * Sets the current user
	 * @param user
	 */
	public static void setUser(String user) {
		if(getUsers().contains(user)) {
			prefs.put("User", user);
		}
	}
	
	/**
	 * returns the current user
	 * @return currenUser
	 */
	public static String getCurrentUser() {
		return prefs.get("User", null);
	}
	
	/**
	 * returns an ArrayList of all the users found in the user directories
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getUsers() {
		File filePath = new File(prefs.get("FilePath", null) + "/Fitr/Users/");
		File[] userDir = filePath.listFiles();
		ArrayList<String> users = new ArrayList<String>();
		
		for(File user : userDir) {
			if(user.isDirectory()) {
				users.add(user.getName());
			}
		}
		return users;
	}
	
	
	public static void main(String args[]) throws BackingStoreException {
		setFilePath("/home/daniel/Desktop");
		setupDirectory();
		newUser("Sam2");
		newUser("Dan");
		System.out.println(getFilePath());
		System.out.println("Saved");
		System.out.println("Users are: ");
		ArrayList<String> a = new ArrayList<String>(getUsers());
		for(int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i));
		}
	}
		
}
