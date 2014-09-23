package tests;

import java.io.File;

import junit.framework.TestCase;
import user.User;
import data.persistant.Loader;
import data.persistant.Persistent;
import data.persistant.Saver;

public class SaverLoaderTest extends TestCase {

	/**
	 * Tests saving a user to json 
	 * @throws Exception
	 */
	public void testSaveUser() throws Exception {
		Persistent.setFilePath("/Users/SamSchofield/Desktop");
		User u = User.mockUser();
		Persistent.newUser(u);
		Persistent.setUser(u);
		Saver.SaveUser(u);
	}

	/**
	 * checks that the user that was loaded is the same as the one which was saved
	 */
	public void testLoader() {
		 User u = Loader.loadUserProfile(new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())));		 
		 assertEquals(u, User.mockUser());
	}
	
	

	

}
