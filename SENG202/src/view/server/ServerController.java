/**
 * Controller for the Server console interface
 * @author Daniel Tapp, James Harrison
 */

package view.server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import server.Server;

public class ServerController {
	public static Server server;
	private Thread serverThread;
	@FXML
	TextArea textConsole;
	static TextArea staticTextArea;
	private Boolean serverRunning = false;
	
	/**
	 * Actions taken when the 'START' button is used.
	 */
	@FXML
	void actionStart() {
		if (serverRunning) {
			setConsoleText("The server is already running.\n");
		} else {			
			server = new Server();
			serverThread = new Thread(server);
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
			server.interrupt();
			try {
				server.stopServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
			serverRunning = false;
		} else {
			setConsoleText("The server is currently not running.\n");
		}
	}

	/**
	 * Adds a new line to the console text area
	 */
	public static void setConsoleText(String newLine) {
		staticTextArea.appendText(newLine);
	}
	
	@FXML
	void initialize() {
		staticTextArea = this.textConsole;
	}
	
}
