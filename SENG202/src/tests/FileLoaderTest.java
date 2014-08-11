package tests;

import dataModel.DataPoint;
import junit.framework.TestCase;

public class FileLoaderTest extends TestCase {
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        DataPoint point = new DataPoint("01/01/2014", "12:00:00", "100", "1000", "1000", "50", 10.5, 30);
        System.out.println("setup");
    }
}
