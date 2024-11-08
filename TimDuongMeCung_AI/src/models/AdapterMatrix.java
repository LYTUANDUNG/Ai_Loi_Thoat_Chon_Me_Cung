package models;

public class AdapterMatrix implements IAdapter {
	private IAlogrithm alogrithm;

	@Override
	public Point[] execute() {
		// TODO Auto-generated method stub
		return alogrithm.execute();
	}

}
