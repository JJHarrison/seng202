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
import view.tile.graph.GraphController;
import view.tile.map.MapController;
import view.tile.summary.SummaryController;
import view.tile.table.TableController;
import data.model.Event;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class TileController {

	// Holder of a switchable view
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
	ToggleButton buttonPlus;

	@FXML
	StackPane summary;
	@FXML
	StackPane map;
	@FXML
	StackPane graph;
	@FXML
	StackPane table;

	@FXML
	Label labelEventName;
	@FXML
	Label labelEventTime;

	@FXML
	private GraphController graphController;
	@FXML
	private MapController mapController;
	@FXML
	private SummaryController summaryController;
	@FXML
	private TableController tableController;

	public Event event;

	private static VBox openTile;

	ToggleGroup toggleGroup = new ToggleGroup();

	/**
	 * Replaces the view displayed in the view holder with a new view.
	 *
	 * @param node the view node to be swapped in.
	 */
	public void setView(Node node) {
		tileView.getChildren().setAll(node);
	}

	@FXML
	private void initialize() {
		tileView.getChildren().clear();
		tile.getChildren().remove(tileBottom); // So that the tiles are by
		// default closed.
		toggleGroup.getToggles().addAll(buttonGraph, buttonMap, buttonSummary, buttonTable);
		buttonSummary.fire();
	}

	@FXML
	void loadSummary(ActionEvent event) {
		toggleGroup.selectToggle(buttonSummary);
		setView(summary);
	}

	@FXML
	void loadMap(ActionEvent event) {
		toggleGroup.selectToggle(buttonMap);
		setView(map);
	}

	@FXML
	void loadGraph(ActionEvent event) {
		toggleGroup.selectToggle(buttonGraph);
		setView(graph);
	}

	@FXML
	void loadTable(ActionEvent event) {
		toggleGroup.selectToggle(buttonTable);
		setView(table);
	}

	@FXML
	void hideContent(MouseEvent event) {
		buttonPlus.selectedProperty().set(! buttonPlus.selectedProperty().get());
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

	/**
	 * Fills the tile with a particular event.
	 * 
	 * @param event the event the tile will display.
	 */
	public void fill(Event event) {
		this.event = event;
		if (event != null) {
			labelEventName.setText(event.getEventName());
			labelEventTime.setText(event.getTimeString());

			mapController.fill(event);
			summaryController.fill(event);
			graphController.fill(event);
			tableController.fill(event);

		}
	}

}
