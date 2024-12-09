package PSO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import models.PSO;
import models.Point;

public class Particle {
	private Point[] maze;
	private List<Point> path;
	private Vector position;
	private Vector velocity;
	private Vector pBest;
	private Vector gBest;
	private FitnessDetails fitness;
	private Vector goal;

	public Particle(Point[] maze) {
		this.maze = maze;
		fitness = new FitnessDetails();
		position = new Vector(maze[0].getX(), maze[0].getY());
		goal = new Vector(maze[maze.length - 1].getX(), maze[maze.length - 1].getY());
		position.setValue(objective(position));
		pBest = position;
		velocity = new Vector(PSOConfig.RANDOM.nextDouble(-0.5, 0.5), PSOConfig.RANDOM.nextDouble(-0.5, 0.5));
		path = new ArrayList<Point>();
		path.add(maze[0]);
	}

	public void update() {
		updateVel();
	}

	public void updateGBest(Vector toUpdate) {
		this.gBest = toUpdate;
	}

	private double objective(Vector v) {
		return fitness.eval(v, goal);
	}

	private void updatePBest(Vector v) {
		double val = objective(v);
		v.setValue(val);
		if (val < pBest.getValue()) {
			pBest = v;
		}
	}

	private void updateVel() {
		Vector inertia = velocity.mul(PSOConfig.INERTIA_WEIGHT);
		Vector cognitive = pBest.sub(position).mul(PSOConfig.RANDOM.nextDouble() * PSOConfig.COGNITIVE_COEFFICIENT);
		Vector social = gBest.sub(position).mul(PSOConfig.RANDOM.nextDouble() * PSOConfig.SOCIAL_COEFFICIENT);
		velocity = inertia.add(cognitive).add(social);
		updatePos();
	}

	private void updatePos() {
		Vector temp = position.add(velocity);
		Point tmp = new Point(path.getLast().getX(), path.getLast().getY());
		nextStep(sigmoid(temp), tmp);
		if (isValid(tmp)) {
			path.add(tmp);
			position = temp;
		} else {
			if (path.size() != 1) {
				Point prev = path.get(path.size() - 2);
				adjustVelocity(tmp, prev);
			}
		}
		limitVelocity();
		updatePBest(temp);
	}

	private void limitVelocity() {
		if (velocity.getX() > PSOConfig.MAX_VELOCITY.getX()) {
			velocity.setX(PSOConfig.MAX_VELOCITY.getX());
		} else if (velocity.getX() < PSOConfig.MIN_VELOCITY.getX()) {
			velocity.setX(PSOConfig.MIN_VELOCITY.getX());
		}

		if (velocity.getY() > PSOConfig.MAX_VELOCITY.getY()) {
			velocity.setY(PSOConfig.MAX_VELOCITY.getY());
		} else if (velocity.getY() < PSOConfig.MIN_VELOCITY.getY()) {
			velocity.setY(PSOConfig.MIN_VELOCITY.getY());
		}
	}
0 0
	private void adjustVelocity(Point tmp, Point prev) {
		if (tmp.getX() - prev.getX() > 0) {
			velocity.sub(new Vector(0,1));
		} else {
			velocity.sub(new Vector(0, -1));
		}

		if (tmp.getY() - prev.getY() > 0) {
			velocity.sub(new Vector(0, 2));
		} else {
			velocity.sub(new Vector(0, -2));
		}
	}

	private void nextStep(Point toAdd, Point curP) {
		if (toAdd.getX() == 0 && toAdd.getY() == 0) {
			curP.setX(curP.getX() - 1);
		} else if (toAdd.getX() == 1 && toAdd.getY() == 1) {
			curP.setX(curP.getX() + 1);
		} else if (toAdd.getX() == 1 && toAdd.getY() == 0) {
			curP.setY(curP.getY() - 1);
		} else if (toAdd.getX() == 0 && toAdd.getY() == 1) {
			curP.setY(curP.getX() + 1);
		}
	}

	private Point sigmoid(Vector vector) {
		int nextX = (vector.getX() > 0) ? 1 : 0;
		int nextY = (vector.getY() > 0) ? 1 : 0;
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
		for (int i = 0; i < path.size(); i++) {
			Point p = path.get(i);
			int index = result.indexOf(p);
			if (index != -1) {
				result = result.subList(0, index);
			}
			result.add(p);
		}
		return result;

	}

	public Vector getpBest() {
		return pBest;
	}

	public void setpBest(Vector pBest) {
		this.pBest = pBest;
	}

	public Vector getgBest() {
		return gBest;
	}

	public void setgBest(Vector gBest) {
		this.gBest = gBest;
	}
}
