package data.model;

import java.util.ArrayList;

public class Graph {
	private String title;
	private String xName;
	private String yName;
	ArrayList<double[]> points;
	
	public Graph(String title, String xName, String yName) {
		this.title = title;
		this.xName = xName;
		this.yName = yName;
		this.points = new ArrayList<double[]>();
	}
	
	public void addPoint(double x, double y) {
		double[] p = {x, y};
		this.points.add(p);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getXName() {
		return xName;
	}
	
	public String getYName() {
		return yName;
	}
	
	public ArrayList<double[]> getPoints() {
		return points;
	}
}
