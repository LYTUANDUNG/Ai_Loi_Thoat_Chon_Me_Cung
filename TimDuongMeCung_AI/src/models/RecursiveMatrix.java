package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RecursiveMatrix implements IAlogrithm {
	private int row = 13;
	private int col = 21;
	private Point[] maze= new Point[row*col];

	@Override
	public Point[] execute(Point[] points) {
		initMaze();
		generateMaze();
		ensureMultiplePaths();
		setEntranceAndExit();		
		return maze;
	}

	private void initMaze() {
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				maze[getIndex(i, j)] = new Point(i, j, StatePoint.Wall);
			}
		}

	}

	private void generateMaze() {
		Stack<Point> stack = new Stack<Point>();
		Point start = maze[0];
		stack.push(start);
		Point cur, next;
		while (!stack.isEmpty()) {
			cur = stack.peek();
			next = getRandomNeighbor(cur);
			if (next != null) {
				next.setPassage();
				removeWall(cur, next);
				stack.push(next);
			} else {
				stack.pop();
			}

		}

	}

	private Point getRandomNeighbor(Point current) {
		List<Integer> randomIndex = new ArrayList<>();
		int index;
		int x = current.getX();
		int y = current.getY();
		if (x > 1 && maze[index = getIndex(x - 2, y)].isWall())
			randomIndex.add(index);
		if (y > 1 && maze[index = getIndex(x, y - 2)].isWall())
			randomIndex.add(index);
		if (x < col - 2 && maze[index = getIndex(x + 2, y)].isWall())
			randomIndex.add(index);
		if (y < row - 2 && maze[index = getIndex(x, y + 2)].isWall())
			randomIndex.add(index);
		Collections.shuffle(randomIndex);
		return randomIndex.isEmpty() ? null : maze[randomIndex.get(0)];
	}

	private void removeWall(Point current, Point next) {
		int x = (current.getX() + next.getX()) / 2;
		int y = (current.getY() + next.getY()) / 2;
		maze[getIndex(x, y)].setPassage();

	}
/**
 * 0 1 2 3
 * 4 5 6 7
 * 
 * 1->0*4+1
 * 5->1*4+1
 * 
 */
	private int getIndex(int x, int y) {
		return y * col + x;

	}

	private void ensureMultiplePaths() {
		for (Point point : maze) {
			if (point.isWall() && isBetweenPassages(point) && Math.random() < 0.075) {
				point.setPassage();
			}
		}

	}

	private boolean isBetweenPassages(Point wall) {
		int x = wall.getX();
		int y = wall.getY();
		if (x == 0 || x == col - 1 || y == 0 || y == row - 1)
			return true;
		boolean isBetweenX = maze[getIndex(x - 1, y)].isPassage() && maze[getIndex(x + 1, y)].isPassage();
		boolean isBetweenY = maze[getIndex(x, y - 1)].isPassage() && maze[getIndex(x, y + 1)].isPassage();
		return isBetweenX || isBetweenY;
	}

	private void setEntranceAndExit() {
		Random random = new Random();
		Point entrance, exit;

		do {
			entrance = maze[getIndex(0, random.nextInt(row))];
		} while (!entrance.isPassage());
		entrance.setEntrance();

		do {
			exit = maze[getIndex(col - 1, random.nextInt(row))];
		} while (!exit.isPassage());
		exit.setExit();
	}

}