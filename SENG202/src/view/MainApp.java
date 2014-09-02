/**
 * A prototype fitr.aw application
 * @author Daniel
 */

package view;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Fitr");

		// Minimum size of the stage
		this.primaryStage.setMinHeight(768);
		this.primaryStage.setMinWidth(1366);

		try {
			this.primaryStage.getIcons().add(
					new Image("/resources/heart11.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		initRootLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("FitrMain.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout, 1366, 768);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}