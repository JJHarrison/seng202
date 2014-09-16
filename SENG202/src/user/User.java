package user;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    private EventContainer events;
    private int userID;

    public enum Gender {
	MALE, FEMALE
    }

    /**
     * Constructor.
     * 
     * @param name
     *            Name of the user.
     * @param dateOfBirth
     *            Date of birth of the user.
     * @param gender
     *            Gender of the user.
     */
    public User(String name, Calendar dateOfBirth, Gender gender) {
	this.setDateofBirth(dateOfBirth);
	this.setName(name);
	this.setGender(gender);
	this.setEvents(new EventContainer());
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
	calculateAndSetBMI();
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
	calculateAndSetBMI();
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
    public void calculateAndSetBMI() {
	this.BMI = weight / (Math.pow(height, 2));
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

    public static void main(String[] args) throws Exception {
	User a = new User("a", null, null);
	Persistent.newUser(a);

	User z = new User("a", null, null);
	Persistent.newUser(z);

	User b = new User("b", null, null);
	Persistent.newUser(b);

	User c = new User("c", null, null);
	Persistent.newUser(b);

	System.out.println(a.getUserId());
	System.out.println(b.getUserId());
	System.out.println(c.getUserId());
    }
}
