package data.persistant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import user.User;

/**
 * static class for keeping track of preferences, file paths etc
 * 
 * @author SamSchofield
 *
 */
public class Persistent {

	private static Preferences prefs = Preferences.userRoot().node("/Fitr");
	private static ObservableList<User> users = FXCollections.observableList(new ArrayList<User>());
	private static ObservableList<String> userNames = FXCollections.observableList(new ArrayList<String>());
	private static User currentUser; //= User.mockUser(); // JUST FOR TESTING

	/**
	 * Sets the FilePath preference to a Fitr directory at location of filePath
	 * Will only set a valid file path
	 * @param filePath location on users computer where they want Fitr to be saved
	 * @throws FileNotFoundException
	 */
	public static void setFilePath(String filePath) throws FileNotFoundException {
		if (new File(filePath).exists()) { // check that filePath is valid
			prefs.put("FilePath", filePath + "/Fitr/");
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}

			// only creates new Fitr directory if the selected one doesn't already exist
			if (!new File(getFilePath()).exists()) {
				setupDirectory();
			} else { // if the Fitr directory already exists then use it.
				Persistent.initialize();
			}
		} else {
			throw new FileNotFoundException();
		}
	}

	/**
	 * sets up a new Fitr directory at the file path if filePath has been set
	 */
	public static void setupDirectory() {
		if (getFilePath() != null) {
			new File(getFilePath()).mkdirs();
		}
	}

	/**
	 * gets the file path to the Fitr directory from preferences
	 * 
	 * @return FilePath
	 */
	public static String getFilePath() {
		return prefs.get("FilePath", null);
	}

	/**
	 * returns the file path to the current users profile within the Fitr directory.
	 * user profile sub directories are named as the users ID number
	 * @return profileFilePath
	 */
	public static String getProfileFilePath(String userID) {
		return getFilePath() + userID + "/." + userID + ".fitr";
	}

	/**
	 * checks if a valid file path has been set.
	 * Used to determine if we need to ask the user to set their file path when opening the program 
	 * @return pathSet
	 */
	public static boolean filePathSet() {
		boolean pathSet = true;
		File filePath;
		if (getFilePath() == null) {
			filePath = null;
		} else {
			filePath = new File(getFilePath());
		}

		if (filePath == null || !filePath.exists()) {
			pathSet = false;
		}
		return pathSet;
	}

	/**
	 * creates a new directory in users with name userID to store user information in.
	 * also adds a userID.json
	 * The usedID.json is hidden on OSX and Linux OS so that users can't corrupt their data
	 * The user will only be added if that user doesn't already exist
	 * @param User the user to be added
	 * @return boolean if the user to be added or not
	 */
	public static boolean newUser(User user) throws Exception {
		boolean userAdded;
		
		if (!users.contains(user)) {
			String userID = user.getUserId();
			new File(getFilePath() + userID).mkdir();

			try {
				new File(getProfileFilePath(userID)).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			users.add(user);
			userNames.add(user.getName());
			Saver.SaveUser(user); // automatically save the user to json once they have been added 
			prefs.put("UserID", generateUserID());
			userAdded = true;
		} else {
			//user has already been added
			userAdded = false;
			//throw new Exception("User already exists");
		}
		return userAdded;
	}
	
	/**
	 * generates a unique UserID using UUID
	 * @return unique userID
	 */
	private static String generateUserID() {
		String userID = UUID.randomUUID().toString();
		System.out.println(userID);
		return userID;
	}

	/**
	 * returns the most recently used userID.
	 * used in generating userID values 
	 * @return userId
	 */
	public static String getUserID() {
		return prefs.get("UserID", null);
	}

	/**
	 * Sets the current user.
	 * Will only set the current user if the user is existing 
	 * 
	 * @param user
	 */
	public static void setUser(User user) {
		if (users.contains(user)) {
			currentUser = user;
		} else {
			currentUser = null;
		}
	}

	/**
	 * returns the current user
	 * 
	 * @return currenUser
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Returns a list of all the users found in the user directories.
	 * 
	 * @return
	 */
	public static ObservableList<User> getUsers() {
		return users;
	}

	/**
	 * gets the user names of all the profiles
	 * 
	 * @return userNames
	 */
	public static ObservableList<String> getUserNames() {
		for (User user : users) {
			userNames.add(user.getName());
		}
		return userNames;
	}

	/**
	 * loads all the profiles from the user directorys into the
	 * ObservableList<User> users
	 */
	public static void initialize() {
		if (getFilePath() != null && new File(getFilePath()).exists()) {
			File filePath = new File(getFilePath());

			// get files / directory in Fitr directory (gets users)
			File[] files = filePath.listFiles();

			for (File file : files) {
				String userID = file.getName();

				if (new File(file + "/." + userID + ".fitr").exists()) {
					// check if the file is a user
					User newUser = Loader.loadUserProfile(new File(file + "/." + userID + ".fitr"));

					if (newUser != null && !users.contains(newUser)) {
						users.add(newUser);
						userNames.add(newUser.getName());
					}
				}
			}
		}
	}
	
	/**
	 * clears the current users activity data and saves the changes 
	 */
	public static void clearUserActivityData() {
		Persistent.currentUser.clearEvents();
		Saver.SaveUser(Persistent.currentUser);
	}
	
	/**
	 * deletes the given user and removes its directory and json files
	 * @param user the user to be deleted
	 */
	public static void deleteUser(User user) {
		File path = new File(getFilePath() + "/" + user.getUserId());
		deleteDirectory(path);
		users.remove(user);
		userNames.remove(user.getName());
	}
	
	/**
	 * deletes the files at filePath 
	 * only public for use in testing 
	 * @param path path to delete files from 
	 */
	public static void deleteDirectory(File path) {

		if(path.exists() && (path.getParentFile().getName().equals("Fitr") || path.getName().equals("Fitr"))) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
			path.delete();
		} else {
			System.out.println("Not deleting that");
		}
	}

	/**
	 * used to clear the user preferences when reseting the program 
	 */
	public static void clear() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * checks that preferences have been stored and that it is safe to close the program 
	 * @throws BackingStoreException
	 */
	public static void exit() throws BackingStoreException {
		prefs.flush();
	}

	/**
	 * Main method used only for testing purposes 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String args[]) throws Exception {
		clear();
//		Persistent.setFilePath("/Users/SamSchofield/Desktop");
//		User u = User.mockUser();
//		Persistent.newUser(u);
//		deleteUser(u);
		//deleteDirectory(new File("/Users/SamSchofield/Desktop/Fitr"));
		
	}
}
