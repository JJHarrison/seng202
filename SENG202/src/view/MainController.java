package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import view.analysis.AnalysisController;
import data.model.DataPoint;
import data.model.Event;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import extfx.scene.control.CalendarView;

public class MainController {
	@FXML
	ToggleButton buttonDash;
	@FXML
	ToggleButton buttonAnalysis;
	@FXML
	ToggleButton buttonWeb;
	@FXML
	VBox tileBox;

	@FXML
	CalendarView calendarView;
	@FXML
	StackPane viewDash;
	@FXML
	StackPane viewWeb;
	@FXML
	StackPane viewAnalysis;
	@FXML
	StackPane viewMainContent;

	@FXML
	MenuItem menuImport;
	@FXML
	MenuItem menuExport;
	@FXML
	MenuItem menuClose;

	FadeTransition ft;

	ToggleGroup toggleGroup = new ToggleGroup();

	public static ObjectProperty<Date> selectedDate;

	@FXML
	AnalysisController viewAnalysisController;

	@FXML
	void initialize() {
		// TODO Test
		selectedDate = calendarView.selectedDateProperty();

		MainController.selectedDate.addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable,
					Object oldValue, Object newValue) {
				Date date = (Date) newValue;
				ArrayList<DataPoint> points = new ArrayList<DataPoint>();
				Calendar c3 = new GregorianCalendar();
				c3.setTime(date);
				Calendar c4 = new GregorianCalendar();
				c4.setTime(date);
				c4.add(Calendar.SECOND, 6);

				DataPoint p1 = new DataPoint.Builder().date(c3).heartRate(120)
						.latitude(30.2553368).longitude(-97.83891084)
						.altitude(50.0).prevDataPoint(null).build();

				DataPoint p2 = new DataPoint.Builder().date(c4).heartRate(125)
						.latitude(30.25499189).longitude(-97.83913958)
						.altitude(51.0).prevDataPoint(p1).build();

				points.add(p1);
				points.add(p2);

				Event event1 = new Event("A Walk in the Woods", points);
				Event event2 = new Event("A Long Walk on the Beach", points);
				Event event3 = new Event("Police chase (a narrow escape)",
						points);

				viewAnalysisController.addTiles(event1, event2, event3);

			}
		});

		selectedDate.set(new Date());

		loadDash(null);

		menuClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.exit();

			}
		});

		toggleGroup.getToggles().addAll(buttonAnalysis, buttonDash, buttonWeb);
		buttonDash.fire();

	}

	@FXML
	void loadDash(ActionEvent event) {
		toggleGroup.selectToggle(buttonDash);

		viewMainContent.getChildren().clear();
		viewMainContent.getChildren().add(viewDash);
		ft = new FadeTransition(Duration.millis(250), viewDash);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		ft = new FadeTransition(Duration.millis(250), calendarView);
		ft.setToValue(0);
		ft.play();
		calendarView.disableProperty().set(true);
	}

	@FXML
	void loadAnalysis(ActionEvent event) {
		toggleGroup.selectToggle(buttonAnalysis);

		viewMainContent.getChildren().clear();
		viewMainContent.getChildren().add(viewAnalysis);
		ft = new FadeTransition(Duration.millis(250), viewAnalysis);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		ft = new FadeTransition(Duration.millis(250), calendarView);
		ft.setToValue(1);
		ft.play();
		calendarView.disableProperty().set(false);
	}

	@FXML
	void loadWeb() {
		toggleGroup.selectToggle(buttonWeb);

		viewMainContent.getChildren().clear();
		viewMainContent.getChildren().add(viewWeb);
		ft = new FadeTransition(Duration.millis(250), viewWeb);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		ft = new FadeTransition(Duration.millis(250), calendarView);
		ft.setToValue(0);
		ft.play();
		calendarView.disableProperty().set(true);
	}

}
