package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FitrController {
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

}
