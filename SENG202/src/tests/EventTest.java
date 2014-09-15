package tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import data.model.DataPoint;
import data.model.Event;

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

    /**
     * Sets up the events to be tested
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();

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

	p1 = new DataPoint.Builder().date(c3).heartRate(120)
		.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
		.prevDataPoint(null).build();

	p2 = new DataPoint.Builder().date(c4).heartRate(125)
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
	assertEquals((120 + 125) / 2, e.getAverageHeartRate());
    }

    public void testMaxHR() {
	assertEquals(125, e.getMaxHeartRate());
    }

    /**
     * Tests the getDataPoints function to make sure it returns the dataPoints
     * of the event.
     */
    public void testGetDataPoints() {
	assertEquals(e.getDataPoints(), points);
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

}
