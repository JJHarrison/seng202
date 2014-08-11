package tests;

import java.util.Date;

import junit.framework.TestCase;
import dataModel.DataPoint;

public class DataPointTest extends TestCase {
	private DataPoint p1;
	private DataPoint p2;
	
	public DataPointTest() {
		System.out.println("Creating DataPointTest");
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        
        Date d1 = new Date(10000); // 10,000 miliseconds
        Date d2 = new Date(20000); // 20,000 miliseconds
        
        p1 = new DataPoint(d1, 120, 100.0, 100.0, 50, p1);
        p2 = new DataPoint(d2, 125, 100.0, 120.0, 51, p1);
        
        System.out.println("setup");
    }
}
