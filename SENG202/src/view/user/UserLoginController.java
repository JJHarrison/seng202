package view.user;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import user.User;
import view.Main;
import view.user.UserManagementController.View;
import data.persistant.Persistent;

/**
 * Controller.
 * 
 * @author Daniel van Wichen
 *
 */
public class UserLoginController implements Switchable {
	private UserManagementController controller;
	
	@Override
	public void setController(UserManagementController controller) {
		this.controller = controller;
	}

	@FXML
	Label loginWarning;

	@FXML
	ListView<User> userList;
	
	private static ListView<User> staticUserList;

	@FXML
	void initialize() {
		staticUserList = userList;
		userList.setItems(Persistent.getUsers());
	}

	@FXML
	void actionCreateProfile(ActionEvent event) {
		controller.setView(View.CREATE);
	}

	@FXML
	void actionLogin(ActionEvent event) {
		User user = userList.getSelectionModel().getSelectedItem();
		if (user != null) {
			UserLoginManager.stage.hide();
			Persistent.setUser(user);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Main main = new Main();
					main.start(new Stage());

				}
			});
		} else {
			loginWarning.setText("User must be selected");
		}

	}
	
	@FXML
	void actionDeleteProfile(ActionEvent event) {
		User user = userList.getSelectionModel().getSelectedItem();
		if (user != null) {
			Persistent.deleteUser(user);
		} else {
			loginWarning.setText("User must be selected");
		}
	}

	@FXML
	void actionCancel(ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Refresh the users in the login manager.
	 */
	public static void refreshUsers() {
		staticUserList.setItems(null);
		staticUserList.setItems(Persistent.getUsers());
		staticUserList.getSelectionModel().clearSelection();
	}
}