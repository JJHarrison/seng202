package view.dash;

import java.util.GregorianCalendar;

import view.warning.Warning;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class DashController {

	@FXML
	VBox warningPane;

	@FXML
	void initialize() {
		warningPane.getChildren().add(
				new Warning("", "", new GregorianCalendar()));
		warningPane.getChildren().add(
				new Warning("", "", new GregorianCalendar()));
	}

}
