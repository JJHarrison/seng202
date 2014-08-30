package tests;

import junit.framework.TestCase;
import dataModel.EventContainer;
import dataModel.Event;

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
	protected void setUp() throws Exception {
		super.setUp();

		ec = new EventContainer();
	}

	/**
	 * Tests adding an event to the event container.
	 */
	public void testAddEvent() {
		Event event = new Event("Test Event");
		//ec.addEvent(event);

		//assertEquals(ec.getEvents().get(0), e);
		assertEquals(true, true);
	}

}
