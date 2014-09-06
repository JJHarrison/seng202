package data.persistant;

import java.io.FileWriter;
import java.io.IOException;

import user.UserProfile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.model.EventContainer;

/**
 * A Saver class used for saving an event container to a json file
 * @author SamSchofield
 */
public class Saver {
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	/**
	 * Saves an event container to the file at file path
	 * @param eventContainer
	 */
	public static void SaveActivityData(EventContainer eventContainer) {
		
		String ECString = gson.toJson(eventContainer);

		try {
			FileWriter writer = new FileWriter(Persistent.getFilePath());
			writer.write(ECString);
			writer.close();
		} catch (IOException e) {
			System.out.println("FilePath does not exist");
			e.printStackTrace();
		}
	}
	
	/**
	 * saves the user profile to a .json file
	 * @param profile
	 */
	public static void SaveUser(UserProfile profile) {
		String profileString = gson.toJson(profile);
		
		try {
			FileWriter writer = new FileWriter(Persistent.getFilePath());
			writer.write(profileString);
			writer.close();
		} catch (IOException e) {
			System.out.println("FilePath does not exist");
			e.printStackTrace();
		}
	}
}
