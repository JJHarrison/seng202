package data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class provides an abstract version of activity events provided by a
 * fitness tracking device. Each event consists of a collection of dataPoints
 * and attributes that relate to the activity event.
 * 
 * @author Fitr.Team
 */
public class Event implements Serializable{
	private String eventName;
	private Calendar startTime = new GregorianCalendar();
	private Calendar finishTime = new GregorianCalendar();
	private int numPoints;
	private double distance;
	private double maxSpeed;
	private double averageSpeed;
	private int totalHeartRate;
	private ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	private Graph heartRateGraph;
	private Graph stressLevelGraph;
	private Graph speedGraph;
	private Graph distanceGraph;
	private Graph caloriesGraph;

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
	
	public String getTimeString() {
	    SimpleDateFormat tf = new SimpleDateFormat("h:mm a");
	    
	    return String.format("%s - %s ", tf.format(startTime.getTime()), tf.format(finishTime.getTime()));
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
	
	public Graph getHeartRateGraph() {
		return heartRateGraph;
	}

	public Graph getStressLevelGraph() {
		return stressLevelGraph;
	}

	public Graph getspeedGraph() {
		return speedGraph;
	}

	public Graph getDistanceGraph() {
		return distanceGraph;
	}
	
	public Graph getCaloriesGraph() {
		// For calories, we only generate the graph if it doesn't exist
		// already. That way if the user's weight has changed since the graph
		// was generated, it will still be accurate.
		// We could do this with the other graphs too but it shouldn't be
		// needed and it will save space.
		if (caloriesGraph != null) {
			return caloriesGraph;
		} else {
			Graph g = new Graph("Calories Burned", "Time", "Calories");
			for (DataPoint p: points) {
				double weight = 75; // need to get user's actual weight
				double calories = p.getDistance() * 1.03 * weight;
				double time = p.getDate().getTimeInMillis() - this.getStartTime().getTimeInMillis();
				g.addPoint(time, calories);
			}
			return g;
		}
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
