package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TileControl extends AnchorPane {
	@FXML
	WebView webViewTest;

	public TileControl() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"Tile.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		WebEngine webEngine = webViewTest.getEngine();

		webEngine
				.loadContent("<img src=\"http://maps.googleapis.com/maps/api/staticmap?path=color:0x0000ff80|weight:3|30.245576,-97.823843|30.246356,-97.823326|30.246539,-97.821931|30.247105,-97.821064|30.247719,-97.820641|30.248482,-97.820708|30.24915,-97.820722&size=500x530\">");

	}
}
