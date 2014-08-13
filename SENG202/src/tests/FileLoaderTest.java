package tests;

import dataImport.FileLoader;
import junit.framework.TestCase;

public class FileLoaderTest extends TestCase {
	FileLoader fl;
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        fl = new FileLoader();
		fl.load();
        System.out.println("setup");
    }
	
	public void testDummy() {
		assertEquals(true, true);
	}
	
	
}
