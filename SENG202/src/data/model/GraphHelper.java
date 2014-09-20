package data.model;

import user.User;

public class GraphHelper {
	public static Graph getHeartRateGraph(Event e) {
		Graph g = new Graph("Heart Rate", "Time (s)", "Heart Rate (bpm)");
		double t = 0;
		for (DataPoint p : e.getPoints()) {
			double hr = p.getHeartRate();
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time, hr);
			t++;
		}
		return g;
	}

	public static Graph getStressLevelGraph(Event e) {
		Graph g = new Graph("Stress Level", "Time (s)", "Stress");
		for (DataPoint p : e.getPoints()) {
			double stress = p.getStressLevel();
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, stress);
		}
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
		double d = 0;
		Graph g = new Graph("Distance Travelled", "Time (s)", "Distance (m)");
		for (DataPoint p : e.getPoints()) {
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			d += p.getDistance();
			g.addPoint(time / 1000, d);
		}
		return g;
	}

	public static Graph getCaloriesGraph(Event e, User u) {
		double c = 0;
		Graph g = new Graph("Calories Burned", "Time (s)", "Calories");
		for (DataPoint p : e.getPoints()) {	
			double time = p.getDate().getTimeInMillis()- e.getStartTime().getTimeInMillis();
			c += p.getCalories();
			g.addPoint(time / 1000, c);
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
