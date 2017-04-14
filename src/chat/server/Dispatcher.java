package chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher extends Thread {

	private int port;
	private IChatMessageHub hub;
	
	public Dispatcher(int port, IChatMessageHub hub) {
		this.port = port;
		this.hub = hub;
		start();
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("waiting for connetion on port" + port);
			while (true) {
				try  {
					Socket s = serverSocket.accept();
					System.out.println("Neue Verbindung ");
					new ServerEndpoint(s, hub);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}






}
