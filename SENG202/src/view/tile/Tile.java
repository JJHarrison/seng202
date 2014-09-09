package view.tile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;


public class Tile extends AnchorPane {
	@FXML
	WebView webViewTest;

	public Tile() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"Tile.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
