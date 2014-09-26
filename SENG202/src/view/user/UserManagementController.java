package view.user;

import java.io.IOException;

import data.persistant.Persistent;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * 
 * @author Daniel van Wichen
 *
 */
public class UserManagementController {

	public static enum View {
		CREATE, PERSIST, LOGIN
	};

	private static UserLoginController userLoginController;
	private static UserCreateController userCreateController;
	private static UserPersistController userPersistController;

	private static AnchorPane userLogin;
	private static AnchorPane userCreate;
	private static AnchorPane userPersist;

	@FXML
	StackPane viewMain;

	@FXML
	void initialize() {
		loadPersist();
		loadLogin();

		viewMain.getChildren().clear();

		if (Persistent.filePathSet()) {
			viewMain.getChildren().add(userLogin);
		} else {
			viewMain.getChildren().add(userPersist);
		}

	}

	public void setView(View view) {
		switch (view) {
		case CREATE:
			loadCreate();
			transition(userCreate);
			break;
		case PERSIST:
			loadLogin();
			transition(userPersist);
			break;
		case LOGIN:
			loadPersist();
			transition(userLogin);
			break;
		}

	}

	private void transition(Node fadein) {
		FadeTransition ft;
		ft = new FadeTransition(Duration.millis(250), viewMain.getChildren()
				.get(0));
		ft.setToValue(0);
		ft.play();
		viewMain.getChildren().clear();
		viewMain.getChildren().add(fadein);
		ft = new FadeTransition(Duration.millis(250), fadein);
		ft.setToValue(1);
		ft.play();
	}

	private void loadCreate() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				UserLoginManager.userCreateFXML));
		try {
			userCreate = fxmlLoader.load(getClass().getResourceAsStream(
					UserLoginManager.userCreateFXML));
			userCreateController = fxmlLoader.getController();
			userCreateController.setController(this);
		} catch (IOException e) {
		}
	}

	private void loadLogin() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				UserLoginManager.userLoginFXML));
		try {
			userLogin = fxmlLoader.load(getClass().getResourceAsStream(
					UserLoginManager.userLoginFXML));
			userLoginController = fxmlLoader.getController();
			userLoginController.setController(this);
		} catch (IOException e) {
		}
	}

	private void loadPersist() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				UserLoginManager.userPersistFXML));
		try {
			userPersist = fxmlLoader.load(getClass().getResourceAsStream(
					UserLoginManager.userPersistFXML));
			userPersistController = fxmlLoader.getController();
			userPersistController.setController(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
