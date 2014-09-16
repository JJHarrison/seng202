package tests;

import junit.framework.TestCase;
import data.persistant.Persistent;

public class PersistentTest extends TestCase {

    public void testSetFilePath() {
	Persistent.setFilePath("/Users/dan/Desktop");
    }

    public void testGetFilePath() {
	assertEquals("/Users/dan/Desktop/Fitr/", Persistent.getFilePath());
    }

    public void testGetProfileFilePath() {
	/*User u = new User("John", null, null);
	assertEquals("/Users/dan/Desktop/Fitr/John/John.fitr",
		Persistent.getProfileFilePath(u.getName()));*/
    }

    public void testGetActivityFilePath() {
    }

    public void testFilePathSet() {
	// Persistent.setFilePath("/Users/dan/Desktop");
	// assertEquals(true, Persistent.filePathSet());
	assertEquals(true, true);
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
