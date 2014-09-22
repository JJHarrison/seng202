package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.prefs.BackingStoreException;

import junit.framework.TestCase;
import user.User;
import user.User.Gender;
import data.persistant.Persistent;

public class PersistentTest extends TestCase {
	
	/*
	 * The personalFilePath needs to be changed for the computer it is being run on
	 * Will work on trying to make this system independent 
	 * NOTE: Tests are a bit temperamental, due to the persistent nature of the Persistent class
	 * To fix delete the Fitr directory and run the persistent main which will clear the preferences 
	 */
	private String personalFilePath = "/Users/SamSchofield/Desktop";
	private String fitrFilePath = personalFilePath + "/Fitr/";
	String username = "Mocky";
	User user = User.mockUser();

	/**
	 * tests that a valid file path can be set
	 * 
	 * @throws FileNotFoundException
	 */
	public void testSetFilePath() throws FileNotFoundException {
		Persistent.setFilePath(personalFilePath);
	}

	/**
	 * tests to see if an invalid file path can be set
	 */
	public void testSetInvalidFilePath() {
		Throwable e = null;
		Persistent.clear();
		try {
			Persistent.setFilePath(personalFilePath + "/NonExistantFilePath");
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof FileNotFoundException);
	}

	/**
	 * test that the filePath is returned correctly
	 * @throws FileNotFoundException 
	 */
	public void testGetFilePath() throws FileNotFoundException {
		Persistent.clear();
		Persistent.setFilePath(personalFilePath);
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	/**
	 * tests that the user profile file path is correct
	 * 
	 * @throws FileNotFoundException
	 */
	public void testGetProfileFilePath() throws FileNotFoundException {
		Persistent.setFilePath(personalFilePath);
		assertEquals(fitrFilePath + user.getUserId() + "/." + user.getUserId() + ".fitr",
				Persistent.getProfileFilePath(user.getUserId()));

	}

	/**
	 * tests when a valid filePath is set
	 * 
	 * @throws FileNotFoundException
	 */
	public void testFilePathSet() throws FileNotFoundException {
		Persistent.clear();
		Persistent.setFilePath(personalFilePath);
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
	 * 
	 * @throws FileNotFoundException
	 */
	public void testSetupDirectory() throws FileNotFoundException {
		Persistent.setFilePath(personalFilePath);
		Persistent.setupDirectory();
		assertEquals(true, new File(Persistent.getFilePath()).exists());
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	/**
	 * tests that the new users directory is being created
	 * @throws Exception
	 */
	public void testNewUser() throws Exception {
		Persistent.clear();
		Persistent.newUser(user);
		assertEquals(true, new File(fitrFilePath + "/" + user.getUserId()).exists());
	}

	/**
	 * tests that an existing user can be set
	 * @throws Exception
	 */
	public void testSetUser() throws Exception {
		Persistent.newUser(user);
		Persistent.setUser(user);
		assertEquals(user, Persistent.getCurrentUser());
	}

	/**
	 * tests that a non existing user can not be set
	 * @throws Exception
	 */
	public void testSetNonExistingUser() throws Exception {
		String username = "Fred";
		User u = new User(username, null, Gender.MALE, 70, 170, null, 60);
		Persistent.setUser(u);
		assertNotSame(u, Persistent.getCurrentUser());
	}

	/**
	 * tests that we are returning the correct current user
	 * @throws Exception
	 */
	public void testGetCurrentUser() throws Exception {
		Persistent.newUser(user);
		Persistent.setUser(user);
		assertEquals(user, Persistent.getCurrentUser());
	}

	/**
	 * tests that get users is returning the right numbe of users
	 * @throws Exception
	 */
	public void testGetUsers() throws Exception {
		User u1 = new User("sam", null, Gender.MALE, 10, 10, null, 0);
		Persistent.clear();
		Persistent.newUser(user);
		Persistent.newUser(u1);
		Persistent.exit();

		assertEquals(2, Persistent.getUsers().size());
	}
	
	/**
	 * testing that the UserID has been incremented appropriately for the number of users that have been added
	 */
	public void testUserID() {
		assertEquals(1, Persistent.getUserID());	
	}
	
	/**
	 * userID shouldn't be incremented if an already existing user is being added
	 */
	public void testExistingUserID() throws Exception {
		Persistent.newUser(user);
		assertEquals(1, Persistent.getUserID());
		
	}

	/** 
	 * test that it is correctly returning the all the appropriate user names
	 * @throws Exception
	 */
	public void testGetUserNames() throws Exception {
		User u1 = new User("sam", null, Gender.MALE, 10, 10, null, 0);
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

}
