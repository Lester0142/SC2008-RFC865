package sc2008;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Rfc865UdpServer {

	static int QOTD_PORT = 17;
	static String QUOTE = "RFC 865 Quote of the Day! - ";

	public static void main(String[] argv) {
		//
		// 1. Open UDP socket at well-known port
		//
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(QOTD_PORT);
			System.out.println("UDP socket is open...");
		} catch (SocketException e) {
			System.out.println("Socket error: " + e.getMessage());
		}
		while (true) {
			try {
				System.out.println("Listening to UDP port...");
				//
				// 2. Listen for UDP request from client
				//
				byte[] buf = new byte[512];
				DatagramPacket request = new DatagramPacket(buf, buf.length);
				socket.receive(request);
				/* Process the request */
				String requestContent = new String(buf, 0, request.getLength());
				System.out.println("Received request: " + requestContent);
				

				/* IP of client */
				InetAddress clientAddress = request.getAddress();
				int clientPort = request.getPort();
				System.out.println("From client: " + clientAddress);

				String replyContent = QUOTE + new java.util.Date();
				byte[] replyBuf = replyContent.getBytes("UTF-8");
				System.out.println("Reply content: " + replyContent);

				//
				// 3. Send UDP reply to client
				//
				DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length, clientAddress, clientPort);
				socket.send(reply);
				//if (reply != null) break;
			} catch (IOException e) {
				System.out.println("Server error: " + e.getMessage());
			}
		}
//		System.out.println("server closing...");
//		socket.close();
	}
}
