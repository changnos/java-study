package prob05;

public class Sol05 {
	public static void main(String[] args) {
		for(int i = 1; i <= 100; i++) {
			String num = String.valueOf(i);
			int len = num.length();
			boolean chk = false;
			for(int j = 0; j < len; j++) {
				char c = num.charAt(j);
				if(c == '3' || c == '6' || c == '9') chk = true;
			}
			if(chk) System.out.println(i + " 짝");
		}
	}
}
