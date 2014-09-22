package view.tile.map;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import data.model.DataPoint;
import data.model.Event;

public class MapController {

	@FXML
	ImageView mapView;

	private Event event;

	private Image image;

	private static String urlStaticMap = "https://maps.googleapis.com/maps/api/staticmap?";
	private static String parameterPath = "path=color:blue|";
	private static String parameterStart = "markers=label:S|";
	private static String parameterFinish = "markers=label:F|";
	private static String parameterSize = "size=640x480";
	private static String parameterType = "maptype=roadmap";

	public void fill(Event event) {
		this.event = event;

		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				getImage();
				return null;
			}

			@Override
			protected void succeeded() {
				fillMap();
			}
		};

		Thread thread = new Thread(task);
		thread.start();
	}

	private void fillMap() {
		mapView.setImage(image);
	}

	private void getImage() {
		StringBuilder stringMapRequest = new StringBuilder();
		stringMapRequest.append(urlStaticMap);
		stringMapRequest.append(parameterSize);

		stringMapRequest.append("&");
		stringMapRequest.append(parameterStart);
		stringMapRequest.append(event.getPointString(event.getPoints().get(0)));

		stringMapRequest.append("&");
		stringMapRequest.append(parameterFinish);
		ArrayList<DataPoint> points = event.getDataPoints();
		stringMapRequest
				.append(event.getPointString(points.get(points.size() - 1)));

		stringMapRequest.append("&");
		stringMapRequest.append(parameterType);

		stringMapRequest.append("&");
		stringMapRequest.append(parameterPath);
		stringMapRequest.append(event.getPathString());



		image = new Image(stringMapRequest.toString());
	}

}
