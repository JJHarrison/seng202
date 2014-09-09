package view.user;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Daniel van Wichen
 */
public class UserLoginManager extends Application {
	public static String loginFXML = "UserLogin.fxml";
	public static String createFXML = "UserCreate.fxml";

	@Override
	public void start(Stage stage) throws Exception {
		ScreenPane mainContainer = new ScreenPane();
		mainContainer.loadScreen("userLogin", UserLoginManager.loginFXML);
		mainContainer.loadScreen("userCreate", UserLoginManager.createFXML);
		mainContainer.setScreen("userLogin");

		Scene scene = new Scene(mainContainer);

		// xx.getChildren().remove(test);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}