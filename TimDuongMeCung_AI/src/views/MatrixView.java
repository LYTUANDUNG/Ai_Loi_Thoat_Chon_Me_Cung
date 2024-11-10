package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import models.Point;

public class MatrixView extends JPanel {

	private Point[] points;

	// Phương thức để cập nhật ma trận
	public void paintMatrix(Point[] points) {
		this.points = points;
		repaint(); // Yêu cầu vẽ lại giao diện
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (points != null) {
			// Vẽ từng điểm trong ma trận
			for (Point point : points) {
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
					g.setColor(Color.GREEN);
					break;
				case Exit:
					g.setColor(Color.RED);
					break;
				default:
					g.setColor(Color.LIGHT_GRAY);
					break;
				}

				// Vẽ hình vuông 20x20 đại diện cho mỗi điểm
				g.fillRect(x * 20, y * 20, 20, 20); 
			}
		}
	}
}
