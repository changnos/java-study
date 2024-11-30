package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickname;
	private Socket socket;

	List<Writer> listWriters;

	// 닉네임 저장을 위한 리스트
	List<String> nicknames;

	public ChatServerThread(Socket socket, List<Writer> listWriters, List<String> nicknames) {
		this.socket = socket;
		this.listWriters = listWriters;
		this.nicknames = nicknames;
	}

	@Override
	public void run() {
		try {
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();

			ChatServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			while (true) {
				String request = br.readLine();
				if (request == null) {
					ChatServer.log("클라이언트로 부터 연결 끊김");
					doQuit(pw);
					break;
				} else {
					ChatServer.log("received: " + request);

					String[] tokens = request.split(":");

					if ("join".equals(tokens[0])) {
						doJoin(tokens[1], pw);
					} else if ("message".equals(tokens[0])) {
						doMessage(tokens[1]);
					} else if ("quit".equals(tokens[0])) {
						doQuit(pw);
						break;
					} else {
						ChatServer.log("에러:알수 없는 요청(" + tokens[0] + ")");
					}
				}
			}

		} catch (SocketException e) {
			ChatServer.log("Socket Exception: " + e);
		} catch (IOException e) {
			ChatServer.log("error: " + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doJoin(String nickName, PrintWriter writer) {

		// 닉네임 중복 체크
		boolean chk = true;
		synchronized (nicknames) {
			for (String nick : nicknames) {
				if (nick.equals(nickName)) {
					chk = false;
					break;
				}
			}
		}

		if (chk) {
			this.nickname = nickName;

			String data = nickName + "님이 참여하였습니다.";
			broadcast(data);

			/* writer pool에 저장 */
			addWriter(writer);

			// 닉네임 배열에 추가
			nicknames.add(nickName);
			System.out.println("현재 인원: " + listWriters.size());

			// ack
			writer.println("join:ok");
			writer.flush();
		} else {
			writer.println("join:false");
			writer.flush();
		}
	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void doMessage(String message) {
		String decodedMessage = new String(Base64.getDecoder().decode(message.getBytes()));
		String data = nickname + ":" + decodedMessage;
		broadcast(data);
	}

	private void doQuit(PrintWriter writer) {
		removeWriter(writer);

		String data = nickname + "님이 퇴장 하였습니다.";
		broadcast(data);

		// ack
		writer.println("quit:ok");
		writer.flush();
	}

	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}

	private void broadcast(String data) {

		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}

		}

	}

}
