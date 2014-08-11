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
			System.out.println("Speed is: " + fl.events.get(i).points.get(3).getSpeed());
			System.out.println("Time is: " + fl.events.get(i).points.get(3).getdTime());
			System.out.println("distance is: " + fl.events.get(i).points.get(3).getDistance());
			//System.out.println(fl.events.get(i).points.get(3).dTime);
			System.out.println(fl.events.get(i).eventName + "\n");
			d += fl.events.get(i).points.get(3).getDistance();
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
					double distance = getDistance(dataLine[3], dataLine[4], lastPoint.getLat(), lastPoint.getLon());
					int time = getTime(dataLine[1], lastPoint.getTime());
					DataPoint p = new DataPoint(dataLine[0], dataLine[1], dataLine[2], dataLine[3], dataLine[4], dataLine[5], distance, time);
					lastPoint = p;
					currentEvent.points.add(p);
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
}
