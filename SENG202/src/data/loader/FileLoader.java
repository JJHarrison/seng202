package data.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import data.model.DataPoint;
import data.model.Event;
import data.model.EventContainer;

/**
 * This class provides a way of reading activity events provided by a fitness
 * tracking device.
 * The activity data needs to be in a CSV format consisting of
 * date, time, heart rate, latitude, longitude, and altitude..
 * 
 * @author Sam, James, Simon
 */
public class FileLoader {
	private InputStream inputStream;
	private EventContainer eventContainer = new EventContainer();
	private DataPoint lastPoint;
	private Date currentDate = Calendar.getInstance().getTime();
	public static int numberOfEvents; //Used to get the number of events in the CSV
	
	/**
	 * Creates a FileLoader with a particular input file.
	 * @param file The input file.
	 */
	public FileLoader(File file) {
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			clearStream();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates a file loader with the default csv file, for testing purposes.
	 */
	public FileLoader() {
		inputStream = this.getClass().getResourceAsStream("seng202_2014_example_data.csv");
	}

	/**
	 * Reads a CSV file and creates a new event at each #start add all following
	 * data points to the event.
	 */
	public void load() {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		ArrayList<DataPoint> points = new ArrayList<DataPoint>();
		String eventName = null;

		try {
			//read until the end of the file
			while ((line = br.readLine()) != null) {
				if (isValidLine(line)) {
					String[] dataLine = line.split(",");
					if (dataLine[0].contains("#start")) {
						// start of new event
						dataLine = fixTitle(dataLine);
						numberOfEvents++;
						if (points.size() > 1) { 
							//An event needs to have at least 2 points	
							eventContainer.addEvent(new Event(eventName, points));
							points = new ArrayList<DataPoint>(); // reset the points for the next event
							lastPoint = null;
						}
						eventName = dataLine[1];

					} else { // line containing data
						DataPoint point = parseLine(dataLine);
						
						//dont add null points
						if(point != null) {
							points.add(point);
						} else {
							LoadSummary.addBadPoint();
						}
					}
				} else {
					//line was not valid 
					LoadSummary.addLineError();
				}
			}
			
			// add the last event of the CSV file to events
			if (points.size() > 1 && eventName != null) { 
				eventContainer.addEvent(new Event(eventName, points));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes a correctly formatted line and returns it as DataPoint.
	 * 
	 * @param line the line to convert to a dataPoint.
	 * @return dataPoint of the line that was read.
	 */
	private DataPoint parseLine(String[] line) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,kk:mm:ss");
		
		Calendar date = null;
		try {
			date = new Calendar.Builder().setInstant(sdf.parse(line[0] + "," + line[1])).build();
		} catch (ParseException e) {
			// line format has already been checked
		}
		
		//parse the other fields from string to appropriate type
		int heartrate = Integer.parseInt(line[2]);
		double latitude = Double.parseDouble(line[3]);
		double longitude = Double.parseDouble(line[4]);
		double altitude = Double.parseDouble(line[5]);

		//create the dataPoint 
		DataPoint point = new DataPoint.Builder().date(date)
				.heartRate(heartrate).latitude(latitude).longitude(longitude)
				.altitude(altitude).prevDataPoint(lastPoint).build();
		
		//checks if the point has reasonable values 
		if(isValidPoint(point)) {
			lastPoint = point;
		} else {
			//point is invalid so don't add it  
			point = null;
		}
		return point;
	}
	
	/**
	 * Normalizes the start line so it always has a title.
	 * @param string the string to normalize.
	 * @return String #start,EventTitle,,,
	 */
	private String[] fixTitle(String[] string) {
		String title;
		if(string.length < 2) {
			// event has no title
			title = string[0] + ",Event(default),,,,";
		} else {
			// only need the first two sections
			title = string[0] + "," + string[1] + ",,,,";
		}
		return title.split(",");
	}

	/**
	 * Returns the event container which holds all the dataPoints
	 * @return eventContainer which all the events were loaded to 
	 */
	public EventContainer getEventContainer() {
		return eventContainer;
	}

	/**
	 * checks that the data line from the CSV file if valid i.e that it has all
	 * the required fields and they are an appropriate value.
	 * @param line the line to be checked.
	 * @return isValid (if the file is valid).
	 */
 	public boolean isValidLine(String line) { 
		boolean isValid = false;
 		String z = "(\\d){2}/(\\d){2}/(\\d){4},";
 		String y = "(\\d){2}:(\\d){2}:(\\d){2},";
 		String x = "(\\d){2,3},(\\-)?(\\d)+.(\\d)+,(\\-)?(\\d)+.(\\d)+,(\\d){2,3}(.(\\d))?";
 		String reg = z + y + x;
 		
		if(line.matches(reg)) {
			// the line must pass the regex before checking validity of the values
			// otherwise it will break as isInRange() does not check if it can parse the string
			String[] values = line.split(",");
 			if ((values.length == 6)
 				&& (isDateValid(values[0]))
 				&& (isTimeValid(values[1]))
 				&& (isHeartrateValid(values[2]))
				&& (isLatitudeValid(values[3]))
				&& (isLongitudeValid(values[4]))){
				isValid = true;
 			}
		} else if(line.startsWith("#start")) {
			isValid = true;
		}
		return isValid;
 	}

 	/**
 	 * clears the input stream
 	 */
	private void clearStream() {
		inputStream = null;
	}

	/**
	* Checks to see if the value is in a range of [low, high] (inclusive).
	* There are no checks to see if the value is valid to be parsed to double,
	* this must be handled by the function caller.
	* @param value The value that will be checked to see if it is in the range
	* @param low The lower bound of the range
	* @param high The higher bound of the range
	* @return True if the value is in the range, false otherwise
	*/
	private boolean isInRange(String value, double low, double high){
 		double d = Double.parseDouble(value);
 		return (d >= low && d <= high);
 	}

 	/**
 	* Check to see if the date is within a valid date format
 	* The date that is accepted is the standard New Zealand format dd/mm/yyyy ...
 	*  [1-31]/[1,12]/[2000,Current year] 
 	* @param date The date that will be checked
 	* @return True if the value is a valid date, false otherwise
 	*/
 	private boolean isDateValid(String date) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		try {
	        Date d = df.parse(date);
	        return d.before(currentDate);
	    } catch (ParseException e) {
	        return false;
	    }
	} 
	

	/**
	* Check to see if the time is a valid time format
	* The time that will be accepted is hh:mm:ss [0,23]:[0,59],[0,59]
	* @param time The time that will be checked
	* @return True if the value is a valid time, false otherwise
	*/
	private boolean isTimeValid(String time){
 		String[] values = time.split(":");
 		return (isInRange(values[0], 0, 23))
 				&& isInRange(values[1], 0, 59)
 				&& isInRange(values[2], 0, 59);
 	}

	/**
	* Checks to see if the heart rate is within a valid range of [20,220]
	* @param hr The heart rate that will be checked
	* @return True if the heart rate is valid, false otherwise
	*/
 	private boolean isHeartrateValid(String hr){
 		return isInRange(hr, 20, 220);
 	}

	/**
	* Checks to see if the latitude is within a valid range of [-90,90]
	* @param lat The latitude that will be checked 
	* @return True if the latitude is valid, false otherwise
	*/
 	private boolean isLatitudeValid(String lat){
 		String[] values = lat.split("\\.");		
 		return (isInRange(lat, -90, 90) &&  values[1].length() >= 3);
 	}

	/**
	* Checks to see if the longitude is within a valid range of [-180,180]
	* @param lng The longitude that will be checked 
	* @return True if the longitude is valid, false otherwise
	*/
 	private boolean isLongitudeValid(String lng){
 		String[] values = lng.split("\\.");		
 		return (isInRange(lng, -180, 180) &&  values[1].length() >= 3);
 	}
 	
 	/**
 	 * Checks to see if the given point is valid e.g
 	 * The time is after the previous point, the lat and lon are reasonable etc
 	 * @param point the point to check 
 	 * @return if the point is valid or not 
 	 */
 	private boolean isValidPoint(DataPoint point) {
 		boolean isValid = false;
 		if(pointDateValid(point)) {
 			isValid = true;
 		}
 		return isValid;
 	}
 	
 	/**
 	 * Checks to see if the current data point comes after the previous data point in time 
 	 * @param point the point to check 
 	 * @return if the date of the point is correct
 	 */
 	private boolean pointDateValid(DataPoint point) {
 		boolean isValid = false;
 		if (lastPoint == null) {
 			//No last point, so the date has to be correct 
 			isValid = true;
 		} else {
 			//check if the current point comes after the last point (in time)
 			isValid = point.getDate().after(lastPoint.getDate());
 		}
 		return isValid;
 	}
}
