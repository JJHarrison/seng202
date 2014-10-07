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
	 * Constructor, sets the title and axis labels of the graph.
	 * 
	 * @param title the title of the graph.
	 * @param xName the label for the x axis.
	 * @param yName the label for the y axis.
	 */
	public Graph(String title, String xName, String yName) {
		this.title = title;
		this.xName = xName;
		this.yName = yName;
		series.setName(title);
	}

	/**
	 * Adds a point to the graph.
	 * 
	 * @param x x value.
	 * @param y y value.
	 */
	public void addPoint(double x, double y) {
		series.getData().add(new XYChart.Data<Number, Number>(x, y));
	}

	/**
	 * Returns the graph title.
	 * 
	 * @return the graph title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the x axis label.
	 * 
	 * @return the x axis label.
	 */
	public String getXName() {
		return xName;
	}

	/**
	 * Returns the y axis label.
	 * 
	 * @return the y axis label.
	 */
	public String getYName() {
		return yName;
	}

	/**
	 * Returns an Series of all the points.
	 * 
	 * @return all points in the graph.
	 */
	public XYChart.Series<Number, Number> getPoints() {
		return series;
	}
}
