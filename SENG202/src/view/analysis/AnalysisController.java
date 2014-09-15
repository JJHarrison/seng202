package view.analysis;

import data.model.Event;
import view.tile.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AnalysisController {

    @FXML
    VBox tileBox;

    @FXML
    void initialize() {
	tileBox.getChildren().addAll(new Tile(new Event("Yay")),
		new Tile(new Event("Jay")), new Tile(new Event("Wins")));
    }

}
