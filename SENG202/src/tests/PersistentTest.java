package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.prefs.BackingStoreException;

import junit.framework.TestCase;
import user.User;
import user.User.Gender;
import data.persistant.Persistent;

public class PersistentTest extends TestCase {
	
	// Set up some of the basics
	private String tempFilePath = System.getProperty("user.home");
	private String fitrFilePath = tempFilePath + "/Fitr/";
	String username = "Mocky"; 
	User user = User.mockUser();
	String userId =user.getUserId();
	/**
	 * tests that a valid file path can be set
	 */
	public void testSetFilePath() {
		try {
			Persistent.setFilePath(tempFilePath);
		} catch (FileNotFoundException e) {
			fail();
		}
	}

	/**
	 * Checks that an invalid filePath can't be set
	 */
	public void testSetInvalidFilePath() {
		Persistent.clear();
		try {
			Persistent.setFilePath("/NonExistantFilePath");
			fail();
		} catch (FileNotFoundException e) {
			//success
		}
	}

	/**
	 * test that the filePath is returned correctly
	 * @throws FileNotFoundException 
	 */
	public void testGetFilePath() throws FileNotFoundException {
		Persistent.clear();
		Persistent.setFilePath(tempFilePath);
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	/**
	 * tests that the user profile file path is correct
	 * @throws FileNotFoundException
	 */
	public void testGetProfileFilePath() throws FileNotFoundException {
		Persistent.setFilePath(tempFilePath);
		assertEquals(fitrFilePath + user.getUserId() + "/." + user.getUserId() + ".fitr",
				Persistent.getProfileFilePath(user.getUserId()));

	}

	/**
	 * tests method under valid filePath
	 * @throws FileNotFoundException
	 */
	public void testFilePathSet() throws FileNotFoundException {
		Persistent.clear();
		Persistent.setFilePath(tempFilePath);
		assertEquals(true, Persistent.filePathSet());
	}

	/**
	 * tests when no file path is set
	 */
	public void testFilePathSetNotSet() {
		Persistent.clear();
		assertEquals(false, Persistent.filePathSet());
	}

	/**
	 * tests that the Fitr directory is set up
	 * @throws FileNotFoundException
	 */
	public void testSetupDirectory() throws FileNotFoundException {
		Persistent.clear();
		Persistent.setFilePath(tempFilePath);
		Persistent.setupDirectory();
		assertEquals(true, new File(Persistent.getFilePath()).exists());
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	/**
	 * tests that the new users directory is being created
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public void testNewUser() throws FileNotFoundException {
		Persistent.deleteUser(Persistent.getCurrentUser());
		Persistent.newUser(user);
		assertTrue(new File(fitrFilePath + user.getUserId()).exists());
	}

	/**
	 * tests that an existing user can be set
	 * @throws Exception
	 */
	public void testSetUser() {
		Persistent.newUser(user);
		Persistent.setUser(user);
		assertEquals(user, Persistent.getCurrentUser());
	}

	/**
	 * tests that a non existing user can not be set
	 * @throws Exception
	 */
	public void testSetNonExistingUser() {
		String username = "Fred";
		Calendar c = new Calendar.Builder().setDate(1994, 0, 3).build();
		User u = new User(username, c, Gender.MALE, 70, 170, null, 60);
		Persistent.setUser(u);
		assertNotSame(u, Persistent.getCurrentUser());
	}

	/**
	 * tests that we are returning the correct current user
	 * @throws Exception
	 */
	public void testGetCurrentUser() {
		Persistent.newUser(user);
		Persistent.setUser(user);
		assertEquals(user, Persistent.getCurrentUser());
	}

	/**
	 * tests that get users is returning the right numbe of users
	 * @throws Exception
	 */
	public void testGetUsers() throws Exception {
		Calendar c = new Calendar.Builder().setDate(1994, 0, 3).build();
		User u1 = new User("Sam", c, Gender.MALE, 10, 10, null, 0);
		Persistent.clear();
		Persistent.newUser(user);
		Persistent.newUser(u1);
		Persistent.exit();
		assertEquals(2, Persistent.getUsers().size());
	}
	

	/** 
	 * test that it is correctly returning the all the appropriate user names
	 * @throws Exception
	 */
	public void testGetUserNames() throws Exception {
		Calendar c = new Calendar.Builder().setDate(1994, 0, 3).build();
		User u1 = new User("sam", c, Gender.MALE, 10, 10, null, 0);
		Persistent.clear();
		Persistent.newUser(user);
		Persistent.newUser(u1);
		Persistent.exit();

		assertEquals(true, Persistent.getUserNames().contains("sam"));
		assertEquals(true, Persistent.getUserNames().contains(username));
	}

	/**
	 * still need to work out an appropriate way to test this properly 
	 */
	public void testInit() {
		Persistent.initialize();
	}

	/**
	 * still need to work out an appropriate way to test this properly 
	 * @throws BackingStoreException
	 */
	public void testExit() throws BackingStoreException {
		Persistent.exit();
	}
	
	/**
	 * removes any files added by the testing
	 * Dont worry it checks that it is only deleting a Fitr folder now!
	 */
	public void testTearDown() {
		System.out.println("Removing temporary files");
		Persistent.deleteDirectory(new File(fitrFilePath));
	}

}
