package tests;

import dataModel.Event;
import junit.framework.TestCase;

public class EventTest extends TestCase {
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        Event e = new Event("My Event");
        //e.setStartTime();
        //e.setFinishTime();
        //e.addDataPoint();
        //e.addDataPoint();
        System.out.println("setup");
        
    }
	
	public void testGetEventName() {
		assertEquals(e.getEventName(), "My Event");
	}
	
	public void testGetStartTime() {
		//assertEquals();
	}
	
	public void testGetFinishTime() {
		//assertEquals();
	}
	
	public void testGetDataPoints() {
		//assertEquals();
	}
}
