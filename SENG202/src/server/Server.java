package server;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;



public class Server {
	private ServerSocket server;
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	//private String host = "localhost";
	final private int portNumber = 8888; 
	final private int maxQueue = 10;
	
	/*
	 * Still need to look into threading for the client sockets
	 */
		
	/**
	 * Starts a server that will endlessly wait for connections
	 */
	public void startServer(){
		try{
			server = new ServerSocket(portNumber, maxQueue);
			while(true){
				try{
					waitForConnection();
					setupStreams();
					whileTransfering();
					System.out.println(startMessage() + " Connection ended!");
					System.out.println("-----------------------------------"
							+ "--------------------");
				}
				catch(EOFException e){
					System.out.println(startMessage() + " Connection ended!");
					System.out.println("-----------------------------------"
							+ "--------------------");
				}
				finally{
					closeStuff();  
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Waits for a connection to be formed with the client.
	 * @throws IOException
	 */
	public void waitForConnection() throws IOException{
		System.out.println(startMessage() + " Waiting for a client to connect to [" + 
							InetAddress.getLocalHost().getCanonicalHostName() + "]...");
		//Once a client connects a socket is open for the server and client
		connection = server.accept();
		System.out.println(String.format(startMessage() + " Now connected to %s", connection.getInetAddress().getHostName()));
	}
	
	/**
	 * Sets up the output/input streams to send/receive objects with the client.
	 * @throws IOException
	 */
	public void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		System.out.println(startMessage() + " Streams are now ready to be used");
	}
	
	/*
	 *This will need to be changed once we are sending actual information that the server will store 
	 */
	public void whileTransfering() throws IOException{
		String clientMessage = null;
		System.out.println(startMessage() + " Ready for transfer...\n");
		do{
		try{
			//Will need to send a string to from the client describing what is being sent
			//maybe a comment that would also be stored with the file
			clientMessage = (String)input.readObject();
			//Prints that we have received the object
			System.out.println(startMessage() + " User sent: " + clientMessage);
			/*
			 * This is where we need to process the file being sent from the client...
			 * --We must decide where it will be stored
			 * --We must decide how it will be stored
			 * --Probably need to have some container/storage class
			 * --I think we will also need some checks here, or decide if that client will perform a check also/instead
			 */
			//send a confirmation to the client that the message was received correctly
			sendMessage(clientMessage);
		}
		catch(ClassNotFoundException e){
			System.out.println(startMessage() + " I don't know what to do with that type of object");
		}
		}
		while(!clientMessage.equals("END"));
	}
	
	/**
	 * Closes the streams and socket once the connection is lost
	 */
	public void closeStuff(){
		try{
			input.close();
			output.close();
			connection.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a confirmation message to the client that their message was received.
	 * @param message The message that was received from the client.
	 */
	private void sendMessage(String message){
		try{
			output.writeObject(message);
			output.flush();
			System.out.println(startMessage() + " Sent confirmation to the <Client>\n");
		}
		catch(IOException e){
			System.out.println(startMessage() + " No confirmation was sent to the <Client>");
		}
	}
	
	/**
	 * Gets the current time on the server.
	 * @return
	 */
	public String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return df.format(cal.getTime());
	}
	
	/**
	 * Creates a message consisting of [currentTime]<Server> for the server output messages.
	 * @return The opening string for server output messages.
	 */
	public String startMessage(){
		return "[" + getCurrentTime()+ "]<Server>";
	}
	
	public static void main(String[] args){
		Server s = new Server();
		s.startServer();
	
}
}