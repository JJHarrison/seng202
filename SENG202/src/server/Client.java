package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import data.model.DataPoint;
import data.model.Event;
import data.model.EventContainer;
import user.User;
import user.User.Gender;

public class Client {
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final int portNumber = 8888;
    private String hostName = "localhost";

    /**
     * Sets up a connection with the server.
     */
    public void setupConnection() {
	try {
	    System.out.println(startMessage()
		    + " attempting to start a connection to [" + hostName
		    + "] on port [" + portNumber + "]...");
	    clientSocket = new Socket(hostName, portNumber);
	    setupStreams();
	    System.out.println(startMessage() + " connection accepted by ["
		    + hostName + "]\n");
	} catch (IOException e) {
	    e.printStackTrace();
	    System.out.println(startMessage()
		    + " uh oh something wrong happened");
	}
    }

    /**
     * Sets up the input/output streams for sending and receiving information
     * with the server
     * 
     * @throws IOException
     */
    public void setupStreams() throws IOException {
	output = new ObjectOutputStream(clientSocket.getOutputStream());
	output.flush();
	input = new ObjectInputStream(clientSocket.getInputStream());
    }

    /*
     * Will need to add most of the functionality for sending events to this
     * function Probably will need a few more exceptions for wrong object or
     * whatever
     */
    /**
     * To transfer information to the server.
     * 
     * @param message
     */
    public void transferToServer(User traffic) {
	try {
	    System.out.println(startMessage() + " attempting to send ["
		    + traffic.getName() + "]...");
	    output.writeObject(traffic);
	    readConfirmation();
	    System.out.println(startMessage() + " succesful!\n");
	} catch (IOException e) {
	    System.out.println(startMessage() + " FAILED!!\n");
	}
    }

    /**
     * Reads and displays the confirmation that the server has correctly
     * received the information sent from the client
     * 
     * @throws IOException
     */
    public void readConfirmation() throws IOException {
	String buf;
	try {
	    buf = (String) input.readObject();
	    if (buf != null) {
		System.out.println("[" + getCurrentTime()
			+ "]<Server> Responded it receieved user [" + buf
			+ "].");
	    }
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Closes the I/O Streams and the connection to the server
     */
    public void closeStuff() {
	try {
	    System.out.println(startMessage() + " closing connection...");
	    output.writeObject("END");
	    input.close();
	    output.close();
	    clientSocket.close();
	    System.out.println(startMessage() + " connection closed!");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Gets the current time on the client formated to HH:mm:SS
     * 
     * @return String of the current time of the client.
     */
    public String getCurrentTime() {
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	return df.format(cal.getTime());
    }

    /**
     * Creates a message consisting of [currentTime]<Client> for the client
     * output messages.
     * 
     * @return The opening string for client output messages.
     */
    public String startMessage() {
	return "[" + getCurrentTime() + "]<Client>";
    }

    public static void main(String[] args) {
	Client c = new Client();
	c.setupConnection();
	// Just test strings at the moment, will need to build on this when we
	// know what we are actually sending
	User u = new User("New User", new GregorianCalendar(1961, 8, 9),
		Gender.MALE);
	u.setWeight(81.2);
	u.setHeight(1.8);

	// set start and finish times 3 minutes apart
	@SuppressWarnings("unused")
	Calendar c1 = new GregorianCalendar(2005, // Year
		5, // Month
		10, // Day
		23, // Hour
		42, // Minute
		28); // Second
	@SuppressWarnings("unused")
	Calendar c2 = new GregorianCalendar(2005, // Year
		5, // Month
		10, // Day
		23, // Hour
		45, // Minute
		28); // Second

	// e.setStartTime(c1);
	// e.setFinishTime(c2);

	// set up data points
	ArrayList<DataPoint> points = new ArrayList<DataPoint>();
	Calendar c3 = new GregorianCalendar(2005, // Year
		5, // Month
		10, // Day
		23, // Hour
		42, // Minute
		28); // Second
	Calendar c4 = new GregorianCalendar(2005, // Year
		5, // Month
		10, // Day
		23, // Hour
		43, // Minute
		5); // Second

	// p1 = new DataPoint(c3, 120, 30.2553368, -97.83891084, 50.0, null);
	DataPoint p1 = new DataPoint.Builder().date(c3).heartRate(120)
		.latitude(30.2553368).longitude(-97.83891084).altitude(50.0)
		.prevDataPoint(null).build();
	// p2 = new DataPoint(c4, 125, 30.25499189, -97.83913958, 51.0, p1);
	DataPoint p2 = new DataPoint.Builder().date(c4).heartRate(125)
		.latitude(30.25499189).longitude(-97.83913958).altitude(51.0)
		.prevDataPoint(p1).build();

	points.add(p1);
	points.add(p2);
	Event e = new Event("My Event", points);
	EventContainer ec = new EventContainer();
	ec.addEvent(e);
	u.setEvents(ec);

	c.transferToServer(u);

	// c.transferToServer("Message one");
	// c.transferToServer("Message two");
	// c.transferToServer("Message three");
	c.closeStuff();
    }
}
