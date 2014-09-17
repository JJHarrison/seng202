package view.tile.map;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import data.model.DataPoint;
import data.model.Event;

public class MapController {

    @FXML
    ImageView mapView;

    private Event event;

    private static String urlStaticMap = "https://maps.googleapis.com/maps/api/staticmap?";
    private static String parameterPath = "path=color:0x3BF783|";
    private static String parameterStart = "markers=label:S|";
    private static String parameterFinish = "markers=label:F|";
    private static String parameterSize = "size=640x480&zoom=19";
    private static String parameterType = "maptype=roadmap";

    public void fill(Event event) {
	this.event = event;
	fillMap();
    }

    private void fillMap() {
	mapView.setImage(getImage());
    }

    private Image getImage() {
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

	Image mapImage = new Image(stringMapRequest.toString());

	return mapImage;
    }

}
