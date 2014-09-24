package view.search;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import view.search.GoogleSearchResults.Result;

public class SearchController {

	@FXML
	Label labelTitle;

	@FXML
	Hyperlink labelURL;

	String strURL;

	/**
	 * Fills the Result's title and URL for the search tile
	 */
	public void fill(Result result) {
		labelTitle.setText(result.getTitle());
		labelURL.setText(result.getUrl());
		strURL = result.getUrl();
	}

	@FXML
	void gotoButton(ActionEvent event) throws IOException, URISyntaxException {
		// Creates a Desktop object
		Desktop d = Desktop.getDesktop();
		// Browses the URL of the result
		String s = System.getProperty("os.name").toLowerCase();
		if (! s.contains("linux")) {
			d.browse(new URI(strURL));
		} else {
			Runtime.getRuntime().exec(String.format("gnome-open %s", strURL));
		}
		
	}

}
