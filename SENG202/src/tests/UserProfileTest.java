package tests;

import java.util.GregorianCalendar;

import junit.framework.TestCase;
import userModel.UserProfile;
import userModel.UserProfile.Gender;

/**
 * Tests the functionality of the UserProfile class
 * @author Fitr.Team
 */
public class UserProfileTest extends TestCase {
	
	private UserProfile john;
	
	/**
	 * Sets up the profile to be tested.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		// John Key's actual birthday
		GregorianCalendar dob = new GregorianCalendar(1961, 8, 9);
		john = new UserProfile("John Key", dob, Gender.MALE);
		john.setWeight(81.2);
		john.setHeight(1.8);
		
	}
	
	/**
	 * Tests the getAge function to make sure it returns the right age
	 */
	public void testGetAge() {
		assertEquals(john.getAge(), 53);
	}
	
	/**
	 * Tests the BMI calculation 
	 */
	public void testGetBMI() {
		double bmi = john.getBMI();
		assertTrue(bmi > 24.9);
		assertTrue(bmi < 25.1);
	}
	
	/**
	 * Tests if changing the weight automatically updates the BMI of the user
	 */
	public void testChangeWeight() {
		john.setWeight(162.4);
		double bmi = john.getBMI();
		assertTrue(bmi > 49.8);
		assertTrue(bmi < 50.2);
	}
	
	/**
	 * Tests if changing the height automatically updates the BMI of the user
	 */
	public void testChangeHeight() {
		john.setHeight(2.0);
		double bmi = john.getBMI();
		assertTrue(bmi > 20);
		assertTrue(bmi < 21);
	}

}
