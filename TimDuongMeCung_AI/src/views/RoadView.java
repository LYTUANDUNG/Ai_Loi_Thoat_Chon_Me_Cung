package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import models.Point;

public class RoadView extends JPanel {

	private Point[] points;

	public RoadView() {
//		setOpaque(false); // Đặt trong suốt
	}

	public void paintRoad(Point[] points) {
		System.out.println("Painting road...");
		this.points = points;
		repaint(); // Yêu cầu vẽ lại giao diện
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (points != null) {
			g.setColor(Color.BLUE);

			// Vẽ từng điểm trong đường đi
			for (Point point : points) {
				int x = point.getX();
				int y = point.getY();

				// hình tròn đại diện cho mỗi điểm trong đường đi
				g.fillOval(x * 20, y * 20, 20, 20);
			}
		}
	}
}
