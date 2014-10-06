package view.notification;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;



public class Notification {

	public static Stage stage = new Stage();
	
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage = stage;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Notification.java"));
			Parent root = loader.load(getClass().getResourceAsStream("Notification.fxml"));
			NotificationController controller = loader.getController();
			
			controller.fill();
			
			Scene scene = new Scene(root);
			
			primaryStage.initOwner(Main.stage);
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.WINDOW_MODAL);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(260);
		primaryStage.setResizable(false);
		
		primaryStage.show();

	}
	
}