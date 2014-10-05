package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.control.TextArea;
import user.User;

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
	private String host = "localhost";
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private DBWriter dbw;
	private TextArea log;
	
	/**
	 * Constructor for the server, takes a TextArea to be used as a console.
	 * @param console
	 */
	public Server(TextArea console) {
		this.log = console;
		try {
			serverSocket = new ServerSocket(port);			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used by the caller to start the server to allow connections
	 */
	public void run() {
		//log.appendText(startMessage() + " Server has been started\n");
		while(true) {
			try {
				waitForConnection();
				Thread.sleep(200);
			} catch(Exception e) {
//				System.out.println("here");
//				e.printStackTrace();
			}
			if (!serverSocket.isClosed()){
				try {
					successfulConnection();
					setupStreams();
					whileTransfering();
					Thread.sleep(200);
				} catch (Exception e) {
					System.out.println("there");
				}
			}
			else{
				break;
			}
		}
	}
	
	/**
	 * Stops the server by closing the serverSocket and no longer allowing connections
	 * @throws IOException 
	 */
	public void stopServer() throws IOException { 
		//log.appendText(startMessage() + " Shutting down server.\n");
		//view.server.ServerController.setConsoleText(" Shutting down server.");
		System.out.println(startMessage() + " Shuting down server.");
		// Close the server socket.
		serverSocket.close();

	}
	

	/**
	 * Waits for a connection to be formed with the client.
	 * @throws IOException
	 */
	private void waitForConnection() throws IOException {
		System.out.println(startMessage()
				+ " Waiting for a client to connect to ["
				+ InetAddress.getLocalHost().getCanonicalHostName() + "]...");
//		log.appendText(startMessage()
//				+ " Waiting for a client to connect to ["
//				+ InetAddress.getLocalHost().getCanonicalHostName() + "]...\n");
		// Once a client connects a socket is open for the server and client
		//view.server.ServerController.setConsoleText("Waiting for stuff");
		connection = serverSocket.accept();
	}

	/**
	 * Announces that the connection was established successfully
	 */
	private void successfulConnection() {
		System.out.println(String.format(startMessage()	+ " Now connected to <client> at [%s]", 
		connection.getInetAddress().getHostName()));
//		log.appendText(String.format(startMessage()	+ " Now connected to <client> at [%s]\n", 
//				connection.getInetAddress().getHostName()));
	}
	
	/**
	 * Sets up the output/input streams to send/receive objects with the client.
	 * @throws IOException
	 */
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		System.out.println(startMessage() + " IO streams are now ready to be used");
//		log.appendText(startMessage() + " IO streams are now ready to be used\n");
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
	System.out.println(startMessage() + " <Client> sent the user: [" + uploadedUser.getName() + "]");
//	log.appendText(startMessage() + " <Client> sent the user: [" + uploadedUser.getName() + "]\n");
	sendMessage(uploadedUser.getName());
	return uploadedUser;
	}

	/**
	 * This functions handles the transfer from the client and automatically writes the 
	 * user to the database if the user is valid
	 * @throws IOException
	 */
	private void whileTransfering() throws IOException {
		System.out.println(startMessage() + " Ready for transfer...\n");
		String clientMessage = null;
		User uploadedUser = null;
		dbw = new DBWriter();
		do {
			try {
				uploadedUser = getUserFromClient();
				try {
					System.out.println(startMessage() + " Adding user [" + uploadedUser.getName() 
							+ "] to the database...");
//					log.appendText(startMessage() + " Adding user [" + uploadedUser.getName() 
//							+ "] to the database...\n");
					dbw.writeUser(uploadedUser);
					System.out.println(startMessage() + " Complete!\n");
//					log.appendText(startMessage() + " Complete!\n\n");
				} catch (Exception e) {
					System.out.println("An error occured");
					e.printStackTrace();
				}
				clientMessage = (String) input.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println(startMessage() + " I don't know what to do with that type of object");
			}
		} while (!clientMessage.equals("END"));
	}
	
//	/**
//	 * Closes the streams and socket once the connection is lost
//	 */
//	public void closeStuff() {
//		try {
//			input.close();
//			output.close();
//			//connection.close();
//		} catch (IOException e) {
//			System.out.println("345");
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * Sends a confirmation message to the client that their message was
	 * received.
	 * @param message The message that was received from the client.
	 */
	private void sendMessage(String message) {
		try {
			output.writeObject(message);
			System.out.println(startMessage() + " Sent confirmation to the <Client>\n");
		} catch (IOException e) {
			System.out.println(startMessage() + " No confirmation was sent to the <Client>");
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
}
	
	
	
	
	
	
//-----------------Don't delete yet till I've tested the new version extensively-------------------	
	
//	private ServerSocket server;
//	private Socket connection;
//	private ObjectInputStream input;
//	private ObjectOutputStream output;
//	// private String host = "localhost";
//	final private int portNumber = 8888;
//	private DBWriter dbw;
//	private boolean running;
//
//	/**
//	 * Starts a server that will endlessly wait for connections
//	 */
//	public void startServer() {
//		try {
//			server = new ServerSocket(portNumber);
//			while (true) {
//				try {
//					waitForConnection();
//					setupStreams();
//					whileTransfering();
//					System.out.println(startMessage() + " Connection ended!");
//					System.out.println("-----------------------------------"
//							+ "--------------------");
//				} catch (EOFException e) {
//					System.out.println(startMessage() + " Connection ended!");
//					System.out.println("-----------------------------------"
//							+ "--------------------");
//				} finally {
//					closeStuff();
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void shutDownServer() {
//		//closeStuff();
//		running = false;
//	}
//	/**
//	 * Waits for a connection to be formed with the client.
//	 * 
//	 * @throws IOException
//	 */
//	private void waitForConnection() throws IOException {
//		System.out.println(startMessage()
//				+ " Waiting for a client to connect to ["
//				+ InetAddress.getLocalHost().getCanonicalHostName() + "]...");
//		// Once a client connects a socket is open for the server and client
//		connection = server.accept();
//		System.out.println(String.format(startMessage()	+ " Now connected to <client> at [%s]", 
//				connection.getInetAddress().getHostName()));
//	}
//
//	/**
//	 * Sets up the output/input streams to send/receive objects with the client.
//	 * 
//	 * @throws IOException
//	 */
//	private void setupStreams() throws IOException {
//		output = new ObjectOutputStream(connection.getOutputStream());
//		output.flush();
//		input = new ObjectInputStream(connection.getInputStream());
//		System.out.println(startMessage() + " IO streams are now ready to be used");
//	}
//
//	private User getUserFromClient() throws IOException, ClassNotFoundException {
//		User uploadedUser;
//		uploadedUser = (User) input.readObject();
//		System.out.println(startMessage() + " <Client> sent the user: [" + uploadedUser.getName() + "]");
//		sendMessage(uploadedUser.getName());
//
//		return uploadedUser;
//	}
//
//	/**
//	 * This functions handles the transfer from the client and automatically writes the user to the datebase
//	 * if the user is valid
//	 * @throws IOException
//	 */
//	private void whileTransfering() throws IOException {
//		System.out.println(startMessage() + " Ready for transfer...\n");
//		String clientMessage = null;
//		User uploadedUser = null;
//		dbw = new DBWriter();
//
//		do {
//			try {
//				uploadedUser = getUserFromClient();
//				try {
//					System.out.println(startMessage() + " Adding user [" + uploadedUser.getName() + "] to the database...");
//					dbw.writeUser(uploadedUser);
//					System.out.println(startMessage() + " Complete!\n");
//				} catch (Exception e) {
//					System.out.println("An error occured");
//					e.printStackTrace();
//				}
//				clientMessage = (String) input.readObject();
//			} catch (ClassNotFoundException e) {
//				System.out.println(startMessage() + " I don't know what to do with that type of object");
//			}
//		} while (!clientMessage.equals("END"));
//	}
//
//	/**
//	 * Closes the streams and socket once the connection is lost
//	 */
//	public void closeStuff() {
//		try {
//			input.close();
//			output.close();
//			connection.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Sends a confirmation message to the client that their message was
//	 * received.
//	 * @param message The message that was received from the client.
//	 */
//	private void sendMessage(String message) {
//		try {
//			output.writeObject(message);
//			System.out.println(startMessage() + " Sent confirmation to the <Client>\n");
//		} catch (IOException e) {
//			System.out.println(startMessage() + " No confirmation was sent to the <Client>");
//		}
//	}
//
//	/**
//	 * Gets the current time on the server.
//	 * @return The current time of the server
//	 */
//	private String getCurrentTime() {
//		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//		Calendar cal = Calendar.getInstance();
//		return df.format(cal.getTime());
//	}
//
//	/**
//	 * Creates a message consisting of [currentTime]<Server> for the server
//	 * output messages.
//	 * 
//	 * @return The opening string for server output messages.
//	 */
//	private String startMessage() {
//		return "[" + getCurrentTime() + "]<Server>";
//	}
//
//	public static void main(String[] args) {
		
//		Server s = new Server();
//		
//		Task<Void> t = new Task<Void>() {
//			@Override
//			protected Void call() throws Exception {
//				s.startServer();
//				return null;
//			}
//		};
//		
//		Thread thread = new Thread(t);
//		thread.start();
//		s.startServer();
//		s.shutDownServer();
//	}
//}