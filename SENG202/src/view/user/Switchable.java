package view.user;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public interface Switchable {

	/**
	 * A method which pass the main controller around the child controllers.
	 * 
	 * @param controller main controller to pass
	 */
	public void setController(UserManagementController controller);

}
