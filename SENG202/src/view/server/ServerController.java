/**
 * Controller for the Server console interface
 * @author Daniel Tapp
 */

package view.server;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


public class ServerController implements Initializable {
	String consoleText = "";
	
	@FXML
	TextArea textConsole;
	
	Boolean serverRunning = false;

	@FXML
	void actionStart() {
		if (serverRunning) {
			consoleText = consoleText + "Server is already on" + '\n';
			setConsoleText();
			scrolldown();
		} else {
			consoleText = consoleText + "Server Started" + '\n';
			setConsoleText();
			scrolldown();
			serverRunning = true;
		}
	}
	
	@FXML
	void actionStop() {
		if (serverRunning) {
			consoleText = consoleText + "Server Stopped" + '\n';
			setConsoleText();
			scrolldown();
			serverRunning = false;
		} else {
			consoleText = consoleText + "Server isn't running" + '\n';
			setConsoleText();
			scrolldown();
		}
	}

	private void setConsoleText() {
		textConsole.setText(consoleText);
	}
	
	private void scrolldown() {
		textConsole.setScrollTop(Double.MAX_VALUE);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
}