package data.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import data.model.DataPoint;
import data.model.Event;
import data.model.EventContainer;

/**
 * This class provides a way of reading activity events provided by a fitness
 * tracking device. The activity data needs to be in a CSV format consisting of
 * date, time, heart rate, latitude, longitude & altitude.
 * 
 * @author Fitr.Team
 */
public class FileLoader {
	private InputStream inputStream;
	private EventContainer eventContainer = new EventContainer();
	public ArrayList<Event> events = new ArrayList<Event>();

	/**
	 * Creates a FileLoader with a particular input file
	 * 
	 * @param file
	 *            The input file
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

	public FileLoader() {
		inputStream = this.getClass().getResourceAsStream(
				"seng202_2014_example_data.csv");
	}

	/**
	 * Gets the input stream
	 * 
	 * @return The input stream
	 */
	public InputStream getStream() {
		return inputStream;
	}

	/**
	 * Reads a CSV file and creates a new event at each #start add all following
	 * data points to the event
	 */
	public void load() {
		BufferedReader br = null;
		String line = "";
		DataPoint lastPoint = null;
		Event currentEvent = null;
		ArrayList<DataPoint> points = new ArrayList<DataPoint>();
		String currentName = null; 

		try {
			br = new BufferedReader(new InputStreamReader(inputStream));

			while ((line = br.readLine()) != null) {
				String[] dataLine = line.split(",");

				if (isValidLine(line) || line.startsWith("#start")) {
					if (dataLine[0].contains("#start")) {
						// we need to add create an event and add the points
						// from before start
					
						if (!points.isEmpty()) {
							
							currentEvent = new Event(currentName, points);
							eventContainer.addEvent(currentEvent);
							events.add(currentEvent);
							points.clear();
						}
						currentName = dataLine[1];
						lastPoint = null;
					} else {
						String[] dateString = dataLine[0].split("/");
						String[] time = dataLine[1].split(":");
	
						// months start from 0...
						Calendar date = new GregorianCalendar(
								Integer.parseInt(dateString[2]), // Year
								Integer.parseInt(dateString[1]), // Month
								Integer.parseInt(dateString[0]), // Day
								Integer.parseInt(time[0]), // Hour
								Integer.parseInt(time[1]), // Minute
								Integer.parseInt(time[2])); // Second

						int heartrate = Integer.parseInt(dataLine[2]);
						double latitude = Double.parseDouble(dataLine[3]);
						double longitude = Double.parseDouble(dataLine[4]);
						double altitude = Double.parseDouble(dataLine[5]);

						DataPoint point = new DataPoint.Builder().date(date)
								.heartRate(heartrate).latitude(latitude)
								.longitude(longitude).altitude(altitude)
								.prevDataPoint(lastPoint).build();

						points.add(point);
						lastPoint = point;
					}
				}
			}
			
			if(currentName != null && points.size() != 0) {
				currentEvent = new Event(currentName, points);
				eventContainer.addEvent(currentEvent);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("couldnt read line");
		}

	}

	/**
	 * returns the event container which holds all the dataPoints
	 * 
	 * @return
	 */
	public EventContainer getEventContainer() {
		return eventContainer;
	}

	/**
	 * checks that the data line from the csv file if valid 
	 * i.e that it has all the required fields and they are an appropriate value
	 * @param line
	 * @return isValid
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
			
		}
		return isValid;
	}

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
	public boolean isInRange(String value, double low, double high){
		double d = Double.parseDouble(value);
		return (d >= low && d <= high);
	}
	
	/**
	 * Check to see if the date is within a valid date format
	 * The date that is accepted is the standard New Zealand format dd/mm/yyyy ...
	 *  [1-31]/[1,12]/[2000,2100] 
	 * @param date The date that will be checked
	 * @return True if the value is a valid date, false otherwise
	 */
	public boolean isDateValid(String date){
		String[] values = date.split("/");
		return (isInRange(values[0], 1, 31)
			&& isInRange(values[1], 1, 12)
			&& isInRange(values[2], 2000, 2100));
	}
	
	/**
	 * Check to see if the time is a valid time format
	 * The time that will be accepted is hh:mm:ss [0,23]:[0,59],[0,59]
	 * @param time The time that will be checked
	 * @return True if the value is a valid time, false otherwise
	 */
	public boolean isTimeValid(String time){
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
	public boolean isHeartrateValid(String hr){
		return isInRange(hr, 20, 220);
	}
	
	/**
	 * Checks to see if the latitude is within a valid range of [-90,90]
	 * @param lat The latitude that will be checked 
	 * @return True if the latitude is valid, false otherwise
	 */
	public boolean isLatitudeValid(String lat){
		String[] values = lat.split("\\.");		
		return (isInRange(lat, -90, 90) &&  values[1].length() >= 5);
	}
	
	/**
	 * Checks to see if the longitude is within a valid range of [-180,180]
	 * @param lng The longitude that will be checked 
	 * @return True if the longitude is valid, false otherwise
	 */
	public boolean isLongitudeValid(String lng){
		String[] values = lng.split("\\.");		
		return (isInRange(lng, -180, 180) &&  values[1].length() >= 5);
	}
	
	
	public static void main(String[] args) {
		FileLoader f = new FileLoader();
		/*f.load();
		
		EventContainer eC = f.getEventContainer();
		System.out.println(eC.getAllEvents().size());
		for (Event e : eC.getAllEvents()) {
			System.out.print(e.getEventName() + "\n");
			//System.out.print(e.getStartTime().get(Calendar.MINUTE) + "\n");
		}*/
		String l = "10/04/2005,23:42:28,69,30.2553368,-97.83891084,50";
		System.out.println(f.isValidLine(l));
	}
}
