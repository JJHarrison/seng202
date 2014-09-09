package view.user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserCreateController implements ScreenController {
	private ScreenPane myScreenPane;
	
	@FXML
	Button buttonCancelCreate;
	@FXML
	Button buttonCreate;
	
	@FXML
    public void initialize() {
        buttonCancelCreate.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				myScreenPane.setScreen("UserLogin");
			}
		});
    }    
	
	
	@Override
	public void setScreenPane(ScreenPane screenPage) {
		myScreenPane = screenPage;
	}
	

}
