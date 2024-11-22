package prob05;

public class Rectangle extends Shape implements Resizable {

	public Rectangle(int w, int h) {
		width = w;
		height = h;
	}

	@Override
	public double getArea() {
		return width * height;
	}

	@Override
	public double getPerimeter() {
		return (width + height) * 2;
	}

	@Override
	public void resize(double rate) {
		width *= rate;
		height *= rate;
	}
}
