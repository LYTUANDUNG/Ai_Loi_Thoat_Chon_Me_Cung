package models;

public class AdapterFiding implements IAdapter {
	private IAlogrithm alogrithm;

	public Point[] execute(Point[] points) {
		return alogrithm.execute(points);
	}

	@Override
	public void setAlogrithm(IAlogrithm alogrithm) {
		this.alogrithm = alogrithm;
	}
}
