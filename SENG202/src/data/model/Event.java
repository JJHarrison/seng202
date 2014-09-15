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
public class Event implements Serializable {

    private static final long serialVersionUID = 2803079853022188247L;

    private String eventName;
    private Calendar startTime = new GregorianCalendar();
    private Calendar finishTime = new GregorianCalendar();
    private double distance;
    private double maxSpeed;
    private double averageSpeed;
    private int averageHeartRate;
    private int maxHeartRate;
    private double caloriesBurned;
    private ArrayList<DataPoint> points = new ArrayList<DataPoint>();

    // private Graph heartRateGraph;
    // private Graph stressLevelGraph;
    // private Graph speedGraph;
    // private Graph distanceGraph;
    private Graph caloriesGraph;

    /**
     * Constructor
     * 
     * @param eventName
     * @param points
     *            All data points for the event
     */
    public Event(String eventName, ArrayList<DataPoint> points) {
	this.eventName = eventName;
	this.points = points;
	calculate();
    }

    /**
     * Calculates max and average speed, using the data points
     */
    private void calculate() {
	int numPoints = points.size();
	int totalHR = 0;

	for (DataPoint point : points) {
	    distance += point.getDistance();
	    totalHR += point.getHeartRate();

	    if (maxSpeed < point.getSpeed()) {
		maxSpeed = point.getSpeed();
	    }

	    if (maxHeartRate < point.getHeartRate()) {
		maxHeartRate = point.getHeartRate();
	    }
	}

	startTime = points.get(0).getDate();
	finishTime = points.get(points.size() - 1).getDate();
	averageHeartRate = totalHR / numPoints;
	averageSpeed = distance / getDuration();
	setCaloriesBurned();
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
     * @return The average speed in meters per second
     */
    public double getAverageSpeed() {
	return averageSpeed;
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
     * Returns the average heart rate of the activity event.
     * 
     * @return The average heart rate in beats per minute.
     */
    public int getAverageHeartRate() {
	return averageHeartRate;
    }

    /**
     * Returns the max heart rate of the activity event.
     * 
     * @return The max heart rate in beats per minute.
     */
    public int getMaxHeartRate() {
	return maxHeartRate;
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
	return eventName;
    }

    /**
     * Returns the start time of the activity event.
     * 
     * @return The start time of the activity event as a calendar object.
     */
    public Calendar getStartTime() {
	return startTime;
    }

    /**
     * Returns the finish time of the activity event.
     * 
     * @return The finish time of the activity event as a calendar object
     */
    public Calendar getFinishTime() {
	return finishTime;
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
	SimpleDateFormat tf = new SimpleDateFormat("EEEE, MMMM d, h:mm a");

	return String.format("%s", tf.format(startTime.getTime()));
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
    private void setCaloriesBurned() {
	int weight = 75;
	double runMET = 7.5;
	double timeInHours = getDuration();
	double calories = weight * runMET * timeInHours;
	caloriesBurned = calories;
    }

    public double getCaloriesBurned() {
	return caloriesBurned;
    }

    public Graph getHeartRateGraph() {
	Graph g = new Graph("Heart Rate", "Time (s)", "Heart Rate (bpm)");
	for (DataPoint p : points) {
	    double hr = p.getHeartRate();
	    double time = p.getDate().getTimeInMillis()
		    - this.getStartTime().getTimeInMillis();
	    g.addPoint(time, hr);
	}
	return g;
    }

    public Graph getStressLevelGraph() {
	Graph g = new Graph("Stress Level", "Time (s)", "Stress");
	// TODO
	return g;
    }

    public Graph getSpeedGraph() {
	Graph g = new Graph("Speed", "Time (s)", "Speed (m/s)");
	for (DataPoint p : points) {
	    double speed = p.getSpeed();
	    double time = p.getDate().getTimeInMillis()
		    - this.getStartTime().getTimeInMillis();
	    g.addPoint(time, speed);
	}
	return g;
    }

    public Graph getDistanceGraph() {
	Graph g = new Graph("Distance Travelled", "Time (s)", "Distance (m)");
	for (DataPoint p : points) {
	    double dist = p.getDistance();
	    double time = p.getDate().getTimeInMillis()
		    - this.getStartTime().getTimeInMillis();
	    g.addPoint(time, dist);
	}
	return g;
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
	    Graph g = new Graph("Calories Burned", "Time (s)", "Calories");
	    for (DataPoint p : points) {
		double weight = 75; // need to get user's actual weight
		double calories = p.getDistance() * 1.03 * weight; // check this
								   // formula
		double time = p.getDate().getTimeInMillis()
			- this.getStartTime().getTimeInMillis();
		g.addPoint(time, calories);
	    }
	    return g;
	}
    }
}
