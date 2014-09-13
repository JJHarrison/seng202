package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

    ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    void initialize() {
	toggleGroup.getToggles().addAll(buttonDash, buttonAnalysis, buttonWeb);
	toggleGroup.selectToggle(buttonDash);
	loadDash(null);
    }

    @FXML
    void loadDash(ActionEvent event) {
	calendarView.setOpacity(0);
	calendarView.disableProperty().set(true);
	toggleGroup.selectToggle(buttonDash);
    }

    @FXML
    void loadAnalysis(ActionEvent event) {
	calendarView.setOpacity(1);
	calendarView.disableProperty().set(false);
	toggleGroup.selectToggle(buttonAnalysis);
    }

    @FXML
    void loadWeb() {
	calendarView.setOpacity(0);
	calendarView.disableProperty().set(true);
	toggleGroup.selectToggle(buttonWeb);
    }
    
    
}
