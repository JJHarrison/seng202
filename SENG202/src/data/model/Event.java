package data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import data.persistant.Persistent;

/**
 * This class provides an abstract version of activity events provided by a
 * fitness tracking device. Each event consists of a collection of dataPoints
 * and attributes that relate to the activity event.
 * 
 * @author Simon
 */
public class Event implements Serializable {

	private static final long serialVersionUID = 2803079853022188247L;

	private String eventName;
	private Calendar startTime = new GregorianCalendar();
	private Calendar finishTime = new GregorianCalendar();
	private double distance; // in meters
	private double maxSpeed;
	private double averageSpeed;
	private int averageHeartRate;
	private int maxHeartRate;
	private int minHeartRate;
	private double caloriesBurned;
	private ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	private boolean hasBradycardia = false;
	private boolean hasTachycardia = false;

	/**
	 * The Event constructor, sets a name and set of points for the Event and
	 * calculates other values.
	 * 
	 * @param eventName the name of the Event.
	 * @param points all data points for the event.
	 */

	public Event(String eventName, ArrayList<DataPoint> points) {
		this.eventName = eventName;
		this.points = points;
		calculate();
		calculateStress();
		calculateWarnings();
	}

	/**
	 * Calculates max and average speed, average heart rate, and start and
	 * finish time using the DataPoints.
	 */
	private void calculate() {
		int numPoints = points.size();
		int totalHR = 0;
		minHeartRate = 1000;

		for (DataPoint point : points) {
			distance += point.getDistance();
			totalHR += point.getHeartRate();
			caloriesBurned += point.getCalories();
			if (maxSpeed < point.getSpeed()) {
				maxSpeed = point.getSpeed();
			}

			if (maxHeartRate < point.getHeartRate()) {
				maxHeartRate = point.getHeartRate();
			}
			
			if (minHeartRate > point.getHeartRate()) {
				minHeartRate = point.getHeartRate();
			}
		}

		startTime = points.get(0).getDate();
		finishTime = points.get(points.size() - 1).getDate();
		averageHeartRate = totalHR / numPoints;
		averageSpeed = distance / getDuration();
	}

	/**
	 * Calculates stress level based on the user's heart rate compared to their
	 * speed and normalises it to be between 0 and 100. The scale factor used
	 * only uses data from one event, so stress levels for different events
	 * should not be compared.
	 */
	private void calculateStress() {
		double sf = calculateStressFactor();
		double stress;

		// calculate stress
		for (DataPoint p : points) {
			if (p.getSpeed() != 0) {
				stress = sf * (p.getHeartRate() / p.getSpeed());
				if (stress < 0.5) {
					stress = 0.25 * stress + 0.25;
					p.setStressLevel(stress);
				} else {
					stress = 0.5 + (1 / (2* Math.PI))
							* Math.atan(stress - 1);
					p.setStressLevel(stress);
				}
			} else {
				p.setStressLevel(0.5);
			}
		}
	}

	/**
	 * Calculates if the user has bradycardia or tachycardia. Bradycardia is if
	 * their heart rate goes below 60 at any point. Tachycardia is if their 
	 * heart rate goes above 207 - 0.7*age at any point.
	 */
	private void calculateWarnings() {
		if (Persistent.getCurrentUser() != null) {
			for (DataPoint p : points) {
				if (p.getHeartRate() > 207 - (0.7 * Persistent.getCurrentUser()
						.getAge())) {
					hasTachycardia = true;
				} else if (p.getHeartRate() < 60) {
					hasBradycardia = true;
				}
			}
		}
	}

	/**
	 * Calculates a scale factor to be used to determine stress level so that
	 * it will tend to be around 50.
	 * @return average speed / average heart rate.
	 */
	private double calculateStressFactor() {
		double totalSpeed = 0.0;
		int totalHeartRate = 0;
		double avgSpeed, avgHeartRate;

		for (DataPoint p : points) {
			totalSpeed += p.getSpeed();
			totalHeartRate += p.getHeartRate();
		}

		avgSpeed = totalSpeed / (points.size() - 1);
		avgHeartRate = (float) totalHeartRate / points.size();

		if (avgHeartRate == 0) {
			return 0.0; // You are DEAD and therefore are not physically
						// stressed
		}
		return avgSpeed / avgHeartRate;
	}

	/**
	 * Prints the name of the activity event.
	 */
	public void printEventName() {
		System.out.println(eventName);
	}

	/**
	 * Returns the average speed of the activity event.
	 * 
	 * @return the average speed in meters per second.
	 */
	public double getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * Returns the max speed of the activity event.
	 * 
	 * @return the max speed in meters per second.
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Returns the average heart rate of the activity event.
	 * 
	 * @return the average heart rate in beats per minute.
	 */
	public int getAverageHeartRate() {
		return averageHeartRate;
	}

	/**
	 * Returns the max heart rate of the activity event.
	 * 
	 * @return the max heart rate in beats per minute.
	 */
	public int getMaxHeartRate() {
		return maxHeartRate;
	}
	
	public int getMinHeartRate() {
		return minHeartRate;
	}

	/**
	 * Returns the total distance traveled in the activity event.
	 * 
	 * @return the total distance in meters.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Returns the name of the activity event.
	 * 
	 * @return a String representation of the activity event name.
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * Returns the start time of the activity event.
	 * 
	 * @return the start time of the activity event as a calendar object.
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * Returns the finish time of the activity event.
	 * 
	 * @return the finish time of the activity event as a calendar object.
	 */
	public Calendar getFinishTime() {
		return finishTime;
	}

	public ArrayList<DataPoint> getPoints() {
		return points;
	}

	/**
	 * Returns the duration of the activity seconds.
	 * 
	 * @return the duration for the activity event in seconds.
	 */
	public long getDuration() {
		return (finishTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000;
	}

	/**
	 * Returns a string formated to HH:MM:SS for the duration of the event to be
	 * displayed in the event summary.
	 * 
	 * @return a String of the activity event's duration.
	 */
	public String getDurationString() {
		StringBuilder durationString = new StringBuilder();
		long duration = getDuration();
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
	 * Returns a nicely formatted distance string for use in the GUI.
	 * 
	 * @return the formatted distance string.
	 */
	public String getDistanceString() {
		String distanceString;
		if (getDistance() >= 1000.0) {
			distanceString = String.format("%.2f km", getDistance() / 1000);
		} else {
			distanceString = String.format("%d meter%s", (int) getDistance(),
					getDistance() == 1.0 ? "" : "s");
		}

		return distanceString;
	}

	/**
	 * Returns the date formated for the GUI event pane.
	 * @return the date formated for the GUI.
	 */
	public String getTimeString() {
		SimpleDateFormat tf = new SimpleDateFormat("EEEE, MMMM d, h:mm a");

		return String.format("%s", tf.format(startTime.getTime()));
	}

	/**
	 * Returns the data points of the activity event.
	 * 
	 * @return the dataPoints inside the activity event.
	 */
	public ArrayList<DataPoint> getDataPoints() {
		return this.points;
	}

	/**
	 * Returns the number of calories burned for the event.
	 * 
	 * @return calories burned for the event.
	 */
	public double getCaloriesBurned() {
		return caloriesBurned;
	}

	/**
	 * A method which takes a dataPoint and returns a string of the location in
	 * the form "lat, long".
	 * 
	 * @param point a single dataPoint.
	 * @return a string of the location of the dataPoint in the form "lat,long".
	 */
	public String getPointString(DataPoint point) {
		String latStr;
		String lonStr;
		StringBuilder pointString = new StringBuilder();

		latStr = String.format("%s", point.getLatitude());
		try {
			latStr = latStr.substring(0, 12);
		} catch (StringIndexOutOfBoundsException e) {
		}
		lonStr = String.format("%s", point.getLongitude());
		try {
			lonStr = lonStr.substring(0, 12);
		} catch (StringIndexOutOfBoundsException e) {
		}
		pointString.append(latStr);
		pointString.append(",");
		pointString.append(lonStr);
		return pointString.toString();
	}

	/**
	 * This method creates a string of Point Locations which is the path taken
	 * for the event.
	 * 
	 * @return a string of locations, in the form "lat, long", which is a path
	 *         for the event.
	 */
	public String getPathString() {
		int MAX_POINTS = 68; // Due to 2048 char limit for static maps.
		ArrayList<DataPoint> dataPoints = getDataPoints();
		int dataSize = dataPoints.size();
		StringBuilder pathBuilder = new StringBuilder();

		pathBuilder.append(getPointString(dataPoints.get(0)));
		pathBuilder.append("|");
		for (int i = 1; i < dataSize - 1; i += ((dataSize - 1) / MAX_POINTS) + 1) {
			pathBuilder.append(getPointString(dataPoints.get(i)));
			pathBuilder.append("|");
		}
		pathBuilder.append(getPointString(dataPoints.get(dataSize - 1)));

		return pathBuilder.toString();
	}

	/**
	 * Returns true if the user has Bradycardia.
	 * @return true if the user has Bradycardia, false otherwise.
	 */
	public boolean hasBradycardia() {
		return hasBradycardia;
	}
	
	/**
	 * Returns true if the user has Tachycardia.
	 * @return true if the user has Tachycardia, false otherwise.
	 */
	public boolean hasTachycardia() {
		return hasTachycardia;
	}

	/**
	 * Returns a string of the average heart rate for this event.
	 * @return the string representation of average heart rate for this event.
	 */
	public String avgHRString() {
		return String.format("%d bpm", getAverageHeartRate());
	}

	/**
	 * Returns a string of the maximum heart rate for this event.
	 * @return the string representation of the maximum heart rate for this event.
	 */
	public String maxHRString() {
		return String.format("%d bpm", getMaxHeartRate());
	}

	/**
	 * Returns the average speed of the event.
	 * @return a string representation of the average speed for this event.
	 */
	public String avgSpeedString() {
		return String.format("%.1f km / h", getAverageSpeed() * 3.6);
	}

	/**
	 * Returns the maximum speed for this event.
	 * @return the string representation of the maximum speed for this event.
	 */
	public String maxSpeedString() {
		return String.format("%.1f km / h", getMaxSpeed() * 3.6);
	}

	/**
	 * Returns the calories burnt for this event.
	 * @return a string representation for the calories burned for this event.
	 */
	public String getCaloriesString() {
		return String.format("%.0f", getCaloriesBurned());
	}

	/**
	 * Returns true if the Events have the same name and same start time.
	 * @return true if the Events are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		boolean equal = false;
		if (other != null && other.getClass() == Event.class) {
			Event e = (Event) other;
			if (e.getStartTime().equals(startTime)
					&& e.getEventName().equals(eventName)) {
				equal = true;
			}
		}
		return equal;

	}
}
