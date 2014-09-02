package view;

public class testerTile extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setScene(new Scene(new FitrTileControl()));
		arg0.show();

	}

}
