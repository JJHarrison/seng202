package view.user;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import user.User;
import data.persistant.Persistent;

public class UserLoginController {

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

		//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
		//		"UserLogin.fxml"));
		//fxmlLoader.setController(this);
		//fxmlLoader.setRoot(this);
		try {
			//fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
				
			}
		});
		
		userList.setItems(Persistent.getUsers());
		
	}
}