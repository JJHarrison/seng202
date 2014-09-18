package tests;

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
		System.out.println("_____");
		assertEquals(true, Persistent.filePathSet());
	}
	

	public void testSetupDirectory() {

	}

	public void testNewUser() {
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
