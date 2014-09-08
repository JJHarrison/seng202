package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class MainController {

	@FXML
	ListView<String> eventSelector;

	@FXML
	TileControl mapTest;

	@FXML
	ScrollPane pane;

	@FXML
	BorderPane mainWindow;

	@FXML
	private void initialize() {

		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		list.add("11:24 - 12:45\nWalk with Dog");
		eventSelector.setItems(list);
	}

}
