package user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import data.loader.FileLoader;
import data.model.DataPoint;
import data.model.Event;
import data.model.EventContainer; 
import data.persistant.Persistent;

/**
 * This class provides an abstract version of a user profile. The profile
 * consists of the users date of birth, name, weight, height, gender. The BMI is
 * estimated by a commonly used formula found on wikipedia. The user profile
 * also has the event container that holds all of the activity events associated
 * with the user.
 * 
 * @author Fitr.Team
 */
public class User implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = -5891761384182212793L;
	private Calendar dateofBirth;
	private String name;
	private double weight;
	private double height;
	private Gender gender;
	private double BMI;
	private int averageHeartRate;
	private EventContainer events;
	private int userID;

	public enum Gender {
		MALE, FEMALE
	}

	/**
	 * 
	 * @param name
	 *            Name of user
	 * @param dateOfBirth
	 *            Birth date of user.
	 * @param gender
	 *            Gender of user (MALE or FEMALE).
	 * @param weight
	 *            Weight of user.
	 * @param height
	 *            Height of user.
	 * @param events
	 *            For testing, but if set to NULL creates a new event container
	 */
	public User(String name, Calendar dateOfBirth, Gender gender,
			double weight, double height, EventContainer events,
			int averageHeartRate) {
		this.name = name;
		this.dateofBirth = dateOfBirth;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
		this.averageHeartRate = averageHeartRate;
		this.BMI = calculateBMI();
		this.events = (events == null) ? new EventContainer() : events;
		userID = Persistent.getLastUserID();
	}

	/**
	 * Sets the weight for the users profile, must be in kg.
	 * 
	 * @param weight
	 *            Weight of the user in kg.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
		this.BMI = calculateBMI();
	}

	/**
	 * Returns the weight of the user in kg.
	 * 
	 * @return
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Set the height for the users profile, must be in m.
	 * 
	 * @param height
	 *            Height of the user in m.
	 */
	public void setHeight(double height) {
		this.height = height;
		this.BMI = calculateBMI();
	}

	/**
	 * Returns the height of the user in m.
	 * 
	 * @return
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Calculates the BMI of the user based on their height and weight and sets
	 * it to their user profile.
	 */
	private double calculateBMI() {
		return weight / (Math.pow(height, 2));
	}

	/**
	 * @return the BMI
	 */
	public double getBMI() {
		return BMI;
	}

	/**
	 * @return the dateofBirth
	 */
	public Calendar getDateofBirth() {
		return dateofBirth;
	}

	/**
	 * @param dateofBirth
	 *            the dateofBirth to set
	 */
	public void setDateofBirth(Calendar dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the events
	 */
	public EventContainer getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(EventContainer events) {
		this.events = events;
	}

	/**
	 * returns the user id for a user so that it can be stored in the database
	 * 
	 * @return
	 */
	public int getUserId() {
		return userID;
	}

	/**
	 * Returns the age of the user in years.
	 * 
	 * @return The age of the user in years.
	 */
	public int getAge() {
		// Need to add some functionality to prevent negative or 0 ages being
		// returned.
		Calendar now = new GregorianCalendar();
		int age = (now.get(Calendar.YEAR) - this.getDateofBirth().get(
				Calendar.YEAR));
		return age;
	}

	@Override
	public String toString() {
		return String.format("%s", getName());
	}

	public String genderForDB() {
		if (this.getGender() == Gender.MALE) {
			return "M";
		} else {
			return "F";
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getAverageHeartRate() {
		return averageHeartRate;
	}

	/**
	 * 
	 * @param averageHeartRate
	 */
	public void setAverageHeartRate(int averageHeartRate) {
		this.averageHeartRate = averageHeartRate;
	}

	public void setUserID(int id){
		this.userID = id;
	}
	/**
	 * TEMPORY PLEASE DONT DELETE!!!!!! Tired of making new users to test my
	 * code, please leave this here till end of project
	 * 
	 * @return
	 */
	public static User mockUser() {
		FileLoader fl = new FileLoader();
		fl.load();
		EventContainer ec = fl.getEventContainer();
		//*/
		User mock = new User("Mocky", new GregorianCalendar(1961, 8, 9),
				Gender.MALE, 85.3, 1.9, ec, 120);
		//mock.setUserID(1);
		return mock;
	}
	
	@Override
	public boolean equals(Object other) {
		boolean same = false;
		User u = (User) other;
		if(name.equals(u.getName())) {
			same = true;
		}
		return same;
	}
}
