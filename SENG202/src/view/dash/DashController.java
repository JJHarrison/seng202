package view.dash;

import java.util.Calendar;

import resources.Reference;
import user.User.Gender;
import data.model.Summary;
import data.persistant.Persistent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
	Label totalKmLabel;
	@FXML
	Label totalHoursLabel;
	@FXML
	Label totalCaloriesLabel;

	@FXML
	Label monthKmLabel;
	@FXML
	Label monthHoursLabel;
	@FXML
	Label monthCaloriesLabel;

	@FXML
	Label achieveMaxSpeed;
	@FXML
	Label achieveMaxDistance;
	@FXML
	Label achieveMaxHours;

	@FXML
	VBox warningPane;
	
	private Summary summaryTotal;
	private Summary summaryMonth;

	@FXML
	void initialize() {
		fillUser();
		fillDash();
	}

	/**
	 * Fills the user information into the dashboard
	 */
	private void fillUser() {
		if (Persistent.getCurrentUser().getGender() == Gender.MALE) {
			imageGender.setImage(new Image(Reference.class
					.getResourceAsStream("male.png")));
		} else {
			imageGender.setImage(new Image(Reference.class
					.getResourceAsStream("female.png")));
		}
		labelName.setText(Persistent.getCurrentUser().getName());
		labelAge.setText(Integer.toString(Persistent.getCurrentUser().getAge()));
		labelHeight.setText(String.format("%.0f", Persistent.getCurrentUser().getHeight()) + " cm");
		labelWeight.setText(String.format("%.0f", Persistent.getCurrentUser().getWeight()) + " kg");
		labelHR.setText(String.format("%d", Persistent.getCurrentUser().getRestingHeartRate()));
		labelBMI.setText(String.format("%.0f", Persistent.getCurrentUser().getBMI()));
	}
	
	private void fillMonth() {
		Calendar to = Calendar.getInstance();
		Calendar from = Calendar.getInstance();
		from.add(Calendar.MONTH, -1);
		
		summaryMonth = new Summary(Persistent.getCurrentUser().getEvents(), from, to);
		monthKmLabel.setText(summaryMonth.getTotalDistance());
		monthCaloriesLabel.setText(summaryMonth.getTotalCalories());
		monthHoursLabel.setText(summaryMonth.getTotalDuration());
	}
	
	private void fillTotal() {
		summaryTotal = new Summary(Persistent.getCurrentUser().getEvents(), null, null);
		totalKmLabel.setText(summaryTotal.getTotalDistance());
		totalCaloriesLabel.setText(summaryTotal.getTotalCalories());
		totalHoursLabel.setText(summaryTotal.getTotalDuration());
		fillAchievements();
	}
	
	private void fillAchievements() {
		System.out.print(summaryTotal.getMaxDistance());
		System.out.print(Persistent.getCurrentUser());
		achieveMaxDistance.setText(summaryTotal.getMaxDistance());
		achieveMaxHours.setText(summaryTotal.getMaxDuration());
		achieveMaxSpeed.setText(summaryTotal.maxSpeed());
	}
	
	/**
	 * Fill the dash board with all the totals and achievements.
	 */
	public void fillDash() {
		fillMonth();
		fillTotal();
	}

}