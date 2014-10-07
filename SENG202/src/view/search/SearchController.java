package view.search;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import view.search.GoogleSearchResults.Result;

/**
 * Controller.
 * 
 * @author Daniel van Wichen, Daniel Tapp, Jaln Rodger
 *
 */
public class SearchController {

	@FXML
	Label labelTitle;

	@FXML
	Hyperlink labelURL;
	
	@FXML
	TextArea textContent;

	String strURL;
	

	/**
	 * Fills the result's title and URL for the search tile.
	 * 
	 * @param result the result to fill tile with.
	 * @throws UnsupportedEncodingException 
	 */
	public void fill(Result result) throws UnsupportedEncodingException {
		labelTitle.setText(result.getTitle());
		labelURL.setText(result.getUrl());
		strURL = result.getUrl();
		textContent.setText(result.getContent());
	}

	@FXML
	void gotoButton(ActionEvent event) throws IOException, URISyntaxException {
		// Creates a Desktop object
		Desktop d = Desktop.getDesktop();
		// Browses the URL of the result
		
		// Gets the name of operating system
		String s = System.getProperty("os.name").toLowerCase();
		
		// If OS is linux then use alternative command to prevent application from crashing
		if (s.contains("linux")) {
			// OS is linux
			Runtime.getRuntime().exec(String.format("gnome-open %s", strURL));
		} else {
			d.browse(new URI(strURL));
		}
		
	}

}
