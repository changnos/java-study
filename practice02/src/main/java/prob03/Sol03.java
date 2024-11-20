package prob03;

public class Sol03 {
	public static void main(String args[]) {
		char c[] = { 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 'p', 'e', 'n', 'c', 'i', 'l', '.' };
		
		// 원래 배열 원소 출력
		printCharArray(c);

		// 공백 문자 바꾸기
		replaceSpace(c);

		// 수정된 배열 원소 출력
		printCharArray(c);
	}
	
	public static void printCharArray(char [] c) {
		int len = c.length;
		for(int i = 0; i < len; i++) System.out.print(c[i]);
		System.out.println();
	}
	
	public static void replaceSpace(char [] c) {
		int len = c.length;
		for(int i = 0; i < len; i++) if(c[i] == ' ') c[i] = ',';
	}
}