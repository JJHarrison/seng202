package view.search;

import java.io.IOException;

import view.search.GoogleSearchResults.Result;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * A search tile to nicely display the title, content and URL of each web page.
 * 
 * @author Daniel van Wichen, Daniel Tapp
 *
 */
public class Search extends AnchorPane {

	public static String TILE = "Search.fxml";

	/**
	 * Constructor.
	 * 
	 * @param result the result to display.
	 */
	public Search(Result result) {
		if (result != null) {
		try {
			loadMainPane(result);
		} catch (IOException e) {
			e.printStackTrace();
		}}
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