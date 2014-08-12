package tests;


import java.util.Calendar;

import junit.framework.TestCase;
import dataModel.DataPoint;

public class DataPointTest extends TestCase {
	private DataPoint p1;
	private DataPoint p2;
	
	@Override
    protected void setUp() throws Exception {
		super.setUp();
		
        Calendar c1 = new Calendar.Builder().setTimeOfDay(23, 42, 28).setDate(2005, 5, 10).build();
        Calendar c2 = new Calendar.Builder().setTimeOfDay(23, 43, 05).setDate(2005, 5, 10).build();
        
        p1 = new DataPoint(c1, 120, 30.2553368, -97.83891084, 50.0, null);
        p2 = new DataPoint(c2, 125, 30.25499189, -97.83913958, 51.0, p1);
    }
	
	public void testcalculateDistance() {
		assertEquals(0.04420, p2.getDistance()/1000, 1e-4);
		assertEquals(0.0, p1.getDistance(), 1e-10);
		
	}
	
	public void testcalculateSpeed() {
		assertEquals(1.194953411338526, p2.getSpeed(), 1e-4);
		assertEquals(0.0, p1.getSpeed(), 1e-10);
	}
}
