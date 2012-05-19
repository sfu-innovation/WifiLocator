import java.io.*;
import java.net.*;

class Client {
	public static void main(String argv[]) throws Exception {
		// connect to server
		Socket clientSocket = new Socket("localhost", 7896);
		
		// open message steams
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		// send message
		System.out.print("Enter Message: ");
		String sentence = inFromUser.readLine();
		outToServer.writeBytes(sentence + '\n');
		
		// recieve message
		String modifiedSentence = inFromServer.readLine();
		System.out.println("FROM SERVER: " + modifiedSentence);
		
		// close connection
		clientSocket.close();
	}
}