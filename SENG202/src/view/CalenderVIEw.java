package view;

import java.util.Date;

import extfx.scene.control.CalendarView;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CalenderVIEw extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		Stage stage = new Stage();
		BorderPane pane = new BorderPane();
		CalendarView cal = new CalendarView();
		Property<Date> calProperty = cal.selectedDateProperty();
		calProperty.setValue(new Date());
		calProperty.addListener(new ChangeListener<Date>() {
			public void changed(ObservableValue<? extends Date> arg0,
					Date arg1, Date arg2) {
				System.out.println(arg1.getTime());
				System.out.println(arg2.getTime());
			}
		});
		pane.setCenter(cal);
		stage.setScene(new Scene(pane));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
