package dataModel;

import java.util.ArrayList;
import java.util.Calendar;

import dataModel.DataPoint;

public class Event {
	private String eventName;
	private Calendar startTime;
	private Calendar finishTime;
	private int numPoints;
	private double distance;
	private double averageSpeed;
	
	private ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	
	
	// GET RID OF THIS AFTER DANIEL IS DONE WITH IT
	public Event() {
		this.eventName = "Event";
		this.startTime = new Calendar.Builder().setDate(2000, 1, 1).setTimeOfDay(12, 0, 0).build();
		this.finishTime = new Calendar.Builder().setDate(2000, 1, 1).setTimeOfDay(1, 0, 0).build();
		this.numPoints = 10;
		this.distance = 1000;
		this.averageSpeed = 6;
	}
	
	/**
	 * Constructor.
	 * @param eventName The name of the event.
	 */
	public Event(String eventName) {
		this.eventName = eventName;
	}
	
	public void printEvent(){
		System.out.println(eventName);
	}
	
	public void setStartTime(Calendar c) {
		this.startTime = c;
	}
	
	public void setFinishTime(Calendar c) {
		this.finishTime = c;
	}
	
	public void addDataPoint(DataPoint p) {
		this.points.add(p);
		this.numPoints += 1;
		this.distance += p.getDistance();
		this.averageSpeed = (averageSpeed*numPoints + p.getSpeed()) / numPoints;
	}
	
	public double getAverageSpeed() {
		return averageSpeed;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	public Calendar getFinishTime() {
		return this.finishTime;
	}
	
	public ArrayList<DataPoint> getDataPoints() {
		return this.points;
	}
}
