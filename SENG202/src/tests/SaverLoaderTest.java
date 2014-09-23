package tests;

import junit.framework.TestCase;
import user.User;
import data.persistant.Persistent;
import data.persistant.Saver;

public class SaverLoaderTest extends TestCase {

	
	public void testSaveUser() throws Exception {
		Persistent.setFilePath("/Users/SamSchofield/Desktop");
		User u = User.mockUser();
		Persistent.newUser(u);
		Persistent.setUser(u);
		Saver.SaveUser(u);
	}

		
	public void testLoader() {
		 //User u = Loader.loadUserProfile(new File(Persistent.getProfileFilePath(Persistent.getCurrentUser().getUserId())));		 
	}
	
	

	

}
