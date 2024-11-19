package prob04;

import java.util.Scanner;

public class Sol04 {

	public static void main(String[] args) {
		System.out.print("문자열을 입력하세요 : ");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		int len = str.length();
		for(int i = 1; i <= len; i++) {
			for(int j = 0; j < i; j++) System.out.print(str.charAt(j));
			System.out.println();
		}
		
		scanner.close();
	}
}