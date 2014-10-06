/**
 * Server console interface
 * @author Daniel Tapp
 */

package view.server;

import java.io.IOException;

import javafx.application.Application;
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
					//need to somehow call the server.stopServer() but idk how this sees controller
					try {
						ServerController.server.stopServer();
					} catch (IOException e) {
						e.printStackTrace();
					} catch(NullPointerException np) {
						System.exit(0);
					}
					
				}
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		primaryStage.setTitle("Fitr Server Console");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(400);
		primaryStage.setResizable(false);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}