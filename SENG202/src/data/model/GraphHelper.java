package data.model;

import user.User;

public class GraphHelper {
	public static Graph getHeartRateGraph(Event e) {
		Graph g = new Graph("Heart Rate", "Time (s)", "Heart Rate (bpm)");
		for (DataPoint p : e.getPoints()) {
			double hr = p.getHeartRate();
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, hr);
		}
		return g;
	}

	public static Graph getStressLevelGraph(Event e) {
		Graph g = new Graph("Stress Level", "Time (s)", "Stress");
		// TODO
		return g;
	}

	public static Graph getSpeedGraph(Event e) {
		Graph g = new Graph("Speed", "Time (s)", "Speed (m/s)");
		for (DataPoint p : e.getPoints()) {
			double speed = p.getSpeed();
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, speed);
		}
		return g;
	}

	public static Graph getDistanceGraph(Event e) {
		Graph g = new Graph("Distance Travelled", "Time (s)", "Distance (m)");
		for (DataPoint p : e.getPoints()) {
			double dist = p.getDistance();
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, dist);
		}
		return g;
	}

	public static Graph getCaloriesGraph(Event e, User u) {
		Graph g = new Graph("Calories Burned", "Time (s)", "Calories");
		for (DataPoint p : e.getPoints()) {	
			double time = p.getDate().getTimeInMillis()- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, p.getCalories());
		}
		return g;
	}

	public static Graph getAltitudeGraph(Event e) {
		Graph g = new Graph("Altitude", "Time (s)", "Altitude (m)");
		for (DataPoint p : e.getPoints()) {
			double alt = p.getAltitude();
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, alt);
		}
		return g;
	}
}
