/**
 * Server console interface
 * @author Daniel Tapp
 */

package view.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Server extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/server/Server.fxml"));
			Scene scene = new Scene(root);
						
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					System.out.println("stage is closing....");
					//need to somehow call the server.stopServer() but idk how this sees controller
				}
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		primaryStage.setTitle("Fitr Server Console");
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}