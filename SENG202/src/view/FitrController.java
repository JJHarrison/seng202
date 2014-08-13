package view;

import dataImport.FileLoader;
import dataModel.DataPoint;
import dataModel.Event;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;


public class FitrController {
	//private MainApp mainApp;
	
	@FXML
	private TableView<DataPoint> tableView;
	
	@FXML
	private TableColumn<DataPoint, String> colDate;
	@FXML
	private TableColumn<DataPoint, String> colTime;
	@FXML
	private TableColumn<DataPoint, Double> colLatitude;
	@FXML
	private TableColumn<DataPoint, Double> colLongitude;
	@FXML
	private TableColumn<DataPoint, Double> colDistance;
	@FXML
	private TableColumn<DataPoint, Double> colSpeed;
	@FXML
	private TableColumn<DataPoint, Integer> colHR;
	
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
	
	ObjectProperty<Event> obsEvent = new ObjectPropertyBase<Event>() {

		public Object getBean() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getName() {
			return getValue().getEventName();
		}
	};
	
	public ObservableValue<Event> getObsEvent() {
		return obsEvent;
	}
	
	public void setObsEvent(Event event) {
		obsEvent.set(event);
	}
	
	@FXML
	private void initialize() {
		
		
		
		
		FileLoader fLoader = new FileLoader();
		fLoader.load();
		
		ObservableList<Event> events = FXCollections.observableArrayList();
		events.setAll(fLoader.getEvents());
		eventSelector.setItems(events);
		
		eventSelector.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Event>() {

					public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
						setObsEvent(newValue);
						eventSummary.setText(obsEvent.getValue().getSummary());
						obsEvent.set(newValue);
						ObservableList<DataPoint> data = FXCollections.observableArrayList(obsEvent.get().getDataPoints());
						tableView.itemsProperty().setValue(data);
					}
				});		
		
		
		
		colDate.setCellValueFactory(new PropertyValueFactory<DataPoint,String>("getDate"));
		colTime.setCellValueFactory(new PropertyValueFactory<DataPoint,String>("getTime"));
		colLatitude.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getLatitude"));
		colLongitude.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getLongitude"));
		colDistance.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getDistance"));
		colSpeed.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getSpeed"));
		colHR.setCellValueFactory(new PropertyValueFactory<DataPoint,Integer>("getHeartRate"));
		
	}
	
}
