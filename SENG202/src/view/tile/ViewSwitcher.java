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

    /** The main tile layout controller. */
    private static TileController tileController;

    /**
     * Stores the tile controller for later use in navigation tasks.
     *
     * @param mainController
     *            the main tile layout controller.
     */
    public static void setMainController(TileController tileController) {
	ViewSwitcher.tileController = tileController;
    }

    /**
     * 
     * @param fxml
     *            The view to load up.
     */
    public static void loadView(String fxml) {
	try {
	    tileController.setView(FXMLLoader.load(ViewSwitcher.class
		    .getResource(fxml)));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
