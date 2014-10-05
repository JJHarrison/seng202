package user;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import data.loader.FileLoader;
import data.loader.LoadSummary;
import data.model.Event;
import data.model.EventContainer;
import data.persistant.Persistent;
import data.persistant.Saver;

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
	private int restingHeartRate;
	private EventContainer events;
	private String userID;
	private boolean hasBradycardia = false;
	private boolean hasTachycardia = false;

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
			int restingHeartRate) {
		this.name = name;
		this.dateofBirth = dateOfBirth;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
		setRestingHeartRate(restingHeartRate);
		this.BMI = calculateBMI();
		this.events = (events == null) ? new EventContainer() : events;
		userID = Persistent.getUserID();
	}
	
	/**
	 * Returns if the use has Tachycardia.
	 * @return True if the user has Tachycardia, false otherwise
	 */
	public boolean hasTachycardia(){
		return this.hasTachycardia;
	}
	
	/**
	 * Returns if the user has Bradycardia
	 * @return True if the user has Bradycardi, false otherwise
	 */
	public boolean hasBradycardia(){
		return this.hasBradycardia;
	}
	
	/**
	 * Sets the resting heart rate of the user.
	 * @param restingHeartRate The resting heart rate that will be associated with the user.
	 */
	public void setRestingHeartRate(int restingHeartRate) {
		this.restingHeartRate = restingHeartRate;
		calculateHealthWarnings();
	}
	
	/**
	 * Calculates to see if the user has any health warnings that need to be displayed.
	 * Health warning consist of Bradycaria and Tachycardia.
	 * Bradycardia is the resting heart rate of under 60 beats per minute (BPM).
	 * Tachycardia is when the heart rate exceeds a given range when exercising.
	 */
	private void calculateHealthWarnings() {
		if (getAge() < 12) {
			hasTachycardia = restingHeartRate > 130 ? true : false;
		} else if (getAge() < 16) {
			hasTachycardia = restingHeartRate > 119 ? true : false;
		} else {
			hasTachycardia = restingHeartRate > 100 ? true : false;
		}
		hasBradycardia = restingHeartRate < 60 ? true : false;
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
		return weight / (Math.pow(height / 100, 2));
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
	 */
	public String getUserId() {
		return userID;
	}

	/**
	 * Returns the age of the user in years.
	 * 
	 * @return The age of the user in years.
	 */
	public int getAge() {
	    Calendar now = Calendar.getInstance();
	    Calendar birth = getDateofBirth();
	    int diff = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
	    if (birth.get(Calendar.MONTH) > now.get(Calendar.MONTH) || 
	        (now.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && birth.get(Calendar.DATE) > now.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}
	
	/**
	 * The toString function is automatically used in the JavafX for the user management system.
	 */
	@Override
	public String toString() {
		return String.format("%s", getName());
	}

	/**
	 * Returns the gender of the user as a string.
	 * @return The string of the users gender.
	 */
	public String genderForDB() {
		if (this.getGender() == Gender.MALE) {
			return "M";
		} else {
			return "F";
		}
	}

	/**
	 * Returns the resting heart rate of the user.
	 * @return The resting heart rate of the uesr.
	 */
	public int getRestingHeartRate() {
		return restingHeartRate;
	}
	
	/**
	 * adds events from the new event container to the users event container
	 * The EventContainer method addEvent() will get rid of any duplicate events
	 * @param events
	 */
	public void addEvents(EventContainer events) {
		int sizeBefore = this.events.getAllEvents().size();
		for(Event event : events.getAllEvents()) {
			this.events.addEvent(event);
		}
		//change in the number of events 
		LoadSummary.setEventsAdded(this.events.getAllEvents().size() - sizeBefore);
		//total events in event container to be added - new events added 
		LoadSummary.setEventsNotAdded(events.getAllEvents().size() - (this.events.getAllEvents().size() - sizeBefore));
		Saver.SaveUser(this);
	}
	
	/**
	 * clears the users event container 
	 */
	public void clearEvents() {
		events = new EventContainer();
	}

	/**
	 * The equals method was overridden for profile creation.
	 * @param other The user profile to compare for equality
	 */
	@Override
	public boolean equals(Object other) {
		boolean same = false;
		if (other != null) {
			User u = (User) other;
			if (name.equals(u.getName())) {
				same = true;
			}
		}
		return same;
	}
//-----------------------------Do not delete till end of project----------------------------	
	/**
	 * TEMPORARY PLEASE DONT DELETE!!!!!! Tired of making new users to test my
	 * code, please leave this here till end of project
	 * 
	 * @return
	 */
	public static User mockUser() {
		Persistent.clear();
		User mock = new User("Mocky", new GregorianCalendar(1961, 8, 9),
				Gender.MALE, 85.3, 190, null, 120);
		//System.out.println("Mockys userId is: " + Persistent.getUserID());
		try {
			//Persistent.setFilePath(System.getProperty("user.home"));
			Persistent.setFilePath("Users/SamSchofield/Desktop");
		} catch (FileNotFoundException e) {

		}
		
		Persistent.newUser(mock);
		
		Persistent.setUser(mock);
		FileLoader fl = new FileLoader();
		fl.load();
		EventContainer ec = fl.getEventContainer();
		mock.setEvents(ec);
		
		// We now 
		//Persistent.deleteDirectory(new File(System.getProperty("user.home") + "Fitr"));
		//Persistent.deleteUser(mock);
		return mock;
	}
	
	public void setUserId(String s) {
		userID = s;
	}
//-----------------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		mockUser();
	}
}
