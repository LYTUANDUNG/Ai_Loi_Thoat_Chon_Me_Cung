package PSO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.PSO;
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

	// Constructor khởi tạo hạt (Particle) với ma trận, chiều dài không gian và đàn (swarm)
	public Particle(Point[] maze, int dimension, Swarm swarm) {
		this.swarm = swarm;
		this.maze = maze;
		fitness = new FitnessDetails(maze);
		init(dimension);
	}

	// Phương thức khởi tạo vị trí, vận tốc và giá trị tốt nhất đã biết của hạt
	private void init(int dimension) {
		historyPath = new HashMap<>();
		path = new ArrayList<Point>();
		velocity = new Vector(new double[] { PSOConfig.RANDOM.nextDouble(-3, 3), PSOConfig.RANDOM.nextDouble(-3, 3) });
		path.add(MazeConfig.ENTRANCE);
		position = new Vector(dimension);
		position.setValue(calculateObjectiveValue(MazeConfig.ENTRANCE));
		pBest = new Vector(position);
	}

	// Cập nhật vận tốc, vị trí và giá trị tốt nhất cá nhân của hạt
	public void updateParticle(int dimension, Vector gBest) {
		updateVelocity(dimension, gBest);
		updatePosition(dimension);
		updatePersonalBest(position);
	}

	// Tính toán giá trị mục tiêu (fitness) của một điểm trong mê cung
	private int calculateObjectiveValue(Point p) {
		return fitness.eval(p);
	}

	// Cập nhật giá trị tốt nhất cá nhân của hạt
	public void updatePersonalBest(Vector v) {
		if (v.getValue() < pBest.getValue()) {
			pBest = new Vector(v);
			if (pBest.getValue() < swarm.getGBest().getValue()) {
				swarm.updateGBest(pBest, this);
			}
		}
	}

	// Cập nhật vận tốc của hạt dựa trên ba yếu tố: quán tính, nhận thức cá nhân và nhận thức xã hội
	private void updateVelocity(int dimension, Vector gBest) {
		double inertia = velocity.getDimensionValue(dimension) * PSOConfig.INERTIA_WEIGHT;
		double cognitive = (pBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble(0, 1) * PSOConfig.COGNITIVE_COEFFICIENT);
		double social = (gBest.getDimensionValue(dimension) - position.getDimensionValue(dimension))
				* (PSOConfig.RANDOM.nextDouble(0, 1) * PSOConfig.SOCIAL_COEFFICIENT);
		velocity.setDimensionValue(dimension, inertia + cognitive + social);
	}

	// Di chuyển một điểm (theo chiều x hoặc y) theo một giá trị cho trước
	public void movePoint(Point point, int value, int dimension) {
		if (dimension == 0)
			point.setX(point.getX() + value);
		else
			point.setY(point.getY() + value);
	}

	// Cập nhật vị trí của hạt dựa trên vận tốc
	private void updatePosition(int dimension) {
		position.setDimensionValue(dimension,
				position.getDimensionValue(dimension) + velocity.getDimensionValue(dimension));
		Point tmp = new Point(path.getLast().getX(), path.getLast().getY());
		Point last = path.getLast();
		tmp = findNextOptimalPoint(tmp, dimension);
		int times = historyPath.getOrDefault(tmp, 0);
		if (isValidPoint(tmp)) {
			position.setValue(calculateObjectiveValue(tmp) + times - 1);
			historyPath.put(tmp, ++times);
			path.add(tmp);
		} else {
			if (path.size() != 1) {
				historyPath.put(tmp, ++times);
				position.setValue(Integer.MAX_VALUE);
				position.setDimensionValue(dimension, position.getDimensionValue(dimension) - 0.5);
				adjustVelocityOnCollision(tmp, last, dimension);
			}
		}
		applyPositionLimit(dimension);
		applyVelocityLimit(dimension);
	}

	// Điều chỉnh hướng đi của hạt (vận tốc và vị trí) dựa trên yếu tố thay đổi
	public void adjustDirection(int dimension, int change, boolean repeat) {
		if (repeat) {
			velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) * change);
			position.setDimensionValue(dimension, position.getDimensionValue(dimension) * change);
		} else {
			velocity.setDimensionValue(dimension,
					velocity.getDimensionValue(dimension) + PSOConfig.RANDOM.nextDouble(0, PSOConfig.MAX_VELOCITY) * change);
			position.setDimensionValue(dimension,
					position.getDimensionValue(dimension) + PSOConfig.RANDOM.nextDouble(0, PSOConfig.MAX_POSITION) * change);
		}
	}

	// Tìm điểm tối ưu tiếp theo để di chuyển trong mê cung, dựa trên vị trí hiện tại
	private Point findNextOptimalPoint(Point current, int dimension) {
		Point temp = new Point(current.getX(), current.getY());
		int bestStep = 0;
		Integer min = Integer.MAX_VALUE;
		int step = determineStepDirection(calculateSigmoid(position, dimension));
		int change = 1;
		for (int i = 0; i < 2; i++) {
			movePoint(temp, step * change, dimension);
			int val = historyPath.getOrDefault(temp, 0) + (isValidPoint(temp) ? calculateObjectiveValue(temp) : 20);
			if (val < min) {
				min = val;
				bestStep = step * change;
			}
			movePoint(temp, -step * change, dimension);
			change = -1;
		}
		movePoint(temp, step, dimension);
		if (historyPath.getOrDefault(temp, 0) > 3) {
			adjustDirection(dimension, bestStep, true);
			step = bestStep;
		} else {
			adjustDirection(dimension, bestStep == step ? step : bestStep, false);
		}
		movePoint(current, step, dimension);
		return current;
	}

	// Giới hạn vận tốc của hạt để không vượt quá vận tốc tối đa cho phép
	private void applyVelocityLimit(int dimension) {
		double value = velocity.getDimensionValue(dimension);
		if (value > PSOConfig.MAX_VELOCITY) {
			velocity.setDimensionValue(dimension, PSOConfig.MAX_VELOCITY);
		} else if (value < PSOConfig.MIN_VELOCITY) {
			velocity.setDimensionValue(dimension, PSOConfig.MIN_VELOCITY);
		}
	}

	// Giới hạn vị trí của hạt để không vượt quá vị trí tối đa cho phép
	private void applyPositionLimit(int dimension) {
		double value = position.getDimensionValue(dimension);
		if (value > PSOConfig.MAX_POSITION) {
			position.setDimensionValue(dimension, PSOConfig.MAX_POSITION);
		} else if (value < PSOConfig.MIN_POSITION) {
			position.setDimensionValue(dimension, PSOConfig.MIN_POSITION);
		}
	}

	// Điều chỉnh vận tốc khi có va chạm (với các điểm đã thăm)
	private void adjustVelocityOnCollision(Point tmp, Point prev, int dimension) {
		double adjustment = (tmp.getX() != prev.getX() || tmp.getY() != prev.getY()) ? 1.0 : -1.0;
		velocity.setDimensionValue(dimension, velocity.getDimensionValue(dimension) + adjustment * 0.5);
	}

	// Xác định bước đi tiếp theo trong phương thức di chuyển (1 hoặc -1)
	private int determineStepDirection(int toAdd) {
		if (toAdd == 0)
			return -1;
		else
			return 1;
	}

	// Tính giá trị sigmoid của vector tại một chiều không gian
	private int calculateSigmoid(Vector vector, int dimension) {
		double value = vector.getDimensionValue(dimension);
		return (value >= 0) ? 1 : 0;
	}

	// Kiểm tra xem một điểm có hợp lệ không (không phải là tường)
	private boolean isValidPoint(Point point) {
		for (Point p : maze) {
			if (p.equals(point)) {
				return !p.isWall();
			}
		}
		return false;
	}

	// Lấy đường đi của hạt (loại bỏ các bước đi lặp lại)
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

	// Getter và setter cho giá trị tốt nhất cá nhân của hạt
	public Vector getpBest() {
		return pBest;
	}

	public void setpBest(Vector pBest) {
		this.pBest = new Vector(pBest);
	}

	// Getter cho vị trí của hạt
	public Vector getPosition() {
		return position;
	}

	// Getter cho vận tốc của hạt
	public Vector getVelocity() {
		return velocity;
	}

	// Kiểm tra xem hạt đã đến đích chưa
	public boolean isGoal() {
		return path.getLast().equals(MazeConfig.EXIT);
	}

	// Getter cho đường đi của hạt
	public List<Point> path() {
		return this.path;
	}

	// Setter cho đường đi của hạt
	public void setPath(List<Point> path) {
		this.path = path;
	}
}
