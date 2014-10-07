package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import user.User;
import data.loader.FileLoader;
import data.model.DataPoint;
import data.model.Event;
import data.model.EventContainer;
import data.persistant.Persistent;

;

/**
 * Tests for the event container class.
 * 
 * @author Simon
 */
public class EventContainerTest extends TestCase {

	private EventContainer ec;
	private EventContainer testEventContainer;
	private static User u;

	/**
	 * Sets up the event container that will be tested.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Persistent.setFilePath(System.getProperty("user.home"));
		u = User.mockUser();
		ec = new EventContainer();
		
		FileLoader fl = new FileLoader();
		fl.load();
		testEventContainer = fl.getEventContainer();
		
	}

	/**
	 * Tests adding an event to the event container.
	 */
	public void testAddEvent1() {
		Persistent.setUser(u);
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
	
	/**	
	 *  tests that all the events are retrieved
	 */
	public void testGetAllEvents() {
		assertEquals(12, testEventContainer.getAllEvents().size());
	}
	
	/**
	 * tests that we are getting all events in a week
	 */
	public void testGetWeekEvents() {
		Calendar date = new Calendar.Builder().setDate(2005, 3, 11).build();
		assertEquals(3, testEventContainer.getWeekEvents(date.getTime()).size());
	}
	
	/**
	 * test that the last date is correct
	 */
	public void testGetLastDate() {
		Calendar date = new Calendar.Builder().setDate(2006, 0, 2).build();
		assertEquals(date.getTime(), testEventContainer.getLastDate());
	}
	
	/**
	 * removes any files which were created
	 */
	protected void tearDown() {
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
	}

}
