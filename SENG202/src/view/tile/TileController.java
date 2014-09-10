package view.tile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class TileController {

    /** Holder of a switchable view. */
    @FXML
    private StackPane tileView;
    
    @FXML
    void initialize() {
	
    }

    /**
     * Replaces the view displayed in the view holder with a new view.
     *
     * @param node
     *            the view node to be swapped in.
     */
    public void setView(Node node) {
	tileView.getChildren().setAll(node);
    }

    @FXML
    void loadSummary(ActionEvent event) {
	ViewSwitcher.loadView(ViewSwitcher.SUMMARY);
    }

    @FXML
    void loadMap(ActionEvent event) {
	ViewSwitcher.loadView(ViewSwitcher.MAP);
    }

    @FXML
    void loadGraph(ActionEvent event) {
	ViewSwitcher.loadView(ViewSwitcher.GRAPH);
    }

    @FXML
    void loadTable(ActionEvent event) {
	ViewSwitcher.loadView(ViewSwitcher.TABLE);
    }

}
