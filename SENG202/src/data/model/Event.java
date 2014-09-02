package data.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class provides an abstract version of activity events provided by a
 * fitness tracking device. Each event consists of a collection of dataPoints
 * and attributes that relate to the activity event.
 * 
 * @author Fitr.Team
 */
public class Event {
	private String eventName;
	private Calendar startTime;
	private Calendar finishTime;
	private int numPoints;
	private double distance;
	private double maxSpeed;
	private double averageSpeed;
	private int totalHeartRate;
	private ArrayList<DataPoint> points = new ArrayList<DataPoint>();

	/**
	 * Constructor.
	 * 
	 * @param eventName
	 *            The name of the event.
	 */
	public Event(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * Prints the name of the activity event.
	 */
	public void printEventName() {
		System.out.println(eventName);
	}

	/**
	 * Sets the start time of the activity event.
	 * 
	 * @param c
	 *            The start time of the event.
	 */
	public void setStartTime(Calendar c) {
		this.startTime = c;
	}

	/**
	 * Sets the finish time of the activity event.
	 * 
	 * @param c
	 *            The finish time of the event.
	 */
	public void setFinishTime(Calendar c) {
		this.finishTime = c;
	}

	/**
	 * Adds a dataPoint to the activity event.
	 * 
	 * @param p
	 *            The dataPoint to be added to the activity event.
	 */
	public void addDataPoint(DataPoint p) {
		points.add(p);
		distance += p.getDistance();
		averageSpeed = calculateAverageSpeed(p);
		numPoints += 1;
		finishTime = p.getDate();
		startTime = this.getDataPoints().get(0).getDate();
		totalHeartRate += p.getHeartRate();

		if (maxSpeed < p.getSpeed()) {
			maxSpeed = p.getSpeed();
		}
	}

	/**
	 * Gets a summary of the activity event consisting of the event name, date,
	 * start time, end time, average speed, calories burned, average heart rate.
	 * The summary is returned as a String.
	 * 
	 * @return A String summary of the activity event.
	 */
	public String getSummary() {
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		String startTime = tf.format(this.startTime.getTime());
		String endTime = tf.format(this.finishTime.getTime());
		String date = df.format(this.startTime.getTime());

		String summary = String
				.format("Event Name: %s\nDate: %s\nDuration: %s\nStart Time: %s\nEnd Time: %s\nAverage Speed: %.2f m/s\nMax Speed: %.2f m/s\nDistance:"
						+ " %.0f m\nCalories Burned: %.0f\nAverage Heart Rate: %d",
						eventName, date, getDurationString(), startTime,
						endTime, getAverageSpeed(), maxSpeed, distance,
						getCaloriesBurned(), getAverageHeartRate());

		return summary;
	}

	private double calculateAverageSpeed(DataPoint p) {
		double newAverageSpeed = ((numPoints * averageSpeed) + p.getSpeed())
				/ (numPoints + 1);
		return newAverageSpeed;
	}

	/**
	 * Returns the average speed of the activity event.
	 * 
	 * @return The average speed in meters per second
	 */
	public double getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * Returns the average heart rate of the activity event.
	 * 
	 * @return The average heart rate in beats per minute.
	 */
	public int getAverageHeartRate() {
		return totalHeartRate / numPoints;
	}

	/**
	 * Returns the max speed of the activity event.
	 * 
	 * @return The max speed in meters per second.
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Returns the total distance traveled in the activity event.
	 * 
	 * @return The total distance in meters.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Returns the name of the activity event.
	 * 
	 * @return A String of containing the activity event name.
	 */
	public String getEventName() {
		return this.eventName;
	}

	/**
	 * Returns the start time of the activity event.
	 * 
	 * @return The start time of the activity event as a calendar object.
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}

	/**
	 * Returns the finish time of the activity event.
	 * 
	 * @return The finish time of the activity event as a calendar object
	 */
	public Calendar getFinishTime() {
		return this.finishTime;
	}

	/**
	 * Returns the duration of the activity event.
	 * 
	 * @return The duration for the activity event in hours.
	 */

	public double getDuration() {
		return (finishTime.getTimeInMillis() - startTime.getTimeInMillis())
				/ (1000.0 * 60 * 60);
	}

	/**
	 * Returns a string formated to HH:MM:SS for the duration of the event to be
	 * displayed in the event summary.
	 * 
	 * @return A string of the activity events duration.
	 */
	public String getDurationString() {
		long seconds = ((finishTime.getTimeInMillis() - startTime
				.getTimeInMillis()) / 1000);
		String duration = String.format("%02d:%02d:%02d", seconds / 3600,
				(seconds % 3600) / 60, (seconds % 60));
		return duration;
	}

	/**
	 * Returns the data points of the activity event.
	 * 
	 * @return The dataPoints inside the activity event.
	 */
	public ArrayList<DataPoint> getDataPoints() {
		return this.points;
	}

	/**
	 * Returns an estimation of calories burned during the activity event.
	 * 
	 * @return The calories burned for the activity events.
	 */
	public double getCaloriesBurned() {
		int weight = 75;
		double runMET = 7.5;
		double timeInHours = getDuration();

		double calories = weight * runMET * timeInHours;
		return calories;
	}

	/**
	 * Returns a String representation of the events name.
	 * 
	 * @return The events name as a String.
	 */
	@Override
	public String toString() {
		return getEventName()
				+ "\n"
				+ new SimpleDateFormat("dd/MM/yyyy").format(getStartTime()
						.getTime());
	}
}
