package models;

public class Point {
	private int x;
	private int y;
	/**
	 * Trạng thái của ô
	 */
	private StatePoint state;

	public Point(int x, int y, StatePoint state) {
		super();
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}



	public StatePoint getState() {
		return state;
	}

}
