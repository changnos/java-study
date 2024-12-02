package chat.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;
import java.util.Scanner;

import chat.ChatServer;

public class ChatWindow {
	private static final String SERVER_IP = "127.0.0.1";

	PrintWriter pw;

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private static TextArea textArea;

	private String nickname;

	public ChatWindow(String name, String nickname) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		 textArea = new TextArea(30, 80);

		this.nickname = nickname;
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.BLACK);
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("clicked");
				sendMessage();
			}
		});
//		buttonSend.addActionListener(actionEvent -> {});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (keyChar == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack();

		Socket socket = null;

		try {
			// 1. 서버 연결 작업

			// 2. IO Stream Set
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, ChatServer.PORT));

			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			// 3. JOIN Protocol
			pw.println("join:" + nickname);
			String response = br.readLine();

			if ("join:ok".equals(response)) {
				updateTextArea(nickname);
				updateTextArea("채팅방 입장에 성공하였습니다.");
			}

			// 4. ChatClientThread 생성
			ChatClientThread chatClientThread = new ChatClientThread(socket);
			chatClientThread.start();
		} catch (IOException e) {
			log("error: " + e);
		}
	}

	private void sendMessage() {
		String message = textField.getText();
		
		if (!("".equals(message))) {
			String EncodedLine = Base64.getEncoder().encodeToString(message.getBytes());
			pw.println("message:" + EncodedLine);
		}

		textField.setText("");
		textField.requestFocus();
	}

	public static void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}

	private void finish() {
		// quit protocol 구현
		pw.println("quit");

		// exit java application
		System.exit(0);
	}

	private class ChatClientThread extends Thread {

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
					updateTextArea(request);
				}

			} catch (SocketException e) {
				ChatServer.log("Socket Exception: " + e);
			} catch (IOException e) {
				ChatServer.log("error: " + e);
			}
		}

	}

	public static void log(String message) {
		System.out.println("[Chat Window] " + message);
	}
}
