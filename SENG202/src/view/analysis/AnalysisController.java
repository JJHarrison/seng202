package view.analysis;

import view.tile.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import data.model.Event;

public class AnalysisController {

	@FXML
	VBox tileBox;

	@FXML
	void initialize() {

	}

	/**
	 * add an event tile
	 * 
	 * @param event
	 *            the event to add
	 */
	public void addTile(Event event) {
		tileBox.getChildren().add(new Tile(event));
	}

	/**
	 * clear all the tile from the view
	 */
	public void clearTiles() {
		tileBox.getChildren().clear();
	}

}
