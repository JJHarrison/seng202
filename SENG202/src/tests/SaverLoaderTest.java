package tests;

import java.io.File;
import java.io.FileNotFoundException;

import junit.framework.TestCase;
import user.User;
import data.persistant.Loader;
import data.persistant.Persistent;
import data.persistant.Saver;

public class SaverLoaderTest extends TestCase {
	static User u;
	
	/**
	 * Sets up the test dataPoints to be tested
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Persistent.setFilePath(System.getProperty("user.home"));
		u = User.mockUser();
	}
	
	/**
	 * Tests saving a user to json 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public void testSaveUser() throws FileNotFoundException {
		Saver.SaveUser(u);
		assertTrue(new File(Persistent.getProfileFilePath(u.getUserId())).exists());
	}

	/**
	 * checks that the user that was loaded is the same as the one which was saved
	 */
	public void testLoader() {
		Saver.SaveUser(u);
		User loadedUser = Loader.loadUserProfile(new File(Persistent.getProfileFilePath(u.getUserId())));
		assertEquals(u, loadedUser);
	}
	
	/**
	 * removes any files which were created
	 */
	protected void tearDown() {
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
	}
	

}
