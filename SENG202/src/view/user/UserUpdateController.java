package view.user;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import user.User;
import user.User.Gender;
import view.MainController;
import data.persistant.Persistent;
import data.persistant.Saver;
import extfx.scene.control.NumberSpinner;

/**
 * Controller.
 * 
 * @author Daniel van Wichen
 *
 */
public class UserUpdateController {
	public static final Double MAX_WEIGHT = 180.0; // kg
	public static final Double MIN_WEIGHT = 0.0; // kg
	public static final Double MAX_HEIGHT = 250.0; // cm
	public static final Double MIN_HEIGHT = 0.0; // cm
	public static final Integer MAX_HR = 200; // resting hr
	public static final Integer MIN_HR = 20; // resting hr
	public static final Integer MAX_NAME_LENGTH = 20;

	@FXML
	Label labelCreateWarning;

	@FXML
	TextField fieldName;
	@FXML
	ComboBox<Gender> fieldGender;
	@FXML
	NumberSpinner fieldHeight;
	@FXML
	NumberSpinner fieldWeight;
	@FXML
	NumberSpinner fieldHR;
	@FXML
	AnchorPane paneUpdate;
	
	DatePicker fieldDate = new DatePicker();

	@FXML
	void initialize() {
		AnchorPane.setLeftAnchor(fieldDate, 200.0);
		AnchorPane.setTopAnchor(fieldDate, 75.0);
		paneUpdate.getChildren().add(fieldDate);
		
		// Redirect the output stream so the error doesn't show
		System.setErr(new PrintStream(new OutputStream() {
			
			@Override
			public void write(int b) throws IOException {
			}
		}));
		
		fieldGender.setItems(FXCollections.observableArrayList(Gender.values()));
		fieldName.setPromptText("Enter your name e.g. Bob Smith");
		fieldWeight.setPromptText("Enter your weight in kg");
		fieldHeight.setPromptText("Enter your height in cm");
		fieldHR.setPromptText("Enter your resting heart rate");
		fieldWeight.setMinValue(0);
		fieldHeight.setMinValue(0);
		fieldHR.setMinValue(0);
		
		User user = Persistent.getCurrentUser();
		fieldName.setText(user.getName());
		
		LocalDate date = LocalDate.of(user.getDateofBirth().get(Calendar.YEAR),
				user.getDateofBirth().get(Calendar.MONTH) + 1, 
				user.getDateofBirth().get(Calendar.DAY_OF_MONTH));
		fieldDate.setValue(date);
		fieldDate.setPromptText("dd/mm/yyyy");
		
		fieldGender.setValue(user.getGender());
		fieldWeight.setValue(user.getWeight());
		fieldHeight.setValue(user.getHeight());
		fieldHR.setValue(user.getRestingHeartRate());
		
	}

	@FXML
	void actionCancelUpdate(ActionEvent event) {
		UserUpdate.close();
	}

	/**
	 * Creates a user taking the parameter from the form filling.
	 * 
	 * @param event 
	 */
	@FXML
	void actionUpdate(ActionEvent event) {
		String name = fieldName.getText().trim();
		LocalDate date = fieldDate.getValue();
		Gender gender = fieldGender.getValue();
		Number height = fieldHeight.getValue();
		Number weight = fieldWeight.getValue();
		Number hr = fieldHR.getValue();

		if (name.trim().isEmpty()) {
			labelCreateWarning.setText("Please provide your name");
		} else if (!name.matches("([a-zA-Z]|[\\s])*")) {
			labelCreateWarning.setText("Please enter a valid name");
		} else if (name.length() > MAX_NAME_LENGTH) {
			labelCreateWarning.setText("Name must be less than 20 characters");
		} else if (Persistent.getUserNames().contains(name) && ! name.equals(Persistent.getCurrentUser().getName())) {
			labelCreateWarning.setText("User name already used");
		} else if (date == null) {
			labelCreateWarning.setText("Birthdate not set");
		} else if (date.isAfter(LocalDate.now())) {
			labelCreateWarning.setText("Cant select birthdate in the future");
		} else if (gender == null) {
			labelCreateWarning.setText("Gender not set");
		} else if (height == null) {
			labelCreateWarning.setText("Please provide your height (cm)");
		} else if (height.doubleValue() > MAX_HEIGHT || height.doubleValue() < MIN_HEIGHT) {
			labelCreateWarning.setText("Enter your height between 0 - 250 cm");
		} else if (weight == null) {
			labelCreateWarning.setText("Please provide your weight (kg)");
		} else if (weight.doubleValue() > MAX_WEIGHT || weight.doubleValue() < MIN_WEIGHT) {
			labelCreateWarning.setText("Enter your weight between 0 and 180 kg");
		} else if (hr == null) {
			labelCreateWarning.setText("Please provide your average heart rate");
		} else if (hr.intValue() > MAX_HR || hr.intValue() < MIN_HR) {
			labelCreateWarning.setText("Enter a heart rate between 20 and 200 bpm");
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
			
			Persistent.getCurrentUser().setDateofBirth(calendar);
			Persistent.getCurrentUser().setGender(gender);
			Persistent.getCurrentUser().setName(name);
			Persistent.getCurrentUser().setWeight(weight.doubleValue());
			Persistent.getCurrentUser().setHeight(height.doubleValue());
			Persistent.getCurrentUser().setRestingHeartRate(hr.intValue());
			Saver.SaveUser(Persistent.getCurrentUser());
			
			// Refresh the view
			MainController.dashController.fillUser();
			MainController.dashController.fillDash();
			UserLoginController.refreshUsers();
			UserUpdate.close();
		}
	}
}
