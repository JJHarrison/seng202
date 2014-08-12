package dataModel;


import java.util.Calendar;

/**
 * 
 * @author FitrTeam
 */
public class DataPoint {
	private Calendar date;
	private int heartRate;
	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;
	private double distance;
	private double deltaTime;//added this
	private DataPoint lastPoint; //added this
	
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
		this.latitude =latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.lastPoint = lastPoint; //added this
		if (lastPoint != null) {
			this.deltaTime = calculateDeltaTime();
			this.distance = calculateDistance();//changed this
			this.speed = calculateSpeed();//changed this
		} else {
			this.deltaTime = 0.0;
			this.distance = 0.0;
			this.speed = 0.0;
		}
	}

	/**
	 * Calculates the change in time between two points.
	 * @param lastPoint The point previous to this point in an event.
	 * @return The change in time (seconds).
	 */
	private double calculateDeltaTime() {
		return ((date.getTimeInMillis() - lastPoint.getDate().getTimeInMillis()) / 1000);//changed this
	}
	
	//added this
	private double getDeltaTime(){
		return deltaTime;
	}
	
	private double calculateDistance() {
		double distance = 0.0;
		double radius = 6373 * 1000; // Converted to meters
		double latPrev = lastPoint.getLatitude();
		double lonPrev = lastPoint.getLongitude();
		
		double deltaLat = latPrev - latitude;
		double deltaLon = lonPrev - longitude;
		double radDeltaLat = Math.toRadians(deltaLat / 2);
		double radDeltaLon = Math.toRadians(deltaLon / 2);
		double radLat = Math.toRadians(latitude);
		double radPrevLat = Math.toRadians(latPrev);
		
		double a = Math.pow(Math.sin(radDeltaLat), 2) + (Math.cos(radLat)
				* Math.cos(radPrevLat) * Math.pow(Math.sin(radDeltaLon),2));
		
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
		return this.latitude;
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
}
