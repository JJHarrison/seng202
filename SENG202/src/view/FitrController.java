package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;


public class FitrController {
	private MainApp mainApp;
	
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
	private ListView<String> eventSelector;
	
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
	
	public FitrController() {
		// TODO Auto-generated constructor stub
	}
	
	@FXML
	private void initialize() {
		eventSummary.setText("Hello");
		ObservableList<String> items = FXCollections.observableArrayList (
			    "Single", "Double", "Suite", "Family App");
			eventSelector.setItems(items);
	}
	
	 /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


    }
	
}
