package data.persistant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

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
	 * Saves the user profile to a JSON file
	 * 
	 * @param user user to be saved
	 */
	public static void SaveUser(User user) {
		String profileString = gson.toJson(user);
		try {
			FileWriter writer = new FileWriter(Persistent.getProfileFilePath(user.getUserId()));
			writer.write(profileString);
			writer.close();
		} catch (IOException e) {
			// file path doesn't exists
			// The file path has been set using a file chooser so we will never reach this
			//e.printStackTrace();
		}
	}
	

	/**
	 * Saves a snapshot of an image to the users directory 
	 * @param image the image to be saved
	 * @param user the user to save the image to 
	 */
	public static void SaveProfilePicture(Image image, User user) {
		String userDir = new File(Persistent.getProfileFilePath(user.getUserId())).getParent();
	    File file = new File(userDir + "/profile.png");
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	    } catch (IOException e) {
	    	//file path doesn't exist
	        //System.out.println("Couldn't save picture. filePath not found");
	    	
	    }
	}

}
