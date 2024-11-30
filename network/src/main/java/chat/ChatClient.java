package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "127.0.0.1";

	public static String nickname = "";

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;

		try {
			scanner = new Scanner(System.in);
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, ChatServer.PORT));

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			// 빈칸 입력 방지
			inputNickname(scanner);

			pw.println("join:" + nickname);
			String response = br.readLine();

			// 닉네임 중복 방지
			while ("join:false".equals(response)) {
				System.out.println("닉네임을 다시 입력해주세요.(이미 등록된 닉네임입니다.)");
				nickname = "";
				inputNickname(scanner);
				pw.println("join:" + nickname);
				response = br.readLine();
			}

			if ("join:ok".equals(response)) {
				System.out.println("채팅방 입장에 성공하였습니다.");
			}

			ChatClientThread chatClientThread = new ChatClientThread(socket);
			chatClientThread.start();

			while (true) {
				String line = scanner.nextLine();

				if ("quit".equals(line)) {
					pw.println("quit");

					try {
						chatClientThread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					break;
				}

				if (!("".equals(line))) {
					String EncodedLine = Base64.getEncoder().encodeToString(line.getBytes());
					pw.println("message:" + EncodedLine);
				}
			}
		} catch (IOException e) {
			log("error: " + e);
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}

				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void inputNickname(Scanner scanner) {
		while (!isValidNickname(nickname)) {
			System.out.print("닉네임을 입력해주세요: ");
			nickname = scanner.nextLine();
			if (isValidNickname(nickname))
				break;
			System.out.println("닉네임을 다시 입력해주세요.(공백이 포함되어 있으면 안됩니다.)");
		}
	}

	private static boolean isValidNickname(String nickname) {
		return !("".equals(nickname) || nickname.contains(" "));
	}

	public static void log(String message) {
		System.out.println("[Chat Client] " + message);
	}
}
