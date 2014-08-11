package dataModel;

import java.util.Date;

/**
 * 
 * @author Sam, Jaln
 *
 */
public class DataPoint {
	private Date date;
	private int heartRate;
	private double lat;
	private double lon;
	private double alt;
	private double speed;
	private double distance;
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
		this.date = date;
		this.heartRate = heartrate;
		this.lat =lat;
		this.lon = lon;
		this.alt = alt;
		this.speed = calculateSpeed();
		this.distance = calculateDistance(lastPoint);
		this.dTime = calculate_dTime(lastPoint);
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
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the heartRate
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * @return the latitude
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return the longitude
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @return the altitude
	 */
	public double getAlt() {
		return alt;
	}
	
	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the dTime
	 */
	public float getdTime() {
		return dTime;
	}
	
}
