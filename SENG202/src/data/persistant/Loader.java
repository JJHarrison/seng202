package data.persistant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.model.EventContainer;



/**
 * A loader class used for loading an event container from a json file
 * @author SamSchofield
 */
public class Loader extends Persistent {
	
	/** 
	 * loads and returns an event container form the json file at file path
	 * @return EventContainer
	 */
	public EventContainer load() {
		
		EventContainer ec = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(super.getFilePath()));
			ec = gson.fromJson(br, EventContainer.class);
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return ec;
	}
	
}
