package dataModel;

import java.util.Calendar;

/**
 * 
 * @author FitrTeam
 *
 */
public class DataPoint {
	private Calendar date;
	private int heartRate;
	private double latitude;
	private double longitude;
	private double altitude;
	private int deltaTime;
	private double distance;
	private double speed;
	
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
		this.date = date;
		this.heartRate = heartrate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		if (lastPoint != null) {
			this.distance = calculateDistance(lastPoint);
			this.speed = calculateSpeed();
		} else {
			this.distance = 0.0;
			this.speed = 0.0;
		}

		distance = calculateDistance(lastPoint);
		deltaTime = (int) calculateDeltaTime(lastPoint);
		speed = calculateSpeed();
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
	private double calculateSpeed() {
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


	/**
	 * @return the dTime
	 */
	public float getDeltaTime() {
		return deltaTime;
	}
	
}
