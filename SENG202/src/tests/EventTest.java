package tests;

import java.util.ArrayList;
import java.util.Calendar;

import dataModel.Event;
import dataModel.DataPoint;
import junit.framework.TestCase;

public class EventTest extends TestCase {
	private Event e;
	private ArrayList<DataPoint> points;
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        
        e = new Event("My Event");
        
        // set start and finish times 3 minutes apart
        Calendar c1 = new Calendar.Builder().setTimeOfDay(23, 42, 28).setDate(2005, 5, 10).build();
        Calendar c2 = new Calendar.Builder().setTimeOfDay(23, 45, 28).setDate(2005, 5, 10).build();
        e.setStartTime(c1);
        e.setFinishTime(c2);
        
        points = new ArrayList();
        //e.addDataPoint();
        //e.addDataPoint();
        System.out.println("setup");
        
    }
	
	public void testGetEventName() {
		assertEquals(e.getEventName(), "My Event");
	}
	
	public void testGetStartTime() {
		//assertEquals();
	}
	
	public void testGetFinishTime() {
		//assertEquals();
	}
	
	public void testGetDataPoints() {
		//assertEquals();
	}
}
