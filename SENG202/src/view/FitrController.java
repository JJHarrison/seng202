package view;

import dataImport.FileLoader;
import dataModel.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;


public class FitrController {
	//private MainApp mainApp;
	
	@FXML
	private TableView<?> tableView;
	
	@FXML
	private TableColumn<?, String> colDate;
	@FXML
	private TableColumn<?, String> colTime;
	@FXML
	private TableColumn<?, Double> colLatitude;
	@FXML
	private TableColumn<?, Double> colLongitude;
	@FXML
	private TableColumn<?, Double> colDistance;
	@FXML
	private TableColumn<?, Double> colSpeed;
	@FXML
	private TableColumn<?, Integer> colHR;
	
	@FXML
	private ListView<Event> eventSelector;
	
	@FXML
	private TextArea eventSummary;
	
	@FXML
	private MenuItem menuImport;
	
	@FXML
	private MenuItem menuClear;
	
	@FXML
	private MenuItem menuClose;
	
	@FXML
	private MenuItem menuAbout;
	
	public FitrController() {}
	
	@FXML
	private void initialize() {
		
		FileLoader fLoader = new FileLoader();
		fLoader.load();
		
		ObservableList<Event> events = FXCollections.observableArrayList();
		events.setAll(fLoader.getEvents());
		eventSelector.setItems(events);
				
		//eventSummary.setText("Hello");
		//ObservableList<String> items = FXCollections.observableArrayList (
			//    "Single", "Double", "Suite", "Family App");
			//eventSelector.setItems(items);
	}
	
}
