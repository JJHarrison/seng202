package data.persistant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import user.UserProfile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.model.EventContainer;



/**
 * A loader class used for loading an event container from a json file
 * @author SamSchofield
 */
public class Loader {
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	/** 
	 * loads and returns an event container form the json file at file path
	 * @return EventContainer
	 */
	public static EventContainer loadEventContainer() {
		
		EventContainer ec = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(Persistent.getFilePath()));
			ec = gson.fromJson(br, EventContainer.class);
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return ec;
	}
	/** 
	 * loads and returns a user profile form the json file at file path
	 * @return EventContainer
	 */
	public static UserProfile loadUserProfile() {
		
		UserProfile user = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(Persistent.getFilePath()));
			user = gson.fromJson(br, UserProfile.class);
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return user;
	}
	
}
