package src.dataModel;

public class DataPoint {
	String date;
	String time;
	String heartRate;
	String lat;
	String lon;
	String alt;
	float speed;
	double distance;
	float dTime;
	
	DataPoint(String date, String time, String hr, String lat, String lon, String alt, double distance, int dTime){
		this.date = date;
		this.time = time;
		heartRate = hr;
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.distance = distance;
		this.dTime = dTime;
		this.speed = (float) (distance / dTime);
	}

}
