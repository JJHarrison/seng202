package data.persistant;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Persistent {
	private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
	
	/**
	 * sets the file Path for where the object will be saved to
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		prefs.put("FilePath", filePath);
		
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getFilePath() {
		return prefs.get("filePath", null);
	}
}
