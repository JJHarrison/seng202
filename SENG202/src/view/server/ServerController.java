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
	
	private Server s;
	private Thread serverThread;

	@FXML
	static
	TextArea textConsole;
	
	Boolean serverRunning = false;

	
	/**
	 * Actions taken when the 'START' button is used.
	 */
	@FXML
	void actionStart() {
		if (serverRunning) {
			setConsoleText("The server is already running.");
		} else {			
			setConsoleText("Server Started!");
			s = new Server(textConsole);
			serverThread = new Thread(s);
			serverThread.start();
			
			serverRunning = true;
		}
	}
	
	/**
	 * Actions taken when the 'STOP' button is used.
	 */
	@FXML
	void actionStop() {
		if (serverRunning) {
			setConsoleText("Server ending");
			serverThread.interrupt();
			
			try {
				s.stopServer();
                serverThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
			s = null;
			serverThread = null;
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
		//textConsole.setText(consoleText);
		//scrolldown();
	}
	
	/**
	 * Scrolls the console window to the bottom to keep the latest line in focus
	 */
	private static void scrolldown() {
		textConsole.setScrollTop(Double.MAX_VALUE);
	}
	
	public static void updateConsole(TextArea console) {
		textConsole = console;
		scrolldown();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}
