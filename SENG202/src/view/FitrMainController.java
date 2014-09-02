package view;

public class FitrMainController {

	@FXML
	ListView<String> eventSelector;

	@FXML
	FitrTileControl mapTest;

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
