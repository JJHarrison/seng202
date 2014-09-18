package view.warning;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class WarningController {

	@FXML
	AnchorPane textPane;

	@FXML
	VBox warning;

	private boolean isOpen = false;

	@FXML
	void initialize() {
		warning.getChildren().remove(textPane);
	}

	@FXML
	void hideContent(MouseEvent event) {
		if (isOpen) {
			warning.getChildren().remove(textPane);
			isOpen = !isOpen;
		} else {
			warning.getChildren().add(textPane);
			isOpen = !isOpen;
		}
	}

	@FXML
	void hideContentButton(ActionEvent event) {
		if (isOpen) {
			warning.getChildren().remove(textPane);
			isOpen = !isOpen;
		} else {
			warning.getChildren().add(textPane);
			isOpen = !isOpen;
		}
	}
}
