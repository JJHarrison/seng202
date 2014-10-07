package tests;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import user.User;
import user.User.Gender;
import data.loader.FileLoader;
import data.persistant.Persistent;

/**
 * Tests the functionality of the UserProfile class
 * 
 * @author Fitr.Team
 */
public class UserProfileTest extends TestCase {
	User u;

	/**
	 * Sets up the profile to be tested.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Persistent.setFilePath(System.getProperty("user.home"));
		u = User.mockUser();
	}

	/**
	 * Tests getName function to make sure it returns the right name
	 */
	public void testGetName() {
		assertEquals("Mocky", u.getName());
	}
	
	/**
	 * Tests setName function to make sure it sets the name correctly
	 */
	public void testSetName(){
		String name = "TestName";
		u.setName(name);
		assertEquals(name, u.getName());
	}
		
	/**
	 * Tests getWeight function to make sure it returns the right weight
	 */
	public void testGetWeight() {
		assertEquals(85.3, u.getWeight());
	}

	/**
	 * Tests getHeight function to make sure it returns the right height
	 */
	public void testGetHeight() {
		assertEquals(190.0, u.getHeight());
	}

	/**
	 * Tests the getAge function to make sure it returns the right age
	 */
	public void testGetAge() {
		assertEquals(53, u.getAge());
	}
	
	/**
	 * Tests the getDateOfBIrth function to make sure it returns the right date
	 */
	public void testDateOfBirth(){
		assertEquals(new GregorianCalendar(1961, 8, 9), u.getDateofBirth());
	}
	
	/**
	 * Tests the setDateOfBirth funtion to make sure it sets the correct date
	 */
	public void testSetDateOfBirth(){
		Calendar c = new GregorianCalendar(1988, 13, 3);
		u.setDateofBirth(c);
		assertEquals(c, u.getDateofBirth());
		
	}
	

	/**
	 * Tests that the Gender enum has a value
	 */
	public void testGetGender() {
		assertEquals(Gender.MALE, u.getGender());
	}

	/**
	 * Tests the BMI calculation
	 */
	public void testGetBMI() {
		double bmi = u.getBMI();
		assertTrue(bmi > 23);
		assertTrue(bmi < 24);
	}

	/**
	 * Tests if changing the weight automatically updates the BMI of the user
	 */
	public void testChangeWeight() {
		u.setWeight(162.4);
		double bmi = u.getBMI();
		assertTrue(bmi > 44);
		assertTrue(bmi < 45);
	}

	/**
	 * Tests if changing the height automatically updates the BMI of the user
	 */
	public void testChangeHeight() {
		u.setHeight(200);
		double bmi = u.getBMI();
		assertTrue(bmi > 21);
		assertTrue(bmi < 22);
	}

	/**
	 * Tests the toString override
	 */
	public void testToString() {
		assertEquals("Mocky", u.toString());
	}
	
	/**
	 * Tests that tachycardia warnings are being generated properly.
	 */
	public void testHasTachycardia() {
		u.setRestingHeartRate(150);
		assertTrue(u.hasTachycardia());
		u.setRestingHeartRate(80);
		assertFalse(u.hasTachycardia());
	}

	
	/**
	 * Tests that bradycardia warnings are being generated properly.
	 */
	public void testHasBradycardia() {
		u.setRestingHeartRate(50);
		assertTrue(u.hasBradycardia());
		u.setRestingHeartRate(80);
		assertFalse(u.hasTachycardia());
	}
	
	/**
	 * Checks that events arent duplicated when adding the same events to a user 
	 * twice 
	 */
	public void testAddSameEvents() {
		int numEvent = u.getEvents().getAllEvents().size();
		FileLoader fl = new FileLoader();
		fl.load();
		// add an event container which contains evens that have already been added
		u.addEvents(fl.getEventContainer());
		// no new events were added by the event container so the size should be the same
		assertEquals(numEvent, u.getEvents().getAllEvents().size());
	}
	
	/**
	 * removes any files which were created
	 */
	protected void tearDown() {
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
	}

}
