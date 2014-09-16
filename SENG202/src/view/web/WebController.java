package view.web;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebController {

    @FXML
    WebView webView;
    
    @FXML 
    TextField textFieldSearch;
    

    WebEngine webEngine;

    @FXML
    void initialize() {
	webEngine = webView.getEngine();
	webEngine.load(SearchQuery.url);
	textFieldSearch.setText("");
    }
    
    @FXML
    void searchButton(ActionEvent event) {
	if (! textFieldSearch.getText().matches("[\\s]*")) {
	    webEngine.load(SearchQuery.getQuery(textFieldSearch.getText()));
	}
    }
    
    @FXML
    void searchField(ActionEvent event) {
	if (! textFieldSearch.getText().matches("[\\s]*")) {
	    webEngine.load(SearchQuery.getQuery(textFieldSearch.getText()));
	}
    }
}
