package tests;

import java.io.File;

import user.User;
import junit.framework.TestCase;
import data.persistant.Persistent;

public class PersistentTest extends TestCase {
	private String personalFilePath = "/Users/SamSchofield/Desktop";
	private String fitrFilePath = personalFilePath + "/Fitr/";

	public void testSetFilePath() {
		Persistent.setFilePath(personalFilePath);
	}

	public void testGetFilePath() {
		assertEquals(fitrFilePath, Persistent.getFilePath());
	}

	public void testGetProfileFilePath() {
		String username = "John";
		User u = new User(username, null, null, 0, 0, null, 0);
		assertEquals(fitrFilePath + username + "/" + username + ".fitr",Persistent.getProfileFilePath(u.getName()));
		 
	}

	public void testFilePathSet() {
		Persistent.clear();
		Persistent.setFilePath(personalFilePath);
		assertEquals(true, Persistent.filePathSet());
	}
	
	public void testFilePathSetNotSet() {
		Persistent.clear();
		assertEquals(false, Persistent.filePathSet());
	}
	
	public void testFilePathSetWrong() {
		Persistent.clear();
		Persistent.setFilePath(personalFilePath + "/1");
		assertEquals(false, Persistent.filePathSet());
	}
	
	public void testSetupDirectory() {
		System.out.println(personalFilePath);
		Persistent.setFilePath(personalFilePath);
		assertEquals(true, new File(Persistent.getFilePath()).exists());
	}

	
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

}
