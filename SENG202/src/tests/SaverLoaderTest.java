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
	 * Tests saving a user to json 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public void testSaveUser() throws FileNotFoundException {
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
		Persistent.setFilePath(System.getProperty("user.home"));
		u = User.mockUser();
		Persistent.newUser(u);
		Persistent.setUser(u);
		Saver.SaveUser(u);
		System.out.println((new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())).exists()));
	}

	/**
	 * checks that the user that was loaded is the same as the one which was saved
	 */
	public void testLoader() {
		System.out.println("=========================");
		Saver.SaveUser(u);
		System.out.println(u);
		System.out.println(Persistent.getCurrentUser());
		System.out.println((new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())).exists()));
		User newUser = Loader.loadUserProfile(new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())));	
		System.out.println("--->" + newUser);
		assertEquals(u, newUser);
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
		System.out.println("=========================");
	}
	
	

	

}
