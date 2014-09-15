package view.web;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebController {

    @FXML
    WebView webView;

    WebEngine webEngine;

    @FXML
    void initialize() {
	webEngine = webView.getEngine();
	webEngine.load("https://duckduckgo.com/");
    }

}
