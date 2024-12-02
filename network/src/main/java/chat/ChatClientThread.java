package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import chat.gui.ChatWindow;

public class ChatClientThread extends Thread {

	private Socket socket;

	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			while (true) {
				String request = br.readLine();

				if (request == null) {
					break;
				} else if ("quit:ok".equals(request)) {
					break;
				}
				System.out.println(request);
			}

		} catch (SocketException e) {
			ChatServer.log("Socket Exception: " + e);
		} catch (IOException e) {
			ChatServer.log("error: " + e);
		}
	}

}
