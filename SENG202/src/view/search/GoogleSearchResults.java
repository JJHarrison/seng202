package view.search;
import java.util.List;

/**
 * This class provides an abstract version of results provided by the Google Search API.
 * Each GoogleSearchResult consists of a ResponseData class which holds an array of Results, with 
 * each Result consisting of attributes of title, URL and content.
 * 
 * @author Daniel van Wichen, Daniel Tapp
 *
 */
public class GoogleSearchResults {
	
	private ResponseData responseData;
	
	/**
	 * Get the response data received from google.
	 * 
	 * @return the response data.
	 */
	public ResponseData getResponseData() { 
		return responseData; 
	}
	
	/**
	 * Set the response data.
	 * 
	 * @param responseData the data received from google.
	 */
	public void setResponseData(ResponseData responseData){ 
		this.responseData = responseData; 
	}
	 
	/**
	 * This class provides an abstraction for the response data that the google API returns.
	 * 
	 */
	public static class ResponseData {
		
		private List<Result> results;
	   
		/**
		 * Returns results
		 * 
		 * @return results
		 */
		public List<Result> getResults() { 
	    	return results; 
	    }
	    
		/**
		 * Sets the returned results.
		 * 
		 * @param results list of the results returned by google.
		 */
	    public void setResults(List<Result> results) { 
	    	this.results = results; 
	    }
	}
	
	/**
	 * This class provides an abstraction for an individual result with attributes of the result
	 * title, URL and content. 
	 * 
	 */
	public class Result {
	    private String titleNoFormatting; // needs to be formatted.
	    private String url;
	    private String content;
	    
	    /**
	     * Gets the title with no formatting.
	     * 
	     * @return title
	     */
	    public String getTitle() { 
	    	return titleNoFormatting;
	    }
	    
	    /**
	     * Gets the content of the result.
	     * 
	     * @return content
	     */
	    public String getContent() {
	    	return content;
	    }
	    
	    /**
	     * Gets the URL of the result.
	     * 
	     * @return url
	     */
	    public String getUrl() { 
	    	return url;
	    }
	    
	    /**
	     * Sets the content of the result.
	     * 
	     * @param content the content text of a particular result.
	     */
	    public void setContent(String content) {
	    	this.content = content;
	    }
	    
	    /**
	     * Sets the title of the result.
	     * 
	     * @param title the title of a particular result.
	     */
	    public void setTitle(String title) { 
	    	this.titleNoFormatting = title; 
	    }
	    
	    /**
	     * Sets the URL of the result.
	     * 
	     * @param url the URL of a particular result.
	     */
	    public void setUrl(String url) { 
	    	this.url = url; 
	    }
	}  
}