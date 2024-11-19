package prob03;

import java.util.Scanner;

public class Sol03 {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("수를 입력하세요 : ");
			int num = scanner.nextInt();
			if(num < 0) {
				System.out.println("프로그램이 종료되었습니다.");
				break;
			} // scanner.close() 라인 접근을 위해 임의로 break 설정
			int left = num%2;
			int sum = 0;
			for(int i = 1; i <= num; i++) if(i%2 == left) sum += i;
			System.out.println("결과값 : " + sum);
		}
		scanner.close();
	}
}
