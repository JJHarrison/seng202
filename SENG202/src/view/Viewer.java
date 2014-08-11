package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Viewer extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    private static Stage stage = new Stage();
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage = stage;
        primaryStage.show();
    }
}
