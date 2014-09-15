package view.user;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import data.persistant.Persistent;

/**
 *
 * @author Daniel van Wichen
 */
public class UserLoginManager extends Application {
    public static String loginFXML = "UserLogin.fxml";
    public static String createFXML = "UserCreate.fxml";
    public static String persistFXML = "UserPersist.fxml";

    Stage stage = new Stage(StageStyle.UTILITY);

    @Override
    public void start(Stage stage) throws Exception {
	stage = this.stage;

	ScreenPane mainContainer = new ScreenPane();
	mainContainer.loadScreen("userLogin", UserLoginManager.loginFXML);
	mainContainer.loadScreen("userCreate", UserLoginManager.createFXML);
	mainContainer.loadScreen("userPersist", UserLoginManager.persistFXML);

	/*
	 * initialize the persistent class.
	 */
	Persistent.initialize();

	Scene scene = new Scene(mainContainer);
	stage.setScene(scene);
	stage.show();
	stage.centerOnScreen();
	stage.setResizable(false);
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
	launch(args);
    }
}