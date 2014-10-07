package view.tile;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import data.model.Event;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class Tile extends AnchorPane {

	private Event event;
	public static String TILE = "Tile.fxml";

	/**
	 * Constructor. Create a tile custom control that wrap the {@link data.model.Event} class.
	 * 
	 * @param event the event used to fill this tile.
	 */
	public Tile(Event event) {
		super();
		this.event = event;
		try {
			loadMainPane();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	/**
	 * Load up the tile pane (AnchorPane).
	 * 
	 * @throws IOException
	 */
	private void loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(TILE));
		loader.setRoot(this);
		loader.load(getClass().getResourceAsStream(TILE));

		TileController tileController = loader.getController();
		tileController.fill(event);

	}

}
