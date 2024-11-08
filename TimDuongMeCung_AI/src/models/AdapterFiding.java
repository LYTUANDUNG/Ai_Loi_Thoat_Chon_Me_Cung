package models;

public class AdapterFiding implements IAlogrithm {
	private IAlogrithm alogrithm;

	public AdapterFiding(IAlogrithm alogrithm) {
		this.alogrithm = alogrithm;
	}

	public Point[] execute() {
		return alogrithm.execute();
	}
}
