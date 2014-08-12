package dataImport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dataModel.DataPoint;
import dataModel.Event;



public class FileLoader {
	ArrayList<Event> events = new ArrayList<Event>();
	
	public static void main(String[] args) {
		FileLoader fl = new FileLoader();
		fl.load();
		double d = 0;
		System.out.println("File Loaded\n");
		
		//print out events and their date
		for(int i = 0; i < fl.events.size(); i++){
			System.out.println("Speed is: " + fl.events.get(i).getDataPoints().get(3).getSpeed());
			System.out.println("Time is: " + fl.events.get(i).getDataPoints().get(3).getDeltaTime());
			System.out.println("distance is: " + fl.events.get(i).getDataPoints().get(3).getDistance());
			//System.out.println(fl.events.get(i).points.get(3).dTime);
			System.out.println(fl.events.get(i).getEventName() + "\n");
			d += fl.events.get(i).getDataPoints().get(3).getDistance();
		}
		System.out.print(d);
		
		
	}
	
	
	public void load() {
		InputStream stream = FileLoader.class.getResourceAsStream("seng202_2014_example_data.csv");
		
		BufferedReader br = null;
		String line = "";
		String split = ",";
		Event currentEvent = new Event("");
		DataPoint lastPoint = new DataPoint(null, 0, 0, 0, 0, null);
		
		try {
			br = new BufferedReader(new InputStreamReader(stream));
			while((line = br.readLine()) != null) {
				String[] dataLine = line.split(split);
				
				if(dataLine[0].contains("#start")){
					currentEvent = new Event(dataLine[1]);
					events.add(currentEvent);
				} else {
					double distance = getDistance(dataLine[3], dataLine[4], lastPoint.getLatitude(), lastPoint.getLongitude());
					int time = getTime(dataLine[1], lastPoint.getDeltaTime());
					// need to make this into new constructor format
					DataPoint p = new DataPoint(dataLine[0], dataLine[1], dataLine[2], dataLine[3], dataLine[4], dataLine[5], distance, time);
					lastPoint = p;
					currentEvent.getDataPoints().add(p);
				}
				
				
			}
		} catch (FileNotFoundException e){ 
			e.printStackTrace();
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("couldnt read line");
		}
	}
	
	private int getTime(String currentTime, String lastTime) {
		// returns the time difference between the previous and last data points
		// need to fix so that it works over midnight
		int dTime = 0;
		String[] cTime = currentTime.split(":");
		String[] lTime = lastTime.split(":");
		
		int cTimeInSec = (Integer.parseInt(cTime[0]) * 60 * 60) + (Integer.parseInt(cTime[1]) * 60) + (Integer.parseInt(cTime[2]));
		int lTimeInSec = (Integer.parseInt(lTime[0]) * 60 * 60) + (Integer.parseInt(lTime[1]) * 60) + (Integer.parseInt(lTime[2]));
		
		dTime = cTimeInSec - lTimeInSec;
		return dTime;
	}
	
	private double getDistance(String currentLat, String currentLon, double d, double e) {
		// returns the distance between the current data point and the previous data point
		// this doesn't  give the right distance 
		double distance = 0;
		double radius = 6373 * 1000;
		double lat2 = Double.parseDouble(d);
		double lat1 = Double.parseDouble(currentLat);
		
		double dlon = Float.parseFloat(currentLon) - Float.parseFloat(e);
		double dlat = lat1 - lat2; 
		
		double a = Math.pow(Math.sin(Math.toRadians(dlat / 2)), 2) + (Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(Math.toRadians(dlon / 2)),2));
		
		double c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1-a) );
		distance = radius * c;
		
		return distance;
	}

}
