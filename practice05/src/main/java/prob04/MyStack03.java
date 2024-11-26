package prob04;

public class MyStack03<T> {
	private int top;
	private T[] buffer;

	@SuppressWarnings("unchecked")
	public MyStack03(int capacity) {
		top = -1;
		buffer = (T[]) new String[capacity];
	}

	public void push(T s) {
		if (top == buffer.length - 1) {
			resize();
		}
		buffer[++top] = s;
	}

	public T pop() throws MyStackException {
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
		@SuppressWarnings("unchecked")
		T[] buffer_tmp = (T[]) new Object[buffer.length * 2];
		for (int i = 0; i < buffer.length; i++)
			buffer_tmp[i] = buffer[i];
		buffer = buffer_tmp;
	}
}