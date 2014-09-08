package data.persistant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


/**
 * static class for keeping track of preferences, filepaths etc
 * @author SamSchofield
 *
 */
public class Persistent {
	
	private static Preferences prefs = Preferences.userRoot().node("/fitr");
	
	/**
	 * sets the FilePath preference 
	 * @param filePath
	 */
	public static void setFilePath(String filePath) {
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
	 * @return filepath 
	 */
	public static String getFilePath() {
		return prefs.get("FilePath", null);
	}
	
	/**
	 * checks to see if the app has been opened before
	 * @return boolean 
	 */
	public static boolean firstOpen() {
		boolean firstOpen = prefs.getBoolean("FirstOpen", false);
		prefs.putBoolean("FirstOpen", false);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return firstOpen;
	}
	
	/**
	 * sets up a new fitr directory with a users sub directory at the file path 
	 */
	public static void setupDirectory() {
		String filePath = prefs.get("FilePath", null);
		if(filePath != null) {
			new File(filePath + "/Fitr//users").mkdirs();
		}
	}
	
	/**
	 * creates a new directory in users with name user to store information in 
	 * also adds an user.json and activity.json file to save data to
	 * @param userName
	 */
	public static void newUser(String userName) {
		new File(prefs.get("FilePath", null) + "/Fitr/users/" + userName).mkdir();
		
		try {
			new File(prefs.get("FilePath", null) + "/Fitr/users/" + userName + "/" + userName + ".json").createNewFile();
			// should go in its own try catch block?
			new File(prefs.get("FilePath", null) + "/Fitr/users/" + userName + "/" + userName + "Activity.json").createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		File filePath = new File(prefs.get("FilePath", null) + "/Fitr/users/");
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
		setFilePath("/Users/SamSchofield/Desktop");
		setupDirectory();
		newUser("Sam");
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
