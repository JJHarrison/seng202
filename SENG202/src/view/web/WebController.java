package view.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import view.search.GoogleSearchResults;

import com.google.gson.Gson;

public class WebController {

	@FXML
	TextField textFieldSearch;
	
	@FXML
	VBox resultPane;
	
	@FXML
	void initialize() {
		textFieldSearch.setText("");
	}

	@FXML
	void searchButton(ActionEvent event) throws IOException {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			findResults(textFieldSearch.getText());
		}
	}

	@FXML
	void searchField(ActionEvent event) {
		if (!textFieldSearch.getText().trim().isEmpty()) {
		}
	}
	
	void findResults(String searchText) throws IOException {
		URL url = new URL(SearchQuery.getQuery(textFieldSearch.getText()));
		Reader reader = new InputStreamReader(url.openStream(), "UTF-8");
		GoogleSearchResults results = new Gson().fromJson(reader, GoogleSearchResults.class);
		sendResults(results);
	}
	
	@FXML
	void sendResults(GoogleSearchResults results) {
		for(int i = 0; i <= 3; i++) {
			resultPane.getChildren().addAll((Collection<? extends Node>) results.getResponseData().getResults().get(i));
		}
	}
}
