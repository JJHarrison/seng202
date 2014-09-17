package view.warning;

import java.io.IOException;
import java.util.Calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Warning extends AnchorPane {
    public static String TILE = "Warning.fxml";

    public Warning(String risk, String riskDescription, Calendar eventStartTime) {
	super();
	FXMLLoader loader = new FXMLLoader();
	loader.setLocation(getClass().getResource(TILE));
	loader.setRoot(this);
	try {
	    loader.load(getClass().getResourceAsStream(TILE));
	} catch (IOException e) {
	    e.printStackTrace();
	}

	WarningController warningController = loader.getController();

    }

}
