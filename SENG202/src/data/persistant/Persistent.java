package data.persistant;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Persistent {
	
	private static Preferences prefs = Preferences.userRoot().node("/fitr");
	
	public static void setFilePath(String filePath) {
		prefs.put("FilePath", filePath);
	}
	
	public static String getFilePath() {
		return prefs.get("FilePath", null);
	}
	
	public static boolean firstOpen() {
		boolean firstOpen = prefs.getBoolean("FirstOpen", false);
		prefs.putBoolean("FirstOpen", false);
		return firstOpen;
	}
	
	public static void main(String args[]) throws BackingStoreException {
		System.out.println(getFilePath());
		System.out.println("Saved");
	}
	
}
