package tests;


import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import dataModel.DataPoint;
/**
 * Tests the functionality of the DataPoint class
 * @author Fitr.Team
 */
public class DataPointTest extends TestCase {
	private DataPoint p1;
	private DataPoint p2;
	
	/**
	 * Sets up the test dataPoints to be tested
	 */
	@Override
    protected void setUp() throws Exception {
		super.setUp();
		
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
				43, // Minute
				5); // Second

        
        p1 = new DataPoint(c1, 120, 30.2553368, -97.83891084, 50.0, null);
        p2 = new DataPoint(c2, 125, 30.25499189, -97.83913958, 51.0, p1);
    }
	
	/**
	 * Test for calculating the distance between two points.
	 */
	public void testcalculateDistance() {
		assertEquals(0.04420, p2.getDistance()/1000, 1e-4);
		assertEquals(0.0, p1.getDistance(), 1e-10);
		
	}
	
	/**
	 * Test for calculating the speed between two points.
	 */
	public void testcalculateSpeed() {
		assertEquals(1.194953411338526, p2.getSpeed(), 1e-4);
		assertEquals(0.0, p1.getSpeed(), 1e-10);
	}
	
	/**
	 * Test for returning the date represented as a string.
	 */
	public void testDateString(){
		assertEquals("10/06/2005", p1.getDateString());
	}
	
	/**
	 * Test for returning the time represented as a string.
	 */
	public void testTimeString(){
		assertEquals("23:42:28", p1.getTimeString());
	}
}
