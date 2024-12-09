package PSO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.Point;

public class Particle {
	private Point[] maze;
	private Set<Point> historyPath;
	private List<Point> path;
	private Vector position;
	private Vector velocity;
	private Vector pBest;
	private Vector gBest;
	private FitnessDetails fitness;
	private Point goal;
	private int dimension;

	public Particle(Point[] maze, int dimension) {
		historyPath = new HashSet<>();
		this.maze = maze;
		this.dimension = dimension;
		fitness = new FitnessDetails(maze);
		position = new Vector(dimension);
		Point exit = maze[maze.length - 1];
		goal = exit;
		position.setValue(objective(maze[0]));
		pBest = new Vector(position);
		velocity = new Vector(new double[] { PSOConfig.RANDOM.nextDouble(-2, 2), PSOConfig.RANDOM.nextDouble(-2, 2) });
		path = new ArrayList<Point>();
		path.add(maze[0]);
	}

	public void update(int dimension) {
		updateVel(dimension);
		updatePos(dimension);
		updatePBest(position);
	}

	public void updateGBest(Vector toUpdate) {
		gBest = new Vector(toUpdate);
	}

	private int objective(Point p) {
		return fitness.eval(p, goal);
	}

	private void updatePBest(Vector v) {
		if (v.getValue() < pBest.getValue()) {
			pBest = new Vector(v);
		}
	}

	private void updateVel(int dimension) {
		double inertia = velocity.getDimensionValue(dimension) * PSOConfig.INERTIA_WEIGHT;
		double cognitive = (pBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble() * PSOConfig.COGNITIVE_COEFFICIENT);
		double social = (gBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble() * PSOConfig.SOCIAL_COEFFICIENT);
		velocity.setDimensionValue(dimension, inertia + cognitive + social);

	}

	private void updatePos(int dimension) {
		position.setDimensionValue(dimension, velocity.getDimensionValue(dimension));

		Point tmp = new Point(path.getLast().getX(), path.getLast().getY());
		int step = nextStep(sigmoid(position, dimension));
		if (historyPath.contains(tmp)) {
			step *= -1;
			position.setValue(position.getValue()-5);
		}
		if (dimension == 0)
			tmp.setX(tmp.getX() + step);
		else
			tmp.setY(tmp.getY() + step);
		if (isValid(tmp)) {
			position.setValue(objective(tmp));
			path.add(tmp);
		} else {
			if (path.size() != 1) {
				historyPath.add(tmp);
				position.setValue(Integer.MAX_VALUE);
				position.setDimensionValue(dimension, position.getDimensionValue(dimension) - 0.5);
				Point prev = path.get(path.size() - 2);
				adjustVelocity(tmp, prev, dimension);
			}
		}
		limitPos(dimension);
		limitVelocity(dimension);
	}

	private void limitVelocity(int dimension) {
	    double value = velocity.getDimensionValue(dimension);
	    if (value > PSOConfig.MAX_VELOCITY) {
	        velocity.setDimensionValue(dimension, PSOConfig.MAX_VELOCITY);
	    } else if (value < PSOConfig.MIN_VELOCITY) {
	        velocity.setDimensionValue(dimension, PSOConfig.MIN_VELOCITY);
	    }
	}

	private void limitPos(int dimension) {
	    double value = position.getDimensionValue(dimension);
	    if (value > PSOConfig.MAX_POSITION) {
	        position.setDimensionValue(dimension, PSOConfig.MAX_POSITION);
	    } else if (value < PSOConfig.MIN_POSITION) {
	        position.setDimensionValue(dimension, PSOConfig.MIN_POSITION);
	    }
	}


	private void adjustVelocity(Point tmp, Point prev, int dimension) {
		double adjustment = (tmp.getX() != prev.getX() || tmp.getY() != prev.getY()) ? -1.0 : 1.0;
		velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) + adjustment * 0.5);
	}

	private int nextStep(int toAdd) {
		if (toAdd == 0)
			return -1;
		else
			return 1;

	}

	private int sigmoid(Vector vector, int dimension) {
		double value = vector.getDimensionValue(dimension);
		return (value >= 0) ? 1 : 0;
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

	public Vector getgBest() {
		return gBest;
	}

	public boolean isGoal() {
		return path.get(path.size() - 1).equals(goal);
	}

}
