package tests;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;

import junit.framework.TestCase;
import user.User;
import data.loader.FileLoader;
import data.model.Event;
import data.model.EventContainer;
import data.model.Graph;
import data.model.GraphHelper;
import data.persistant.Persistent;

/**
 * 
 * @author simon
 *
 */
public class GraphHelperTest extends TestCase {
	private FileLoader fl = new FileLoader();
	private EventContainer ec;
	private Event walkInWoods;
	private Event runAroundBlock;
	private User u = User.mockUser();
	private Graph g;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		Persistent.setUser(u);
		
		Calendar c1 = new Calendar.Builder().setDate(2005, 03, 10).build();
		Calendar c2 = new Calendar.Builder().setDate(2005, 03, 12).build();
		Date d1 = c1.getTime();
		Date d2 = c2.getTime();
		fl.load();
		ec = fl.getEventContainer();
		
		LinkedList<Event> day1 = ec.getEvents(d1);
		LinkedList<Event> day2 = ec.getEvents(d2);
		walkInWoods = day1.get(0);		// has 33 data points
		runAroundBlock = day2.get(0);	// has 7 data points
	}
	
	@Test
	public void testGetStressLevelGraph() {
		g = GraphHelper.getStressLevelGraph(walkInWoods);
		/* there should be 31 points as a 3-point average graph will have
		   two less points than the total number of points */
		assertEquals(31, g.getPoints().getData().size());
		
		g = GraphHelper.getStressLevelGraph(runAroundBlock);
		assertEquals(7, g.getPoints().getData().size());
	}

}
