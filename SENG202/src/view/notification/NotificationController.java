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
	
	@FXML
	void initialize() {
	}
}
