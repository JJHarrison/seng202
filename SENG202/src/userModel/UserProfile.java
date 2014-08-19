package userModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import dataModel.EventContainer;

/**
 * This class provides an abstract version of a user profile. The profile consists of the users date of birth, name,
 * weight, height, gender. The BMI is estimated by a commonly used formula found on wikipedia. The user profile also
 * has the event container that holds all of the activity events associated with the user.
 * @author Fitr.Team
 */
public class UserProfile {
	private Calendar dateofBirth;
	private String name;
	private double weight;
	private double height;
	private Gender gender;
	private double BMI;
	private EventContainer events;
	public enum Gender {
		MALE, FEMALE
	}
	
	
	/**
	 * Constructor.
	 * @param name Name of the user.
	 * @param dateOfBirth Date of birth of the user.
	 * @param gender Gender of the user.
	 */
	public UserProfile(String name, Calendar dateOfBirth, Gender gender){
		this.setDateofBirth(dateOfBirth);
		this.setName(name);
		this.setGender(gender);
		this.setEvents(new EventContainer());
		}
	
	/**
	 * Sets the weight for the users profile, must be in kg.
	 * @param weight Weight of the user in kg.
	 */
	public void setWeight(double weight){
		this.weight = weight;
		calculateAndSetBMI();
	}
	
	/**
	 * Returns the weight of the user in kg.
	 * @return
	 */
	public double getWeight(){
		return weight;
	}
	
	/**
	 * Set the height for the users profile, must be in m.
	 * @param height Height of the user in m.
	 */
	public void setHeight(double height){
		this.height = height;
		calculateAndSetBMI();
	}
	
	/**
	 * Returns the height of the user in m.
	 * @return
	 */
	public double getHeight(){
		return height;
	}
	
	/**
	 * Calculates the BMI of the user based on their height and weight and sets it to their
	 * user profile.
	 */
	public void calculateAndSetBMI(){
		this.BMI = weight/(Math.pow(height, 2));
	}
	
	/**
	 * @return the BMI
	 */
	public double getBMI(){
		return BMI;
	}

	/**
	 * @return the dateofBirth
	 */
	public Calendar getDateofBirth() {
		return dateofBirth;
	}

	/**
	 * @param dateofBirth the dateofBirth to set
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
	 * @param name The name to set
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
	 * @param gender the gender to set
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
	 * @param events the events to set
	 */
	public void setEvents(EventContainer events) {
		this.events = events;
	}
	
	/**
	 * Returns the age of the user in years.
	 * @return The age of the user in years.
	 */
	public int getAge(){
		// Need to add some functionality to prevent negative or 0 ages being returned.
		Calendar now = new GregorianCalendar();
		int age = (now.get(Calendar.YEAR)- this.getDateofBirth().get(Calendar.YEAR));
		return age;
	}
	
}

