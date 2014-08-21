package server;

import java.net.*;
import java.io.*;


public class Server {
	
	public static void main(String[] args){
	
	//port number for the socket
	final int portNumber = 8888;
	
	try {
		//the socket to be used for server
		ServerSocket myServerSocket = new ServerSocket(portNumber);
		
		//wait for incoming connection
		System.out.println("Server is waiting for an incoming connection to host = "
				+ InetAddress.getLocalHost().getCanonicalHostName()
				+ ", port = " + myServerSocket.getLocalPort());
		//blocks the server until a client makes a connection to this port
		Socket skt = myServerSocket.accept();
		
		//connection established, using java.io to help transfer information
		BufferedReader myInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		PrintStream myOutput = new PrintStream(skt.getOutputStream());
		
		
		//attempt to read from the socket connection
		String buf = myInput.readLine();
		
		//prints the message that was sent to the server
		if(buf != null){
			System.out.println("Server has read [" + buf + "]" );
			myOutput.print("Got it");
		}
		
		//close connection
		skt.close();
		System.out.println("Server is exiting");
		myServerSocket.close();
		
	}
	
	catch (IOException ex) {
		ex.printStackTrace();
		System.out.println("Whoops, something bad has happened!");
	}
	
	
}
}