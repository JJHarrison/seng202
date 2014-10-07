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
		FileLoader.numberOfEvents = 0;
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
	public static void addEventsAdded(int numberOfEvents) {
		eventsAdded += numberOfEvents;
	}
	
	/**
	 * increments the number of events not added
	 */
	public static void addEventsNotAdded(int numberOfEvents) {
		eventsNotAdded += numberOfEvents;
	}
	
	/**
	 * gets the formatted string of the number of events added
	 * @return String of events added
	 */
	public static String getAddedEvents() {
		return String.format("%d", eventsAdded);
	}
	
	/**
	 * gets the formatted string of the number of events not added
	 * @return String of events not added
	 */
	public static String getIgnoredEvents() {
		return String.format("%d", eventsNotAdded);
	}
	
	/**
	 * gets the formatted string of the number lines with errors
	 * @return String of number of lines with errors
	 */
	public static String getLineErrors() {
		return String.format("%d", lineErrors);
	}
	
	/**
	 * gets the formatted string of the number lines with errors
	 * @return String of number of lines with errors
	 */
	public static String getIgnoredPoints() {
		return String.format("%d", badPoints);
	}
	
	/**
	 * gets the summary string for the loading of the csv file
	 * @return summary of the load 
	 */
	public static String getSumamry() {
		return String.format("Import Summary:\t\t\t\t\t\t\t\t\t\n\n"
				+ "%-50s%d\n"
				+ "%-50s%d\n"
				+ "%-50s%d\n"
				+ "%-50s%d\n\n"
				+ "*Go to [About->Importing] to learn about file checking",
				"Events added:", eventsAdded, "Events ignored:", eventsNotAdded,
				"Line errors:", lineErrors, "Bad points:", badPoints);
	}
	
	
	public static void main(String[] args) {
		String s = String.format("Import Summary:\t\t\t\t\t\t\t\t\t\n\n"
				+ "%-50s%d\n"
				+ "%-50s%d\n"
				+ "%-50s%d\n"
				+ "%-50s%d\n\n"
				+ "*See About->Importing learn about file checking",			
//				+ "Events are ignored if they: \n-Contain less than 2 points\n-Have already been added to the user\n\n"
//				+ "A line has errors if it is not in the required format\n\n"
//				+ "A point is ignored if it is deemed to be unreasonable",
				"Events added:", eventsAdded, "Events ignored:", eventsNotAdded,
				"Line errors:", lineErrors, "Bad points:", badPoints);
		
		System.out.println(s);
	}

}
