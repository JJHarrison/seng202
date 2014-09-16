package view.tile.summary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import data.model.Event;

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

    private Event event;

    public void fill(Event event) {
	this.event = event;
	labelDuration.setText(event.getDurationString());
	labelDistance.setText(String.format("%f", event.getDistance()));
	labelAverageHR
		.setText(String.format("%d", event.getAverageHeartRate()));
	labelAverageSpeed.setText(String.format("%f", event.getAverageSpeed()));
	labelMaxSpeed.setText(String.format("%f", event.getMaxSpeed()));
	labelMaxHR.setText(String.format("%d", event.getMaxHeartRate()));
    }

}
