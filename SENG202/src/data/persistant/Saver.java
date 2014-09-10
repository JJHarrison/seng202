package data.persistant;

import java.io.FileWriter;
import java.io.IOException;

import user.User;

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
	public static void SaveActivityData(EventContainer eventContainer, User user) {
		
		String ECString = gson.toJson(eventContainer);

		try {
			FileWriter writer = new FileWriter(Persistent.getActivityFilePath(user.getName()));
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
	public static void SaveUser(User user) {
		String profileString = gson.toJson(user);
		try {
			FileWriter writer = new FileWriter(Persistent.getProfileFilePath(user.getName()));
			writer.write(profileString);
			writer.close();
		} catch (IOException e) {
			System.out.println("FilePath does not exist");
			e.printStackTrace();
		}
	}
}
