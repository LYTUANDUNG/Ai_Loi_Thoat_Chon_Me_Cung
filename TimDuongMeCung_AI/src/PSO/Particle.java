package PSO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Point;

public class Particle {
	private Point[] maze;
	private Map<Point, Integer> historyPath;
	private List<Point> path;
	private Vector position;
	private Vector velocity;
	private Vector pBest;
	private Vector gBest;
	private FitnessDetails fitness;
	private int dimension;

	public Particle(Point[] maze, int dimension) {

		historyPath = new HashMap<>();
		this.maze = maze;
		this.dimension = dimension;
		fitness = new FitnessDetails(maze);
		position = new Vector(dimension);
		position.setValue(objective(MazeConfig.ENTRANCE));
		pBest = new Vector(position);
		velocity = new Vector(new double[] { PSOConfig.RANDOM.nextDouble(-2, 2), PSOConfig.RANDOM.nextDouble(-2, 2) });
		path = new ArrayList<Point>();
		path.add(MazeConfig.ENTRANCE);
	}

	public void update(int dimension) {
		updateVel(dimension);
		updatePos(dimension);
		updatePBest(position);
	}

	public void updateGBest(Vector toUpdate) {
		gBest = toUpdate;
	}

	private int objective(Point p) {
		return fitness.eval(p);
	}

	public void updatePBest(Vector v) {
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
		int step = nextStep(sigmoid(position, dimension));
		step(tmp, step, dimension);
		Point prev=null;
		if (path.size() != 1)
			prev = path.get(path.size() - 2);
		boolean contains = false;
		if (historyPath.get(tmp) != null) {
			historyPath.put(tmp, historyPath.get(tmp) + 1);
			if (prev != null)
				adjustVelocity(tmp,prev, dimension);
			velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension)* 0.8);
			contains = true;
		}
		if (isValid(tmp)) {
			if (!contains) {
				position.setValue(objective(tmp) - 1);
				historyPath.put(tmp, 1);
			} else
				position.setValue(objective(tmp) + historyPath.get(tmp) + 5);
			path.add(tmp);
		} else {
			if (path.size() != 1) {
				if (!contains)
					historyPath.put(tmp, 1);
				else
					historyPath.put(tmp, historyPath.get(tmp) + 1);
				position.setValue(Integer.MAX_VALUE);
				position.setDimensionValue(dimension, position.getDimensionValue(dimension) - 0.5);
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
		return path;

	}

	public Vector getpBest() {
		return pBest;
	}

	public void setpBest(Vector pBest) {
		this.pBest = new Vector(pBest);
	}

	public Vector getgBest() {
		return gBest;
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public boolean isGoal() {
		return path.get(path.size() - 1).equals(MazeConfig.EXIT);
	}

	public List<Point> path() {
		return this.path;
	}

	public void setPath(List<Point> path) {
		this.path = path;
	}

}
