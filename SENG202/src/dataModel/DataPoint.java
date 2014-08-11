package dataModel;

import java.util.Date;

public class DataPoint {
	private Date date;
	private int heartRate;
	private double lat;
	private double lon;
	private double alt;
	private DataPoint lastPoint;
	private float speed;
	private double distance;
	private float dTime;
	
	public DataPoint(Date date, int heartrate, double lat, double lon, double alt, DataPoint lastPoint){
		this.date = date;
		this.heartRate = heartrate;
		this.lat =lat;
		this.lon = lon;
		this.alt = alt;
		this.lastPoint = lastPoint;
		calculateSpeed();
		calculateDistance();
		calculatedTime();
	}

	private void calculateSpeed() {
		// TODO Auto-generated method stub
		
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
	public float getSpeed() {
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

	/**
	 * @return the lastPoint
	 */
	public DataPoint getLastPoint() {
		return lastPoint;
	}
	
}
