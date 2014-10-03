package data.loader;

/**
 * class used for summarizing the loading of a csv file to give the user 
 * some feedback on what has happened when they uploaded a csv file 
 * @author SamSchofield
 *
 */
public class LoadSummary {
	
	private static int lineErrors;
	private static int badPoints;
	private static int eventsAdded;
	private static int eventsNotAdded;
	
	/**
	 * re-sets all the counters to zero
	 * used at the end of a load method 
	 */
	public static void clear() {
		lineErrors = 0;
		badPoints = 0;
		eventsAdded = 0;
		eventsNotAdded = 0;
	}
	
	/**
	 * increments the number of line errors
	 */
	public static void addLineError() {
		lineErrors++;
	}
	
	/**
	 * increments the number of bad points
	 */
	public static void addBadPoint() {
		badPoints++;
	}
	
	/**
	 * increments the number of events added
	 */
	public static void setEventsAdded(int numberOfEvents) {
		eventsAdded += numberOfEvents;
	}
	
	/**
	 * increments the number of events not added
	 */
	public static void setEventsNotAdded(int numberOfEvents) {
		eventsNotAdded += numberOfEvents;
	}
	
	/**
	 * gets the summary string for the loading of the csv file
	 * @return summary of the load 
	 */
	public static String getSumamry() {
		return String.format("Import Summary:\n"
				+ "%d new events added\n"
				+ "%d events not added\n"
				+ "%d line errors occured\n"
				+ "%d bad points were found",
				eventsAdded, eventsNotAdded, lineErrors, badPoints);
	}

}
