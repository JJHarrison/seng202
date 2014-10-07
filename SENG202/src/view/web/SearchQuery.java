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
	private static final String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&key=AIzaSyBeMipx63vMq-R8_jkj5QffJQ5RTRu_kks&start=";
	private static boolean searched = false; //True when the query has been searched
	private static String currentSearchQuery; // Keeps track of what the query was and so knows if it has changed
	
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
		setCurrentSearchQuery(inputQuery);
		try {
			query = query + getCount() + "&q=" + (URLEncoder.encode(inputQuery, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		}
		incrementCount();
		return query;
	}
	
	/**
	 * Increments the count by 4 so that the next 4 results can be retrieved.
	 */
	public static void incrementCount() {
		setCount(getCount() + 4);
	}
	
	/** 
	 * Resets the count to 0
	 */
	public static void clearCount() {
		setSearched(false);
		setCount(0);
	}

	/**Checks to see if the Query has been searched for or not.
	 * @return the searched
	 */
	public static boolean isSearched() {
		return searched;
	}

	/**
	 * @param searched The state of the web search
	 */
	public static void setSearched(boolean searched) {
		SearchQuery.searched = searched;
	}

	/**
	 * @return the inputQuery
	 */
	public static String getCurrentSearchQuery() {
		return currentSearchQuery;
	}

	/**
	 * @param currentSearchQuery the search query to set
	 */
	public static void setCurrentSearchQuery(String currentSearchQuery) {
		SearchQuery.currentSearchQuery = currentSearchQuery;
	}

	/**
	 * @return the count
	 */
	public static int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public static void setCount(int count) {
		SearchQuery.count = count;
	}
}
