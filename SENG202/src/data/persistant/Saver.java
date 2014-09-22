package data.persistant;

import java.io.FileWriter;
import java.io.IOException;

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
	 * @param profile
	 */
	public static void SaveUser(User user) {
		String profileString = gson.toJson(user);
		try {
			FileWriter writer = new FileWriter(
					Persistent.getProfileFilePath(Integer.toString(user
							.getUserId())));
			writer.write(profileString);
			writer.close();
		} catch (IOException e) {
			System.out.println("FilePath does not exist");
			e.printStackTrace();
		}
	}
}
