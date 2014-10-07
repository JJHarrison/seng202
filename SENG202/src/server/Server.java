package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.application.Platform;
import user.User;
import view.server.ServerController;

/**
 * This class provides a server for interacting with the client.
 * Is is used for taking a serialized user profile from the client and writing it to the 
 * database using DBWriter
 * @author James
 *
 */
public class Server extends Thread{
	private final int port = 8888;
	private ServerSocket serverSocket;
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private DBWriter dbw;
	public volatile boolean running;
	private boolean connected;
	
	
	/**
	 * Constructor for the server
	 */
	public Server() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
//				e.printStackTrace();
			}	
	}
	
	@Override
	public void run() { 
		connected = false;
		consoleWriter(startMessage() + " Server started");
		while(true) {
				try {
					waitForConnection();
				} catch (IOException e) {
//					e.printStackTrace();
				}			
		if (connected){
				successfulConnection();
				try {
					setupStreams();
					whileTransfering();
				} catch (IOException e) {
//					e.printStackTrace();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
//					e.printStackTrace();
				}
				connected = false;
		}
		else {
			break;
		}
		}
	}
	
	
	/**
	 * Stops the server by closing the serverSocket and no longer allowing connections
	 * @throws IOException 
	 */
	public void stopServer() throws IOException { 
		consoleWriter("\n" + startMessage() + " Shutting down server.\n");
		if(connected) {
			connection.close();
		}
		System.out.println(startMessage() + " Shuting down server.");
		// Close the server socket.
		serverSocket.close();
	}
	
	/**
	 * Waits for a connection to be formed with the client.
	 * @throws IOException
	 */
	private void waitForConnection() throws IOException {
		consoleWriter(startMessage()
				+ " Waiting for a client to connect to ["
				+ InetAddress.getLocalHost().getCanonicalHostName() + "]...");
		
		// Once a client connects a socket is open for the server and client
		try {
			connection = serverSocket.accept();
			connected = true;
		}
		catch(SocketException e) {
			connected = false;
		}
		
	}

	/**
	 * Announces that the connection was established successfully
	 */
	private void successfulConnection() {
		consoleWriter(String.format(startMessage()	+ " Now connected to <client> at [%s]", 
				connection.getInetAddress().getHostName()));
	}
	
	/**
	 * Sets up the output/input streams to send/receive objects with the client.
	 * @throws IOException
	 */
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		consoleWriter(startMessage() + " IO streams are now ready to be used");
	}
	
	/**
	 * Gets the user profile from the input stream
	 * @return A user object from the input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private User getUserFromClient() throws IOException, ClassNotFoundException {
		User uploadedUser;
		uploadedUser = (User) input.readObject();
		consoleWriter(startMessage() + " Client sent the user: [" + uploadedUser.getName() + "]");
		return uploadedUser;
	}

	/**
	 * This functions handles the transfer from the client and automatically writes the 
	 * user to the database if the user is valid
	 * @throws IOException
	 */
	private void whileTransfering() throws IOException {
		String clientMessage = null;
		User uploadedUser = null;
		dbw = new DBWriter();
		do {
			try {
				uploadedUser = getUserFromClient();
				try {
					consoleWriter(startMessage() + " Atempting to add user [" + uploadedUser.getName() 
							+ "] to the database...");
					dbw.writeUser(uploadedUser);
					consoleWriter(startMessage() + " Complete!\n");
					sendMessage(uploadedUser.getName());
					endMessage();
				} catch (SQLException e) {
					consoleWriter(startMessage() + " Database appears to be down!");
					sendMessage("NACK");
					endMessage();
					e.printStackTrace();
				}
				clientMessage = (String) input.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println(startMessage() + " I don't know what to do with that type of object");
			}
		} while (!clientMessage.equals("END"));
	}
	
	/**
	 * Writes to the console and server log in ServerController
	 * @param newLine the string to be written
	 */
	private void consoleWriter(String newLine) {
		System.out.println(newLine);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ServerController.setConsoleText(newLine + "\n");
			}
		});
	}
	
	/**
	 * Sends a confirmation message to the client that their message was
	 * received.
	 * @param message The message that was received from the client.
	 */
	private void sendMessage(String message) {
		try {
			output.writeObject(message);
			consoleWriter(startMessage() + " Sent confirmation to the Client");
		} catch (IOException e) {
			consoleWriter(startMessage() + " No confirmation was sent to the <Client>");
		}
	}
	
	/**
	 * Gets the current time on the server.
	 * @return The current time of the server
	 */
	private String getCurrentTime() {
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
	private String startMessage() {
		return "[" + getCurrentTime() + "]<Server>";
	}

	private void endMessage() {
		consoleWriter("-----------------------------------------------"
				+ "------------------------------------------------" + "\n");
	}	
}
