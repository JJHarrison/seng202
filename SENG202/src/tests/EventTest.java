package tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import user.User;
import junit.framework.TestCase;
import data.model.DataPoint;
import data.model.Event;
import data.model.EventContainer;
import data.persistant.Persistent;

/**
 * Tests for the event class functionality
 * 
 * @author James, Sam
 * 
 */
public class EventTest extends TestCase {

	private Event e;
	private ArrayList<DataPoint> points;
	private DataPoint p1;
	private DataPoint p2;

	private User user;

	/**
	 * Sets up the events to be tested
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		user = User.mockUser();
		Calendar c = new GregorianCalendar(2000, 01, 01);
		user.setDateofBirth(c);
		Persistent.setUser(user);

		// set up data points
		points = new ArrayList<DataPoint>();
		Calendar c3 = new GregorianCalendar(2005, // Year
				5, // Month
				10, // Day
				23, // Hour
				42, // Minute
				28); // Second
		Calendar c4 = new GregorianCalendar(2005, // Year
				5, // Month
				10, // Day
				23, // Hour
				43, // Minute
				5); // Second

		p1 = new DataPoint.Builder().date(c3).heartRate(59)
				// don't change hr
				.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
				.prevDataPoint(null).build();

		p2 = new DataPoint.Builder().date(c4).heartRate(195)
				// don't change hr
				.latitude(30.25499189).longitude(-97.83913958).altitude(51.0)
				.prevDataPoint(p1).build();

		points.add(p1);
		points.add(p2);
		e = new Event("My Event", points);
	}

	/**
	 * Tests the getEventName function to make sure it returns the correct name
	 * of the event
	 */
	public void testGetEventName() {
		assertEquals("My Event", e.getEventName());
	}

	public void testAverageHR() {
		assertEquals((59 + 195) / 2, e.getAverageHeartRate());
	}

	public void testMaxHR() {
		assertEquals(195, e.getMaxHeartRate());
	}

	/**
	 * Tests the getDataPoints function to make sure it returns the dataPoints
	 * of the event.
	 */
	public void testGetDataPoints() {
		assertEquals(e.getDataPoints(), points);
	}

	/**
	 * Tests the getPointString method
	 */
	public void testGetPointString() {
		assertEquals(e.getPointString(points.get(0)), "30.2553368,-97.83891084");
	}

	/**
	 * Tests the getPathString method
	 */
	public void testGetPathString() {
		assertEquals(e.getPathString(),
				"30.2553368,-97.83891084|30.25499189,-97.83913958");
	}

	// run this last or you will cause the other test to fail
	/**
	 * Tests the addDataPoint function.
	 */
	public void testAddDataPoint() {
		assertEquals(p2, points.get(points.size() - 1));
		points.add(p1);
		assertEquals(p1, points.get(points.size() - 1));
	}

	public void testTachycardia() {
		EventContainer ec = new EventContainer();
		ec.addEvent(e);
		// assertEquals(e.hasTachycardia(), false); // 14 years old
		// c = new GregorianCalendar(1950, 01, 01);
		// user.setDateofBirth(c);
		// assertEquals(e.hasTachycardia(), true); // 64 years old
	}

	public void testBradycardia() {
		// assertEquals(e.hasBradycardia(), true);
	}
}
