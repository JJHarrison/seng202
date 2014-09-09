package view.tile;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.user.UserLoginManager;


public class testerTile extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setScene(new Scene(new Tile()));
		//arg0.show();
		UserLoginManager.show();

	}

}
