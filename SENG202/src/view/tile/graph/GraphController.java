package view.tile.graph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import data.model.Event;
import data.model.Graph;
import data.model.GraphHelper;

/**
 * Controller.
 * 
 * @author Daniel van Wichen
 *
 */
public class GraphController {

	@FXML
	ToggleButton buttonHR;

	@FXML
	ToggleButton buttonStress;

	@FXML
	ToggleButton buttonCalories;

	@FXML
	ToggleButton buttonSpeed;

	@FXML
	ToggleButton buttonDistance;

	@FXML
	ToggleButton buttonAltitude;

	@FXML
	LineChart<Number, Number> lineChart;

	private ToggleGroup toggleGroup = new ToggleGroup();
	private Event event;
	
	private enum GraphType {
		HEARTRATE, STRESS, CALORIES,
		DISTANCE, SPEED, ALTITUDE
	}
	private GraphType selected;

	@FXML 
	void initialize() {
		toggleGroup.getToggles().addAll(buttonAltitude, buttonCalories, buttonDistance, buttonHR, buttonSpeed,
				buttonStress);
		buttonHR.fire();
	}

	/**
	 * Fill the graph view and set to default graph (HR graph).
	 * 
	 * @param event the event of this tile.
	 */
	public void fill(Event event) {
		this.event = event;
		fillHRGraph(event);
		lineChart.getXAxis().animatedProperty().set(false);
		lineChart.getYAxis().animatedProperty().set(false);
		lineChart.setStyle("chart-series-line series<0> default-color<2>");
	}

	private void fillHRGraph(Event event) {
		if (selected != GraphType.HEARTRATE) {
			Graph graph = GraphHelper.getHeartRateGraph(event);
			lineChart.getXAxis().setLabel(graph.getXName());
			lineChart.getYAxis().setLabel(graph.getYName());
			lineChart.getData().clear();
			lineChart.getData().add(graph.getPoints());
			selected = GraphType.HEARTRATE;
		}
	}

	private void fillStressGraph(Event event) {
		if (selected != GraphType.STRESS) {
			Graph graph = GraphHelper.getStressLevelGraph(event);
			lineChart.getXAxis().setLabel(graph.getXName());
			lineChart.getYAxis().setLabel(graph.getYName());
			lineChart.getData().clear();
			lineChart.getData().add(graph.getPoints());
			selected = GraphType.STRESS;
		}
	}

	private void fillCaloriesGraph(Event event) {
		if (selected != GraphType.CALORIES) {
			Graph graph = GraphHelper.getCaloriesGraph(event, null);
			lineChart.getXAxis().setLabel(graph.getXName());
			lineChart.getYAxis().setLabel(graph.getYName());
			lineChart.getData().clear();
			lineChart.getData().add(graph.getPoints());
			selected = GraphType.CALORIES;
		}
	}

	private void fillDistanceGraph(Event event) {
		if (selected != GraphType.DISTANCE) {
			Graph graph = GraphHelper.getDistanceGraph(event);
			lineChart.getXAxis().setLabel(graph.getXName());
			lineChart.getYAxis().setLabel(graph.getYName());
			lineChart.getData().clear();
			lineChart.getData().add(graph.getPoints());
			selected = GraphType.DISTANCE;
		}
	}

	private void fillSpeedGraph(Event event) {
		if (selected != GraphType.SPEED) {
			Graph graph = GraphHelper.getSpeedGraph(event);
			lineChart.getXAxis().setLabel(graph.getXName());
			lineChart.getYAxis().setLabel(graph.getYName());
			lineChart.getData().clear();
			lineChart.getData().add(graph.getPoints());
			selected = GraphType.SPEED;
		}
	}

	private void fillAltitudeGraph(Event event) {
		if (selected != GraphType.ALTITUDE) {
			Graph graph = GraphHelper.getAltitudeGraph(event);
			lineChart.getXAxis().setLabel(graph.getXName());
			lineChart.getYAxis().setLabel(graph.getYName());
			lineChart.getData().clear();
			lineChart.getData().add(graph.getPoints());
			selected = GraphType.ALTITUDE;
		}
	}

	@FXML
	void actionHR(ActionEvent event) {
		toggleGroup.selectToggle(buttonHR);
		if (this.event != null)
			fillHRGraph(this.event);
	}

	@FXML
	void actionStress(ActionEvent event) {
		toggleGroup.selectToggle(buttonStress);
		fillStressGraph(this.event);
	}

	@FXML
	void actionCalories(ActionEvent event) {
		toggleGroup.selectToggle(buttonCalories);
		fillCaloriesGraph(this.event);
	}

	@FXML
	void actionSpeed(ActionEvent event) {
		toggleGroup.selectToggle(buttonSpeed);
		fillSpeedGraph(this.event);
	}

	@FXML
	void actionDistance(ActionEvent event) {
		toggleGroup.selectToggle(buttonDistance);
		fillDistanceGraph(this.event);
	}

	@FXML
	void actionAltitude(ActionEvent event) {
		toggleGroup.selectToggle(buttonAltitude);
		fillAltitudeGraph(this.event);
	}

}
