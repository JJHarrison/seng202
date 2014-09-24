package view.search;

import java.io.IOException;

import view.search.GoogleSearchResults.Result;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Search extends AnchorPane {

	public static String TILE = "Search.fxml";

	public Search(Result result) { 
		try {
			loadMainPane(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void loadMainPane(Result result) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(TILE));
		loader.setRoot(this);
		loader.load(getClass().getResourceAsStream(TILE));

		SearchController searchController = loader.getController();
		searchController.fill(result);

	}
	
}