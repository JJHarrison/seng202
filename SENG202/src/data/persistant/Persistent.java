package data.persistant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	private static User currentUser = User.mockUser(); // TODO JUST FOR TESTING

	/**
	 * Sets the FilePath preference to a Fitr directory at location of filePath
	 * Will only set a valid file path 
	 * @param filePath
	 * @throws FileNotFoundException 
	 */
	public static void setFilePath(String filePath) throws FileNotFoundException {
		// check that filePath is valid
		if(new File(filePath).exists()) { 
			prefs.put("FilePath", filePath + "/Fitr/");
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			
			// only creates new directory if the selected one doesn't already exist
			if (!new File(getFilePath()).exists()) {
				setupDirectory();
			} else { // if the Fitr directory already exists then use it.
				Persistent.initialize();
			}
		} else {
			//System.out.println("FILEPATH DOESNT EXIST");
			throw new FileNotFoundException();
		}
	}


	/**
	 * sets up a new Fitr directory with a users sub directory at the file path
	 */
	public static void setupDirectory() {
		if (getFilePath() != null) {
			new File(getFilePath()).mkdirs();
		}
	}

	/**
	 * gets the file path from preferences
	 * 
	 * @return FilePath
	 */
	public static String getFilePath() {
		return prefs.get("FilePath", null);
	}
	
	/**
	 * returns the file path to the current users profile
	 * 
	 * @return profileFilePath
	 */
	public static String getProfileFilePath(String userName) {
		return getFilePath() + userName + "/." + userName + ".fitr";
	}

	/**
	 * checks if a valid file path has been set
	 * 
	 * @return pathSet
	 */
	public static boolean filePathSet() {
		System.out.println("Checking filepath");
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
		System.out.println(pathSet);
		return pathSet;
	}

	/**
	 * creates a new directory in users with name user to store information in
	 * also adds a user.json and activity.json file to save data to
	 * 
	 * @param userName
	 * @return TODO
	 */
	public static boolean newUser(User user) throws Exception {
		boolean userAdded;
		String userName = user.getName();
		System.out.println(users.contains(user));
		System.out.println(userNames.contains(user.getName()));
		if (!users.contains(user)) {
			new File(getFilePath() + userName).mkdir();

			try {
				new File(getProfileFilePath(userName)).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			users.add(user);
			userNames.add(user.getName());
			
			Saver.SaveUser(user);
			prefs.putInt("LastUserID", prefs.getInt("LastUserID", 0) + 1);
			userAdded = true;
		} else {
			userAdded = false;
			System.out.println("User has already beeen created. try different username");
			// throw new Exception("User already exists");
		}
		return userAdded;
	}

	public static int getLastUserID() {
		return prefs.getInt("LastUserID", 0);
	}

	/**
	 * Sets the current user
	 * 
	 * @param user
	 */
	public static void setUser(User user) {
		//System.out.println("_----_");
		for(User u : users) {
			System.out.println(u);
		}
		//System.out.println("_----_");
		if(users.contains(user)){
		//	System.out.println("YAY");
			currentUser = user;
		} else {
			//System.out.println("NAY");
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
	 * returns an ArrayList of all the users found in the user directories
	 * 
	 * @return ArrayList<String>
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
	 * loads all the profiles from the users directory into the
	 * ObservableList<User> users
	 */
	public static void initialize() {
		if (getFilePath() != null && new File(getFilePath()).exists()) {
			File filePath = new File(getFilePath());

			// get files / directory in Fitr directory (gets users)
			File[] files = filePath.listFiles();

			
			
			for (File file : files) {
				String userName = file.getName(); 
				
				if (new File(file + "/." + userName + ".fitr").exists()) { 
					// check if the file is a user
					User newUser = Loader.loadUserProfile(new File(file + "/."+ userName + ".fitr"));
					
					if(newUser != null && !users.contains(newUser)){						users.add(newUser);
						userNames.add(newUser.getName());
					}
				}
			}
		}
	}
	
	public static void clear() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void exit() throws BackingStoreException {
		prefs.flush();
	}

	
	public static void main(String args[]) throws Exception {
		clear();
		/*
		 * setFilePath("/Users/SamSchofield/Desktop"); setupDirectory();
		 * initialize(); System.out.println("______________________"); User u =
		 * new User("a", null, null); User v = new User("b", null, null); //
		 * setUser(u); newUser(u); newUser(v);
		 * System.out.println(prefs.getInt("LastUserID", 0));
		 * 
		 * System.out.println(getFilePath()); System.out.println("Saved");
		 * System.out.println("Users are: ");
		 * 
		 * ArrayList<User> a = new ArrayList<User>(users); for (int i = 0; i <
		 * a.size(); i++) { System.out.println(a.get(i)); }
		 * 
		 * prefs.clear();
		 *
		 System.out.println("data cleared");
		
		EventContainer e = new EventContainer();
		User u = new User("Sam", null, null, 0, 0, e, 0);
		//User v = new User("Dan", null, null, 0, 0, e, 0);
		setUser(u);
		System.out.println(getCurrentUser());
		
		

	}
*/
	}
}
