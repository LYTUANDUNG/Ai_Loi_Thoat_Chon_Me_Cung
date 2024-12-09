package PSO;

public class Vector {
	private double x;
	private double y;
	private double value;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Vector mul(double toMul) {
		return new Vector(x * toMul, y * toMul);
	}

	public Vector sub(Vector toSub) {
		return new Vector(x - toSub.getX(), y - toSub.getY());
	}

	public Vector add(Vector toAdd) {
		return new Vector(x + toAdd.getX(), y + toAdd.getY());
	}

	public Vector mul(Vector toMul) {
		return new Vector(x * toMul.getX(), y * toMul.getY());
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", value=" + value + "]";
	}
}
