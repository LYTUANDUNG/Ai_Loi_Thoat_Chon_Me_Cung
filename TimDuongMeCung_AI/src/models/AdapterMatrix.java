package models;

public class AdapterMatrix implements IAdapter {
	private IAlogrithm alogrithm;

	public AdapterMatrix() {
		alogrithm = new RecursiveMatrix();
	}

	@Override
	public Point[] execute(Point[] points) {
		// TODO Auto-generated method stub
		return alogrithm.execute(points);
	}

	@Override
	public void setAlogrithm(IAlogrithm alogrithm) {
		this.alogrithm = alogrithm;
	}

}
