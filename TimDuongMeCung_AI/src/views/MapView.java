package views;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import models.Point;

public class MapView extends JPanel {
	private Point[] maze;
	private Point[] road;
	public static Map<Point[],Color> roads;
	int current;

	public void setMaze(Point[] maze) {
		this.maze = maze;
		road = null;
	}

	public void setRoad(Point[] road) {
		this.road = road;
	}

	public void draw() {
		current = 0;
		int max = 0;
		if (roads != null)
			for (Map.Entry<Point[], Color> entry : roads.entrySet()) {
				Point[] key = entry.getKey();
				max=Math.max(max, key.length);
			}
		drawRoad(max);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (maze != null) {
			// Vẽ từng điểm trong ma trận
			for (Point point : maze) {
				int x = point.getX();
				int y = point.getY();

				// Xác định màu sắc dựa trên trạng thái của điểm
				switch (point.getState()) {
				case Wall:
					g.setColor(Color.BLACK);
					break;
				case Entrance:
					g.setColor(Color.WHITE);
					break;
				case Passage:
					g.setColor(Color.GRAY);
					break;
				case Exit:
					g.setColor(Color.RED);
					break;
				}

				// Vẽ hình vuông 20x20 đại diện cho mỗi điểm
				g.fillRect(x * 20, y * 20, 20, 20);
			}
		}
		if (roads != null) {
			for (Map.Entry<Point[], Color> entry : roads.entrySet()) {
				g.setColor(entry.getValue());
				Point[] key = entry.getKey();
				if (current < key.length) {
					Point point = key[current];
					int x = point.getX();
					int y = point.getY();
					// hình tròn đại diện cho mỗi điểm trong đường đi
					g.fillOval(x * 20, y * 20, 20, 20);
				}
		}

		}

	}

	private void drawRoad(int size) {
		new Thread((() -> {
			if (road != null)
				for (int i = 0; i < size; i++) {
					current++;
					repaint();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			else
				repaint();
		})).start();

	}

}
