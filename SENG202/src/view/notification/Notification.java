package view.notification;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Main;



public class Notification extends Application {

	public static Stage stage = new Stage();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage = stage;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/notification/Notification.fxml"));
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
	
	
	public static void main(String[] args) {
		launch(args);
	}
}