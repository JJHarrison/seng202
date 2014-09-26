package view.user;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import user.User;
import user.User.Gender;
import view.user.UserManagementController.View;
import data.model.EventContainer;
import data.persistant.Persistent;
import extfx.scene.control.NumberSpinner;

public class UserCreateController implements Switchable {
	private UserManagementController controller;
	public static final Double MAX_WEIGHT = 180.0; // kg
	public static final Double MAX_HEIGHT = 250.0; // cm
	public static final Integer MAX_HR = 150; // resting hr

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
	DatePicker fieldDate;

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
		String name = fieldName.getText();
		LocalDate date = fieldDate.getValue();
		Gender gender = fieldGender.getValue();
		Number height = fieldHeight.getValue();
		Number weight = fieldWeight.getValue();
		Number hr = fieldHR.getValue();

		if (name.trim().isEmpty()) {
			labelCreateWarning.setText("Please provide your name");
		} else if (!name.matches("([a-zA-Z]|[\\s])*")) {
			labelCreateWarning.setText("Please enter a valid name");
		} else if (Persistent.getUserNames().contains(name)) {
			labelCreateWarning.setText("User already exists");
		} else if (date == null) {
			labelCreateWarning.setText("Birthdate not set");
		} else if (gender == null) {
			labelCreateWarning.setText("Gender not set");
		} else if (height == null) {
			labelCreateWarning.setText("Please provide your height (cm)");
		} else if (height.doubleValue() > MAX_HEIGHT) {
			labelCreateWarning.setText("Enter your height between 0 - 250 cm");
		} else if (weight == null) {
			labelCreateWarning.setText("Please provide your weight (kg)");
		} else if (weight.doubleValue() > MAX_WEIGHT) {
			labelCreateWarning.setText("Enter your weight between 0 and 180 kg");
		} else if (hr == null) {
			labelCreateWarning.setText("Please provide your average heart rate");
		} else if (hr.intValue() > MAX_HR) {
			labelCreateWarning.setText("Enter a heart rate between 0 and 150 bpm");
		} else {
			Calendar calendar = new GregorianCalendar();
			Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date res = Date.from(instant);
			calendar.setTime(res);
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
