package view.analysis;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import view.tile.Tile;
import data.model.Event;

public class AnalysisController {

    @FXML
    VBox tileBox;
    
    Tile tile1;
    Tile tile2;
    Tile tile3;
    
    @FXML
    void initialize() {

    }

    public void addTiles(Event event1, Event event2, Event event3) {
	tileBox.getChildren().clear();
	
	Task<Void> task = new Task<Void>() {

	    @Override
	    protected Void call() throws Exception {
		tile1 = new Tile(event1);
		return null;
	    }
	    
	    @Override
	    protected void succeeded() {
		tileBox.getChildren().add(tile1);
	    }
	};
	
	Thread thread = new Thread(task);
	thread.start();
	
	Task<Void> task1 = new Task<Void>() {

	    @Override
	    protected Void call() throws Exception {
		tile2 = new Tile(event2);
		return null;
	    }
	    
	    @Override
	    protected void succeeded() {
		tileBox.getChildren().add(tile2);
	    }
	};
	
	Thread thread1 = new Thread(task1);
	thread1.start();
	
	Task<Void> task2 = new Task<Void>() {

	    @Override
	    protected Void call() throws Exception {
		tile3 = new Tile(event3);
		return null;
	    }
	    
	    @Override
	    protected void succeeded() {
		tileBox.getChildren().add(tile3);
	    }
	};
	
	Thread thread2 = new Thread(task2);
	thread2.start();

    }

}
