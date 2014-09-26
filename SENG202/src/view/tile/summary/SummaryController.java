package view.tile.summary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import data.model.Event;

/**
 * 
 * @author Daniel van Wichen
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
	}

}
