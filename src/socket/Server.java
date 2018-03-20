package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

	private static ArrayList<ServerClient> clients = new ArrayList<ServerClient>();

	private final int PORT = 444;
	private ServerSocket server;

	public Server() {

		System.out.println("STARTING SERVER...");

		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("COULDN'T START SERVER");
			System.exit(0);
		}

		Runnable r = () -> {

			while (true) {
				try {
					ServerClient client = new ServerClient(server.accept());
					clients.add(client);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};

		new Thread(r).start();

		System.out.println("SERVER STARTED ON PORT: " + PORT);
	}

	public static void messageAllClients(String msg) {

		for (ServerClient serverClient : clients) {
			serverClient.printMessage(msg);
		}

	}
}
