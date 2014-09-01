package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FitrMainController {

	@FXML
	ListView<String> eventSelector;

	@FXML
	private void initialize() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Event1");
		list.add("Event2");
		list.add("Event3");
		list.add("Event4");
		eventSelector.setItems(list);
	}

}
