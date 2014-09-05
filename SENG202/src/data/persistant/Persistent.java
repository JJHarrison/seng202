package data.persistant;

import java.io.File;
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
	
	public static void main(String args[]) throws BackingStoreException {
		setFilePath("/Users/SamSchofield/Desktop");
		setupDirectory();
		System.out.println(getFilePath());
		System.out.println("Saved");
	}
		
}
