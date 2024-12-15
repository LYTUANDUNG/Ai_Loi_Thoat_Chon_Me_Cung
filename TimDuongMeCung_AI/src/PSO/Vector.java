package PSO;

import java.util.Arrays;

public class Vector {
	private int dimension;
	private double[] pos;

	private int value;

	public Vector(int dimension) {
		this.dimension = dimension;
		this.pos = new double[] { Math.random(), Math.random()};
	}

	public Vector(Vector that) {
		this.pos = new double[] { that.pos[0], that.pos[1] };
		this.value = that.value;
	}

	public Vector(double[] pos, int value) {
		this.pos = pos;
		this.value = value;
	}

	public Vector(double[] pos) {
		super();
		this.pos = pos;
	}

	public double getDimensionValue(int dimension) {
		return pos[dimension];
	}

	public void setDimensionValue(int dimension, double val) {
		this.pos[dimension] = val;
	}

	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Vector [dimension=" + dimension + ", pos=" + Arrays.toString(pos) + ", value=" + value + "]";
	}

}
