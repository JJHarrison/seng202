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

	/**
	 * Creates a new task that contains the server.
	 * This way the interface wont lag when the server is operating.
	 */
	Task<Void> t = new Task<Void>() {
		@Override
		protected Void call() throws Exception {
			s.startServer();	
			if (isCancelled()) {
				System.out.println("cancelled");
				
				s.shutDownServer();
			}
			return null;
		}
	};
	
	/**
	 * Actions taken when the 'START' button is used.
	 */
	@FXML
	void actionStart() {
		if (serverRunning) {
			setConsoleText("The server is already running.");
		} else {			
			setConsoleText("Server Started!");

			Thread thread = new Thread(t);
			thread.start();
			serverRunning = true;
		}
	}
	
	/**
	 * Actions taken when the 'STOP' button is used.
	 */
	@FXML
	void actionStop() {
		if (serverRunning) {
			if (t.cancel()) {
				setConsoleText("Server Stopped.");
			}
			serverRunning = false;
		} else {
			setConsoleText("The server is currently not running.");
		}
	}

	/**
	 * Adds a new line to the console output on the interface with the passed String
	 */
	public void setConsoleText(String newLine) {
		consoleText = consoleText + newLine + '\n';
		textConsole.setText(consoleText);
		scrolldown();
	}
	
	/**
	 * Scrolls the console window to the bottom to keep the latest line in focus
	 */
	private void scrolldown() {
		textConsole.setScrollTop(Double.MAX_VALUE);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}
