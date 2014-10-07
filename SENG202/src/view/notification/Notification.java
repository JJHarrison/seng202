package view.notification;

/**
 * 
 * @author Daniel Tapp
 *
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;

/**
 * A custom notification panel for showing 
 * 
 * @author Daniel van Wichen, Daniel Tapp
 *
 */
public class Notification {

	/**
	 * The stage of the notification panel.
	 */
	public static Stage stage;
	
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		
		// Create the Window
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Notification.java"));
			Parent root = loader.load(getClass().getResourceAsStream("Notification.fxml"));
			NotificationController controller = loader.getController();
			
			// Fills the labels on the notification panel with the appropriate data
			controller.fill();
			
			Scene scene = new Scene(root);
			
			
			// Makes it so that the window acts like a pop-up
			// (Cannot use the program until 'Ok' is clicked)
			primaryStage.initOwner(Main.stage);
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.WINDOW_MODAL);
			
		} catch(Exception e) {
			//e.printStackTrace();
		}
		
		// Set the size of the window
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(260);
		primaryStage.setResizable(false);
		
		// Show the window
		primaryStage.show();

	}
	
}