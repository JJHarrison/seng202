package tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
		Calendar c1 = new GregorianCalendar(
				2005, // Year 
				5, // Month
				10, // Day
				23, // Hour
				42, // Minute
				28); // Second
		Calendar c2 = new GregorianCalendar(
				2005, // Year 
				5, // Month
				10, // Day
				23, // Hour
				45, // Minute
				28); // Second
		
        e.setStartTime(c1);
        e.setFinishTime(c2);
        
        // set up data points
        points = new ArrayList<DataPoint>();
		Calendar c3 = new GregorianCalendar(
				2005, // Year 
				5, // Month
				10, // Day
				23, // Hour
				42, // Minute
				28); // Second
		Calendar c4 = new GregorianCalendar(
				2005, // Year 
				5, // Month
				10, // Day
				23, // Hour
				43, // Minute
				5); // Second
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
	
	
	public void testGetDataPoints() {
		assertEquals(e.getDataPoints(), points);
	}
}
