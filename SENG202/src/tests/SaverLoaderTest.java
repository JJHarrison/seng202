package tests;

import junit.framework.TestCase;
import user.User;
import user.User.Gender;
import data.persistant.Persistent;
import data.persistant.Saver;

public class SaverLoaderTest extends TestCase {

	public void testSaveUser() throws Exception {
		Persistent.setFilePath("/Users/SamSchofield/Desktop");
		User u = new User("SAM", null, Gender.MALE, 70, 170, null, 60);
		Persistent.newUser(u);
		Saver.SaveUser(u);

	}

	public void testLoader() {
		// User u = Loader.loadUserProfile(new File(Persistent.getFilePath()));
	}

}
