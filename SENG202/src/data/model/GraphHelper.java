package data.model;

import java.util.ArrayList;

import user.User;

/**
 * Has static methods for building each graph from a given event.
 * @author Simon
 *
 */
public class GraphHelper {
	/**
	 * Builds a graph with heart rate on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from.
	 * @return the heart rate graph.
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
	 * Uses the average of the stress level over 3 points for events with
	 * at least 10 points.
	 * @param e the event the points are to be taken from.
	 * @return the stress level graph.
	 */
	public static Graph getStressLevelGraph(Event e) {
		Graph g = new Graph("Stress Level", "Time (s)", "Stress");
		ArrayList<DataPoint> points = e.getPoints();
		
		// only take the average if there are 10 or more points
		if (points.size() >= 10) {
			double time = points.get(0).getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			double stress = points.get(0).getStressLevel();
			
			g.addPoint(time / 1000, stress);
			
			for (int i = 1; i < points.size() - 3; i++) {
				double stress1 = points.get(i).getStressLevel();
				double stress2 = points.get(i+1).getStressLevel();
				double stress3 = points.get(i+2).getStressLevel();
				double startTime = points.get(i).getDate().getTimeInMillis()
						- e.getStartTime().getTimeInMillis();
				double endTime = points.get(i+2).getDate().getTimeInMillis()
						- e.getStartTime().getTimeInMillis();
				
				stress = (stress1 + stress2 + stress3) / 3; // average stress
				time = (startTime + endTime) / 2; // average/midpoint time
				
				g.addPoint(time / 1000, stress);
			}
			
			time = points.get(points.size() - 1).getDate().getTimeInMillis()
					- e.getStartTime().getTimeInMillis();
			stress = points.get(points.size() - 1).getStressLevel();
			
			g.addPoint(time / 1000, stress);
		} else {
			// just use the normal stress level for less than 10 points
			for (DataPoint p : points) {
				double stress = p.getStressLevel();
				double time = p.getDate().getTimeInMillis()
						- e.getStartTime().getTimeInMillis();
				g.addPoint(time / 1000, stress);
			}
		}
		
		return g;
	}

	/**
	 * Builds a graph with speed on the y-axis and time on the x-axis.
	 * @param e the event the points are to be taken from.
	 * @return the speed graph.
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
	 * @param e the event the points are to be taken from.
	 * @return the distance graph.
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
	 * @param e the event the points are to be taken from.
	 * @param u the current user.
	 * @return the calories graph.
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
	 * @param e the event the points are to be taken from.
	 * @return the altitude graph.
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
