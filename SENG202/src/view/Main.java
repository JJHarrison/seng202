/**
 * The Fitr application
 * @author The Fitr Team
 */

package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import resources.Reference;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class Main extends Application {

	/**
	 * The root stage of the Fitr application.
	 */
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Fitr");

		// Minimum size of the stage.
		stage.setMinHeight(700);
		stage.setMinWidth(1366);
		stage.setMaximized(true);
		stage.getIcons().add(new Image(Reference.class.getResourceAsStream("heart_icon.png")));

		initRootLayout(); 
	}

	/**
	 * Initializes the root layout.
	 */
	private void initRootLayout() {
		try {
			// Load root layout from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Main.fxml"));
			BorderPane rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			stage.setScene(scene);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					stage.show();
				}
			});

		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}