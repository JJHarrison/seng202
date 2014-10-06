package tests;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import user.User;
import junit.framework.TestCase;
import data.model.DataPoint;
import data.persistant.Persistent;

/**
 * Tests the functionality of the DataPoint class
 * 
 * @author Fitr.Team
 */
public class DataPointTest extends TestCase {
	private static DataPoint p1;
	private static DataPoint p2;
	private static User u;

	/**
	 * Sets up the test dataPoints to be tested
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Persistent.setFilePath(System.getProperty("user.home"));
		u = User.mockUser();
		
		Calendar c1 = new GregorianCalendar(2005, // Year
				5, // Month
				10, // Day
				23, // Hour
				42, // Minute
				28); // Second

		Calendar c2 = new GregorianCalendar(2005, // Year
				5, // Month
				10, // Day
				23, // Hour
				43, // Minute
				5); // Second

		// p1 = new DataPoint(c1, 120, 30.2553368, -97.83891084, 50.0, null);
		p1 = new DataPoint.Builder().date(c1).heartRate(120)
				.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
				.prevDataPoint(null).build();
		// p2 = new DataPoint(c2, 125, 30.25499189, -97.83913958, 51.0, p1);
		p2 = new DataPoint.Builder().date(c2).heartRate(125)
				.latitude(30.25499189).longitude(-97.83913958).altitude(51.0)
				.prevDataPoint(p1).build();
	}

	/**
	 * Test for calculating the distance between two points.
	 */
	public void testcalculateDistance() {
		assertEquals(0.04420, p2.getDistance() / 1000, 1e-4);
		assertEquals(0.0, p1.getDistance(), 1e-10);

	}

	/**
	 * Tests the getDate function.
	 */
	public void testGetDate() {
		Calendar c1 = new GregorianCalendar(2005, 5, 10, 23, 42, 28);
		assertEquals(c1, p1.getDate());
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
	public void testDateString() {
		assertEquals("10/06/2005", p1.getDateString());
	}

	/**
	 * Test for returning the time represented as a string.
	 */
	public void testTimeString() {
		assertEquals("23:42:28", p1.getTimeString());
	}
	
	/** 
	 * removes temp files that were created
	 */
	public void testRemoveTemp() {
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
	}
}
