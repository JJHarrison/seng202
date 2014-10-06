package data.persistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.scene.image.Image;
import user.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A loader class used for loading an event container from a JSON file.
 * 
 * @author SamSchofield
 */
public class Loader {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * loads and returns a user profile from the JSON file at file path
	 * 
	 * @return EventContainer
	 */
	public static User loadUserProfile(File filepath) {

		User user = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			user = gson.fromJson(br, User.class);

		} catch (FileNotFoundException e) {
			System.out.println("User not found");
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Gets the users profile picture from their user directory 
	 * @param user user to get profile picture for
	 * @return Image The profile image
	 * @throws FileNotFoundException
	 */
	public static Image loadProfileImage(User user) throws FileNotFoundException {
		String userDir = new File(Persistent.getProfileFilePath(user.getUserId())).getParent();
		FileInputStream imagePath = new FileInputStream(new File(userDir + "/profile.jpg"));
		Image image = new Image(imagePath);
		return image;
	}

}
