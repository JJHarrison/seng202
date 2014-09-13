package view;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import view.tile.Tile;
import data.model.Event;

public class MainController {
    @FXML
    ToggleButton buttonDash;
    @FXML
    ToggleButton buttonAnalysis;
    @FXML
    ToggleButton buttonWeb;
    @FXML
    VBox tileBox;

    ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    void initialize() {
	toggleGroup.getToggles().addAll(buttonDash, buttonAnalysis, buttonWeb);
	toggleGroup.selectToggle(buttonDash);
    }

    /**
     * 
     * @param event
     *            The event to add
     */
    public void addEventTile(Event event) {
	tileBox.getChildren().add(new Tile(event));
    }
}
