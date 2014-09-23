package view.tile.table;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import data.model.DataPoint;
import data.model.Event;

public class TableController {

	@FXML
	TableView<DataPoint> tableView;

	@FXML
	TableColumn<DataPoint, String> tableTime;
	@FXML
	TableColumn<DataPoint, String> tableHR;
	@FXML
	TableColumn<DataPoint, String> tableAltitude;
	@FXML
	TableColumn<DataPoint, String> tableLatitude;
	@FXML
	TableColumn<DataPoint, String> tableLongitude;
	@FXML
	TableColumn<DataPoint, String> tableDistance;

	/**
	 * fill the tile
	 * 
	 * @param event event to fill
	 */
	public void fill(Event event) {
		tableView.setItems(FXCollections.observableArrayList(event
				.getDataPoints()));
		tableTime.setCellValueFactory(new PropertyValueFactory<>("getTime"));
		tableDistance.setCellValueFactory(new PropertyValueFactory<>(
				"getDistance"));
		tableAltitude.setCellValueFactory(new PropertyValueFactory<>(
				"getAltitude"));
		tableLatitude.setCellValueFactory(new PropertyValueFactory<>(
				"getLatitude"));
		tableLongitude.setCellValueFactory(new PropertyValueFactory<>(
				"getLongitude"));
		tableHR.setCellValueFactory(new PropertyValueFactory<>("getHeartRate"));

	}

}
