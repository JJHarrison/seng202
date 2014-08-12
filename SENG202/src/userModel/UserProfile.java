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
	

	public UserProfile(String name, Calendar dateOfBirth, double weight, double height, Gender gender){
		this.dateofBirth = dateOfBirth;
		this.name = name;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
		this.BMI = calculateBMI();
		this.events = new EventContainer();
		}
	
	
	
}
