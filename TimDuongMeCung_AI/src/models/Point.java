package models;

public class Point {
	private int x;
	private int y;
	private StatePoint state;

	public Point(int x, int y) {
		this(x, y, StatePoint.Passage);
	}

	public Point(int x, int y, StatePoint state) {
		this.x = x;
		this.y = y;
		this.state = state;
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

	public boolean isWall() {
		return this.state.equals(StatePoint.Wall);
	}

	public boolean isPassage() {
		return this.state.equals(StatePoint.Passage);
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public String toString() {
		return "Point{" + "x=" + x + ", y=" + y + ", state=" + state + '}';

	}

}