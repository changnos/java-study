package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("> ");
				String host = scanner.nextLine();
				if ("exit".equals(host)) {
					break;
				}

				InetAddress[] InetAddresses = InetAddress.getAllByName(host);

				for (InetAddress InetAddress : InetAddresses) {
					System.out.println(host + " : " + InetAddress.getHostAddress());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		scanner.close();
	}

}
