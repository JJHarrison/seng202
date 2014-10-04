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
import view.dash.DashController;
import data.loader.FileLoader;
import data.loader.LoadSummary;
import data.model.Event;
import data.persistant.Persistent;
import data.persistant.Saver;
import extfx.scene.control.CalendarView;

/**
 * 
 * @author Daniel van Wichen
 *
 */
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
	@FXML
	MenuItem menuClearEvents;

	FadeTransition ft;

	ToggleGroup toggleGroup = new ToggleGroup();

	public static ObjectProperty<Date> selectedDate;
	private static FileChooser fileChooser = new FileChooser();
	private static final int TRANSITION_TIME = 500;

	@FXML
	AnalysisController viewAnalysisController;
	@FXML
	DashController viewDashController;

	@FXML
	void initialize() {
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		ArrayList<String> filterCSV = new ArrayList<String>();
		filterCSV.add("*.csv");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", filterCSV));
		selectedDate = calendarView.selectedDateProperty();

		MainController.selectedDate.addListener(new ChangeListener<Date>() {

			@Override
			public void changed(ObservableValue<? extends Date> observable, Date oldValue, Date newValue) {
				if (oldValue != null) {
					Calendar newCalendar = Calendar.getInstance();
					newCalendar.setTime(newValue);
					newCalendar.set(Calendar.DAY_OF_WEEK, 0);
					Calendar oldCalendar = Calendar.getInstance();
					oldCalendar.setTime(oldValue);
					oldCalendar.set(Calendar.DAY_OF_WEEK, 0);
					if (!oldCalendar.equals(newCalendar)) {
						viewAnalysisController.clearTiles();
						for (Event event : Persistent.getCurrentUser().getEvents().getWeekEvents(newValue)) {
							viewAnalysisController.addTile(event);
						}
					}

				} else {
					viewAnalysisController.clearTiles();
					for (Event event : Persistent.getCurrentUser().getEvents().getWeekEvents(newValue)) {
						viewAnalysisController.addTile(event);
					}
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
				Thread t = new Thread(c);
				t.start();
//				c.setupConnection();
//				c.transferToServer(Persistent.getCurrentUser());
//				c.closeStuff();
//				if (c.isSuccessful()) {
//					MessageBox.show(Main.stage, "User has ben uploaded to the server sucessfully", "", MessageBox.OK);
//				} else {
//					MessageBox.show(Main.stage, "Sorry, something went wrong.", "", MessageBox.OK);
//				}
			}
		});

		menuImport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(Main.stage);
				if (file != null) {
					LoadSummary.clear();
					FileLoader fl = new FileLoader(file);
					fl.load();
					Persistent.getCurrentUser().addEvents(fl.getEventContainer());
					
					// run the load summary pop up in its own thread to stop it
					// from stopping the flow of the import
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							MessageBox.show(Main.stage, LoadSummary.getSumamry(), "", MessageBox.OK);
						}
					});

					LoadSummary.clear();
					
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.YEAR, 1);
					selectedDate.setValue(calendar.getTime());
					selectedDate.setValue(Persistent.getCurrentUser().getEvents().getLastDate());
					viewDashController.fillDash();
				}
			}
		});

		menuAbout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MessageBox.show(Main.stage,
						"Developers: Fitr Team\n\nVersion 1.0 BETA\n\nHealth Tracking and Analysis System",
						"About Fitr", MessageBox.OK);

			}
		});
		
		menuClearEvents.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Persistent.clearUserActivityData();
				viewDashController.fillDash();
				viewAnalysisController.clearTiles();
				
			}
		});

		toggleGroup.getToggles().addAll(buttonAnalysis, buttonDash, buttonWeb);
		
		// need this so the dash loads first... i don't know why either...
		toggleGroup.selectToggle(buttonWeb);
		buttonDash.fire();

	}

	@FXML
	private void loadDash(ActionEvent event) {
		if (toggleGroup.getSelectedToggle() == buttonDash) {
			toggleGroup.selectToggle(buttonDash);
	
			viewMainContent.getChildren().clear();
			viewMainContent.getChildren().add(viewDash);
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME), viewDash);
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.play();
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME), calendarView);
			ft.setToValue(0);
			ft.play();
			calendarView.disableProperty().set(true);
		} else {
			toggleGroup.selectToggle(buttonDash);
		}
	}

	@FXML
	private void loadAnalysis(ActionEvent event) {
		if (toggleGroup.getSelectedToggle() == buttonAnalysis) {
			toggleGroup.selectToggle(buttonAnalysis);
	
			viewMainContent.getChildren().clear();
			viewMainContent.getChildren().add(viewAnalysis);
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME), viewAnalysis);
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.play();
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME), calendarView);
			ft.setToValue(1);
			ft.play();
			calendarView.disableProperty().set(false);
		} else {
			toggleGroup.selectToggle(buttonAnalysis);
		}
	}

	@FXML
	private void loadWeb() {
		if (toggleGroup.getSelectedToggle() == buttonWeb) {
			toggleGroup.selectToggle(buttonWeb);
	
			viewMainContent.getChildren().clear();
			viewMainContent.getChildren().add(viewWeb);
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME), viewWeb);
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.play();
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME), calendarView);
			ft.setToValue(0);
			ft.play();
			calendarView.disableProperty().set(true);
		} else {
			toggleGroup.selectToggle(buttonWeb);
		}
	}

}
