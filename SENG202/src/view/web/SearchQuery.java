package view.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * This class provides an abstraction of a SearchQuery, which has the attribute of an address
 * which is the address to request the Gson file from the Google API, including a "count" which
 * is for what set of results the user wants to show.
 * @author Jaln Rodger, Daniel van Wichen
 *
 */
public class SearchQuery {
	
	private static int count = 0;
	public static final String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&key=AIzaSyBeMipx63vMq-R8_jkj5QffJQ5RTRu_kks&start="+count+"&q=";
	
	/**
	 * A function that takes a plain text search query and returns a complete
	 * search request URL
	 * 
	 * @param inputQuery
	 *            The plain text search query
	 * @return The search request URL
	 * @throws IOException 
	 */
	public static String getQuery(String inputQuery) throws IOException {
		String query = address;
		try {
			query = query + (URLEncoder.encode(inputQuery, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	/**
	 * Increments the count by 4
	 */
	public void incrementCount() {
		count = count + 4;
	}
	
	 /**
	  * Decrements the count by 4
	  */
	public void decrementCount() {
		if (count != 0) {
			count = count - 4;
		}
	}
}
