package dataImport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import dataModel.DataPoint;
import dataModel.Event;


public class FileLoader {
	ArrayList<Event> events = new ArrayList<Event>();
	
	public static void main(String[] args) {
		FileLoader fl = new FileLoader();
		fl.load();
		System.out.println("File Loaded\n");	
		
		for(int i = 0; i < fl.events.size(); i++) {
			System.out.println(fl.events.get(i).getEventName());
			System.out.println("average speed is: " + fl.events.get(i).getAverageSpeed());
			System.out.println("total distance is: " + fl.events.get(i).getDistance());
			System.out.printf( "%.2f hours\n", fl.events.get(i).getDuration());
			System.out.println(fl.events.get(i).getDataPoints().get(0).getDate().getTime() + "\n");
			
		}
	}
	/**
	 * reads a csv file and create a new event at each #start
	 * add all following data points to the event
	 */
	public void load() {
		InputStream stream = FileLoader.class.getResourceAsStream("seng202_2014_example_data.csv");
		BufferedReader br = null;
		String line = "";
		DataPoint lastPoint = null;
		Event currentEvent = new Event("");
		
		try {
			br = new BufferedReader(new InputStreamReader(stream));
			
			while((line = br.readLine()) != null) {
				String[] dataLine = line.split(",");
				if (line.isEmpty()){
					//this is ugly and should be changed???
					// used to prevent crash if a empty line is read
				} else if(dataLine[0].contains("#start")){ // creates a new event when a new #start line is encountered
					currentEvent = new Event(dataLine[1]);
					events.add(currentEvent);
					lastPoint = null;
				} else {
					String[] dateString = dataLine[0].split("/");
					String[] time = dataLine[1].split(":");
					
					//months start from 0...
					Calendar date = new Calendar.Builder().setTimeOfDay(
							Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2])).setDate(
									Integer.parseInt(dateString[2]), Integer.parseInt(dateString[1]) - 1, Integer.parseInt(dateString[0])).build();
				
					int heartrate = Integer.parseInt(dataLine[2]);
					double latitude = Double.parseDouble(dataLine[3]);
					double longitude = Double.parseDouble(dataLine[4]);
					double altitude = Double.parseDouble(dataLine[5]);
					
					DataPoint point = new DataPoint(date, heartrate, latitude, longitude, altitude, lastPoint);					
					currentEvent.addDataPoint(point);
					lastPoint = point;
				}
			}
			stream.close();
			
		} catch (FileNotFoundException e){ 
			e.printStackTrace();
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("couldnt read line");
		}
		
	}	
}
