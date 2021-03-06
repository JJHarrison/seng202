package view.analysis;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import view.tile.Tile;
import data.model.Event;

/**
 * Controller.
 * 
 * @author Daniel van Wichen
 *
 */
public class AnalysisController {

	@FXML
	VBox tileBox;

	@FXML
	void initialize() {

	}

	/**
	 * Add an event tile to the analysis view.
	 * 
	 * @param event the event to add.
	 */
	public void addTile(Event event) {
		tileBox.getChildren().add(new Tile(event));
	}

	/**
	 * Clear all the tiles from the view.
	 */
	public void clearTiles() {
		tileBox.getChildren().clear();
	}

}
