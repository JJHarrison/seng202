package view.search;
import java.util.List;

/**
 * This class provides an abstract version of results provided by the Google Search API.
 * Each GoogleSearchResult consists of a ResponseData class which holds an array of Results, with 
 * each Result consisting of attributes of title, URL and content.
 * 
 * @author Fitr Team
 *
 */
public class GoogleSearchResults {
	
	private ResponseData responseData;
	
	/*
	 * Get responseData
	 * @return responseData
	 */
	public ResponseData getResponseData() { 
		return responseData; 
	}
	
	/**
	 * Sets responseData
	 * @param responseData
	 */
	public void setResponseData(ResponseData responseData){ 
		this.responseData = responseData; 
	}
	 
	/**
	 * This class provides an abstraction for the responseData that the Google API returns.
	 * 
	 * @author Fitr Team
	 *
	 */
	public static class ResponseData {
		private List<Result> results;
	   
		/**
		 * Returns results
		 * 
		 * @return results
		 */
		public List<Result> getResults() 
	    { 
	    	return results; 
	    }
	    
		/**
		 * Sets results
		 * 
		 * @param results
		 */
	    public void setResults(List<Result> results) 
	    { 
	    	this.results = results; 
	    }
	}
	
	/**
	 * This class provides an abstraction for an individual Result with attributes of the Result
	 * title, URL and content. 
	 * 
	 * @author Fitr Team
	 *
	 */
	public class Result {
	    private String titleNoFormatting;
	    private String url;
	    private String content;
	    
	    /**
	     * Gets the Title with no formatting
	     * @return titleNoFormatting
	     */
	    public String getTitle() { 
	    	return titleNoFormatting;
	    }
	    
	    /**
	     * Gets the content of the result
	     * @return content
	     */
	    public String getContent() {
	    	return content;
	    }
	    
	    /**
	     * Gets the URL of the result
	     * @return url
	     */
	    public String getUrl() { 
	    	return url;
	    }
	    
	    /**
	     * Sets the content of the result
	     * @param content
	     */
	    public void setContent(String content) {
	    	this.content = content;
	    }
	    
	    /**
	     * Sets the title of the result
	     * @param title
	     */
	    public void setTitle(String title) { 
	    	this.titleNoFormatting = title; 
	    }
	    
	    /**
	     * Sets the URL of the result
	     * @param url
	     */
	    public void setUrl(String url) { 
	    	this.url = url; 
	    }
	}  
}