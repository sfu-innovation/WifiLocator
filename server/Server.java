import java.io.*;
import java.net.*;

class Server {
	public static void main(String args[]) throws Exception {
		// get command line arguments
		String ipaddress = "127.0.0.1";
		if (args.length > 0) ipaddress = args[0];
		int serverPort = 7896;
		if (args.length > 1) serverPort = Integer.parseInt(args[1]);
		
		System.out.println("-------------------------");
		System.out.println("WifiLocator Server");
		System.out.println("Listening on: " + ipaddress + ":" + serverPort);
		System.out.println("-------------------------");
		
		try{
			ServerSocket listenSocket = new ServerSocket(
					serverPort, 0, InetAddress.getByName(ipaddress));
			while(true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		}
	}
}