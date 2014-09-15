/**
 * The Fitr application
 * @author The Fitr Team
 */

package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import user.User;
import data.model.EventContainer;

public class Main extends Application {

    public static EventContainer eventContainer;
    public static User user;

    private Stage stage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {

	stage = primaryStage;
	stage.setTitle("Fitr");

	// Minimum size of the stage.
	stage.setMinHeight(700);
	stage.setMinWidth(1366);
	stage.setMaximized(true);

	/*try {
	    this.stage.getIcons().add(new Image("/resources/heart11.png"));
	} catch (Exception e) {
	    e.printStackTrace();
	}*/

	initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
	try {
	    // Load root layout from FXML file.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("Main.fxml"));
	    rootLayout = (BorderPane) loader.load();

	    // Show the scene containing the root layout.
	    Scene scene = new Scene(rootLayout);
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    stage.show();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}