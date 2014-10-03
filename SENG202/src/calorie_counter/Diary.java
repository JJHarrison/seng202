package calorie_counter;

import java.util.ArrayList;

public class Diary {
	private ArrayList<Meal> meals;
	private int calories;
	/**
	 * @return the meals for the diary entry
	 */
	public ArrayList<Meal> getMeals() {
		return meals;
	}
	/**
	 * @param meals the meals for the diary entry to set
	 */
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}
	/**
	 * @return the calories for the diary entry
	 */
	public int getCalories() {
		return calories;
	}
	/**
	 * @param calories the calories for the diary entry to set
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}
}
