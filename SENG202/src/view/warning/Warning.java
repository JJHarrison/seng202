package view.warning;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import resources.Reference;

public class Warning extends AnchorPane {
	public static String TILE = "Warning.fxml";
	
	public enum WARNING {BRADYCARDIA, TACHYCARDIA};
	private WarningController warningController;

	public Warning(WARNING riskType, Calendar eventStartTime) {
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
		this.warningController = warningController;
		warningController.setDate(eventStartTime);
		warningController.setDescription(getRiskDescription(riskType));
		
		
	}
	
	/**
	 * Returns the string of warning messages relating to Bradycardia and Tachycardia.
	 * @param warning The type of warning to get a message for.
	 * @return The warning string full of information about that warning.
	 */
	public String getRiskDescription(WARNING warning) {
		String text = "DESCRIPTION HAS FAILED";
		switch (warning) {
		case BRADYCARDIA:
			text = new Scanner(Reference.class.getResourceAsStream("bradycardia.txt"), "UTF-8" ).useDelimiter("\\A").next();
			warningController.setRisk("Bradycardia Risk");
			break;

		case TACHYCARDIA:
			text = new Scanner(Reference.class.getResourceAsStream("tachycardia.txt"), "UTF-8" ).useDelimiter("\\A").next();
			warningController.setRisk("Tachycardia Risk");
			break;
		}
		return text;
	}

}
