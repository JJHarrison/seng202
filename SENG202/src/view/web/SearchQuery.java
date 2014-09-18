package view.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchQuery {

	public static final String url = "https://duckduckgo.com/?q=";

	/**
	 * A function that takes a plain text search query and returns a complete
	 * search request URL
	 * 
	 * @param inputQuery
	 *            The plain text search query
	 * @return The search request URL
	 */
	public static String getQuery(String inputQuery) {
		String query = url;
		try {
			query = query + (URLEncoder.encode(inputQuery, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return query;
	}
}
