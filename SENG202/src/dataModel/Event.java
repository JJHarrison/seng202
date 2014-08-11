package dataModel;

import java.util.ArrayList;

public class Event {
	String eventName;
	String data;
	ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	
	public Event(String name){
		eventName = name;
	}
	
	public void printEvent(){
		System.out.println(eventName);
	}

}
