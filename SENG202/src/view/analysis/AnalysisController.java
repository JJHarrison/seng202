package view.analysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import view.tile.Tile;
import data.model.DataPoint;
import data.model.Event;

public class AnalysisController {

    @FXML
    VBox tileBox;

    @FXML
    void initialize() {
	ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	Calendar c3 = new GregorianCalendar(2005, // Year
		5, // Month
		10, // Day
		23, // Hour
		42, // Minute
		28); // Second
	Calendar c4 = new GregorianCalendar(2005, // Year
		5, // Month
		10, // Day
		23, // Hour
		43, // Minute
		5); // Second

	DataPoint p1 = new DataPoint.Builder().date(c3).heartRate(120)
		.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
		.prevDataPoint(null).build();

	DataPoint p2 = new DataPoint.Builder().date(c4).heartRate(125)
		.latitude(30.25499189).longitude(-97.83913958).altitude(51.0)
		.prevDataPoint(p1).build();

	points.add(p1);
	points.add(p2);
	tileBox.getChildren().addAll(new Tile(new Event("Yay", points)),
		new Tile(new Event("Jay", points)),
		new Tile(new Event("Wins", points)));
    }

}
