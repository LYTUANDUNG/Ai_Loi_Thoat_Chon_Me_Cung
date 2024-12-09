package PSO;

public class Vector {
	private int dimension;
	private double[] pos;
	private double value;

	public Vector(int dimension) {
		this.dimension=dimension;
		this.pos = new double[]{0,0};
		value=0;
	}
	

	public Vector(double[] pos, double value) {
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



	public void setDimensionValue(int dimension,double val) {
		this.pos[dimension] = val;
	}



	public double[] getPos() {
		return pos;
	}



	public void setPos(double[] pos) {
		this.pos = pos;
	}



	public double getValue() {
		return value;
	}



	public void setValue(double value) {
		this.value = value;
	}


}
