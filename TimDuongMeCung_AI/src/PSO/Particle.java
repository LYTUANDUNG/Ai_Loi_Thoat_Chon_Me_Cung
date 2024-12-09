package PSO;

import java.util.ArrayList;
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
	private FitnessDetails fitness;
	private Vector goal;
	private int dimension;

	public Particle(Point[] maze, int dimension) {
		this.maze = maze;
		this.dimension = dimension;
		fitness = new FitnessDetails();
		position = new Vector(dimension);
		Point exit = maze[maze.length - 1];
		goal = new Vector(new double[] { exit.getX(), exit.getY() });
		position.setValue(objective(position));
		pBest = position;
		velocity = new Vector(new double[] { PSOConfig.RANDOM.nextDouble(), PSOConfig.RANDOM.nextDouble() });
		path = new ArrayList<Point>();
		path.add(maze[0]);
	}

	public void update(int dimension) {
		updateVel(dimension);
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

	private void updateVel(int dimension) {
		double inertia = velocity.getDimensionValue(dimension) * PSOConfig.INERTIA_WEIGHT;
		double cognitive = (pBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble() * PSOConfig.COGNITIVE_COEFFICIENT);
		double social = (gBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble() * PSOConfig.SOCIAL_COEFFICIENT);
		velocity.setDimensionValue(dimension, inertia + cognitive + social);
		updatePos(dimension);
	}

	private void updatePos(int dimension) {
		position.setDimensionValue(dimension, velocity.getDimensionValue(dimension));
		Point tmp = new Point(path.getLast().getX(), path.getLast().getY());
		nextStep(sigmoid(position,dimension), tmp,dimension);
		if (isValid(tmp)) {
			path.add(tmp);
		} else {
			if (path.size() != 1) {
				Point prev = path.get(path.size() - 2);
				adjustVelocity(tmp, prev,dimension);
			}
		}
		limitVelocity(dimension);
		updatePBest(position);
	}

	private void limitVelocity(int dimension) {
		if (velocity.getDimensionValue(dimension) > PSOConfig.MAX_VELOCITY) {
			velocity.setDimensionValue(dimension, PSOConfig.MIN_VELOCITY);
		} else if (velocity.getDimensionValue(dimension) < PSOConfig.MIN_VELOCITY) {
			velocity.setDimensionValue(dimension, PSOConfig.MAX_VELOCITY);
		}
	}

	private void adjustVelocity(Point tmp, Point prev, int dimension) {
		if (dimension == 0)
			if (tmp.getX() - prev.getX() > 0) {
				velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) - 1);
			} else {
				velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) + 1);
			}
		else if (dimension == 1)
			if (tmp.getY() - prev.getY() > 0) {
				velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) - 1);
			} else {
				velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) + 1);
			}
	}

	private void nextStep(int toAdd, Point curP, int dimension) {
		if (dimension == 0)
			if (toAdd == 0) {
				curP.setX(curP.getX() - 1);
			} else
				curP.setX(curP.getX() + 1);
		if (dimension == 1)
			if (toAdd == 0) {
				curP.setY(curP.getY() - 1);
			} else
				curP.setY(curP.getY() + 1);

	}

	private int sigmoid(Vector vector, int dimemsion) {
		return (vector.getDimensionValue(dimemsion) > 0) ? 1 : 0;

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
