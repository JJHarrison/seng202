package data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import data.persistant.Persistent;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class provides an abstract version of points provided by a fitness
 * tracking device. Each dataPoint consists of attributes related to the actual
 * values in the real world at that time point.
 * 
 * @author Sam
 */
public class DataPoint implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 8381176069733917667L;
	private Calendar date;
	private int heartRate;
	private double latitude;
	private double longitude;
	private double altitude;
	private double speed;
	private double distance;
	private double duration;
	private double caloriesBurned;
	private double stressLevel;

	/**
	 * Builder for DataPoint class
	 * 
	 * @author SamSchofield
	 */
	public static class Builder {
		private Calendar date;
		private int heartRate;
		private double latitude;
		private double longitude;
		private double altitude;
		private DataPoint previousPointPoint;

		/**
		 * set date for data point
		 * 
		 * @param date
		 * @return builder
		 */
		public Builder date(Calendar date) {
			this.date = date;
			return this;
		}

		/**
		 * set heartRate for data point
		 * 
		 * @param heartRate
		 * @return builder
		 */
		public Builder heartRate(int heartRate) {
			this.heartRate = heartRate;
			return this;
		}

		/**
		 * set latitude for data point
		 * 
		 * @param latitude
		 * @return builder
		 */
		public Builder latitude(Double latitude) {
			this.latitude = latitude;
			return this;
		}

		/**
		 * set longitude for data point
		 * 
		 * @param longitude
		 * @return builder
		 */
		public Builder longitude(Double longitude) {
			this.longitude = longitude;
			return this;
		}

		/**
		 * set altitude for data point
		 * 
		 * @param altitude
		 * @return builder
		 */
		public Builder altitude(Double altitude) {
			this.altitude = altitude;
			return this;
		}

		/**
		 * set previous point for data point
		 * 
		 * @param point
		 * @return builder
		 */
		public Builder prevDataPoint(DataPoint point) {
			this.previousPointPoint = point;
			return this;
		}

		/**
		 * build the dataPoint
		 * 
		 * @return data point
		 */
		public DataPoint build() {
			return new DataPoint(this);
		}
	}

	/**
	 * constructor for DataPoint Set data point values using builder
	 * 
	 * @param builder
	 */
	public DataPoint(Builder builder) {
		this.date = builder.date;
		this.heartRate = builder.heartRate;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.altitude = builder.altitude;

		if (builder.previousPointPoint != null) {
			this.distance = calculateDistance(builder.previousPointPoint);
			this.speed = calculateSpeed(calculateDeltaTime(builder.previousPointPoint));
			this.duration = calculateDeltaTime(builder.previousPointPoint);
			this.caloriesBurned = calculateCalories();
		} else {
			this.distance = 0.0;
			this.speed = 0.0;
			this.stressLevel = 0.5;
		}
	}

	/**
	 * Calculates the change in time between two points.
	 * 
	 * @param previosPoint The point previous to this point in an event.
	 * @return The change in time (seconds).
	 */
	private long calculateDeltaTime(DataPoint previousPoint) {
		long previousTime = previousPoint.getDate().getTimeInMillis();
		return ((date.getTimeInMillis() - previousTime) / 1000);
	}

	/**
	 * Calculates the distance from the current point to the previousPoint in meters. 
	 * @param previousPoint The point from which to calculate the distance from
	 * @return The distance in meters from the previous point
	 */
	private double calculateDistance(DataPoint previousPoint) {
		double distance = 0;
		double radius = 6373 * 1000; // Converted to meters
		double latPrev = previousPoint.getLatitude();
		double lonPrev = previousPoint.getLongitude();

		double deltaLat = latPrev - latitude;
		double deltaLon = lonPrev - longitude;

		double a = Math.pow(Math.sin(Math.toRadians(deltaLat / 2)), 2)
				+ (Math.cos(Math.toRadians(latitude))
						* Math.cos(Math.toRadians(latPrev)) 
						* Math.pow(Math.sin(Math.toRadians(deltaLon / 2)), 2));

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		distance = radius * c;

		return distance;
	}

	/**
	 * 
	 * @param deltaTime The between two data points
	 * @return The average speed between two points
	 */
	private double calculateSpeed(long deltaTime) {
		double speed;
		if (deltaTime == 0) {
			speed = 0;
		} else {
			speed = (distance / deltaTime);
		}
		return speed;
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
	 * calculates the calories burnt from the last point to the current point.
	 * @return calories burnt from last point to this point
	 */
	private double calculateCalories() {
		double weight = Persistent.getCurrentUser().getWeight();
		double runMET = 7.5; // assuming that all activity is running for now
		double timeInHours = (double) duration / 3600;
		double calories = weight * runMET * timeInHours;
		return calories;
	}

	/**
	 * Returns the calories burnt at this point
	 * @return The calories burnt from last point to this point
	 */
	public double getCalories() {
		return caloriesBurned;
	}

	/**
	 * Returns the physical stress level from last point to this point
	 * @return The physical stress level
	 */
	public double getStressLevel() {
		return stressLevel;
	}

	/**
	 * Sets the physical stress level at this point.
	 * The stress level is calculated by
	 * @param stressLevel
	 */
	public void setStressLevel(double stressLevel) {
		this.stressLevel = stressLevel;
	}

	/**
	 * Returns a string representation of the timestamp for the data point as
	 * "hh:mm:ss"
	 * 
	 * @return time of the data point
	 */
	public String getTimeString() {
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
		return (tf.format(date.getTime()));
	}

	/**
	 * Returns a string representation of the date for the data point as
	 * "dd/mm/yyyy"
	 * 
	 * @return date of the data point
	 */
	public String getDateString() {
		// The calendar takes january being the 0th month and december the 11th
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return (df.format(date.getTime()));
	}

	/**
	 * Gets the observable date property 
	 * @return date property
	 */
	public Property<String> getDateProperty() {
		return new SimpleStringProperty(getDateString());
	}

	/**
	 * Gets the observable time property 
	 * @return time property
	 */
	public Property<String> getTimeProperty() {
		return new SimpleStringProperty(getTimeString());
	}

	/**
	 * Gets the observable latitude property 
	 * @return latitude property
	 */
	public SimpleDoubleProperty getLatitudeProperty() {
		return new SimpleDoubleProperty(getLatitude());
	}

	/**
	 * Gets the observable longitude property 
	 * @return date property
	 */
	public SimpleDoubleProperty getLongitudeProperty() {
		return new SimpleDoubleProperty(getLongitude());
	}

	/**
	 * Gets the observable distance property 
	 * @return distance property
	 */
	public Property<String> getDistanceProperty() {
		return new SimpleStringProperty(String.format("%.2f", getDistance()));
	}

	/**
	 * Gets the observable speed property 
	 * @return speed property
	 */
	public Property<String> getSpeedProperty() {
		return new SimpleStringProperty(String.format("%.2f", getSpeed()));
	}

	/**
	 * Gets the observable altitude property 
	 * @return altitude property
	 */
	public SimpleDoubleProperty getAltitudeProperty() {
		return new SimpleDoubleProperty(altitude);
	}

	/**
	 * Gets the observable heart rate property 
	 * @return heart rate property
	 */
	public SimpleIntegerProperty getHeartRateProperty() {
		return new SimpleIntegerProperty(getHeartRate());
	}

}
