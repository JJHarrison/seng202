package test;

import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;

public class MyClass {
//change made
	public static void main(String[] argv) throws URISyntaxException, UnsupportedEncodingException, IOException {
		System.out.print(false);
		System.out.println(true);
		MyClass.class.getResourceAsStream("data/seng202_2014_example_date.csv");
		
        //String xml = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
		//InputStream stream = new InputSFileLoader.class.getResourceAsStream(""));
		//Change made
	}
}
