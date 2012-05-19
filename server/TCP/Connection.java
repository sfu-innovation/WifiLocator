import java.io.*;
import java.net.*;

class Connection extends Thread {
	private Socket clientSocket;

	public Connection(Socket clientSocket) {
		this.clientSocket = clientSocket;
		start();
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			
			// recieve messge
			String clientSentence = in.readLine();
			System.out.println("Received: " + clientSentence);
			
			// send message
			String capitalizedSentence = clientSentence.toUpperCase() + '\n';
			out.writeBytes(capitalizedSentence);
			
			// close connection
			in.close();
			out.close(); 
			clientSocket.close();
			System.out.println("Connection Closed");
		} catch (IOException e) {
	         System.err.println("Problem with Communication Server");
	         System.exit(1); 
	    }
	}
}