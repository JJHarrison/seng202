package view.tile;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testerTile extends Application {
    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
	VBox vBox = new VBox();
	vBox.minHeight(600);
	vBox.minWidth(1200);
	vBox.getChildren().add(new Tile());
	primaryStage.setScene(new Scene(vBox));
	primaryStage.show();
	primaryStage.setHeight(700);

    }

}
