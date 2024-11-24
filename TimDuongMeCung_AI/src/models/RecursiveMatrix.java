package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RecursiveMatrix implements IAlogrithm {
	private int size = 5;
	private Random random = new Random();

	@Override
	public Point[] execute(Point[] points) {
		points = new Point[size * size];
		init(points);
		boolean[] visited = new boolean[points.length];
		Point curP = null;
		curP = points[getRandom(0, points.length)];
		Stack<Point> stack = new Stack<Point>();
		stack.push(curP);
		while (!stack.isEmpty()) {
			curP = stack.pop();
			int index = getIndex(curP.getX(), curP.getY());
			if (visited[index])
				continue;
			curP.setState(StatePoint.Passage);
			visited[index] = true;
			List<Integer> randomPoint = new ArrayList<>();
			{
//x
				{
					if (curP.getY() < size - 1)
						randomPoint.add(getIndex(curP.getX(), curP.getY() + 1));
					if (curP.getY() != 0)
						randomPoint.add(getIndex(curP.getX(), curP.getY() - 1));
				}
//y
				{
					if (curP.getX() < size - 1)
						randomPoint.add(getIndex(curP.getX() + 1, curP.getY()));
					if (curP.getX() != 0)
						randomPoint.add(getIndex(curP.getX() - 1, curP.getY()));
				}
			}
			while (!randomPoint.isEmpty()) {
				int i = getRandom(0, randomPoint.size());
				if (!visited[i])
					stack.push(points[i]);
				randomPoint.remove(i);
			}

		}

		return points;
	}

	private int getIndex(int x, int y) {
		return (x * size) + y;
	}

	private void init(Point[] points) {
		int index = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				points[index++] = new Point(i, j, StatePoint.Wall);
			}
		}
	}

	private int getRandom(int min, int max) {
		return random.nextInt(min, max);
	}

	

}
