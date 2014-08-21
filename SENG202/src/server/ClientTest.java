package server;

import java.net.*;
import java.io.*;
public class ClientTest {
	
	
	
	public static void main(String[] args){
		
	//port number and host name	
	int portNumber = 8888;
	String host = "localhost";
	
	//try to open a connection and send the string "Hello World"
	try {
		//message to show that the client is trying to conact the server
		System.out.println("Client will attempt to contact server " + host + " on port " + portNumber);
		
		//socket that will be used for the connection
		Socket skt = new Socket(host, portNumber);
		
		//once a connection is established uses some streams
		BufferedReader myInput = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		PrintStream myOutput = new PrintStream(skt.getOutputStream());
		
		//write something to the server
		myOutput.print("Hello World\n"); //string that is sent to the server
		String buf = myInput.readLine();
		if(buf != null){
			System.out.println("The server responded with [" + buf + "]");
		}
		
		//close the socket
		skt.close();
		System.out.println("Client is exiting");
			
		}
	
	catch (IOException ex) {
		ex.printStackTrace();
		System.out.println("Uh oh something wrong happened");
	}
	
}
}
