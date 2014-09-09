package view.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class UserLoginManager {
	@FXML
	AnchorPane paneUserLogin;
	@FXML
	AnchorPane paneUserCreate;
	@FXML
	BorderPane paneLoginManager;
	
	
	
	
	private static Stage stage = new Stage(StageStyle.DECORATED);
	private static UserLoginManager userLoginManager = new UserLoginManager();
	
	private void initialize() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"UserLoginManager.fxml"));
		try {
			BorderPane rootLayout = (BorderPane) fxmlLoader.load();
			Scene scene = new Scene(rootLayout);
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stage.show();
		
	}

	public static void show() {
		userLoginManager.initialize();
		
	}


}