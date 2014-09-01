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
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		list.add("<S Time - F Time>\n<Event Title>");
		eventSelector.setItems(list);
	}

}
