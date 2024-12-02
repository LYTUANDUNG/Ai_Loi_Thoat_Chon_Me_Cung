package PSO;

import java.util.LinkedList;
import java.util.List;

import models.Point;

public class Particle {
	private Point[] maze;
	private List<Point> path;
	private Vector position;
	private Vector velocity;
	private Vector pBest;
	private Vector gBest;

	public void update() {
		updateVel();
	}

	public void updateGBest(Vector toUpdate) {
		this.gBest = toUpdate;
	}

	private void updatePBest() {
//		heuristic
	}

	private void updateVel() {
		Vector inertia = velocity.mul(PSOParams.W);
		Vector cognitive = pBest.sub(position).mul(PSOParams.Random.nextDouble() * PSOParams.C1);
		Vector social = gBest.sub(position).mul(PSOParams.Random.nextDouble() * PSOParams.C2);
		velocity = inertia.add(cognitive).add(social);
		updatePos();
	}

	private void updatePos() {
		Vector temp = position.add(velocity);
		Point tmp = path.getLast();
		tmp = new Point(tmp.getX(), tmp.getX());
		nextStep(sigmoid(temp), tmp);
		if (isValid(tmp)) {
			path.add(tmp);
			position = temp;
		} else {
			velocity.sub(new Vector(1, 1));
		}
		updatePBest();
	}

	private void nextStep(Point toAdd, Point curP) {
		if (toAdd.getX() == 0 && toAdd.getY() == 0) {
			curP.setX(curP.getY() + 1);
		}
		if (toAdd.getX() == 0 && toAdd.getY() == 1) {
			curP.setX(curP.getX() + 1);
		}
		if (toAdd.getX() == 1 && toAdd.getY() == 0) {
			curP.setX(curP.getX() - 1);
		}
		if (toAdd.getX() == 1 && toAdd.getY() == 1) {
			curP.setX(curP.getY() - 1);
		}
	}

	private Point sigmoid(Vector vector) {
		double x = 1 / (1 + Math.pow(Math.E, -vector.getX()));
		double y = 1 / (1 + Math.pow(Math.E, -vector.getY()));
		int nextX = 0;
		int nextY = 0;
		if (x < 1)
			x = 1;
		if (y < 1)
			y = 1;
		return new Point(nextX, nextY);
	}

	private boolean isValid(Point point) {
		for (Point p : maze) {
			if (p.equals(point)) {
				return !p.isWall();
			}
		}
		return false;
	}

	public List<Point> getPath() {
		List<Point> result = new LinkedList<Point>();
		for (int i = path.size() - 1; i > 0; i--) {
			Point p = path.get(i);
			int index = path.lastIndexOf(p);
			if (index != i)
				i = index;
			else
				result.addLast(p);
		}
		return result;

	}
}
