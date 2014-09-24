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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	public WebController() {
		// TODO Auto-generated constructor stub
	}

	@FXML
	void searchButton(ActionEvent event) throws IOException {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			resultPane.getChildren().clear();
			findResults(textFieldSearch.getText());
		}
	}

	@FXML
	void searchField(ActionEvent event) throws IOException {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			resultPane.getChildren().clear();
			findResults(textFieldSearch.getText());
		}
	}

	/**
	 * This method takes the search text and then requests the Gson file from the Google API
	 * and then assigns each Result to a Result class.
	 * @param searchText
	 * @throws IOException
	 */
	void findResults(String searchText) throws IOException {
		URL url = new URL(SearchQuery.getQuery(textFieldSearch.getText()));
		Reader reader = new InputStreamReader(url.openStream(), "UTF-8");
		Gson gs = new GsonBuilder()
					.setPrettyPrinting()
					.disableHtmlEscaping()
					.create();
		
		GoogleSearchResults results = gs.fromJson(reader, GoogleSearchResults.class);
		this.results = results;
		sendResults();
	}
	
	@FXML
	void sendResults() {
		for(int i = 0; i <= 3; i++) {
			resultPane.getChildren().add(new Search(results.getResponseData().getResults().get(i)));
		}
	}
}
