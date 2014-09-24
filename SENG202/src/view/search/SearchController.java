package view.search;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.search.GoogleSearchResults.Result;

public class SearchController {

	@FXML
	Label labelTitle;

	@FXML
	Label labelURL;
	
	String strURL;

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
		strURL = result.getUrl();
	}
	
	@FXML
	void gotoButton(ActionEvent event) throws IOException, URISyntaxException {
		//Creates a Desktop object
		Desktop d = Desktop.getDesktop();
		//Browses the URL of the result
		d.browse(new URI(strURL));
	}

}
