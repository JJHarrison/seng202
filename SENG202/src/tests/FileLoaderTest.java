package tests;

import junit.framework.TestCase;
import data.loader.FileLoader;

/**
 * Tests for the FileLoader class functionality
 * 
 * @author Simon, James
 *
 */
public class FileLoaderTest extends TestCase {

	private FileLoader fl;

	public FileLoaderTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		fl = new FileLoader();
	}

	// "10/04/2005,23:42:28,69,30.2553368,-97.83891084,239.5"

	/**
	 * Test to see if it will return false of if their are less/more fields than there should be
	 */
	public void testIsValidLineSize() {
		// 1 less value than should be
		assertFalse(fl.isValidLine("10/04/2005,23:42:28,69,30.2553368,-97.83891084"));
		// 1 more value than should be
		assertFalse(fl.isValidLine("10/04/2005,23:42:28,69,30.2553368,-97.83891084,239,400"));
	}
	
	/**
	 * Test to see if the date is within an acceptable format
	 */
	public void testIsValidLineDate(){
		// day should be [1,31]
		assertFalse(fl.isValidLine("00/04/2005,23:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("32/04/2005,23:42:28,69,30.2553368,-97.83891084,239.5"));
		// month should be [1,12]
		assertFalse(fl.isValidLine("10/00/2005,23:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/13/2005,23:42:28,69,30.2553368,-97.83891084,239.5"));
		// year should be [2000,Current year]
		assertFalse(fl.isValidLine("10/04/0,23:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/20000,23:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2101,23:42:28,69,30.2553368,-97.83891084,239.5"));
		assertTrue(fl.isValidLine("10/04/1999,23:42:28,69,30.2553368,-97.83891084,239.5"));
	}
	
	/**
	 * Check to see if the time is within an acceptable format
	 */
	public void testIsValidLineTime(){
		// hour should be [00,23]
	assertFalse(fl.isValidLine("10/04/2005,-1:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,000:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,1:42:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,24:42:28,69,30.2553368,-97.83891084,239.5"));
		// min should be [00,59]
		assertFalse(fl.isValidLine("10/04/2005,23:-1:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:1:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:100:28,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:61:28,69,30.2553368,-97.83891084,239.5"));
		// sec should be [00,59]
		assertFalse(fl.isValidLine("10/04/2005,23:42:-1,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:1,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:100,69,30.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:61,69,30.2553368,-97.83891084,239.5"));
	}
	
	/**
	 * Check to see if the latitude is within a valid range
	 */
	public void testIsValidLineLatitude(){
		// latitude should be [-90,90] with accuracy 5dp or higher
		assertTrue(fl.isValidLine("10/04/2005,23:42:28,69,30.2553,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:28,69,-300.2553368,-97.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:28,69,300.2553368,-97.83891084,239.5"));
	}
	
	/**
	 * Check to see if the longitude is within a valid range
	 */
	public void testIsValidLongitude(){
		// longitude should be [-180,180] with accuracy 5dp or higher
		assertTrue(fl.isValidLine("10/04/2005,23:42:28,69,30.2553368,-97.884,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:28,69,30.2553368,-907.83891084,239.5"));
		assertFalse(fl.isValidLine("10/04/2005,23:42:28,69,30.2553368,907.83891084,239.5"));
	}
	
	/**
	 * Check the entire isValidLine() function with points from the example data 
	 */
	public void testIsValidLine(){
		assertTrue(fl.isValidLine("10/04/2005,23:42:28,69,30.2553368,-97.83891084,239.5"));
		assertTrue(fl.isValidLine("10/04/2005,23:43:05,87,30.25499189,-97.83913958,239"));
		assertTrue(fl.isValidLine("10/04/2005,23:56:02,129,30.25924009,-97.84268571,251"));
		assertTrue(fl.isValidLine("28/03/2005,23:32:12,137,30.270315,-97.831443,173.7"));
	}
}
