package view.dash;

import user.User.Gender;
import data.persistant.Persistent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class DashController {

	@FXML
	Image imageGender;
	
	@FXML
	Label labelName;
	@FXML
	Label labelAge;
	@FXML
	Label labelHeight;
	@FXML
	Label labelWeight;
	@FXML
	Label labelHR;
	@FXML
	Label labelBMI;
	
	@FXML
	void initialize() {
		if (Persistent.getCurrentUser().getGender().equals(Gender.MALE) {
			
		} else {
			
		}
	}

}