package PSO;

import models.Point;

public class FitnessDetails {
	private int row = 23;
	private int col = 31;
	private Point[] maze;

	public FitnessDetails(Point[] maze) {
		this.maze = maze;
	}

	private int getIndex(int x, int y) {
		return y * col + x;

	}

	public int checkPoint(Point p) {
		int sum = 0;
		if (p.getX() ==col - 1 || maze[getIndex(p.getX() + 1, p.getY())].isWall()) {
			sum += 1;
		}
		if (p.getX() ==0  || maze[getIndex(p.getX() - 1, p.getY())].isWall()) {
			sum += 1;
		}
		if (p.getY() == row - 1 || maze[getIndex(p.getX(), p.getY() + 1)].isWall()) {
			sum += 1;
		}
		if (p.getY() == 0 || maze[getIndex(p.getX(), p.getY() - 1)].isWall()) {
			sum += 1;
		}
		
		return sum;
	}

	public int eval(Point p, Point goal) {
		return Math.abs(p.getX() - goal.getX()) + Math.abs(p.getY() - goal.getY())+checkPoint(p);
	}

}
