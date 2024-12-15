package PSO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Point;

public class FitnessDetails {
	private int row = MazeConfig.ROW;
	private int col = MazeConfig.COLUMN;
	private Point[] maze;
	private Map<Set<Point>, Integer> waves;

	public FitnessDetails(Point[] maze) {
		this.maze = maze;
		this.waves = new HashMap<Set<Point>, Integer>();
		int size = row > col ? row : col;
		int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 1; i <= size; i++) {
			Set<Point> points = new HashSet<>();
			// Duyệt qua 8 hướng xung quanh điểm EXIT
			for (int d = 0; d < dx.length; d++) {
				int newX = MazeConfig.EXIT.getX() + i * dx[d];
				int newY = MazeConfig.EXIT.getY() + i * dy[d];
				// Kiểm tra điểm có nằm trong giới hạn không
				if (newX >= 0 && newX < col && newY >= 0 && newY < row) {
					points.add(maze[getIndex(newX, newY)]);
				}
			}
			// Thêm tập hợp điểm vào waves
			this.waves.put(points, i);
		}

	}

	private int getIndex(int x, int y) {
		return y * col + x;

	}

	private int getLevelWaves(Point p) {
		int result = 0;
		for (Map.Entry<Set<Point>, Integer> entry : waves.entrySet()) {
			Set<Point> key = entry.getKey();
			Integer val = entry.getValue();
			if (key.contains(maze[getIndex(p.getX(), p.getY())]))
				result = val;
		}
		return result;
	}

	public int checkPoint(Point p) {
		int sum = 0;
		if (MazeConfig.EXIT.equals(p))
			return 0;
		if (p.getX() == col - 1 || maze[getIndex(p.getX() + 1, p.getY())].isWall()) {
			sum += 1;
		}
		if (p.getX() == 0 || maze[getIndex(p.getX() - 1, p.getY())].isWall()) {
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

	public int eval(Point p) {
		return checkPoint(p) + getLevelWaves(p);
	}

}
