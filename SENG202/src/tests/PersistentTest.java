package tests;

import java.util.prefs.Preferences;

import user.User;
import data.persistant.Persistent;
import junit.framework.TestCase;

public class PersistentTest extends TestCase {

	public void testSetFilePath() {
		Persistent.setFilePath("/Users/SamSchofield/Desktop");
	}

	public void testGetFilePath() {
		assertEquals("/Users/SamSchofield/Desktop/Fitr/", Persistent.getFilePath());
	}

	public void testGetProfileFilePath() {
		User u = new User("sam", null, null);
		assertEquals("/Users/SamSchofield/Desktop/Fitr/sam/sam.fitr", Persistent.getProfileFilePath(u.getName()));
	}

	public void testGetActivityFilePath() {
	}

	public void testFilePathSet() {
		Persistent.setFilePath("/Users/SamSchofield/Desktop");
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
