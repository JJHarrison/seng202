package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class FitrTileControl extends BorderPane {

	public FitrTileControl() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"FitrTile.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
