package dataModel;


import dataModel.Event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is abstract version of an activity event container. The activity event container consists of
 * activity event objects representing activity events imported from a fitness tracking device.
 * @author Fitr.Team
 */
public class EventContainer {
	private HashMap<String, ArrayList<Event>> days;
	
	/**
	 * Constructs a new event container object consisting of an arraylist to contain activity events.
	 */
	public EventContainer() {
		days = new HashMap<String, ArrayList<Event>>();
	}
	
	/**
	 * Add a activity event to the event container.
	 * @param e The activity event to be added to the container.
	 */
	public void addEvent(Event e) {
		this.events.add(e);
	}
	
	/**
	 * Returns the event container array that contains the activity events.
	 * @return The array containing activity events
	 */
	public ArrayList<Event> getEvents() {
		return this.events;
	}
}
