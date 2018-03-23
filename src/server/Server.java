package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

	ServerSocket server;
	int PORT = 444;

	static ArrayList<ServerClient> clients = new ArrayList<ServerClient>();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {

		try {
			server = new ServerSocket(PORT);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runnable connection_listener = () -> {
			while (true) {
				try {

					ServerClient client = new ServerClient(server.accept());
					clients.add(client);
					System.out.println("A CLIENT CONNECTED");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		new Thread(connection_listener).start();

		System.out.println("SERVER STARTED ON PORT " + PORT);

	}

}
