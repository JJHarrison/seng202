package view.dash;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import resources.Reference;
import view.Main;
import view.warning.Warning;
import view.warning.Warning.Risk;
import data.model.Event;
import data.model.Summary;
import data.persistant.Persistent;
import data.persistant.Saver;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class DashController {

	@FXML
	Button buttonImage;
	
	@FXML
	ImageView imageProfile;

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
		// show warnings for resting heart rate
		if (Persistent.getCurrentUser().hasBradycardia()) {
			warningPane.getChildren().add(
					new Warning(Risk.BRADYCARDIA, Calendar.getInstance()));
		}
		if (Persistent.getCurrentUser().hasTachycardia()) {
			warningPane.getChildren().add(
					new Warning(Risk.TACHYCARDIA, Calendar.getInstance()));
		}
		
		// show any warnings for each event
		// might want this to only show recent events...
		for (Event e: Persistent.getCurrentUser().getEvents().getAllEvents()) {
			if (e.hasBradycardia()) {
				warningPane.getChildren().add(
						new Warning(Risk.BRADYCARDIA, e.getStartTime()));
			}
			if (e.hasTachycardia()) {
				warningPane.getChildren().add(
						new Warning(Risk.TACHYCARDIA, e.getStartTime()));
			}
		}
	}

	/**
	 * Fill the dash board with all the totals and achievements.
	 */
	public void fillDash() {
		fillMonth();
		fillTotal();
		fillWarnings();
	}
	
	@FXML
	void actionSetImage(ActionEvent event) {
		Image image = new Image(Reference.class.getResourceAsStream("p.jpg"));
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		// Set filters
		ArrayList<String> filterPNG = new ArrayList<String>();
		filterPNG.add("*.png");
		ArrayList<String> filterJPG = new ArrayList<String>();
		filterJPG.add("*.jpg");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", filterJPG));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", filterPNG));
		
		fileChooser.showOpenDialog(Main.stage);
		//File file = f
		
		
		Saver.SaveProfilePicture(image, Persistent.getCurrentUser());
		imageProfile.setFitHeight(160);
		imageProfile.setFitWidth(160);
		imageProfile.preserveRatioProperty().set(false);
		imageProfile.setClip(new Circle(80.0, 80.0, 80.0));
		imageProfile.setImage(image);
	}

}