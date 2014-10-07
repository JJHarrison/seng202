package tests;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.TestCase;
import user.User;
import data.loader.FileLoader;
import data.model.EventContainer;
import data.model.Summary;
import data.persistant.Persistent;

public class SummaryTest extends TestCase {
	
	private EventContainer eventContainer;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static User u;
	
	/**
	 * sets up the event container loaded from the default csv file
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Persistent.setFilePath(System.getProperty("user.home"));
		u = User.mockUser();
		Persistent.newUser(u);
		Persistent.setUser(u);
		FileLoader fl = new FileLoader();
		fl.load();
		eventContainer = fl.getEventContainer();
	}
	
	/**
	 * checks that the constructor is correctly identifying the events between the start and end date
	 * @throws ParseException
	 */
	public void testSummary() throws ParseException {
		Calendar startDate = new Calendar.Builder().setInstant(sdf.parse("10/04/2005")).build();
		Calendar endDate = new Calendar.Builder().setInstant(sdf.parse("12/04/2005")).build();
		Summary s = new Summary(eventContainer,startDate, endDate);
		// there are 3 events between the specified dates
		assertEquals(3, s.getNumberOfEvents());
	}
	
	/**
	 * checks that the constructor is correctly identifying the events between the start and end date
	 * @throws ParseException
	 */
	public void testCalculate() throws ParseException {
		Summary s = new Summary(eventContainer,null, null);
		
		/*
		 * this part is to test that there were no errors in the formatting 
		 */
		System.out.println("totals:");
		System.out.println(s.getTotalDuration());
		System.out.println(s.getTotalDistance());
		System.out.println(s.getTotalCalories());
		
		System.out.println("\nMaximums:");
		System.out.println(s.getMaxDuration());
		System.out.println(s.maxCalories());
		System.out.println(s.getMaxDistance());
		System.out.println(s.maxCalories());
		System.out.println(s.maxSpeed());
		System.out.println(s.maxHeartRate());
		
		
	}
	
	/**
	 * checks that the constructor is correctly identifying the events between the start and end date
	 * @throws ParseException
	 */
	public void testNullDates() throws ParseException {
		// there should be 12 events in total 
		Summary s = new Summary(eventContainer,null, null);
		assertEquals(12, s.getNumberOfEvents());
	}
	
	
	/**
	 * removes any files which were created
	 */
	protected void tearDown() {
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
	}
	
}
