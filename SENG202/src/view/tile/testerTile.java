package view.tile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import data.model.DataPoint;
import data.model.Event;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testerTile extends Application {
    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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
	Event event = new Event("Yay I got this working", points);
	// Calendar calendar = new GregorianCalendar();
	/*
	 * event.setStartTime(calendar); calendar = new GregorianCalendar();
	 * calendar.add(Calendar.HOUR, 3); event.setFinishTime(calendar);
	 */

	VBox vBox = new VBox();
	vBox.minHeight(600);
	vBox.minWidth(1200);
	vBox.getChildren().add(new Tile(event));
	primaryStage.setScene(new Scene(vBox));
	primaryStage.show();
	primaryStage.setHeight(700);

    }

}
