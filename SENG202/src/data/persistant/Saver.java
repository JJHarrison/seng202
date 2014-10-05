package data.persistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A Saver class used for saving an event container to a JSON file
 * 
 * @author SamSchofield
 */
public class Saver {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * saves the user profile to a JSON file
	 * 
	 * @param User, user to be saved
	 */
	public static void SaveUser(User user) {
		String profileString = gson.toJson(user);
		try {
			FileWriter writer = new FileWriter(Persistent.getProfileFilePath(user.getUserId()));
			writer.write(profileString);
			writer.close();
		} catch (IOException e) {
			System.out.println("FilePath does not exist");
			//e.printStackTrace();
		}
	}
	
	/**
	 * saves a profile picture (file) that the user has imported to the users directory
	 * @param picture the picture to be saved
	 * @param user the user who the profile picture is for
	 * @throws IOException
	 */
	public static void SaveProfilePicture(File picture, User user) throws IOException {
		InputStream input = null;
		OutputStream output = new FileOutputStream(Persistent.getProfileFilePath(user.getUserId()));
		try {
	    	  input = new FileInputStream(picture);
	    	  byte[] buf = new byte[1024];
	    	  int bytesRead;
	    	  while ((bytesRead = input.read(buf)) > 0) {
	    		  output.write(buf, 0, bytesRead);
	    	  }
	    	  
		} finally {
			input.close();
			output.close();
		}

	}

}
