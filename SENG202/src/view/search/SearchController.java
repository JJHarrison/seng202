package view.search;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class SearchController {

	@FXML
	Label labelTitle;
	
	@FXML
	Label labelURL;
	
	public void fill() {
		labelTitle.setText("Test Title");
		labelURL.setText("Test URL");
	}
	

}

