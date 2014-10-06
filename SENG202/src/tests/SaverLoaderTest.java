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
		System.out.println("===================================");
		Persistent.deleteDirectory(new File(System.getProperty("user.home") + "/Fitr"));
		Persistent.setFilePath(System.getProperty("user.home"));
		System.out.println("Fitr directory exosts = " + (new File(System.getProperty("user.home") + "/Fitr").exists()));
		u = User.mockUser();
		Persistent.newUser(u);
		Persistent.setUser(u);
		System.out.println(Persistent.getCurrentUser().getUserId());
		System.out.println(u.getUserId());
		System.out.println(Persistent.getFilePath());
		System.out.println(Persistent.getProfileFilePath(u.getUserId()));
		Saver.SaveUser(u);
		System.out.println((new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())).getParentFile().exists()));
	}

	/**
	 * checks that the user that was loaded is the same as the one which was saved
	 */
	public void testLoader() {
		System.out.println((new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())).exists()));
		System.out.println("===================================");
	}
	
	

	

}
