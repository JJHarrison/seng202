package view.user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import user.User;
import data.persistant.Persistent;

public class UserLoginController implements ScreenController {
	@FXML
	private ScreenPane myScreenPane;

	@FXML
	Button buttonCreateProfile;
	@FXML
	Button buttonLogin;
	@FXML
	Button buttonCancel;
	@FXML
	ListView<User> userList;

	@FXML
	private void initialize() {

		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});

		buttonLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				System.out.println("Login");
			}
		});

		buttonCreateProfile.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				myScreenPane.setScreen("userCreate");
			}
		});

		userList.setItems(Persistent.getUsers());

	}

	@Override
	public void setScreenPane(ScreenPane screenPage) {
		myScreenPane = screenPage;

	}
}