/**
 * This class provides an abstraction for a food item.
 */
package calorie_counter;

/**
 * @author Jaln R
 *
 */
public class Food {
	private String name;
	private String id;
	private String serving;
	private int calories;
	/**
	 * @return the name of the food
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name of the food to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the unique id of the food
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the unique id of the food to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the serving size of the food
	 */
	public String getServing() {
		return serving;
	}
	/**
	 * @param serving the serving size of the food to set
	 */
	public void setServing(String serving) {
		this.serving = serving;
	}
	/**
	 * @return the calories per serving
	 */
	public int getCalories() {
		return calories;
	}
	/**
	 * @param calories the calories per serving to set
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}
}
