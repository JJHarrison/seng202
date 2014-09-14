package view.web;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebController {
    
    @FXML
    final WebView webView = new WebView();
    final WebEngine webEngine = webView.getEngine();
    
    @FXML
    void initialize() {
	webEngine.load("https://www.google.co.nz/");
    }

}
