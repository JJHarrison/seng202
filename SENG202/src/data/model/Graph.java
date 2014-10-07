package data.model;

import javafx.scene.chart.XYChart;

/**
 * This class provides a basic model for graphs, which can be used in the GUI.
 * The graphs are stored in the Event class until they are needed.
 * 
 * @author Simon
 *
 */
public class Graph {
	private String title;
	private String xName;
	private String yName;
	XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();

	/**
	 * Constructor
	 * 
	 * @param title
	 *            the title of the graph
	 * @param xName
	 *            the label for the x axis
	 * @param yName
	 *            the label for the y axis
	 */
	public Graph(String title, String xName, String yName) {
		this.title = title;
		this.xName = xName;
		this.yName = yName;
		series.setName(title);
	}

	/**
	 * Adds a point to the graph
	 * 
	 * @param x
	 *            x value
	 * @param y
	 *            y value
	 */
	public void addPoint(double x, double y) {
		series.getData().add(new XYChart.Data<Number, Number>(x, y));
	}

	/**
	 * Returns graph title
	 * 
	 * @return graph title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns x axis label
	 * 
	 * @return xName
	 */
	public String getXName() {
		return xName;
	}

	/**
	 * Returns y axis label
	 * 
	 * @return yName
	 */
	public String getYName() {
		return yName;
	}

	/**
	 * Returns an ArrayList of all the points
	 * 
	 * @return points
	 */
	public XYChart.Series<Number, Number> getPoints() {
		return series;
	}
}
