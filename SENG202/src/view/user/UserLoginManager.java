package view.user;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	public void start(Stage stage) throws Exception {
		stage = new Stage(StageStyle.UTILITY);
		UserLoginManager.stage = stage;
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		/*
		 * initialize the persistent class.
		 */
		Persistent.initialize();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(userManagementFXML));
		loader.setRoot(userManagementView);
		StackPane root = loader.load(getClass().getResourceAsStream(
				userManagementFXML));
		stage.setScene(new Scene(root));
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