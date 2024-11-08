package models;

public class AdapterMatrix implements IAdapter{
	private IAlogrithm alogrithm;

	@Override
	public Point[] execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public IAlogrithm getAlogrithm() {
		return alogrithm;
	}

	public void setAlogrithm(IAlogrithm alogrithm) {
		this.alogrithm = alogrithm;
	}

}
