package view.dash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import resources.Reference;
import view.Main;
import view.warning.Warning;
import view.warning.Warning.Risk;
import data.model.Event;
import data.model.Summary;
import data.persistant.Loader;
import data.persistant.Persistent;
import data.persistant.Saver;

/**
 * Controller.
 * 
 * @author Daniel van Wichen, Simon
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
		imageProfile.setFitHeight(160);
		imageProfile.setFitWidth(160);
		imageProfile.setClip(new Circle(80.0, 80.0, 80.0));
		try {
			Image image = Loader.loadProfileImage(Persistent.getCurrentUser());
			imageProfile.setImage(image);
		} catch (FileNotFoundException e) {
			Image image = new Image(
					Reference.class.getResourceAsStream("profile.png"));
			imageProfile.setImage(image);
		}
	}

	/**
	 * Fills the dashboard view with the user information.
	 */
	public void fillUser() {
		labelName.setText(Persistent.getCurrentUser().getName());
		labelAge.setText(Integer.toString(Persistent.getCurrentUser().getAge()));
		labelHeight.setText(String.format("%.0f", Persistent.getCurrentUser()
				.getHeight())
				+ " cm");
		labelWeight.setText(String.format("%.0f", Persistent.getCurrentUser()
				.getWeight())
				+ " kg");
		labelHR.setText(String.format("%d", Persistent.getCurrentUser()
				.getRestingHeartRate()));
		labelBMI.setText(String.format("%.0f", Persistent.getCurrentUser()
				.getBMI()));
	}

	/**
	 * Calculates and displays the total distance, total calories and total duration for the month to be displayed in the dashboard.
	 */
	private void fillMonth() {
		Calendar to = Calendar.getInstance();
		Calendar from = Calendar.getInstance();
		from.add(Calendar.MONTH, -1);

		summaryMonth = new Summary(Persistent.getCurrentUser().getEvents(),
				from, to);
		monthKmLabel.setText(summaryMonth.getTotalDistance());
		monthCaloriesLabel.setText(summaryMonth.getTotalCalories());
		monthHoursLabel.setText(summaryMonth.getTotalDuration());
	}

	/**
	 * Calculates and displays the total distance, total calories and total duration for all time to be displayed in the dashboard.
	 */
	private void fillTotal() {
		summaryTotal = new Summary(Persistent.getCurrentUser().getEvents(),
				null, null);
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
		boolean warningShown = false;
		
		// show warnings for resting heart rate
		if (Persistent.getCurrentUser().hasBradycardia()) {
			String cause = "Resting heart rate too low: "
					+ Persistent.getCurrentUser().getRestingHeartRate();
			warningPane.getChildren().add(new Warning(Risk.BRADYCARDIA, cause));
			warningShown = true;
		}
		if (Persistent.getCurrentUser().hasTachycardia()) {
			String cause = "Resting heart rate too high: "
					+ Persistent.getCurrentUser().getRestingHeartRate();
			warningPane.getChildren().add(new Warning(Risk.TACHYCARDIA, cause));
			warningShown = true;
		}

		// show any warnings for each event
		// might want this to only show recent events...
		for (Event e : Persistent.getCurrentUser().getEvents().getAllEvents()) {
			if (e.hasBradycardia()) {
				SimpleDateFormat tf = new SimpleDateFormat("MMMM d yyyy, h:mm a");
				String timeString = tf.format(e.getStartTime().getTime());
				String cause = timeString + "     Heart Rate too low: "
						+ e.getMinHeartRate();
				warningPane.getChildren().add(
						new Warning(Risk.BRADYCARDIA, cause));
				warningShown = true;
			}
			if (e.hasTachycardia()) {
				SimpleDateFormat tf = new SimpleDateFormat("MMMM d yyyy, h:mm a");
				String timeString = tf.format(e.getStartTime().getTime());
				String cause = timeString + "     Heart Rate too high: "
						+ e.getMaxHeartRate();
				warningPane.getChildren().add(
						new Warning(Risk.TACHYCARDIA, cause));
				warningShown = true;
			}
		}
		
		if (!warningShown) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Warning.class.getResource("NoWarnings.fxml"));
			try {
				AnchorPane pane = loader.load(Warning.class.getResourceAsStream("NoWarnings.fxml"));
				warningPane.getChildren().add(pane);
			} catch (IOException ioException) {
				//ioException.printStackTrace();
			}
		}
	}

	/**
	 * Fill the dash board with all the totals and achievements.
	 */
	public void fillDash() {
		// avoid double ups
		warningPane.getChildren().clear();
		fillMonth();
		fillTotal();
		fillWarnings();
	}

	@FXML
	void actionSetImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));

		// Set filters
		ArrayList<String> filterIMG = new ArrayList<String>();
		filterIMG.add("*.png");
		filterIMG.add("*.jpg");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Image File", filterIMG));

		Image image;
		File file = fileChooser.showOpenDialog(Main.stage);
		if (file != null) {
			image = new Image(file.toURI().toString(), 160, 160, false, true);
			Saver.SaveProfilePicture(image, Persistent.getCurrentUser());
			imageProfile.setImage(image);

		}

	}

}
