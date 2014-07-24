

//Imports are listed in full to show what's being used could just import javafx.*
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class JavaFx extends Application {

//JavaFX application still use the main method.
  //It should only ever contain the call to the launch method
  public static void main(String[] args) {
      launch(args);
  }
  
  //starting point for the application
  //this is where we put the code for the user interface
  @Override
  public void start(Stage primaryStage) {
      
      //The primaryStage is the top-level container
      primaryStage.setTitle("Example Gui");
      primaryStage.centerOnScreen();
      primaryStage.getIcons().add(new Image("test/home.png"));
      

      //The BorderPane has the same areas laid out as the
      //BorderLayout layout manager
      BorderPane componentLayout = new BorderPane();
      componentLayout.setPadding(new Insets(20,0,20,20));
      
      //The FlowPane is a container that uses a flow layout
      final FlowPane choicePane = new FlowPane();
      choicePane.setHgap(100);
      Label choiceLbl = new Label("Vegetables (Daniel)");
      
      //The choice box is populated from an observableArrayList
      ChoiceBox<String> fruits = new ChoiceBox<String>(FXCollections.observableArrayList("Asparagus", "Beans", "Broccoli", "Cabbage"
       , "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
       , "Pepper", "Radish", "Shallot", "Spinach", "Swede"
       , "Turnip"));
      
      
      
      
      //Add the label and choicebox to the flowpane
      choicePane.getChildren().add(choiceLbl);
      choicePane.getChildren().add(fruits);
      
      //put the flowpane in the top area of the BorderPane
      componentLayout.setTop(choicePane);
      
      final FlowPane listPane = new FlowPane();
      listPane.setHgap(100);
      Label listLbl = new Label("Fruits");
      
      ListView<String> vegetables = new ListView<String>(FXCollections.observableArrayList("Apple", "Apricot", "Banana","Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"));
      listPane.getChildren().add(listLbl);
      listPane.getChildren().add(vegetables);
      listPane.setVisible(false);
      
      componentLayout.setCenter(listPane);
      
      //The button uses an inner class to handle the button click event
      Button vegFruitBut = new Button("Fruit or Veg");
      vegFruitBut.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
            choicePane.setVisible(!choicePane.isVisible());
            listPane.setVisible(!listPane.isVisible());
			
		}
      });
      
      componentLayout.setBottom(vegFruitBut);
      
      //Add the BorderPane to the Scene
      Scene appScene = new Scene(componentLayout,500,500);
      
      //Add the Scene to the Stage
      primaryStage.setScene(appScene);
      primaryStage.show();
  }
}