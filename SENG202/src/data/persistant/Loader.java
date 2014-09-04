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
public class Loader {
	
	private String filePath;
	
	/** 
	 * loads and returns an event container form the json file at file path
	 * @return EventContainer
	 */
	public EventContainer load() {
		
		EventContainer ec = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			ec = gson.fromJson(br, EventContainer.class);
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return ec;
	}
	
	/**
	 * sets the file path to load the event container from 
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
