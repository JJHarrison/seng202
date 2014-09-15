package view.analysis;

import java.util.ArrayList;

import data.model.DataPoint;
import data.model.Event;
import view.tile.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AnalysisController {

    @FXML
    VBox tileBox;

    @FXML
    void initialize() {
    	ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	tileBox.getChildren().addAll(new Tile(new Event("Yay", points)), new Tile(new Event("Jay", points)), new Tile(new Event("Wins", points)));
    }

}
