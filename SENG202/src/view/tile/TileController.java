package view.tile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import data.model.Event;

public class TileController {

    /** Holder of a switchable view. */
    @FXML
    StackPane tileView;
    @FXML
    VBox tile;
    @FXML
    AnchorPane tileTop;
    @FXML
    AnchorPane tileBottom;
    @FXML
    ToggleButton buttonSummary;
    @FXML
    ToggleButton buttonGraph;
    @FXML
    ToggleButton buttonMap;
    @FXML
    ToggleButton buttonTable;

    @FXML
    StackPane viewSummary;
    @FXML
    StackPane viewMap;
    @FXML
    StackPane viewGraph;
    @FXML
    StackPane viewTable;

    @FXML
    Label labelEventName;
    @FXML
    Label labelEventTime;

    public Event event;

    public static VBox openTile;

    ToggleGroup toggleGroup = new ToggleGroup();

    /**
     * Replaces the view displayed in the view holder with a new view.
     *
     * @param node
     *            the view node to be swapped in.
     */
    public void setView(Node node) {
	tileView.getChildren().setAll(node);
    }

    @FXML
    private void initialize() {
	tileView.getChildren().clear();
	tile.getChildren().remove(tileBottom); // So that the tiles are by
					       // default closed.
	toggleGroup.getToggles().addAll(buttonGraph, buttonMap, buttonSummary,
		buttonTable);
	buttonSummary.fire();
    }

    @FXML
    void loadSummary(ActionEvent event) {
	toggleGroup.selectToggle(buttonSummary);
	setView(viewSummary);
    }

    @FXML
    void loadMap(ActionEvent event) {
	toggleGroup.selectToggle(buttonMap);
	setView(viewMap);
    }

    @FXML
    void loadGraph(ActionEvent event) {
	toggleGroup.selectToggle(buttonGraph);
	setView(viewGraph);
    }

    @FXML
    void loadTable(ActionEvent event) {
	toggleGroup.selectToggle(buttonTable);
	setView(viewTable);
    }

    @FXML
    void hideContent(MouseEvent event) {
	if (openTile == tile) {
	    tile.getChildren().remove(tileBottom);
	    openTile = null;
	} else if (openTile == null) {
	    tile.getChildren().add(tileBottom);
	    openTile = tile;
	} else {
	    openTile.getChildren().remove(1);
	    tile.getChildren().add(tileBottom);
	    openTile = tile;
	}
    }

    @FXML
    void hideContentButton(ActionEvent event) {
	if (openTile == tile) {
	    tile.getChildren().remove(tileBottom);
	    openTile = null;
	} else if (openTile == null) {
	    tile.getChildren().add(tileBottom);
	    openTile = tile;
	} else {
	    openTile.getChildren().remove(1);
	    tile.getChildren().add(tileBottom);
	    openTile = tile;
	}
    }

    public void fill(Event event) {
	this.event = event;
	if (event != null) {
	    labelEventName.setText(event.getEventName());
	    labelEventTime.setText(event.getTimeString());
	}
    }

}
