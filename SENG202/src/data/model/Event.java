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
    // private Graph caloriesGraph;

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
    
    public ArrayList<DataPoint> getPoints() {
    	return points;
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
    
    public String getPointString(DataPoint point) {
    	String latStr;
    	String lonStr;
    	StringBuilder pointString = new StringBuilder();
    	
    	latStr = String.format("%s", point.getLatitude());
		latStr.substring(0, 12);
		lonStr = String.format("%s", point.getLongitude());
		lonStr.substring(0, 12);
		pointString.append(latStr);
		pointString.append(",");
		pointString.append(lonStr);
		return pointString.toString();
	}
    
    public String getPathString() {
    	int MAX_POINTS = 68; // Due to 2048 char limit for static maps.
    	ArrayList<DataPoint> dataPoints = getDataPoints();
    	int dataSize = dataPoints.size();
    	StringBuilder pathBuilder = new StringBuilder();
    	
    	pathBuilder.append(getPointString(dataPoints.get(0)));
		pathBuilder.append("|");
    	for (int i = 1; i < dataSize - 1; i+=((dataSize-1)/MAX_POINTS)+1) {
    		pathBuilder.append(getPointString(dataPoints.get(i)));
    		pathBuilder.append("|");
    	}
    	pathBuilder.append(getPointString(dataPoints.get(dataSize - 1)));
		
		return pathBuilder.toString();
    }
}
