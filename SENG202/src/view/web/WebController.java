package view.web;

import view.search.Search;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class WebController {

	@FXML
	TextField textFieldSearch;
	
	@FXML
	VBox resultPane;
	
	@FXML
	void initialize() {
		textFieldSearch.setText("");
		resultPane.getChildren().add(new Search());
	}

	@FXML
	void searchButton(ActionEvent event) {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			//webEngine.load(SearchQuery.getQuery(textFieldSearch.getText()));
		}
	}

	@FXML
	void searchField(ActionEvent event) {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			//webEngine.load(SearchQuery.getQuery(textFieldSearch.getText()));
		}
	}

}
