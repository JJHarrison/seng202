package view.user;

import java.util.GregorianCalendar;

import user.User;
import user.User.Gender;
import data.persistant.Persistent;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class UserCreateController implements ScreenController {
	private ScreenPane myScreenPane;
	
	@FXML
	Button buttonCancelCreate;
	@FXML
	Button buttonCreate;
	
	@FXML
	ComboBox<Gender> fieldGender;
	
	@FXML
    public void initialize() {
        buttonCancelCreate.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				myScreenPane.setScreen("userLogin");
			}
		});
        
        fieldGender.setItems(FXCollections.observableArrayList(Gender.values()));
        
        buttonCreate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					User user = new  User("Daniel van Wichen", new GregorianCalendar(), Gender.MALE);
					//Persistent.setUser(user);
					Persistent.newUser(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myScreenPane.setScreen("userLogin");
				
			}
		});
    }    
	
	
	@Override
	public void setScreenPane(ScreenPane screenPage) {
		myScreenPane = screenPage;
	}
	

}
