package tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import data.model.Event;
import data.model.EventContainer;
import data.model.DataPoint;;

/**
 * Tests for the event container class.
 * 
 * @author Fitr.Team
 */
public class EventContainerTest extends TestCase {

	private EventContainer ec;

	/**
	 * Sets up the event container that will be tested.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		ec = new EventContainer();
	}

	/**
	 * Tests adding an event to the event container.
	 */
	public void testAddEvent() {
		ArrayList<DataPoint> points = new ArrayList<DataPoint>();
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

		DataPoint p1 = new DataPoint.Builder().date(c3).heartRate(120)
				.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
				.prevDataPoint(null).build();

		DataPoint p2 = new DataPoint.Builder().date(c4).heartRate(125)
				.latitude(30.25499189).longitude(-97.83913958).altitude(51.0)
				.prevDataPoint(p1).build();

		points.add(p1);
		points.add(p2);
		
		Event event = new Event("Test Event", points);
		ec.addEvent(event);

		assertEquals(ec.getEvents(event.getStartTime().getTime()).get(0), event);
		assertEquals(true, true);
	}

}
