/**
 * Controller for the Server console interface
 * @author Daniel Tapp
 */

package view.server;

import java.net.URL;
import java.util.ResourceBundle;

import server.Server;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


public class ServerController implements Initializable {

	String consoleText = "";
	private Server s = new Server();

	@FXML
	TextArea textConsole;
	
	Boolean serverRunning = false;

	@FXML
	void actionStart() {
		if (serverRunning) {
			setConsoleText("The server is already running.");
		} else {			
			setConsoleText("Server Started!");
			Task<Void> t = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					// TODO Auto-generated method stub
					s.startServer();
					return null;
				}
			};
			Thread thread = new Thread(t);
			thread.start();
			serverRunning = true;
		}
	}
	
	@FXML
	void actionStop() {
		if (serverRunning) {
			s.shutDownServer();
			setConsoleText("Server Stopped.");
			serverRunning = false;
		} else {
			setConsoleText("The server is currently not running.");
		}
	}

	public void setConsoleText(String newLine) {
		consoleText = consoleText + newLine + '\n';
		textConsole.setText(consoleText);
		scrolldown();
	}
	
	private void scrolldown() {
		textConsole.setScrollTop(Double.MAX_VALUE);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}