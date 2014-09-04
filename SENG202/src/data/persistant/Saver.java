package data.persistant;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.model.EventContainer;

/**
 * A Saver class used for saving an event container to a json file
 * @author SamSchofield
 */
public class Saver extends Persistent {
	
	/**
	 * Saves an event container to the file at file path
	 * @param eventContainer
	 */
	public void Save(EventContainer eventContainer) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String ECString = gson.toJson(eventContainer);
		
		try {
			FileWriter writer = new FileWriter(super.getFilePath());
			writer.write(ECString);
			writer.close();
		} catch (IOException e) {
			System.out.println("FilePath does not exist");
			e.printStackTrace();
		}
	}
}
