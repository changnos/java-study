package prob04;

public class MyStack02 {
	private int top;
	private Object[] buffer;

	public MyStack02(int capacity) {
		top = -1;
		buffer = new String[capacity];
	}

	public void push(Object s) {
		if(top == buffer.length-1) {
			resize();
		}
		buffer[++top] = s; 
	}

	public Object pop() throws MyStackException {
		if (top == -1) {
			throw new MyStackException();
		}
		return buffer[top--];
	}

	public boolean isEmpty() {
		/* 구현하기 */
		return (top == -1);
	}

	private void resize() {
		/* 구현하기 */

		// 사이즈 2배로 늘리기 - 2배 배열 만들고 복
		Object[] buffer_tmp = new Object[buffer.length*2];
		for(int i = 0; i < buffer.length; i++) buffer_tmp[i] = buffer[i];
		buffer = buffer_tmp;
	}
}