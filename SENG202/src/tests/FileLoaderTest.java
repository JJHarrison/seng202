package tests;

import junit.framework.TestCase;
import data.loader.FileLoader;

/**
 * Tests for the FileLoader class functionality
 * 
 * @author Simon
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

    public void testIsValidLine() {
	assertTrue(fl.isValidLine("23/24/2345,23:24:26,23.534,-1.4256236,97"));
	assertTrue(fl
		.isValidLine("01/02/1997,00:00:00,-1.2,-437.3857203453,97.1"));
	assertTrue(fl.isValidLine("09/09/2014,13:11:47,34.234,-135.46,187"));
	assertTrue(!fl.isValidLine("01/023/2345,23:24:26,23.534,-1.4256236,97"));
    }
}
