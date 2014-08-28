package view;

import java.util.Calendar;

import extfx.scene.control.CalendarView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;



public class FitrMainController {
	
	@FXML
	GridPane selectionGrid;
	
	Label l = new Label();
	
	@FXML
	private void initialize() {
		CalendarView calendarView = new CalendarView();
		ObjectProperty<Calendar> calProperty = calendarView.calendarProperty();
		
		selectionGrid.add(calendarView, 0, 2);
		System.out.print(1);
	}
	
	
}

