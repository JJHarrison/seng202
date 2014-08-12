package userModel;

import java.util.Calendar;

import dataModel.EventContainer;

public class UserProfile {
	private Calendar dateofBirth;
	private String name;
	private float weight;
	private float height;
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
	
	public void setWeight(float weight){
		this.weight = weight;
	}
	
	public float getWeight(){
		return weight;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public float getHeight(){
		return height;
	}
	
	public double calculateBMI(){
		return Math.pow((weight/height), 2);
	}
	
	public double getBMI(){
		return BMI;
	}
	
}
