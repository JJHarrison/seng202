package userModel;

import java.util.Calendar;

import dataModel.EventContainer;

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
		this.dateofBirth = dateOfBirth;
		this.name = name;
		this.gender = gender;
		this.events = new EventContainer();
		}
	
	/**
	 * Sets the weight for the users profile, must be in kg.
	 * @param weight Weight of the user in kg.
	 */
	public void setWeight(double weight){
		this.weight = weight;
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
	}
	
	/**
	 * Returns the height of the user in m.
	 * @return
	 */
	public double getHeight(){
		return height;
	}
	
	/**
	 * Calculates the BMI of the user based on their height and weight.
	 * @return
	 */
	public double calculateBMI(){
		return weight/(Math.pow(height, 2));
	}
	
	public double getBMI(){
		return BMI;
	}
	
	//public Calendar getDateOfBirth(){
		//return 
	//}
}
