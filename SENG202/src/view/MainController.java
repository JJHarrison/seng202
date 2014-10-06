package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.FutureTask;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import jfx.messagebox.MessageBox;
import resources.Reference;
import server.Client;
import view.analysis.AnalysisController;
import view.dash.DashController;
import view.notification.Notification;
import view.user.UserLoginManager;
import data.loader.FileLoader;
import data.loader.LoadSummary;
import data.model.Event;
import data.persistant.Persistent;
import data.persistant.Saver;
import extfx.scene.control.CalendarView;
import extfx.scene.control.DateCell;

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
	MenuItem menuUserManual;
	@FXML
	MenuItem menuClearEvents;
	@FXML
	MenuItem menuLogout;

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

		// highlight the days which have events on them
		calendarView.setDayCellFactory(new Callback<CalendarView, DateCell>() {
			@Override
			public DateCell call(CalendarView calendarView) {
				DateCell dateCell = new DateCell() {
					@Override
					protected void updateItem(Date date, boolean empty) {
						super.updateItem(date, empty);
						calendar.setTime(date);
						setStyle("-fx-background-color: #ffffff;");
						if (Persistent.getCurrentUser().getEvents()
								.getEvents(date).size() > 0) {
							setStyle("-fx-background-radius: 8; "
									+ "-fx-background-color: derive(-fx-accent, 80%);");
						}
					}
				};

				return dateCell;
			}
		});

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
				if (oldValue != null) {
					Calendar newCalendar = Calendar.getInstance();
					newCalendar.setTime(newValue);
					newCalendar.set(Calendar.DAY_OF_WEEK, 0);
					Calendar oldCalendar = Calendar.getInstance();
					oldCalendar.setTime(oldValue);
					oldCalendar.set(Calendar.DAY_OF_WEEK, 0);
					if (!oldCalendar.equals(newCalendar)) {
						viewAnalysisController.clearTiles();
						for (Event event : Persistent.getCurrentUser()
								.getEvents().getWeekEvents(newValue)) {
							viewAnalysisController.addTile(event);
						}
					}

				} else {
					viewAnalysisController.clearTiles();
					for (Event event : Persistent.getCurrentUser().getEvents()
							.getWeekEvents(newValue)) {
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
				FutureTask<Boolean> task = new FutureTask<Boolean>(c);
				task.run();
				try {
					if (task.get()) {
						MessageBox
								.show(Main.stage,
										"User has been uploaded to the database sucessfully =)",
										"", MessageBox.OK);
					} else {
						MessageBox.show(Main.stage,
								"Sorry, upload was unsuccessful =(", "",
								MessageBox.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
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
					Persistent.getCurrentUser().addEvents(
							fl.getEventContainer());

					// run the load summary pop up in its own thread to stop it
					// from stopping the flow of the import
					// MessageBox.show(Main.stage, LoadSummary.getSumamry(), "", MessageBox.OK);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
//							MessageBox.show(Main.stage,
//									LoadSummary.getSumamry(), "", MessageBox.OK);
//							LoadSummary.clear();
							try {
								new Notification().start(null);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.YEAR, 1);
					selectedDate.setValue(calendar.getTime());
					selectedDate.setValue(Persistent.getCurrentUser()
							.getEvents().getLastDate());
					viewDashController.fillDash();
				}
			}
		});

		menuAbout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MessageBox
						.show(Main.stage,
								  "Fitr: Fitness Is Training Right.\n\n"
								+ "Version: Build 2.0.0.0\n"
								+ "Release: October 2014\n\n"
								+ "Licence: This software is provided by rights holders and contributers "
								+ "\"as is\" and any express or implied warrenties are disclaimed.\n"
								+ "In no event shall the developers of Fitr be liable for any direct, indirect "
								+ "or incidental damages incured in the use of fitr.\n\n"
								+ "Use at your own risk.",
								"About Fitr", MessageBox.OK);

			}
		});

		menuUserManual.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					String s = System.getProperty("os.name").toLowerCase();

					// If OS is linux then use alternative command to prevent application from crashing
					if (s.contains("linux")) {
						// OS is linux
						Runtime.getRuntime().exec(
								String.format("gnome-open %s", Reference.class
										.getResource("fitrUG.pdf")));
					} else {
						File myFile = new File("src/resources/fitrUG.pdf");
						Desktop.getDesktop().open(myFile);
					}
				} catch (IOException ex) {
					// no application registered for PDFs
					ex.printStackTrace();
				}
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

		menuClose.setAccelerator(new KeyCodeCombination(KeyCode.F4,
				KeyCombination.ALT_DOWN));

		menuLogout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Saver.SaveUser(Persistent.getCurrentUser());
				Main.stage.close();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						UserLoginManager.stage.show();
					}
				});

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
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME),
					calendarView);
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
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME),
					viewAnalysis);
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.play();
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME),
					calendarView);
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
			ft = new FadeTransition(Duration.millis(TRANSITION_TIME),
					calendarView);
			ft.setToValue(0);
			ft.play();
			calendarView.disableProperty().set(true);
		} else {
			toggleGroup.selectToggle(buttonWeb);
		}
	}

}
