package view;

import java.util.Date;

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
import javafx.util.Duration;
import view.analysis.AnalysisController;
import data.model.Event;
import data.persistant.Persistent;
import extfx.scene.control.CalendarView;

public class MainController {
	@FXML
	ToggleButton buttonDash;
	@FXML
	ToggleButton buttonAnalysis;
	@FXML
	ToggleButton buttonWeb;

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
		

		MainController.selectedDate.addListener(new ChangeListener<Date>() {

			@Override
			public void changed(ObservableValue<? extends Date> observable,
					Date oldValue, Date newValue) {
				viewAnalysisController.clearTiles();
				
				for (Event event : Persistent.getCurrentUser().getEvents().getEvents(newValue)) {
					viewAnalysisController.addTile(event);
				}
				
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
