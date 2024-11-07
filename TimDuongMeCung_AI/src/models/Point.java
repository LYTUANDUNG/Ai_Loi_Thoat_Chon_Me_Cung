package models;

import data.StatePoint;

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
		this.state = StatePoint.Entrance;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public StatePoint getState() {
		return state;
	}

	public void setState(StatePoint state) {
		this.state = state;
	}
}
