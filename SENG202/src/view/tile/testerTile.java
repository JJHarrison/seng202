package view.tile;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
	Event event = new Event("Yay I got this working");
	Calendar calendar = new GregorianCalendar();
	event.setStartTime(calendar);
	calendar = new GregorianCalendar();
	calendar.add(Calendar.HOUR, 3);
	event.setFinishTime(calendar);

	VBox vBox = new VBox();
	vBox.minHeight(600);
	vBox.minWidth(1200);
	vBox.getChildren().add(new Tile(event));
	primaryStage.setScene(new Scene(vBox));
	primaryStage.show();
	primaryStage.setHeight(700);

    }

}
