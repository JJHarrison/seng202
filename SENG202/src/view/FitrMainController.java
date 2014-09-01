package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FitrMainController {

	@FXML
	ListView<String> eventSelector;
	
	@FXML
	FitrTileControl mapTest;
	
	
	
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
