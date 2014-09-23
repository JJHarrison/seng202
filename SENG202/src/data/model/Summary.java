package data.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * manages summaries over a given time
 * used with dash board
 * @author SamSchofield
 *
 */
public class Summary {
	
	private ArrayList<Event> events;
	private double totalDistance;
	private long totalHours;
	private double totalCalories;
	
	private double maxDistance;
	private double maxCalories;
	private long maxDuration;
	private double maxSpeed;
	private long maxHeartRate;
	
	/**
	 * Sets up a summary for the events over the time period startTime - endTime
	 * @param events the event container of all events
	 * @param startTime the start of the time period the summary is for
	 * @param endTime the end of the time period the summary is for
	 */
	public Summary(EventContainer events, Calendar startTime, Calendar endTime) {
		if(endTime == null) {
			endTime = Calendar.getInstance();
		}
		
		if(startTime == null) {
			startTime = new Calendar.Builder().build();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Event> eventsNeeded = new ArrayList<Event>();
		HashMap<String, LinkedList<Event>> days = events.getDays();
		
		for(String dateString : days.keySet()) {
			try {
				Calendar day = new Calendar.Builder().setInstant(sdf.parse(dateString)).build();
				// only allow days between the start date and end date inclusive 
				if(day.compareTo(startTime) >= 0 && day.compareTo(endTime) <= 0) {
					eventsNeeded.addAll(days.get(dateString));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.events = eventsNeeded;
		calculate();
	}
	
	/**
	 * calculates all the values once the correct events have been determined 
	 */
	public void calculate() {
		for (Event event : events) {
			totalHours += event.getDuration();
			totalCalories += event.getCaloriesBurned();
			totalDistance += event.getDistance();
			
			if(maxCalories < event.getCaloriesBurned()) {
				maxCalories = event.getCaloriesBurned();
			}
			
			if(maxDuration < event.getDuration()) {
				maxDuration = event.getDuration();
			}
			
			if(maxDistance < event.getDistance()) {
				maxDistance = event.getDistance();
			}
			
			System.out.println("-->" + event.getMaxSpeed());
			if(maxSpeed < event.getMaxSpeed()) {
				maxSpeed = event.getMaxSpeed();
			}
			
			if(maxHeartRate < event.getMaxHeartRate()) {
				maxHeartRate = event.getMaxHeartRate();
			}
		}
	}
	
	/**
	 * gets the events that are between the start and end dates
	 * @return events 
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	/** 
	 * gets the number of events over the time period
	 * @return numberOfEvents
	 */
	public int getNumberOfEvents() {
		return events.size();
	}
	
	/**
	 * gets the total duration of all the events over the time period
	 */
	public String getTotalDuration() {
		return String.format("%d hours logged", totalHours / 3600);
	}
	
	/**
	 * gets the total distance traveled over the time period
	 */
	public String getTotalDistance() {
		return String.format("%.1f km travelled", totalDistance / 1000);	
	}
	
	/**
	 * gets the total calories burned over the time period
	 */
	public String getTotalCalories() {
		return String.format("%.0f calories burned", totalCalories);
	}
	
	/**
	 * Returns a string formated to HH:MM:SS for the duration of the event to be
	 * displayed in the event summary.
	 * 
	 * @return A string of the activity events duration.
	 */
	private String getDurationString(long time) {
		StringBuilder durationString = new StringBuilder();
		long duration = time;
		long days = duration / (3600 * 24);
		duration -= days * 3600 * 24;
		long hours = duration / 3600;
		duration -= hours * 3600;
		long minutes = duration / 60;
		duration -= minutes * 60;
		long seconds = duration;

		if (days > 0) {
			durationString.append(String.format("%d day%s %d hour%s", days,
					days == 1 ? "" : "s", hours, hours == 1 ? "" : "s"));
		} else if (hours > 0) {
			durationString.append(String.format("%d hour%s %d minute%s", hours,
					hours == 1 ? "" : "s", minutes, minutes == 1 ? "" : "s"));
		} else if (minutes > 0) {
			durationString.append(String.format("%d minute%s %d second%s",
					minutes, minutes == 1 ? "" : "s", seconds,
					seconds == 1 ? "" : "s"));
		} else {
			durationString.append(String.format("%d second%s", seconds,
					seconds == 1 ? "" : "s"));
		}
		return durationString.toString();
	}
	
	/**
	 * get the maximum distance traveled in the time period
	 */
	public String getMaxDistance() {
		return String.format("%.1f km", maxDistance);
	}
	
	/**
	 * get the maximum number of calories burned in an event in the time period 
	 */
	public String maxCalories() {
		return String.format("%.0f cal", maxCalories);
	}

	/**
	 * get the maximum speed in an event in the time period
	 */
	public String maxSpeed() {
		return String.format("%.1f kph", maxSpeed);
	}	
	
	/**
	 * gets the maximum duration for an event in a tine period
	 * @return
	 */
	public String getMaxDuration() {
		return getDurationString(maxDuration);
	}
	
	/**
	 * gets the maximum heart rate in any event in the time period 
	 */
	public String maxHeartRate() {
		return String.format("%d bpm", maxHeartRate);
	}

}
