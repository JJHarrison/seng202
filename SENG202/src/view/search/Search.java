package view.search;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Search extends AnchorPane {

	public static String TILE = "Search.fxml";

	/**
	 * Constructor.
	 */
	public Search() { 
		try {
			loadMainPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * load the main pane
	 */
	private void loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(TILE));
		loader.setRoot(this);
		loader.load(getClass().getResourceAsStream(TILE));

		SearchController searchController = loader.getController();
		searchController.fill();

	}
}