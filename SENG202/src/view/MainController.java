package view;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
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

    @FXML
    void initialize() {
	viewMainContent.getChildren().clear();
	toggleGroup.getToggles().addAll(buttonDash, buttonAnalysis, buttonWeb);
	toggleGroup.selectToggle(buttonDash);
	loadDash(null);

	menuClose.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent event) {
		Platform.exit();

	    }
	});

    }

    @FXML
    void loadDash(ActionEvent event) {
	viewMainContent.getChildren().clear();
	viewMainContent.getChildren().add(viewDash);
	ft = new FadeTransition(Duration.millis(500), viewDash);
	ft.setFromValue(0);
	ft.setToValue(1);
	ft.play();
	ft = new FadeTransition(Duration.millis(500), calendarView);
	ft.setToValue(0);
	ft.play();
	calendarView.disableProperty().set(true);
	toggleGroup.selectToggle(buttonDash);
    }

    @FXML
    void loadAnalysis(ActionEvent event) {
	viewMainContent.getChildren().clear();
	viewMainContent.getChildren().add(viewAnalysis);
	ft = new FadeTransition(Duration.millis(500), viewAnalysis);
	ft.setFromValue(0);
	ft.setToValue(1);
	ft.play();
	ft = new FadeTransition(Duration.millis(500), calendarView);
	ft.setToValue(1);
	ft.play();
	calendarView.disableProperty().set(false);
	toggleGroup.selectToggle(buttonAnalysis);
    }

    @FXML
    void loadWeb() {
	viewMainContent.getChildren().clear();
	viewMainContent.getChildren().add(viewWeb);
	ft = new FadeTransition(Duration.millis(500), viewWeb);
	ft.setFromValue(0);
	ft.setToValue(1);
	ft.play();
	ft = new FadeTransition(Duration.millis(500), calendarView);
	ft.setToValue(0);
	ft.play();
	calendarView.disableProperty().set(true);
	toggleGroup.selectToggle(buttonWeb);
    }

}
