package view.user;

import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import user.User;
import user.User.Gender;
import view.user.UserManagementController.View;
import data.model.EventContainer;
import data.persistant.Persistent;
import extfx.scene.control.DatePicker;
import extfx.scene.control.NumberSpinner;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class UserCreateController implements Switchable {
	private UserManagementController controller;
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
	AnchorPane paneCreate;

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
	
	DatePicker fieldDate = new DatePicker();

	@Override
	public void setController(UserManagementController controller) {
		this.controller = controller;
		fieldName.setPromptText("Enter your name e.g. Bob Smith");
		fieldWeight.setPromptText("Enter your weight in kg");
		fieldHeight.setPromptText("Enter your height in cm");
		fieldHR.setPromptText("Enter your resting heart rate");
		fieldWeight.setMinValue(0);
		fieldHeight.setMinValue(0);
		fieldHR.setMinValue(0);
	}

	@FXML
	void initialize() {
		fieldGender.setItems(FXCollections.observableArrayList(Gender.values()));
		AnchorPane.setLeftAnchor(fieldDate, 200.0);
		AnchorPane.setTopAnchor(fieldDate, 75.0);
		paneCreate.getChildren().add(fieldDate);
	}

	@FXML
	void actionCancelCreate(ActionEvent event) {
		controller.setView(View.LOGIN);
	}

	/**
	 * Creates a user taking the parameter from the form filling.
	 * 
	 * @param event 
	 */
	@FXML
	void actionCreate(ActionEvent event) {
		String name = fieldName.getText().trim();
		Date date = fieldDate.getValue();
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
		} else if (Persistent.getUserNames().contains(name)) {
			labelCreateWarning.setText("User already exists");
		} else if (date == null) {
			labelCreateWarning.setText("Birthdate not set");
		} else if (date.after(Calendar.getInstance().getTime())) {
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
			calendar.setTime(date);
			try {
				Persistent.newUser(new User(name, calendar, gender, weight.doubleValue(), height.doubleValue(),
						(EventContainer) null, hr.intValue()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			controller.setView(View.LOGIN);
		}
	}
}
