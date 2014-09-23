package view.user;

/**
 * 
 * @author daniel
 *
 */
public interface Switchable {

	/**
	 * A method which pass the main controller around the child controllers
	 * 
	 * @param controller
	 *            main controller to pass
	 */
	public void setController(UserManagementController controller);

}
