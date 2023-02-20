package sc2008;

import java.io.*;
import java.net.*;

public class Rfc865UdpClient {

	static int QOTD_PORT = 17;

	public static void main(String[] argv) throws UnknownHostException {
		//
		// 1. Open UDP socket
		//
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket();
			InetAddress serverAddress = InetAddress.getByName("localhost");
//			InetAddress serverAddress = InetAddress.getByName("hwlab1.scse.ntu.edu.sg");
			System.out.println("Connecting to Server: " + serverAddress);
			

			// Initialize Connection to Server with address above
			socket.connect(serverAddress, QOTD_PORT);
		} catch (SocketException e) {
			System.out.println("Socket error: " + e.getMessage());
		}

		try {
			//
			// 2. Send UDP request to server
			//
			InetAddress serverAddress = InetAddress.getByName("localhost");
//			InetAddress serverAddress = InetAddress.getByName("hwlab1.scse.ntu.edu.sg");
			System.out.println("Sending Datagram to Server: " + serverAddress);
			InetAddress clientAddress = InetAddress.getByName("localhost");
			byte[] buffer = ((String) ("Information content from " + clientAddress + " Lester, A26, 172.21.150.148")).getBytes();

			// sending the packet above to the server, port 17 to get the QOTD back
			DatagramPacket request = new DatagramPacket(buffer, buffer.length, serverAddress, QOTD_PORT);
			socket.send(request);
			//
			// 3. Receive UDP reply from server
			//
			buffer = new byte[512];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			socket.receive(reply);

			// Decode the quote from byte to String and print it out.
			String quote = new String(buffer, 0, reply.getLength());
			System.out.println(quote);
		} catch (IOException e) {
			System.out.println("Client error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("Disconnecting...");
			socket.disconnect();
		}
	}
}
