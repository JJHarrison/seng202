package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import jfx.messagebox.MessageBox;
import server.Client;
import view.analysis.AnalysisController;
import data.loader.FileLoader;
import data.model.Event;
import data.persistant.Persistent;
import data.persistant.Saver;
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
	@FXML
	MenuItem menuAbout;

	FadeTransition ft;

	ToggleGroup toggleGroup = new ToggleGroup();

	public static ObjectProperty<Date> selectedDate;
	private static FileChooser fileChooser = new FileChooser();

	@FXML
	AnalysisController viewAnalysisController;

	@FXML
	void initialize() {
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		ArrayList<String> filterCSV = new ArrayList<String>();
		filterCSV.add("*.csv");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("CSV", filterCSV));
		selectedDate = calendarView.selectedDateProperty();

		MainController.selectedDate.addListener(new ChangeListener<Date>() {

			@Override
			public void changed(ObservableValue<? extends Date> observable,
					Date oldValue, Date newValue) {
				Calendar c = Calendar.getInstance();
				c.setTime(newValue);
				c.set(Calendar.DAY_OF_WEEK, 0);
				
				
				viewAnalysisController.clearTiles();

				System.out.println(Persistent.getCurrentUser());

				for (Event event : Persistent.getCurrentUser().getEvents()
						.getWeekEvents(newValue)) {
					viewAnalysisController.addTile(event);
				}

			}
		});

		selectedDate.set(Persistent.getCurrentUser().getEvents().getLastDate());

		loadDash(null);

		menuClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Saver.SaveUser(Persistent.getCurrentUser());
				Platform.exit();

			}
		});

		

		menuExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Client c = new Client();
				c.setupConnection();
				c.transferToServer(Persistent.getCurrentUser());
				c.closeStuff();
			}
		});

		menuImport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(Main.stage);
				if (file != null) {
					FileLoader fl = new FileLoader(file);
					fl.load();
					
					Persistent.getCurrentUser().addEvents(fl.getEventContainer());
					selectedDate.setValue(Persistent.getCurrentUser().getEvents().getLastDate());
				}
			}
		});

		menuAbout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MessageBox.show(Main.stage, "message", "About", MessageBox.OK);

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
