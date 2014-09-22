package view.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import view.user.UserManagementController.View;
import data.persistant.Persistent;

public class UserPersistController implements Switchable {

	private UserManagementController controller;
	@FXML
	Tooltip tooltipFilepath;
	@FXML
	Label labelFilepath;
	@FXML
	Label labelNoFileSet;

	private DirectoryChooser chooser = new DirectoryChooser();
	private File file;

	@FXML
	void initalize() {
	}

	@Override
	public void setController(UserManagementController controller) {
		this.controller = controller;
	}

	@FXML
	void actionBrowse(ActionEvent event) {
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		file = chooser.showDialog(UserLoginManager.stage);
		if (file != null) {
			try {
				labelFilepath.setText(file.getCanonicalPath());
				tooltipFilepath.setText(file.getCanonicalPath());
			} catch (IOException e) {
				labelFilepath.setText(file.getAbsolutePath());
				tooltipFilepath.setText(file.getAbsolutePath());
			}
		}
	}

	@FXML
	void actionOk(ActionEvent event) {
		if (file != null) {
			try {
				Persistent.setFilePath(file.getAbsolutePath());
				controller.setView(View.LOGIN);
			} catch (FileNotFoundException e) {
				labelNoFileSet.setText("Your system is screwed");
				e.printStackTrace();
			}

		} else {
			labelNoFileSet.setText("Please set a valid filepath");
		}

	}

	@FXML
	void actionCancel(ActionEvent event) {
		Platform.exit();
	}

}
