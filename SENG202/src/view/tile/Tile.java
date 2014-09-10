package view.tile;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Tile extends AnchorPane {

    public Tile() {
	super();
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
	loader.setRoot(this);
	loader.load(getClass().getResourceAsStream(ViewSwitcher.TILE));
	
	TileController tileController = loader.getController();
	

	ViewSwitcher.setMainController(tileController);
	ViewSwitcher.loadView(ViewSwitcher.SUMMARY);
    }


}
