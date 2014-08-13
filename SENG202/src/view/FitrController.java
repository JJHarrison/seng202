package view;

import java.awt.Desktop.Action;
import java.awt.Dialog;
import java.io.File;
import java.util.ArrayList;

import jfx.messagebox.MessageBox;
import dataImport.FileLoader;
import dataModel.DataPoint;
import dataModel.Event;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * The current controller for the Fitr application.
 * @author daniel
 *
 */
public class FitrController {
	@FXML
	private Stage stage;
	
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
		menuImport.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				loadFile(importFile());
				
			}
		});
		
		menuClear.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				clear();
				
			}
		});
		
		menuClose.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.exit(0);
				
			}
		});
		
		menuAbout.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				 
			     MessageBox.show(stage,
			         "Version 1.0\n"
			         + "\nThis is a prototype\n"
			        		 + "USE WITH CARE!",
			         "About Fitr",
			          MessageBox.OK | MessageBox.CANCEL);
				
			}
		});
		
		
		FileLoader fLoader = new FileLoader();
		fLoader.load();
		
		ObservableList<Event> events = FXCollections.observableArrayList();
		events.setAll(fLoader.getEvents());
		eventSelector.setItems(events);
		
		eventSelector.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Event>() {

					public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
						if (newValue != null) {
						obsEvent.set(newValue);
						eventSummary.setText(obsEvent.getValue().getSummary());
						ObservableList<DataPoint> data = FXCollections.observableArrayList(obsEvent.get().getDataPoints());
						tableView.itemsProperty().setValue(data);
						} else {
							eventSummary.setText(null);
							tableView.itemsProperty().setValue(null);
						}
					}
				});
		
		eventSelector.getSelectionModel().clearAndSelect(0);
		
		colDate.setCellValueFactory(new PropertyValueFactory<DataPoint,String>("getDate"));
		colTime.setCellValueFactory(new PropertyValueFactory<DataPoint,String>("getTime"));
		colLatitude.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getLatitude"));
		colLongitude.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getLongitude"));
		colDistance.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getDistance"));
		colSpeed.setCellValueFactory(new PropertyValueFactory<DataPoint,Double>("getSpeed"));
		colHR.setCellValueFactory(new PropertyValueFactory<DataPoint,Integer>("getHeartRate"));
	}
	
	public File importFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Activity File");
		ArrayList<ExtensionFilter> filter = new ArrayList<ExtensionFilter>();
		filter.add(new ExtensionFilter("CSV file", "*.csv"));
		fileChooser.getExtensionFilters().setAll(FXCollections.observableList(filter));
		File file = fileChooser.showOpenDialog(new Stage());
		return file;
	}
	
	public void loadFile(File file) {
		FileLoader fLoader = new FileLoader();
		fLoader.load();
		
		ObservableList<Event> events = FXCollections.observableArrayList();
		events.setAll(fLoader.getEvents());
		eventSelector.setItems(events);
		eventSelector.getSelectionModel().clearAndSelect(0);
	}
	
	public void clear() {
		eventSelector.setItems(null);
	}
	
	
}
