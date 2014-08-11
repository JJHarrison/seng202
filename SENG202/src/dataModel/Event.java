package dataModel;

import java.util.ArrayList;
import java.util.Calendar;

public class Event {
	public String eventName;
	public Calendar startTime;
	public Calendar finishTime;
	
	/**
	 * Constructor.
	 * @param eventName The name of the event.
	 */
	public Event(String eventName) {
		this.eventName = eventName;
	}
	
	String data;
	public ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	
	public void printEvent(){
		System.out.println(eventName);
	}

}
