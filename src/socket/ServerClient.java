package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerClient {
	
	private Socket socket;
	private PrintStream out;
	private BufferedReader in;
	private String name;
	
	public ServerClient(Socket socket) {
		this.socket = socket;
		
		try {
			out = new PrintStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			name = in.readLine();
			System.out.println( name + " CONNECTED");
			out.println("Välkommen " + name);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Runnable input_r = () -> {

			while (true) {
				String input;
				try {
					input = in.readLine();
					System.out.println(name + " WROTE: " + input);
					Server.messageAllClients(name + ": " + input);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};

		new Thread(input_r).start();

		
	}
	
	public void printMessage(String msg){
		out.println(msg);
	
	}
	
	public String getName(){
		return this.name;
	}

}
