package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.prefs.BackingStoreException;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import user.User;
import user.User.Gender;
import junit.framework.TestCase;
import data.persistant.Persistent;

public class PersistentTest extends TestCase {
	private String personalFilePath = "/Users/SamSchofield/Desktop";
	private String fitrFilePath = personalFilePath + "/Fitr/";
	String username = "Stan";
	User user = new User(username, null, Gender.MALE, 70, 170, null, 60);

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
		Persistent.setFilePath(personalFilePath);
		assertEquals(fitrFilePath + username + "/." + username + ".fitr",Persistent.getProfileFilePath(user.getName()));
		
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
		Persistent.setupDirectory();
		assertEquals(true, new File(Persistent.getFilePath()).exists());
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	
	public void testNewUser() throws Exception {	
		Persistent.newUser(user);
		assertEquals(true, new File(fitrFilePath + "/" + username).exists());
	}
	
	public void testSetUser() throws Exception {
		Persistent.newUser(user);
		Persistent.setUser(user);
		assertEquals(user, Persistent.getCurrentUser());
	}
	
	public void testSetNonExistingUser() throws Exception {
		String username = "Fred";
		User u = new User(username, null, Gender.MALE, 70, 170, null, 60);
		Persistent.setUser(u);
		assertNotSame(u, Persistent.getCurrentUser());
	}

	public void testGetCurrentUser() throws Exception {
		Persistent.newUser(user);
		Persistent.setUser(user);
		assertEquals(user, Persistent.getCurrentUser());
	}

	public void testGetUsers() throws Exception {
		User u1 = new User("sam", null, Gender.FEMALE, 10, 10, null, 0);
		Persistent.clear();
		Persistent.newUser(user);
		Persistent.newUser(u1);
		Persistent.exit();
		
		
		assertEquals(2,Persistent.getUsers().size());
	}

	public void testGetUserNames() throws Exception {
		User u1 = new User("sam", null, Gender.FEMALE, 10, 10, null, 0);
		Persistent.clear();
		Persistent.newUser(user);
		Persistent.newUser(u1);
		Persistent.exit();
		
		assertEquals(true, Persistent.getUserNames().contains("sam"));
		assertEquals(true, Persistent.getUserNames().contains(username));
	}

	public void testInit() {
		Persistent.initialize();
	}
	
	public void testExit() throws BackingStoreException {
		Persistent.exit();
	}

	
}

