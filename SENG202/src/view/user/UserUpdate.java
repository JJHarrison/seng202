package view.user;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;
import view.dash.DashController;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class UserUpdate {

	public static Stage stage = new Stage();
	
	/**
	 * Constructor.
	 * 
	 * @param dashController
	 */
	public UserUpdate(DashController dashController) {
		dashController.fillDash();
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage = stage;

		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("UserUpdate.fxml"));
			Parent root = loader.load(getClass().getResourceAsStream("UserUpdate.fxml"));
			
			Scene scene = new Scene(root);

			primaryStage.initOwner(Main.stage);
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.WINDOW_MODAL);

		} catch (Exception e) {
		}

		//primaryStage.setMinWidth(300);
		//primaryStage.setMinHeight(260);
		primaryStage.setResizable(false);

		primaryStage.show();

	}
}
