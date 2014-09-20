package data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
public class EventContainer implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 5379337115069957991L;
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
			LinkedList<Event> day = days.get(dateString); // gets events on that day
			int size = day.size();
			for (int i = 0; i < size; i++) {
				// reached the right index.
				if (e.equals(day.get(i))) {
					break;
				}else if (e.getStartTime().before(day.get(i).getStartTime())) {
					// add e at i
					day.add(i, e);
					break;
				} else if (i == day.size() - 1) {// e is the latest event
					// add e to the end of the list
					day.add(e);
					break;
				}
			}
		} else {
			// create new key date string with value linked list
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
			return new LinkedList<Event>();
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

	/**
	 * Used in DB.
	 * 
	 * @return All of the events as a collection.
	 */
	public Collection<Event> getAllEvents() {
		LinkedList<Event> events = new LinkedList<Event>();

		for (LinkedList<Event> list_i : days.values()) {
			events.addAll(list_i);
		}

		return events;
	}
	
	public Date getLastDate() {
		Calendar lastDate = new Calendar.Builder().build();
		for(String dateLine : days.keySet()){
			String[] dL = dateLine.split("/");
			Calendar date = new Calendar.Builder().setDate(Integer.parseInt(dL[2]), Integer.parseInt(dL[1]) - 1, Integer.parseInt(dL[0])).build();
			if(lastDate.before(date)) {
				lastDate = date;
			}
		}

		return lastDate.getTime();
	}
}
