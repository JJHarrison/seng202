package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class FitrTileControl extends AnchorPane {

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
