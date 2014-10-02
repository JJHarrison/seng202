/**
 * This class provides an abstraction of a meal, which consists of food items.
 */
package calorie_counter;

import java.util.ArrayList;

/**
 * @author Jaln R
 *
 */
public class Meal {
	private String id;
	private String name;
	private ArrayList<Food> items;
	/**
	 * @return the unique id of the meal
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the unique id of the meal to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name of the meal
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name of the meal to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the items that the meal contains
	 */
	public ArrayList<Food> getItems() {
		return items;
	}
	/**
	 * @param items the items that the meal contains to set
	 */
	public void setItems(ArrayList<Food> items) {
		this.items = items;
	}
}
