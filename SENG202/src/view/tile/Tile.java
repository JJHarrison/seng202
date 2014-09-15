package view.tile;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import data.model.Event;

public class Tile extends AnchorPane {

    public Event event;
    public static String TILE = "Tile.fxml";

    public Tile(Event event) {
	super();
	this.event = event;
	try {
	    loadMainPane();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * 
     * @throws IOException
     */
    private void loadMainPane() throws IOException {
	FXMLLoader loader = new FXMLLoader();
	loader.setLocation(getClass().getResource("Tile.fxml"));
	loader.setRoot(this);
	loader.load(getClass().getResourceAsStream(Tile.TILE));

	TileController tileController = loader.getController();
	tileController.fill(event);

    }

}
