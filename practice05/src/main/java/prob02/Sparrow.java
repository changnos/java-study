package prob02;

public class Sparrow extends Bird {
	public void fly() {
		System.out.println("참새(" + this.getName() + ")가 날아다닙니다.");
	}
	
	public void sing() {
		System.out.println("참새(" + this.getName() + ")가 소리내어 웁니다.");
	}
	
	@Override
	public String toString() {
		return "참새의 이름은 " + this.getName() + " 입니다.";
	}
}
