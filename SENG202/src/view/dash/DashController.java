package view.dash;

import resources.Reference;
import user.User.Gender;
import data.persistant.Persistent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashController {

	@FXML
	ImageView imageGender;
	
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
		if (Persistent.getCurrentUser().getGender() == Gender.MALE) {
			imageGender.setImage(new Image(Reference.class.getResourceAsStream("male.png")));
		} else {
			imageGender.setImage(new Image(Reference.class.getResourceAsStream("female.png")));
		}
		labelName.setText(Persistent.getCurrentUser().getName());
		labelAge.setText(Integer.toString(Persistent.getCurrentUser().getAge()));
		labelHeight.setText(String.format("%.0f", Persistent.getCurrentUser().getHeight()) + " cm");
		labelWeight.setText(String.format("%.0f", Persistent.getCurrentUser().getWeight()) + " kg");
		labelHR.setText(String.format("%d", Persistent.getCurrentUser().getRestingHeartRate()));
		labelBMI.setText(String.format("%.0f", Persistent.getCurrentUser().getBMI()));
		
	}

}