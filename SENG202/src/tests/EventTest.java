package tests;

import java.util.ArrayList;
import java.util.Calendar;

import dataModel.Event;
import dataModel.DataPoint;
import junit.framework.TestCase;

public class EventTest extends TestCase {
	private Event e;
	private ArrayList<DataPoint> points;
	private Calendar c1;
	private Calendar c2;
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        
        e = new Event("My Event");
        
        // set start and finish times 3 minutes apart
        c1 = new Calendar.Builder().setTimeOfDay(23, 42, 28).setDate(2005, 5, 10).build();
        c2 = new Calendar.Builder().setTimeOfDay(23, 45, 28).setDate(2005, 5, 10).build();
        e.setStartTime(c1);
        e.setFinishTime(c2);
        
        // set up data points
        points = new ArrayList<DataPoint>();
        Calendar c3 = new Calendar.Builder().setTimeOfDay(23, 42, 28).setDate(2005, 5, 10).build();
        Calendar c4 = new Calendar.Builder().setTimeOfDay(23, 43, 05).setDate(2005, 5, 10).build();
        DataPoint p1 = new DataPoint(c3, 120, 30.2553368, -97.83891084, 50.0, null);
        DataPoint p2 = new DataPoint(c4, 125, 30.25499189, -97.83913958, 51.0, p1);
        points.add(p1);
        points.add(p2);
        e.addDataPoint(p1);
        e.addDataPoint(p2);
        
        System.out.println("setup");
        
    }
	
	public void testGetEventName() {
		assertEquals(e.getEventName(), "My Event");
	}
	
	public void testGetStartTime() {
		assertEquals(e.getStartTime(), c1);
	}
	
	public void testGetFinishTime() {
		//assertEquals(e.getFinishTime(), c2);
	}
	
	public void testGetDataPoints() {
		assertEquals(e.getDataPoints(), points);
	}
}
