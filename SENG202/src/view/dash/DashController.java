package view.dash;

import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.warning.Warning;
import view.warning.Warning.Risk;
import data.model.Summary;
import data.persistant.Persistent;

/**
 * 
 * @author Daniel van Wichen
 *
 */
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
	 * Fills the dashboard view with the user information.
	 */
	private void fillUser() {
		labelName.setText(Persistent.getCurrentUser().getName());
		labelAge.setText(Integer.toString(Persistent.getCurrentUser().getAge()));
		labelHeight.setText(String.format("%.0f", Persistent.getCurrentUser().getHeight()) + " cm");
		labelWeight.setText(String.format("%.0f", Persistent.getCurrentUser().getWeight()) + " kg");
		labelHR.setText(String.format("%d", Persistent.getCurrentUser().getRestingHeartRate()));
		labelBMI.setText(String.format("%.0f", Persistent.getCurrentUser().getBMI()));
	}

	/**
	 * Calculates and displays the total distance, total calories and total duration for the month to be displayed in the dashboard.
	 */
	private void fillMonth() {
		Calendar to = Calendar.getInstance();
		Calendar from = Calendar.getInstance();
		from.add(Calendar.MONTH, -1);

		summaryMonth = new Summary(Persistent.getCurrentUser().getEvents(), from, to);
		monthKmLabel.setText(summaryMonth.getTotalDistance());
		monthCaloriesLabel.setText(summaryMonth.getTotalCalories());
		monthHoursLabel.setText(summaryMonth.getTotalDuration());
	}

	/**
	 * Calculates and displays the total distance, total calories and total duration for all time to be displayed in the dashboard.
	 */
	private void fillTotal() {
		summaryTotal = new Summary(Persistent.getCurrentUser().getEvents(), null, null);
		totalKmLabel.setText(summaryTotal.getTotalDistance());
		totalCaloriesLabel.setText(summaryTotal.getTotalCalories());
		totalHoursLabel.setText(summaryTotal.getTotalDuration());
		fillAchievements();
	}

	/**
	 * Displays the achievements for that user consisting of max distance, max duration and max speed over all events.
	 */
	private void fillAchievements() {
		achieveMaxDistance.setText(summaryTotal.getMaxDistance());
		achieveMaxHours.setText(summaryTotal.getMaxDuration());
		achieveMaxSpeed.setText(summaryTotal.maxSpeed());
	}

	/**
	 * Displays the warnings the user has generated.
	 */
	private void fillWarnings() {
		warningPane.getChildren().add(
				new Warning(Risk.BRADYCARDIA, Calendar.getInstance()));
		warningPane.getChildren().add(
				new Warning(Risk.TACHYCARDIA, Calendar.getInstance()));
		warningPane.getChildren().add(
				new Warning(Risk.BRADYCARDIA, Calendar.getInstance()));
	}

	/**
	 * Fill the dash board with all the totals and achievements.
	 */
	public void fillDash() {
		fillMonth();
		fillTotal();
		fillWarnings();
	}

}