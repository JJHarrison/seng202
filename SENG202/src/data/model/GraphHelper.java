package data.model;

import user.User;

/**
 * Has static methods for building each graph from a given event.
 * @author simon
 *
 */
public class GraphHelper {
	/**
	 * Builds a graph with heart rate on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from
	 * @return The heart rate graph
	 */
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

	/**
	 * Builds a graph with stress level on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from
	 * @return The stress level graph
	 */
	public static Graph getStressLevelGraph(Event e) {
		Graph g = new Graph("Stress Level", "Time (s)", "Stress");
		for (DataPoint p : e.getPoints()) {
			double stress = p.getAverageStress();
			System.out.println(p.getAverageStress());
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			g.addPoint(time / 1000, stress);
		}
		return g;
	}

	/**
	 * Builds a graph with speed on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from
	 * @return The speed graph
	 */
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

	/**
	 * Builds a graph with distance on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from
	 * @return The distance graph
	 */
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

	/**
	 * Builds a graph with calories burned on the y-axis and time on the
	 * x-axis.
	 * @param e the event the points are to be taken from
	 * @param u the current user
	 * @return The calories graph
	 */
	public static Graph getCaloriesGraph(Event e, User u) {
		double c = 0;
		Graph g = new Graph("Calories Burned", "Time (s)", "Calories");
		for (DataPoint p : e.getPoints()) {
			double time = p.getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			c += p.getCalories();
			g.addPoint(time / 1000, c);
		}
		return g;
	}

	/**
	 * Builds a graph with altitude on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from
	 * @return The altitude graph
	 */
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
