package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerClient {

	BufferedReader from_client;
	PrintStream to_client;
	String name;

	public ServerClient(Socket connection) {

		try {

			from_client = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			to_client = new PrintStream(connection.getOutputStream());

			name = from_client.readLine();
			to_client.println("WELCOME " + name);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runnable input_listener = () -> {
			while (true) {
				try {
					String input = from_client.readLine();

					if (input.charAt(0) == '/') {
						String cmd = input.substring(1).toLowerCase();
						if (cmd.equals("count")) {

							for (int i = 1; i <= 10; i++) {
								to_client.println(i);
							}

						} else if (cmd.equals("ping")) {
							to_client.println("pong");
						} else {
							to_client.println("NOT A COMMAND");
						}

					} else {
						for (ServerClient client : Server.clients) {
							if (client != this) {
								client.toClient(name + ": " + input);
							}
						}
					}

				} catch (IOException e) {
					// Tappat connection
					break;
				}
			}
		};

		new Thread(input_listener).start();

	}

	public void toClient(String msg) {
		to_client.println(msg);
	}

	public String getName() {
		return this.name;
	}

}
