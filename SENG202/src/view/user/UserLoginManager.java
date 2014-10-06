package view.user;

import java.io.IOException;

import resources.Reference;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import data.persistant.Persistent;

/**
 *
 * @author Daniel van Wichen
 */
public class UserLoginManager extends Application {
	public static String userManagementFXML = "UserManagement.fxml";
	public static String userCreateFXML = "UserCreate.fxml";
	public static String userLoginFXML = "UserLogin.fxml";
	public static String userPersistFXML = "UserPersist.fxml";
	public static Stage stage;

	StackPane userManagementView = new StackPane();

	@Override
	public void start(Stage stage) {
		stage = new Stage();
		UserLoginManager.stage = stage;
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.getIcons().add(new Image(Reference.class.getResourceAsStream("heart_icon.png")));
		/*
		 * initialize the persistent class.
		 */
		Persistent.initialize();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(userManagementFXML));
		loader.setRoot(userManagementView);
		StackPane root;
		try {
			root = loader.load(getClass().getResourceAsStream(
					userManagementFXML));
			stage.setScene(new Scene(root));
		} catch (IOException e) {
		}
		
		stage.setTitle("User Management");
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				UserLoginManager.stage.show();
			}
		});
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}