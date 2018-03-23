package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConnectingClient {

	BufferedReader from_server;
	PrintStream to_server;

	Scanner sc;
	Socket connection;

	public static void main(String[] args) {
		new ConnectingClient();
	}

	public ConnectingClient() {

		sc = new Scanner(System.in);

		try {

			connection = new Socket("localhost", 444);

			from_server = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			to_server = new PrintStream(connection.getOutputStream());

			System.out.print("SKRIV IN DITT NAMN: ");
			String name = sc.nextLine();
			to_server.println(name);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runnable input_listener = () -> {
			while (connection != null) {

				if (sc.hasNextLine()) {
					String input = sc.nextLine();
					to_server.println(input);
				}

			}
		};

		Runnable output_listener = () -> {

			while (true) {
				try {
					System.out.println(from_server.readLine());
				} catch (IOException e) {
					System.out.println("DISCONNETED FROM SERVER.\nPRESS ENTER TO EXIT... ");
					connection = null;
					break;
				}
			}
		};

		new Thread(output_listener).start();
		new Thread(input_listener).start();

	}

}
