package tests;

import junit.framework.TestCase;
import dataModel.EventContainer;
import dataModel.Event;

public class EventContainerTest extends TestCase {
	
	private EventContainer ec;

	protected void setUp() throws Exception {
		super.setUp();
		
		ec = new EventContainer();
	}
	
	public void testAddEvent() {
		Event e = new Event("Test Event");
		ec.addEvent(e);
		
		assertEquals(ec.getEvents().get(0), e);
	}

}
