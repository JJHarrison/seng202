package dataModel;

import dataModel.Event;
import java.util.ArrayList;

public class EventContainer {
	private ArrayList<Event> events;
	
	public EventContainer() {
		events = new ArrayList<Event>();
	}
	
	public void addEvent(Event e) {
		this.events.add(e);
	}
	
	public ArrayList<Event> getEvents() {
		return this.events;
	}
}
