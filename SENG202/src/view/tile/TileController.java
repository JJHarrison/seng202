package view.tile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

    public static VBox openTile;

    ToggleGroup toggleGroup = new ToggleGroup();

    public ViewSwitcher viewSwitcher = new ViewSwitcher(this);

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
	tile.getChildren().remove(tileBottom); // So that the tiles are by
					       // default closed.

	toggleGroup.getToggles().addAll(buttonGraph, buttonMap, buttonSummary,
		buttonTable);
	toggleGroup.selectToggle(buttonSummary);

    }

    @FXML
    void loadSummary(ActionEvent event) {
	toggleGroup.selectToggle(buttonSummary);
	viewSwitcher.loadView(ViewSwitcher.SUMMARY);
    }

    @FXML
    void loadMap(ActionEvent event) {
	toggleGroup.selectToggle(buttonMap);
	viewSwitcher.loadView(ViewSwitcher.MAP);
    }

    @FXML
    void loadGraph(ActionEvent event) {
	toggleGroup.selectToggle(buttonGraph);
	viewSwitcher.loadView(ViewSwitcher.GRAPH);
    }

    @FXML
    void loadTable(ActionEvent event) {
	toggleGroup.selectToggle(buttonTable);
	viewSwitcher.loadView(ViewSwitcher.TABLE);
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

}
