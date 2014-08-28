package view;

import extfx.scene.control.CalendarView;
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
		
		selectionGrid.add(calendarView, 0, 2);
		System.out.print(1);
	}
	
	
}

