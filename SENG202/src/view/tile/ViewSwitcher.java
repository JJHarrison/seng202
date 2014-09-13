package view.tile;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class ViewSwitcher {

    /**
     * Easy way to switch views.
     */
    public static final String TILE = "Tile.fxml";
    public static final String SUMMARY = "summary/Summary.fxml";
    public static final String GRAPH = "graph/Graph.fxml";
    public static final String MAP = "map/Map.fxml";
    public static final String TABLE = "table/Table.fxml";

    public ViewSwitcher(TileController tileController) {
	this.tileController = tileController;
    }
    
    /** The main tile layout controller. */
    private TileController tileController;

    /**
     * Stores the tile controller for later use in navigation tasks.
     *
     * @param tileController
     *            the main tile layout controller.
     */
    public void setMainController(TileController tileController) {
	this.tileController = tileController;
    }

    /**
     * 
     * @param fxml
     *            The view to load up.
     */
    public void loadView(String fxml) {
	try {
	    tileController.setView(FXMLLoader.load(ViewSwitcher.class
		    .getResource(fxml)));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
