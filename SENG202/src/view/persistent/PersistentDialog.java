package view.persistent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class PersistentDialog {

	private static Stage stage = new Stage(StageStyle.UTILITY);
	private static PersistentDialog dialog = new PersistentDialog();
	
	
	private PersistentDialog() {
		stage.setResizable(false);
	}

	public void initialize(final Stage stage) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"PersistentDialog.fxml"));
		try {
			AnchorPane rootLayout = (AnchorPane) fxmlLoader.load();
			Scene scene = new Scene(rootLayout);
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
		stage.show();
	}

	public static void show(Stage parent) {
		dialog.initialize(stage);
		parent.hide();
	}
	
	public static void close() {
		stage.close();
	}
}
