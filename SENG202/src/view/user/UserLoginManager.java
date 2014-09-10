package view.user;

import view.persistent.PersistentDialog;
import data.persistant.Persistent;
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
		Persistent.init();

		Scene scene = new Scene(mainContainer);
		stage.setScene(scene);
		System.out.println(Persistent.getFilePath());
		if (!Persistent.filePathSet()) {
			PersistentDialog.show(stage);
		}
		
		stage.show();
		stage.centerOnScreen();
		//stage.close();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}