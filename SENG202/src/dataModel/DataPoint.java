package dataModel;

public class DataPoint {
	public String date;
	public String time;
	public String heartRate;
	public String lat;
	public String lon;
	public String alt;
	public float speed;
	public double distance;
	public float dTime;
	
	public DataPoint(String date, String time, String hr, String lat, String lon, String alt, double distance, int dTime){
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
