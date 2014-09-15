package tests;

import java.util.GregorianCalendar;

import junit.framework.TestCase;
import data.model.Event;
import data.model.EventContainer;

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
	Event event = new Event("Test Event");
	event.setStartTime(new GregorianCalendar());
	ec.addEvent(event);

	assertEquals(ec.getEvents(event.getStartTime().getTime()).get(0), event);
	assertEquals(true, true);
    }

}
