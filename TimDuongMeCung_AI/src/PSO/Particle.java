package PSO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Point;

public class Particle {
	private Swarm swarm;
	private Point[] maze;
	private Map<Point, Integer> historyPath;
	private List<Point> path;
	private Vector position;
	private Vector velocity;
	private Vector pBest;
	private FitnessDetails fitness;

	public Particle(Point[] maze, int dimension, Swarm swarm) {
		this.swarm = swarm;
		this.maze = maze;
		fitness = new FitnessDetails(maze);
		init(dimension);
	}

	private void init(int dimension) {
		historyPath = new HashMap<>();
		path = new ArrayList<Point>();
		velocity = new Vector(new double[] { PSOConfig.RANDOM.nextDouble(-3, 3), PSOConfig.RANDOM.nextDouble(-3, 3) });
		path.add(MazeConfig.ENTRANCE);
		position = new Vector(dimension);
		position.setValue(objective(MazeConfig.ENTRANCE));
		pBest = new Vector(position);
	}

	public void update(int dimension, Vector gBest) {
		updateVel(dimension, gBest);
		updatePos(dimension);
		updatePBest(position);

	}

	private int objective(Point p) {
		return fitness.eval(p);
	}

	public void updatePBest(Vector v) {
		if (v.getValue() < pBest.getValue()) {
			pBest = new Vector(v);
			if (pBest.getValue() < swarm.getGBest().getValue()) {
				swarm.updateGBest(pBest, this);
			}
		}
	}

	private void updateVel(int dimension, Vector gBest) {
		double inertia = velocity.getDimensionValue(dimension) * PSOConfig.INERTIA_WEIGHT;
		double cognitive = (pBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble(0, 1) * PSOConfig.COGNITIVE_COEFFICIENT);
		double social = (gBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble(0, 1) * PSOConfig.SOCIAL_COEFFICIENT);
		velocity.setDimensionValue(dimension, inertia + cognitive + social);

	}

	public void step(Point point, int value, int dimension) {
		if (dimension == 0)
			point.setX(point.getX() + value);
		else
			point.setY(point.getY() + value);
	}

	private void updatePos(int dimension) {
		position.setDimensionValue(dimension,
				position.getDimensionValue(dimension) + velocity.getDimensionValue(dimension));
		Point tmp = new Point(path.getLast().getX(), path.getLast().getY());
		Point last = path.getLast();
		findOptimalPoint(tmp, dimension);
		int times = historyPath.getOrDefault(tmp, 0);
		if (isValid(tmp)) {
			position.setValue(objective(tmp) + times - 1);
			historyPath.put(tmp, ++times);
			path.add(tmp);
		} else {
			if (path.size() != 1) {
				historyPath.put(tmp, ++times);
				position.setValue(Integer.MAX_VALUE);
				position.setDimensionValue(dimension, position.getDimensionValue(dimension) - 0.5);
				adjustVelOnCollision(tmp, last, dimension);
			}
		}
		limitPos(dimension);
		limitVelocity(dimension);
	}

	public void changeDirection(int dimension, int change) {
		velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) * change);
		position.setDimensionValue(dimension, position.getDimensionValue(dimension) * change);
	}

	private Point findOptimalPoint(Point current, int dimension) {
		int bestStep = 0;
		Integer min = Integer.MAX_VALUE;
		int step = nextStep(sigmoid(position, dimension));
		int change = 1;
		for (int i = 0; i < 2; i++) {
			step(current, step * change, dimension);
			int val = historyPath.getOrDefault(current, 0) + (isValid(current) ? objective(current) : 20);
			if (val < min) {
				min = val;
				bestStep = step * change;
			}
			step(current, -step * change, dimension);
			change = -1;
		}
		step(current, bestStep, dimension);
		changeDirection(dimension, bestStep);
		return current;
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

	private void adjustVelOnCollision(Point tmp, Point prev, int dimension) {
		double adjustment = (tmp.getX() != prev.getX() || tmp.getY() != prev.getY()) ? 1.0 : -1.0;
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

	public void setpBest(Vector pBest) {
		this.pBest = new Vector(pBest);
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public boolean isGoal() {
		return path.getLast().equals(MazeConfig.EXIT);
	}

	public List<Point> path() {
		return this.path;
	}

	public void setPath(List<Point> path) {
		this.path = path;
	}

}
