package data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import user.User.Gender;
import data.persistant.Persistent;

/**
 * This class provides an abstract version of points provided by a fitness
 * tracking device. Each DataPoint consists of attributes related to the actual
 * values in the real world at that time point.
 * 
 * @author Sam, Simon
 */
public class DataPoint implements Serializable {
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
	 * Builder for the DataPoint class.
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
		 * @param date the date and time that the DataPoint occurred.
		 * @return builder a new Builder object which contains the date.
		 */
		public Builder date(Calendar date) {
			this.date = date;
			return this;
		}

		/**
		 * Sets heartRate for the DataPoint.
		 * 
		 * @param heartRate the heart rate measured by the device.
		 * @return builder a Builder which now contains the heart rate.
		 */
		public Builder heartRate(int heartRate) {
			this.heartRate = heartRate;
			return this;
		}

		/**
		 * Sets latitude for the DataPoint.
		 * 
		 * @param latitude the latitude measured by the device.
		 * @return builder a Builder which now contains the latitude.
		 */
		public Builder latitude(Double latitude) {
			this.latitude = latitude;
			return this;
		}

		/**
		 * Sets longitude for the DataPoint.
		 * 
		 * @param longitude the longitude measured by the device.
		 * @return builder a Builder which now contains the longitude.
		 */
		public Builder longitude(Double longitude) {
			this.longitude = longitude;
			return this;
		}

		/**
		 * Sets altitude for the DataPoint.
		 * 
		 * @param altitude the altitude measured by the device.
		 * @return builder a Builder which now contains the altitude
		 */
		public Builder altitude(Double altitude) {
			this.altitude = altitude;
			return this;
		}

		/**
		 * Sets the DataPoint that occurred before the current one.
		 * 
		 * @param point the previous DataPoint.
		 * @return builder a Builder which now contains the previous DataPoint.
		 */
		public Builder prevDataPoint(DataPoint point) {
			this.previousPointPoint = point;
			return this;
		}

		/**
		 * Builds the DataPoint.
		 * 
		 * @return data the new DataPoint.
		 */
		public DataPoint build() {
			return new DataPoint(this);
		}
	}

	/**
	 * The constructor for DataPoint. Sets DataPoint values using Builder.
	 * 
	 * @param builder the Builder that contains the DataPoint values.
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
	 * @param previousPoint The point previous to this point in an event.
	 * @return The change in time (seconds).
	 */
	private long calculateDeltaTime(DataPoint previousPoint) {
		long previousTime = previousPoint.getDate().getTimeInMillis();
		return ((date.getTimeInMillis() - previousTime) / 1000);
	}

	/**
	 * Calculates the distance from the current point to the previousPoint in meters. 
	 * @param previousPoint The point from which to calculate the distance from.
	 * @return The distance in meters from the previous point.
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
	 * Calculates the speed at the particular point.
	 * @param deltaTime the time difference between two DataPoints.
	 * @return the average speed between two points.
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
	 * @return the date.
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Gets the heart rate at a particular point. 
	 * @return the heartRate.
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * Gets the latitude at a particular point. 
	 * @return the latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Gets the longitude at a particular point.
	 * @return the longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Gets the altitude at a particular point.
	 * @return the altitude.
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * Gets the average speed (m/s) from the previous point to this point.
	 * @return the speed.
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Gets the distance (m) from the previous point to this point.
	 * @return the distance.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Calculates the calories burnt from the last point to the current point.
	 * @return calories burnt from last point to this point.
	 */
	private double calculateCalories() {
		double calories;
		double weight = Persistent.getCurrentUser().getWeight();
		int age = Persistent.getCurrentUser().getAge();
		double timeInHours = (double) duration / 3600;
		
		if(Persistent.getCurrentUser().getGender() == Gender.MALE) {
			calories = ((-55.0969 + (0.6309 * heartRate) + (0.1988 * weight)
					+ (0.2017 * age)) / 4.184) * 60 * timeInHours;
		} else {
			calories = ((-20.4022 + (0.4472 * heartRate) - (0.1263 * weight)
					+ (0.074 * age)) / 4.184) * 60 * timeInHours;
		}
		return calories;
	}

	/**
	 * Returns the calories burnt at this point.
	 * @return the calories burnt from last point to this point.
	 */
	public double getCalories() {
		return caloriesBurned;
	}

	/**
	 * Returns the physical stress level from last point to this point.
	 * @return the physical stress level.
	 */
	public double getStressLevel() {
		return stressLevel;
	}

	/**
	 * Sets the physical stress level at this point.
	 * @param stressLevel the physical stress level.
	 */
	public void setStressLevel(double stressLevel) {
		this.stressLevel = stressLevel;
	}

	/**
	 * Returns a string representation of the timestamp for the data point as
	 * "hh:mm:ss".
	 * 
	 * @return time of the DataPoint.
	 */
	public String getTimeString() {
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
		return (tf.format(date.getTime()));
	}

	/**
	 * Returns a string representation of the date for the DataPoint as
	 * "dd/mm/yyyy".
	 * 
	 * @return date string of the data point.
	 */
	public String getDateString() {
		// The calendar takes january being the 0th month and december the 11th
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return (df.format(date.getTime()));
	}

	/**
	 * Gets the observable date property.
	 * @return the date property.
	 */
	public Property<String> getDateProperty() {
		return new SimpleStringProperty(getDateString());
	}

	/**
	 * Gets the observable time property. 
	 * @return the time property.
	 */
	public Property<String> getTimeProperty() {
		return new SimpleStringProperty(getTimeString());
	}

	/**
	 * Gets the observable latitude property.
	 * @return the latitude property.
	 */
	public SimpleDoubleProperty getLatitudeProperty() {
		return new SimpleDoubleProperty(getLatitude());
	}

	/**
	 * Gets the observable longitude property.
	 * @return the date property.
	 */
	public SimpleDoubleProperty getLongitudeProperty() {
		return new SimpleDoubleProperty(getLongitude());
	}

	/**
	 * Gets the observable distance property.
	 * @return the distance property.
	 */
	public Property<String> getDistanceProperty() {
		return new SimpleStringProperty(String.format("%.2f", getDistance()));
	}

	/**
	 * Gets the observable speed property.
	 * @return the speed property.
	 */
	public Property<String> getSpeedProperty() {
		return new SimpleStringProperty(String.format("%.2f", getSpeed()));
	}

	/**
	 * Gets the observable altitude property.
	 * @return the altitude property.
	 */
	public SimpleDoubleProperty getAltitudeProperty() {
		return new SimpleDoubleProperty(altitude);
	}

	/**
	 * Gets the observable heart rate property.
	 * @return the heart rate property.
	 */
	public SimpleIntegerProperty getHeartRateProperty() {
		return new SimpleIntegerProperty(getHeartRate());
	}

}
