package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import user.User;

public class Server {
    private ServerSocket server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    // private String host = "localhost";
    final private int portNumber = 8888;
    final private int maxQueue = 10;
    @SuppressWarnings("unused")
    private DBWriter dbw;

    /*
     * Still need to look into threading for the client sockets
     */

    /**
     * Starts a server that will endlessly wait for connections
     */
    public void startServer() {
	try {
	    server = new ServerSocket(portNumber, maxQueue);
	    while (true) {
		try {
		    waitForConnection();
		    setupStreams();
		    whileTransfering();
		    System.out.println(startMessage() + " Connection ended!");
		    System.out.println("-----------------------------------"
			    + "--------------------");
		} catch (EOFException e) {
		    System.out.println(startMessage() + " Connection ended!");
		    System.out.println("-----------------------------------"
			    + "--------------------");
		} finally {
		    closeStuff();
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Waits for a connection to be formed with the client.
     * 
     * @throws IOException
     */
    public void waitForConnection() throws IOException {
	System.out.println(startMessage()
		+ " Waiting for a client to connect to ["
		+ InetAddress.getLocalHost().getCanonicalHostName() + "]...");
	// Once a client connects a socket is open for the server and client
	connection = server.accept();
	System.out.println(String.format(startMessage()
		+ " Now connected to <client> at [%s]", connection
		.getInetAddress().getHostName()));
    }

    /**
     * Sets up the output/input streams to send/receive objects with the client.
     * 
     * @throws IOException
     */
    public void setupStreams() throws IOException {
	output = new ObjectOutputStream(connection.getOutputStream());
	output.flush();
	input = new ObjectInputStream(connection.getInputStream());
	System.out.println(startMessage()
		+ " IO streams are now ready to be used");
    }

    public User getUserFromClient() throws IOException, ClassNotFoundException {
	User uploadedUser;
	uploadedUser = (User) input.readObject();
	System.out.println(startMessage() + " User sent user: "
		+ uploadedUser.getName());
	sendMessage(uploadedUser.getName());

	return uploadedUser;
    }

    public void whileTransfering() throws IOException {
	System.out.println(startMessage() + " Ready for transfer...\n");
	String clientMessage = null;
	User uploadedUser = null;
	dbw = new DBWriter();

	do {
	    try {
		uploadedUser = getUserFromClient();
		try {
		    // dbw.writeUser(uploadedUser);
		    // System.out.println("seeing if shit happened: " +
		    // uploadedUser.getEvents().getAllEvents().next());
		    System.out.println(uploadedUser.getName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		clientMessage = (String) input.readObject();
	    } catch (ClassNotFoundException e) {
		System.out.println(startMessage()
			+ " I don't know what to do with that type of object");
	    }
	} while (!clientMessage.equals("END"));
    }

    /**
     * Closes the streams and socket once the connection is lost
     */
    public void closeStuff() {
	try {
	    input.close();
	    output.close();
	    connection.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Sends a confirmation message to the client that their message was
     * received.
     * 
     * @param message
     *            The message that was received from the client.
     */
    private void sendMessage(String message) {
	try {
	    output.writeObject(message);
	    System.out.println(startMessage()
		    + " Sent confirmation to the <Client>\n");
	} catch (IOException e) {
	    System.out.println(startMessage()
		    + " No confirmation was sent to the <Client>");
	}
    }

    /**
     * Gets the current time on the server.
     * 
     * @return
     */
    public String getCurrentTime() {
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	return df.format(cal.getTime());
    }

    /**
     * Creates a message consisting of [currentTime]<Server> for the server
     * output messages.
     * 
     * @return The opening string for server output messages.
     */
    public String startMessage() {
	return "[" + getCurrentTime() + "]<Server>";
    }

    public static void main(String[] args) {
	Server s = new Server();
	s.startServer();

    }
}