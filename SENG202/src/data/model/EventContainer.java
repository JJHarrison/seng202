package data.model;

import dataModel.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class is abstract version of an activity event container. The activity
 * event container consists of activity days containing Event objects
 * representing activity events imported from a fitness tracking device.
 * 
 * @author Fitr.Team
 */
public class EventContainer {
	private HashMap<String, LinkedList<Event>> days;

	/**
	 * Constructs a hash map to store the activity events for each day in.
	 */
	public EventContainer() {
		days = new HashMap<String, LinkedList<Event>>();
	}

	/**
	 * Add a activity event to the event container.
	 * 
	 * @param e
	 *            The activity event to be added to the container.
	 */
	public void addEvent(Event e) {
		String dateString = dateString(e.getStartTime().getTime());
		if (days.containsKey(dateString)) {
			// find the correct position to add the event
			LinkedList<Event> day = days.get(dateString);
			for (int i = 0; i < days.size(); i++) {
				// reached the right index.
				if (e.getStartTime().before(day.get(i).getStartTime())) {
					// add e at i
					day.add(i, e);
				}
				// e is the latest event
				else if (i == days.size() - 1) {
					// add e to the end of the list
					day.add(e);
				}
			}
		} else {
			LinkedList<Event> day = new LinkedList<Event>();
			day.add(e);
			days.put(dateString, day);
		}
	}

	/**
	 * Returns the linked list that contains the activity events for the day.
	 * 
	 * @param date
	 *            The day that the caller wants the activity events for.
	 * @return The linked list containing activity events
	 */
	public LinkedList<Event> getEvents(Date date) {
		String dateString = dateString(date);
		if (days.containsKey(dateString)) {
			return days.get(dateString);
		} else {
			return null;
		}
	}

	/**
	 * Returns a date string in the format dd/mm/yyyy to be used as the key for
	 * the hash map.
	 * 
	 * @param date
	 *            The date object to be formatted
	 * @return The formatted date string
	 */
	private String dateString(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date.getTime());
	}
}
