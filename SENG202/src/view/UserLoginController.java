package view;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class UserLoginController extends AnchorPane {
	@FXML
	Button ButtonCreateProfile;
	
	@FXML
	Button ButtonLogin;
	
	@FXML
	Button ButtonCancel;
	
	@FXML
	ListView<String> UserList;

	public UserLoginController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"UserLogin.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ButtonCancel.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		ButtonLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				System.out.println("Login");
			}
		});
		
		ButtonCreateProfile.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				System.out.println("Profile");
			}
		});
		
		ObservableList<String> profileList = FXCollections.observableArrayList();
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		profileList.add("Daniel van Wichen");
		UserList.setItems(profileList);

	}
}