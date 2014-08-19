package dataImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
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
	private File file;

	public FileLoader(File file) {
		this.file = file;
	}

	public FileLoader() {
		URL url =
		this.getClass().getResource("seng202_2014_example_data.csv");
		try {
			this.file = new File(url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<Event> events = new ArrayList<Event>();

	public static void main(String[] args) {
		FileLoader fl = new FileLoader();
		fl.load();
		System.out.println("File Loaded\n");

		for (int i = 0; i < fl.events.size(); i++) {
			/*
			 * System.out.println(fl.events.get(i).getEventName());
			 * System.out.println("average speed is: " +
			 * fl.events.get(i).getAverageSpeed());
			 * System.out.println("total distance is: " +
			 * fl.events.get(i).getDistance()); System.out.printf(
			 * "%.2f hours\n", fl.events.get(i).getDuration());
			 * System.out.println
			 * (fl.events.get(i).getDataPoints().get(0).getDate().getTime() +
			 * "\n");
			 */
			System.out.print(fl.events.get(i).getSummary() + "\n");
		}
	}

	/**
	 * reads a csv file and create a new event at each #start add all following
	 * data points to the event
	 */
	public void load() {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(this.file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//InputStream stream = FileLoader.class.getResourceAsStream(file.getAbsolutePath());
		BufferedReader br = null;
		String line = "";
		DataPoint lastPoint = null;
		Event currentEvent = new Event("");

		try {
			br = new BufferedReader(new InputStreamReader(stream));

			while ((line = br.readLine()) != null) {
				String[] dataLine = line.split(",");
				if (line.isEmpty()) {
					// this is ugly and should be changed???
					// used to prevent crash if a empty line is read
				} else if (dataLine[0].contains("#start")) { // creates a new
																// event when a
																// new #start
																// line is
																// encountered
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
			stream.close();

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
