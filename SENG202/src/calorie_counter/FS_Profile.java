/**
 * This class provides an abstraction for a user. May be unnecessary considering that can just
 * use the User class. However keeping the idea of separation in case the calorie counter is 
 * scrapped.
 */
package calorie_counter;

/**
 * @author Jaln R
 *
 */
public class FS_Profile {
	private String id; // The user id to access Fat Secret Database for that user.

	/**
	 * @return the user id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the user id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
