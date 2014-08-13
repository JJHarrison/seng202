package dataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dataModel.DataPoint;

public class Event {
	private String eventName;
	private Calendar startTime;
	private Calendar finishTime;
	private int numPoints;
	private double distance;
	private double maxSpeed;
	private double totalSpeed;
	private int totalHeartRate;
	
	private ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	
	
	// GET RID OF THIS AFTER DANIEL IS DONE WITH IT
	public Event() {
		this.eventName = "Event";
		this.startTime = new Calendar.Builder().setDate(2000, 1, 1)
							.setTimeOfDay(12, 0, 0).build();
		this.finishTime = new Calendar.Builder().setDate(2000, 1, 1)
							.setTimeOfDay(1, 0, 0).build();
		this.numPoints = 10;
		this.distance = 1000;
		this.points.add(new DataPoint(new Calendar.Builder().setDate(
						2000, 1, 1).setTimeOfDay(12, 0, 0).build(),
						100, 10000, 10000, 50, null));
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
		
		//this.averageSpeed = (averageSpeed*numPoints + p.getSpeed()) / numPoints;
		totalSpeed += p.getSpeed();
		this.finishTime = p.getDate();
		this.startTime = this.getDataPoints().get(0).getDate();
		
		this.totalHeartRate += p.getHeartRate();
		
		if(maxSpeed < p.getSpeed()) {
			maxSpeed = p.getSpeed();
		}
	}
	
	public String getSummary() {
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		String startTime = tf.format(this.startTime.getTime());
		String endTime = tf.format(this.finishTime.getTime());
		String date = df.format(this.startTime.getTime());
		
		String summary = String.format("Summary:\nEvent Name: %s\nDate: %s\nStart Time: %s\nEnd Time: %s\nAverageSpeed: %.2f m/s\nMax Speed: %.2f m/s\nDistance:"
				+ " %.0f m\nCalories Burned: %.0f\nAverage HR: %d", eventName, date, startTime, endTime, getAverageSpeed(), maxSpeed, distance, getCaloriesBurned(), getAverageHeartRate());
		
		return summary;
	}
	
	public double getAverageSpeed() {
		return (totalSpeed / numPoints);
	}
	
	public int getAverageHeartRate() {
		return totalHeartRate / numPoints;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
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
	
	public double getDuration() {
		return (finishTime.getTimeInMillis() - startTime.getTimeInMillis()) / (1000.0 * 60 * 60);
	}
	
	public ArrayList<DataPoint> getDataPoints() {
		return this.points;
	}
	
	public double getCaloriesBurned() {
		int weight = 75;
		double runMET = 7.5;
		double timeInHours = getDuration();
		
		double calories = weight * runMET * timeInHours;
		return calories;
	}
	
	@Override
	public String toString() {
		return getEventName();
	}
}
