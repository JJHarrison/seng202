package server;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class Client {
	private Socket clientSocket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private final int portNumber = 8888;
	private String hostName = "LocalHost";
	
	/**
	 * Sets up a connection with the server.	
	 */
	public void setupConnection(){
		try{
			System.out.println(startMessage() + " attempting to start a connection to [" + hostName + 
					"] on port [" + portNumber + "]...");
			clientSocket = new Socket(hostName, portNumber);
			setupStreams();
			System.out.println(startMessage() + " connection accepted by [" + hostName + "]\n");
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println(startMessage() + " uh oh something wrong happened");
		}
	}
	
	/**
	 * Sets up the input/output streams for sending and receiving information with the server
	 * @throws IOException
	 */
	public void setupStreams() throws IOException{
		output = new ObjectOutputStream(clientSocket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(clientSocket.getInputStream());
	}
	
	/*
	 * Will need to add most of the functionality for sending events to this function
	 * Probably will need a few more exceptions for wrong object or whatever
	 * 
	 */
	/**
	 * To transfer information to the server.
	 * @param message
	 */
	public void transferToServer(Object message){
		try{
			System.out.println(startMessage() + " attempting to send [" + message + "]...");
			output.writeObject(message);
			readConfirmation();
			System.out.println(startMessage() + " succesful!\n");
		}
		catch(IOException e){
			System.out.println(startMessage() + " FAILED!!\n");
		}
	}
	
	/**
	 * Reads and displays the confirmation that the server has correctly received the information sent from the client 
	 * @throws IOException
	 */
	public void readConfirmation() throws IOException{
		String buf;
		try {
			buf = (String)input.readObject();
			if(buf != null){
				System.out.println("[" + getCurrentTime() + "]<Server> Responded with [" + buf + "].");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the I/O Streams and the connection to the server
	 */
	public void closeStuff(){
		try {
			System.out.println(startMessage() + " closing connection...;");
			output.writeObject("END");
			input.close();
			output.close();
			clientSocket.close();
			System.out.println(startMessage() + " connection closed()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gets the current time on the client formated to HH:mm:SS
	 * @return String of the current time of the client.
	 */
	public String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return df.format(cal.getTime());
	}
	
	/**
	 * Creates a message consisting of [currentTime]<Client> for the client output messages.
	 * @return The opening string for client output messages.
	 */
	public String startMessage(){
		return "[" + getCurrentTime()+ "]<Client>";
	}
	
	
	public static void main(String[] args){
		Client c = new Client();
		c.setupConnection();
		//Just test strings at the moment, will need to build on this when we know what we are actually sending
		c.transferToServer("Message one");
		c.transferToServer("Message two");
		c.transferToServer("Message three");
		c.closeStuff();	
}
}
