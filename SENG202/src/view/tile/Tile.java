package view.tile;

import java.io.IOException;

import data.model.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Tile extends AnchorPane {
    
    public Event event;
    
    public Tile(Event event) {
	super();
	this.event = event;
	try {
	    loadMainPane();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public Tile() {
	super();
	try {
	    loadMainPane();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    

    private ViewSwitcher viewSwitcher;

    /**
     * 
     * @throws IOException
     */
    private void loadMainPane() throws IOException {
	FXMLLoader loader = new FXMLLoader();
	loader.setRoot(this);
	loader.load(getClass().getResourceAsStream(ViewSwitcher.TILE));

	TileController tileController = loader.getController();
	this.applyCss();
	viewSwitcher = tileController.viewSwitcher;
	viewSwitcher.setMainController(tileController);
	viewSwitcher.loadView(ViewSwitcher.SUMMARY);
    }

}
