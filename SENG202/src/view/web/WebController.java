package view.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.search.GoogleSearchResults;
import view.search.Search;
import view.web.SearchQuery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author Daniel Tapp, Daniel van Wichen, Jaln Rodger
 *
 */
public class WebController {

	@FXML
	TextField textFieldSearch;
	
	@FXML
	VBox resultPane;
	
	private GoogleSearchResults results;
	
	@FXML
	void initialize() {
		textFieldSearch.setText("");
	}

	@FXML
	void searchButton(ActionEvent event) throws IOException {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			SearchQuery.clearCount();
			SearchQuery.setSearched(true);
			resultPane.getChildren().clear();
			findResults(textFieldSearch.getText());
		} else {
			clearResults(event);
		}
	}

	@FXML
	void searchField(ActionEvent event) throws IOException {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			SearchQuery.clearCount();
			SearchQuery.setSearched(true);
			resultPane.getChildren().clear();
			findResults(textFieldSearch.getText());
		} else {
			clearResults(event);
		}
	}
	
	@FXML
	void moreResults(ActionEvent event) throws IOException {
		if (!textFieldSearch.getText().trim().isEmpty() && SearchQuery.isSearched()) {
			findResults(SearchQuery.getCurrentSearchQuery());
		}
	}
	
	@FXML
	void clearResults(ActionEvent event) throws IOException {
		SearchQuery.clearCount();
		resultPane.getChildren().clear();
		textFieldSearch.setText("");
	}

	/**
	 * This method takes the search text and then requests the Gson file from the Google API.
	 * 
	 * @param searchText the search query inputted by user.
	 * 
	 * @throws IOException
	 */
	void findResults(String searchText) throws IOException {
		URL url = new URL(SearchQuery.getQuery(searchText));
		Reader reader = new InputStreamReader(url.openStream(), "UTF-8");
		Gson gs = new GsonBuilder()
					.setPrettyPrinting()
					.disableHtmlEscaping()
					.create();
		GoogleSearchResults results = gs.fromJson(reader, GoogleSearchResults.class);
		this.results = results;
		sendResults();
	}
	
	void sendResults() {
		for(int i = 0; i <= 3; i++) {
			
			if (results.getResponseData() == null) {
				System.out.print("Error");
			} else {
				resultPane.getChildren().add(new Search(results.getResponseData().getResults().get(i)));
			}
		}
	}

}
