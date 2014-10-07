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
 * Static class for keeping track of preferences, file paths
 * and all other horrible things.
 * @author SamSchofield
 *
 */
public class Persistent {

	private static Preferences prefs = Preferences.userRoot().node("/Fitr");
	private static ObservableList<User> users = FXCollections.observableList(new ArrayList<User>());
	private static ObservableList<String> userNames = FXCollections.observableList(new ArrayList<String>());
	private static User currentUser;

	/**
	 * Sets the FilePath preference to a Fitr directory which will be set up 
	 * at the location of filePath.
	 * @param filePath location on users computer where they want Fitr to be saved.
	 * @throws FileNotFoundException
	 */
	public static void setFilePath(String filePath) throws FileNotFoundException {
		if (new File(filePath).exists()) { // check that filePath is valid
			prefs.put("FilePath", filePath + "/Fitr/");
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				//e.printStackTrace();
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
	 * Sets up a new Fitr directory at the file path if filePath has been set
	 */
	public static void setupDirectory() {
		if (getFilePath() != null) {
			new File(getFilePath()).mkdirs();
		}
	}

	/**
	 * Gets the file path to the Fitr directory from preferences
	 * 
	 * @return FilePath of Fitr directory.
	 */
	public static String getFilePath() {
		return prefs.get("FilePath", null);
	}

	/**
	 * Gets the filePath to the user.fitr JSON file.
	 * @param userID the id of the user who's file we want to get.
	 * @return String of the filePath.
	 */
	public static String getProfileFilePath(String userID) {
		return getFilePath() + userID + "/." + userID + ".fitr";
	}

	/**
	 * checks if a valid file path has been set.
	 * Used to determine if we need to ask the user to set their file path when opening the program.
	 * @return pathSet - if the filePath has been set or not.
	 */
	public static boolean filePathSet() {
		boolean pathSet = true;
		File filePath;
		//check if filePath has been set
		if (getFilePath() == null) {
			filePath = null;
		} else {
			filePath = new File(getFilePath());
		}
		
		// check that the filePath exists
		if (filePath == null || !filePath.exists()) {
			pathSet = false;
		}
		return pathSet;
	}

	/**
	 * creates a new directory in users with name userID to store user information in.
	 * also adds a userID.json
	 * The usedID.json is hidden on OSX and Linux OS so that users can't corrupt their data
	 * The user will only be added if that user doesn't already exist.
	 * @param user the user to be added.
	 * @return boolean if the user to be added or not.
	 */
	public static boolean newUser(User user) {
		boolean userAdded;
		
		if (!users.contains(user)) {
			String userID = user.getUserId();
			new File(getFilePath() + userID).mkdir();

			try {
				new File(getProfileFilePath(userID)).createNewFile();
			} catch (IOException e) {
				//e.printStackTrace();
			}

			users.add(user);
			userNames.add(user.getName());
			Saver.SaveUser(user); // automatically save the user to json once they have been added 
			userAdded = true;
		} else {
			//user has already been added
			userAdded = false;
		}
		return userAdded;
	}
	
	/**
	 * generates a unique UserID using UUID
	 * @return unique userID
	 */
	public static String generateUserID() {
		String userID = UUID.randomUUID().toString();
		return userID;
	}

	/**
	 * Sets the current user.
	 * Will only set the current user if the user is existing .
	 * 
	 * @param user to set as current.
	 */
	public static void setUser(User user) {
		if (users.contains(user)) {
			currentUser = user;
		} else {
			currentUser = null;
		}
	}

	/**
	 * Returns the current user.
	 * 
	 * @return currentUser, the current user.
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Returns a list of all the users found in the user directories.
	 * 
	 * @return Observable list of users
	 */
	public static ObservableList<User> getUsers() {
		return users;
	}

	/**
	 * Gets the user names of all the profiles.
	 * 
	 * @return Observable list of userNames
	 */
	public static ObservableList<String> getUserNames() {
		userNames.clear();
		for (User user : users) {
			userNames.add(user.getName());
		}
		return userNames;
	}

	/**
	 * Loads all the profiles from the user directories into the
	 * list of users.
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
	 * Clears the current users activity data and saves the changes.
	 */
	public static void clearUserActivityData() {
		Persistent.currentUser.clearEvents();
		Saver.SaveUser(Persistent.currentUser);
	}
	
	/**
	 * Deletes the given user and removes its directory and JSON files.
	 * @param user the user to be deleted
	 */
	public static void deleteUser(User user) {
		if(user != null) {
			users.remove(user);
			userNames.remove(user.getName());
			File path = new File(getFilePath() + "/" + user.getUserId());
			deleteDirectory(path);
		}
	}
	
	/**
	 * Deletes the files at filePath 
	 * (only public for use in testing)
	 * Only deletes Fitr directories and the files within them.
	 * Be Careful!
	 * @param path path to delete files from.
	 */
	public static void deleteDirectory(File path) {
		// Check that the file you are deleting isn't too important  
		if(path.exists() && (path.getParentFile().getName().equals("Fitr") || path.getName().equals("Fitr"))) {
			if(path.getName().equals("Fitr")) {
				userNames.clear();
				users.clear();
			}
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
			// dont want to delete things that arn't to do with Fitr.
			System.out.println("not deleting: " + path + ".. Not a Fitr folder");
		}
	}

	/**
	 * Used to clear the user preferences when reseting the program.
	 */
	public static void clear() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks that preferences have been stored and that it is safe to close the program.
	 * @throws BackingStoreException
	 */
	public static void exit() throws BackingStoreException {
		prefs.flush();
	}

	/**
	 * Main method used only for testing purposes 
	 * Clears the preferences if things have stopped working
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String args[]) throws Exception {
		//clear the preferences 
		clear();		
	}
}
