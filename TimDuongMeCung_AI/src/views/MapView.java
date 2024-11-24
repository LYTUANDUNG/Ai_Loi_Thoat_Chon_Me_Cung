package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import models.Point;

public class MapView extends JPanel {
	private MatrixView matrixView;
	private RoadView roadView;

	public MapView() {
		// Khởi tạo MatrixView và RoadView
		this.matrixView = new MatrixView();
		this.roadView = new RoadView();

		// Đảm bảo rằng matrixView được thêm vào
		setLayout(new BorderLayout());
		add(matrixView, BorderLayout.CENTER); // Đảm bảo matrixView được thêm vào panel
	}

	public void createMatrix(Point[] points) {
		if (matrixView != null) {
			matrixView.paintMatrix(points);
		}
	}

	public void createRoad(Point[] points) {
		if (roadView != null) {
			roadView.paintRoad(points);
		}
	}
}
