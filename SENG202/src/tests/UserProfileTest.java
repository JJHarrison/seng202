package tests;

import junit.framework.TestCase;
import user.User;
import user.User.Gender;

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
		u = User.mockUser();
		

	}

	/**
	 * Tests getName function to make sure it returns the right name
	 */
	public void testGetName() {
		assertEquals("Mocky", u.getName());
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
		assertEquals(1.9, u.getHeight());
	}

	/**
	 * Tests the getAge function to make sure it returns the right age
	 */
	public void testGetAge() {
		assertEquals(53, u.getAge());
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
		u.setHeight(2.0);
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

}
