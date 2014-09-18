package view.web;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
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
		if (!textFieldSearch.getText().trim().isEmpty()) {
			webEngine.load(SearchQuery.getQuery(textFieldSearch.getText()));
		}
	}

	@FXML
	void searchField(ActionEvent event) {
		if (!textFieldSearch.getText().trim().isEmpty()) {
			webEngine.load(SearchQuery.getQuery(textFieldSearch.getText()));
		}
	}

	@FXML
	void backAction(ActionEvent event) {
		WebHistory history = webEngine.getHistory();
		int back = history.getCurrentIndex();
		if (back <= 0) {
		} else {
			history.go(-1);
		}

	}

	@FXML
	void forwardAction(ActionEvent event) {
		WebHistory history = webEngine.getHistory();
		int fwd = history.getCurrentIndex();
		fwd++;
		ObservableList<WebHistory.Entry> entryList = history.getEntries();
		int stop = entryList.size();
		if (fwd == stop) {
		} else {
			history.go(1);
		}
	}
}
