package view.search;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

import view.search.GoogleSearchResults.Result;


public class SearchController {

	@FXML
	Label labelTitle;
	
	@FXML
	Label labelURL;
	
	public void fill(Result result) {
		labelTitle.setText(result.getTitle());
		labelURL.setText(result.getUrl());
	}
	

}

