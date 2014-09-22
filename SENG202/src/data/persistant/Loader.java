package data.persistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import user.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A loader class used for loading an event container from a JSON file
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
			System.out.println(filepath);
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			user = gson.fromJson(br, User.class);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return user;
	}

}
