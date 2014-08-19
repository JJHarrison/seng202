package dataImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dataModel.DataPoint;
import dataModel.Event;

/**
 * This class provides a way of reading activity events provided by a fitness
 * tracking device. The activity data needs to be in a csv format consisting of
 * date, time, heart rate, latitude, longitude & altitude.
 * 
 * @author Fitr.Team
 */
public class FileLoader {
	private InputStream inputStream;
	
	public void clearStream(){
		inputStream=null;
	}
	
	public InputStream getStream() {
		return inputStream;
	}

	public FileLoader(File file) {
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			clearStream();
		}
	}

	public FileLoader() {
		inputStream = this.getClass().getResourceAsStream(
				"seng202_2014_example_data.csv");
	}

	private ArrayList<Event> events = new ArrayList<Event>();

	/**
	 * reads a csv file and create a new event at each #start add all following
	 * data points to the event
	 */
	public void load() {
		BufferedReader br = null;
		String line = "";
		DataPoint lastPoint = null;
		Event currentEvent = new Event("");

		try {
			br = new BufferedReader(new InputStreamReader(inputStream));

			while ((line = br.readLine()) != null) {
				String[] dataLine = line.split(",");
				if (!line.isEmpty()) {
					if (dataLine[0].contains("#start")) {
						currentEvent = new Event(dataLine[1]);
						events.add(currentEvent);
						lastPoint = null;
					} else {
						String[] dateString = dataLine[0].split("/");
						String[] time = dataLine[1].split(":");
	
						// months start from 0...
						Calendar date = new GregorianCalendar(
								Integer.parseInt(dateString[2]), // Year
								Integer.parseInt(dateString[1]), // Month
								Integer.parseInt(dateString[0]), // Day
								Integer.parseInt(time[0]), // Hour
								Integer.parseInt(time[1]), // Minute
								Integer.parseInt(time[2])); // Second
	
						int heartrate = Integer.parseInt(dataLine[2]);
						double latitude = Double.parseDouble(dataLine[3]);
						double longitude = Double.parseDouble(dataLine[4]);
						double altitude = Double.parseDouble(dataLine[5]);
	
						DataPoint point = new DataPoint(date, heartrate, latitude,
								longitude, altitude, lastPoint);
						currentEvent.addDataPoint(point);
						lastPoint = point;
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("couldnt read line");
		}

	}

	public ArrayList<Event> getEvents() {
		return events;
	}
}
