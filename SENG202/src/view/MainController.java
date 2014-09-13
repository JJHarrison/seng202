package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.tile.Tile;
import data.model.Event;
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
    
    

    ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    void initialize() {
	viewMainContent.getChildren().clear();
	toggleGroup.getToggles().addAll(buttonDash, buttonAnalysis, buttonWeb);
	toggleGroup.selectToggle(buttonDash);
	loadDash(null);
	
    }

    @FXML
    void loadDash(ActionEvent event) {
	viewMainContent.getChildren().clear();
	viewMainContent.getChildren().add(viewDash);
	calendarView.setOpacity(0);
	calendarView.disableProperty().set(true);
	toggleGroup.selectToggle(buttonDash);
    }

    @FXML
    void loadAnalysis(ActionEvent event) {
	viewMainContent.getChildren().clear();
	viewMainContent.getChildren().add(viewAnalysis);
	calendarView.setOpacity(1);
	calendarView.disableProperty().set(false);
	toggleGroup.selectToggle(buttonAnalysis);
    }

    @FXML
    void loadWeb() {
	viewMainContent.getChildren().clear();
	viewMainContent.getChildren().add(viewWeb);
	calendarView.setOpacity(0);
	calendarView.disableProperty().set(true);
	toggleGroup.selectToggle(buttonWeb);
    }
    
    
}
