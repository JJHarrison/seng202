package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.scene.control.ListSpinner;

public class Test extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ListSpinner<Integer> listSpinner = new ListSpinner<Integer>(1, 100);
		BorderPane root = new BorderPane();
		root.setCenter(listSpinner);

		listSpinner.editableProperty().set(true);
		listSpinner.setStringConverter(new StringConverter<Integer>() {

			@Override
			public String toString(Integer object) {
				// TODO Auto-generated method stub
				return String.format("%d", object);
			}

			@Override
			public Integer fromString(String string) {
				int i = 0;
				try {
					i = Integer.parseInt(string);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				return i;
			}
		});

		primaryStage.setScene(new Scene(root, 300, 300));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
