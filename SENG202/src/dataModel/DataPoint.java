package dataModel;

import java.util.Calendar;

/**
 * 
<<<<<<< HEAD
 * @author FitrTeam
=======
 * @author Sam, Jaln
 *
>>>>>>> branch 'master' of https://bitbucket.org/simoncrequer/seng202_2014_team5.git
 */
public class DataPoint {
	private Calendar date;
	private int heartRate;
<<<<<<< HEAD
	private double latitude;
	private double longitude;
	private double altitude;
=======
	private double lat;
	private double lon;
	private double alt;
>>>>>>> branch 'master' of https://bitbucket.org/simoncrequer/seng202_2014_team5.git
	private double speed;
	private double distance;
<<<<<<< HEAD
	
	/**
	 * Constructor.
	 * @param date The current date at this point.
	 * @param heartrate The current heart rate at this point.
	 * @param latitude The current latitude at this point.
	 * @param longitude The current longitude at this point.
	 * @param altitude The current altitude at this point.
	 * @param lastPoint The previous point, used for distance calculations.
	 */
	public DataPoint(Calendar date, int heartrate, double latitude, double longitude, double altitude, DataPoint lastPoint) {
=======
	private long dTime;
	/**
	 * 
	 * @param date
	 * @param heartrate
	 * @param lat
	 * @param lon
	 * @param alt
	 * @param lastPoint
	 */
	public DataPoint(Date date, int heartrate, double lat, double lon, double alt, DataPoint lastPoint){
>>>>>>> branch 'master' of https://bitbucket.org/simoncrequer/seng202_2014_team5.git
		this.date = date;
		this.heartRate = heartrate;
<<<<<<< HEAD
		this.latitude =latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		if (lastPoint != null) {
			this.distance = calculateDistance(lastPoint);
			this.speed = calculateSpeed(calculateDeltaTime(lastPoint));
		} else {
			this.distance = 0.0;
			this.speed = 0.0;
		}
=======
		this.lat =lat;
		this.lon = lon;
		this.alt = alt;
		distance = calculateDistance(lastPoint);
		dTime = calculate_dTime(lastPoint);
		speed = calculateSpeed();
	}

	/**
	 * @return the dTime
	 * Calculates the difference in time, in seconds, by using previous points time   
	 */
	private long calculate_dTime(DataPoint lastPoint) {
		// Returns the time difference between the previous and last data points in seconds
		
		dTime = (this.getDate().getTime() - lastPoint.getDate().getTime()) * 1000;
		
		return dTime;
	}
	
	/**
	 * @return the distance
	 * Calculates the speed, in metres, by using the previous points longitude and latitude
	 * and appropriate function to take into account the curvature of the earth.   
	 */
	private double calculateDistance(DataPoint lastPoint) {
		// Returns the distance between the current data point and the previous data point
		// This method doesn't currently give the right distance. 
		
		double radius = 6373 * 1000;
		
		double dlon = lon - lastPoint.getLon();
		double dlat = lat - lastPoint.getLat();
		
		double a = Math.pow(Math.sin(Math.toRadians(dlat / 2)), 2) + (Math.cos(Math.toRadians(lat))
				* Math.cos(Math.toRadians(lastPoint.getLat())) * Math.pow(Math.sin(Math.toRadians(dlon / 2)),2));
		
		double c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1-a) );
				distance = radius * c;
		
		return distance;
	}
	
	/**
	 * @return the speed
	 * Calculates the speed by using the calculated distance and dTime   
	 */
	private double calculateSpeed() {
		// Calculates the speed from the distance and change in time from the previous point.
		speed = distance / dTime;
		return speed;
>>>>>>> branch 'master' of https://bitbucket.org/simoncrequer/seng202_2014_team5.git
	}

	/**
	 * Calculates the change in time between two points.
	 * @param lastPoint The point previous to this point in an event.
	 * @return The change in time (seconds).
	 */
	private long calculateDeltaTime(DataPoint lastPoint) {
		long previousTime = lastPoint.getDate().getTimeInMillis();
		return ((date.getTimeInMillis() - previousTime) / 1000);
	}
	
	private double calculateDistance(DataPoint lastPoint) {
		double distance = 0;
		double radius = 6373 * 1000; // Converted to meters
		double latPrev = lastPoint.latitude;
		double lonPrev = lastPoint.longitude;
		
		double deltaLat = latPrev - latitude;
		double deltaLon = lonPrev - longitude;
		
		double a = Math.pow(Math.sin(Math.toRadians(deltaLat / 2)), 2) + (Math.cos(Math.toRadians(latitude))
				* Math.cos(Math.toRadians(latPrev)) * Math.pow(Math.sin(Math.toRadians(deltaLon / 2)),2));
		
		double c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1-a) );
		distance = radius * c;
		
		return distance;
	}
	
	/**
	 * 
	 * @param deltaTime The between two data points
	 * @return The average speed between two points
	 */
	private double calculateSpeed(long deltaTime) {
		return (distance / deltaTime);
	}

	/**
	 * Gets the date at a particular point.
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Gets the heart rate at a particular point.
	 * @return the heartRate
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * Gets the latitude at a particular point.
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Gets the longitude at a particular point.
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Gets the altitude at a particular point.
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	
	/**
	 * Gets the average speed (m/s) from the previous point to this point.
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Gets the distance (m) from the previous point to this point.
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
<<<<<<< HEAD
=======

	/**
	 * @return the dTime
	 */
	public float getdTime() {
		return dTime;
	}
	
>>>>>>> branch 'master' of https://bitbucket.org/simoncrequer/seng202_2014_team5.git
}
