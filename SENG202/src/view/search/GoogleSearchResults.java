package view.search;
import java.util.List;

public class GoogleSearchResults {
	
	private ResponseData responseData;
	
	public ResponseData getResponseData() { 
		return responseData; 
	}
	
	public void setResponseData(ResponseData responseData){ 
		this.responseData = responseData; 
	}
	 
	public static class ResponseData {
		private List<Result> results;
	    
		public List<Result> getResults() 
	    { 
	    	return results; 
	    }
	    
	    public void setResults(List<Result> results) 
	    { 
	    	this.results = results; 
	    }
	}
	
	class Result {
	    private String title;
	    private String url;
	    
	    public String getTitle() { 
	    	return title; 
	    }
	    
	    public String getUrl() { 
	    	return url;
	    }
	    
	    public void setTitle(String title) { 
	    	this.title = title; 
	    }
	    public void setUrl(String url) { 
	    	this.url = url; 
	    }
	}  
}