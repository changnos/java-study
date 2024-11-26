package prob01;

public class Printer {

	public <T> void println(T t) {
		System.out.println(t);
	}
	
	public <T> void println(T... ts) {
		for(T t : ts) {
			System.out.println(t + " ");
		}
		
		System.out.println("\n");
	}

	public int sum(Integer... nums) {
		int s = 0;
		for(Integer n : nums) {
			s += n;
		}
		return s;
	}

//	public void println(int n) {
//		System.out.println(n);
//	}
//	
//	public void println(boolean b) {
//		System.out.println(b);
//	}
//	
//	public void println(double d) {
//		System.out.println(d);
//	}
//	
//	public void println(String s) {
//		System.out.println(s);
//	}

}
