package tests;

import java.io.File;
import java.io.FileNotFoundException;

import user.User;
import junit.framework.TestCase;
import data.persistant.Persistent;

public class PersistentTest extends TestCase {
	private String personalFilePath = "/Users/SamSchofield/Desktop";
	private String fitrFilePath = personalFilePath + "/Fitr/";

	/**
	 * tests that a valid file path can be set
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
			Persistent.setFilePath(personalFilePath + "/alduh");
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof FileNotFoundException);
	}

	/**
	 * test that the filePath is returned correctly 
	 */
	public void testGetFilePath() {
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	/**
	 * tests that the user profile file path is correct
	 * @throws FileNotFoundException
	 */
	public void testGetProfileFilePath() throws FileNotFoundException {
		String username = "John";
		Persistent.setFilePath(personalFilePath);
		User u = new User(username, null, null, 0, 0, null, 0);
		assertEquals(fitrFilePath + username + "/" + username + ".fitr",Persistent.getProfileFilePath(u.getName()));
		 
	}
	
	/**
	 * tests when a valid filepath is set
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
	 * @throws FileNotFoundException
	 */
	public void testSetupDirectory() throws FileNotFoundException {
		Persistent.setFilePath(personalFilePath);
		assertEquals(true, new File(Persistent.getFilePath()).exists());
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	/*
	public void testNewUser() throws Exception {	
		String username = "Stan";
		User u = new User(username, null, null, 0, 0, null, 0);
		Persistent.newUser(u);
		assertEquals(true, new File(fitrFilePath + "/" + username).exists());
	}
	
	public void testSetUser() {
	}

	public void testGetCurrentUser() {
	}

	public void testGetUsers() {
	}

	public void testGetUserNames() {
	}

	public void testInit() {
	}
*/
	
}

