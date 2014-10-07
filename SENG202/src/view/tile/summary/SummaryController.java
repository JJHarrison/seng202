package view.tile.summary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import data.model.Event;

/**
 * Controller.
 * 
 * @author Daniel van Wichen, Simon
 *
 */
public class SummaryController {

	@FXML
	Label labelDuration;

	@FXML
	Label labelDistance;

	@FXML
	Label labelMaxSpeed;

	@FXML
	Label labelAverageSpeed;

	@FXML
	Label labelMaxHR;

	@FXML
	Label labelAverageHR;

	@FXML
	Label labelCalories;

	@FXML
	Label labelWarning;

	@FXML
	AnchorPane paneWarning;

	@FXML
	void initialize() {
		paneWarning.setOpacity(0);
	}

	/**
	 * Fill the summary view by setting the text of all labels.
	 * 
	 * @param event the event of this tile.
	 */
	public void fill(Event event) {
		labelDuration.setText(event.getDurationString());
		labelDistance.setText(event.getDistanceString());
		labelAverageHR.setText(event.avgHRString());
		labelAverageSpeed.setText(event.avgSpeedString());
		labelMaxSpeed.setText(event.maxSpeedString());
		labelMaxHR.setText(event.maxHRString());
		labelCalories.setText(event.getCaloriesString());
		fillWarning(event);
	}
	
	/**
	 * Fills the warning pane with the appropriate warning message, or leaves
	 * it invisible if there is no warning for the event.
	 */
	private void fillWarning(Event event) {
		boolean bradycardia = event.hasBradycardia();
		boolean tachycardia = event.hasTachycardia();
		
		if (bradycardia && tachycardia) {
			labelWarning.setText("Bradycardia and tachycardia detected");
			paneWarning.setOpacity(1);
		} else if (bradycardia) {
			labelWarning.setText("Bradycardia detected");
			paneWarning.setOpacity(1);
		} else if (tachycardia) {
			labelWarning.setText("Tachycardia detected");
			paneWarning.setOpacity(1);
		}
	}

}
