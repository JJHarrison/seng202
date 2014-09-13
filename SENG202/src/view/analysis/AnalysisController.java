package view.analysis;

import view.tile.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AnalysisController {

    @FXML
    VBox tileBox;

    @FXML
    void initialize() {
	tileBox.getChildren().addAll(new Tile(), new Tile(), new Tile(),
		new Tile(), new Tile(), new Tile());
    }

}
