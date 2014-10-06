package view.warning;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class WarningController {

	@FXML
	AnchorPane textPane;

	@FXML
	VBox warning;
	
	@FXML
	Label labelRisk;
	
	@FXML
	TextArea textDescription;
	
	@FXML
	ToggleButton buttonPlus;
	
	@FXML
	Label labelTime;

	private boolean isOpen = false;

	@FXML
	void initialize() {
		warning.getChildren().remove(textPane);
	}

	@FXML
	void hideContent(MouseEvent event) {
		buttonPlus.setSelected(! buttonPlus.selectedProperty().get());
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
	
	/**
	 * Set the description for the warning pane
	 * @param description the description to give the warning tile.
	 */
	protected void setDescription(String description) {
		textDescription.setText(description);
	}
	
	/**
	 * Set the date recorded for the warning tile.
	 * @param date the date the warning occurred on.
	 */
	protected void setCause(String cause) {
		labelTime.setText(cause);
	}
	
	/**
	 * Set the type of risk the warning tile is.
	 * @param risk the type of risk (e.g. Bradycardia).
	 */
	protected void setRisk(String risk) {
		labelRisk.setText(risk);
	}
}
