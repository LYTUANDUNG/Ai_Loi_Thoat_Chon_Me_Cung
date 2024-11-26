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

	public void setState(StatePoint state) {
		this.state = state;
	}

	public boolean isWall() {
		return this.state.equals(StatePoint.Wall);
	}

	public void setPassage() {
		setState(StatePoint.Passage);
	}

	public void setWall() {
		setState(StatePoint.Wall);
	}

	public void setExit() {
		setState(StatePoint.Exit);
	}

	public void setEntrance() {
		setState(StatePoint.Entrance);
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", state=" + state + "]";
	}

	public boolean isPassage() {
		return this.state.equals(StatePoint.Passage);
	}

}