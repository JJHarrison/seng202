package view.notification;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotificationController {

	@FXML
	Label addedLabel;
	
	@FXML
	Label ignoredLabel;
	
	@FXML
	Label errorsLabel;
	
	@FXML
	Label badPointsLabel;
	

	@FXML
	void actionOk() {
		Notification.stage.close();
	}
	
	public void fill() {
		addedLabel.setText(data.loader.LoadSummary.getAddedEvents());
		ignoredLabel.setText(data.loader.LoadSummary.getIgnoredEvents());
		errorsLabel.setText(data.loader.LoadSummary.getLineErrors());
		badPointsLabel.setText(data.loader.LoadSummary.getIgnoredPoints());
		data.loader.LoadSummary.clear();
	}
	
	@FXML
	void initialize() {
	}
}
