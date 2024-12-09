package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import models.Point;

public class MapView extends JPanel {
	private Point[] maze;
	private Point[] road;
	
	public void setMaze(Point[] maze) {
		this.maze = maze;
	}
	public void setRoad(Point[] road) {
		this.road = road;
	}
	public void draw() {
		repaint();
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
		if (road != null) {
			g.setColor(Color.BLUE);
			// Vẽ từng điểm trong đường đi
			for (Point point : road) {
				int x = point.getX();
				int y = point.getY();

				// hình tròn đại diện cho mỗi điểm trong đường đi
				g.fillOval(x * 20, y * 20, 20, 20);
			}
		}
	}

}
