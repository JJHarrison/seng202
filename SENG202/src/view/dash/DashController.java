package view.dash;

import java.util.GregorianCalendar;

import data.persistant.Persistent;
import view.warning.Warning;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashController {

	@FXML
	VBox warningPane;

	@FXML
	Label userName;
	
	@FXML
	void initialize() {
		userName.setText(Persistent.getCurrentUser().getName());
		
		
		warningPane.getChildren().add(
				new Warning("", "", new GregorianCalendar()));
		warningPane.getChildren().add(
				new Warning("", "", new GregorianCalendar()));
	}

}
