package socket_connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client_connect {

	private PrintStream out;
	private BufferedReader in;
	private Scanner sc;

	public static void main(String[] args) {
		new Client_connect();
	}

	public Client_connect() {

		sc = new Scanner(System.in);

		try {
			System.out.println("CONNECTING TO SERVER..");
			Socket socket = new Socket("localhost", 444);

			out = new PrintStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.print("Skriv ditt namn: ");
			out.println(sc.nextLine());
			System.out.println(in.readLine());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Runnable input_r = () -> {

			while (true) {
				String input;
				try {
					input = in.readLine();
					System.out.println(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		new Thread(input_r).start();

		Runnable output_r = () -> {

			while (true) {

				String output = sc.nextLine();
				out.println(output);

			}

		};

		new Thread(output_r).start();

	}

}
