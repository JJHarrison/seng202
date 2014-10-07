package view.user;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class UserUpdate {

	public static Stage stage;

	public void start(Stage primaryStage) {
		stage = primaryStage;

		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("UserUpdate.fxml"));
			Parent root = loader.load(getClass().getResourceAsStream("UserUpdate.fxml"));
			
			Scene scene = new Scene(root);

			primaryStage.initOwner(Main.stage);
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.WINDOW_MODAL);

		} catch (Exception e) {
			//e.printStackTrace();
		}
		primaryStage.setResizable(false);
		primaryStage.show();

	}
	
	public static void close() {
		stage.close();
	}
}
