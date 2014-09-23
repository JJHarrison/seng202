package view.warning;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
	Label labelTime;

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
	
	/**
	 * Set the description for the warning pane
	 * @param description The description to give the warning tile.
	 */
	protected void setDescription(String description) {
		textDescription.setText(description);
	}
	
	/**
	 * Set the date recorded for the warning tile.
	 * @param date The date the warning occurred on.
	 */
	protected void setDate(Calendar date) {
		SimpleDateFormat tf = new SimpleDateFormat("MMMM d, h:mm a");
		labelTime.setText(tf.format(date.getTime()));
	}
	
	/**
	 * Set the type of risk the warning tile is.
	 * @param risk The type of risk (e.g. Bradycardia)
	 */
	protected void setRisk(String risk) {
		labelRisk.setText(risk);
	}
}
