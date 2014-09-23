package view.search;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.search.GoogleSearchResults.Result;

public class SearchController {

	@FXML
	Label labelTitle;

	@FXML
	Label labelURL;

	/**
	 * Fill the label and URL for the search tile
	 */
	public void fill() {
		labelTitle.setText("Insert Useful Health Result");
		labelURL.setText("www.heartdiseasefordummies.org");
	}

	public void fill(Result result) {
		labelTitle.setText(result.getTitle());
		labelURL.setText(result.getUrl());
	}

}
