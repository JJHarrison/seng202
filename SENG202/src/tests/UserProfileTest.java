package tests;

import java.util.GregorianCalendar;

import junit.framework.TestCase;
import userModel.UserProfile;
import userModel.UserProfile.Gender;

public class UserProfileTest extends TestCase {
	
	private UserProfile john;

	protected void setUp() throws Exception {
		super.setUp();
		
		// John Key's actual birthday
		
		john = new UserProfile("John Key", new GregorianCalendar(), Gender.MALE);
		john.setWeight(81.2);
		john.setHeight(1.8);
	}
	
	public void testGetAge() {
		//assertEquals(john.getAge(), 53);
	}
	
	public void testGetBMI() {
		double bmi = john.getBMI();
		assertTrue(bmi > 24.9);
		assertTrue(bmi < 25.1);
	}
	
	public void testChangeWeight() {
		john.setWeight(162.4);
		double bmi = john.getBMI();
		assertTrue(bmi > 49.8);
		assertTrue(bmi < 50.2);
	}
	
	public void testChangeHeight() {
		john.setHeight(2.0);
		double bmi = john.getBMI();
		assertTrue(bmi > 20);
		assertTrue(bmi < 21);
	}

}
