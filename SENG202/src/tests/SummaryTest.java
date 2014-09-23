package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import data.loader.FileLoader;
import data.model.EventContainer;
import data.model.Summary;
import junit.framework.TestCase;

public class SummaryTest extends TestCase {
	
	private EventContainer eventContainer;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * sets up the event container loaded from the default csv file
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
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
		assertEquals(3, s.getNumberOfEvents());
	}
	
	/**
	 * checks that the constructor is correctly identifying the events between the start and end date
	 * @throws ParseException
	 */
	public void testCalculate() throws ParseException {
		Calendar startDate = new Calendar.Builder().setInstant(sdf.parse("11/04/2005")).build();
		Calendar endDate = new Calendar.Builder().setInstant(sdf.parse("11/04/2005")).build();
		Summary s = new Summary(eventContainer,startDate, endDate);
		
		System.out.println(s.getDurationString());
		System.out.println(s.maxCalories());
		System.out.println(s.getTotalDuration());
		System.out.println(s.getTotalDistance());
		getTotalCalories()
		
		
	}
	
	/**
	 * checks that the constructor is correctly identifying the events between the start and end date
	 * @throws ParseException
	 */
	public void testNullDates() throws ParseException {
		
		Summary s = new Summary(eventContainer,null, null);
		assertEquals(12, s.getNumberOfEvents());
	}
	
}
