package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import models.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class View {
	private MapView mapView;
	private ToolView toolView;

	public View(MapView mapView, ToolView toolView) {
		this.mapView = mapView;
		this.toolView = toolView;
	}

	// Phương thức để tạo ma trận
	public void createMatrix(Point[] points) {
		mapView.createMatrix(points); // Truyền các điểm vào MapView để vẽ ma trận
	}

	// Phương thức để tạo đường đi
	public void createRoad(Point[] points) {
		mapView.createRoad(points); // Truyền các điểm vào MapView để vẽ đường đi
	}

	// Phương thức để hiển thị kết quả
	public void displayResult(Point[] points, boolean exist) {
		// Xử lý và hiển thị kết quả
	}

	public void createGUI() {
		// Tạo Frame chính
		JFrame frame = new JFrame("Map View with Tools");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		frame.setLayout(new BorderLayout());

		// ToolView cho các nút chức năng
		toolView = new ToolView();

		// Tạo panel cho ma trận
		JPanel matrixPanel = new JPanel();
		matrixPanel.setBackground(Color.LIGHT_GRAY);
		matrixPanel.setPreferredSize(new Dimension(400, 200));
		matrixPanel.add(new JLabel("Matrix sẽ được hiển thị ở đây."));

		// JTextArea để hiển thị kết quả
		JTextArea textArea = new JTextArea(5, 40);
		textArea.setText("Kết quả sẽ được hiển thị ở đây...");
		textArea.setEditable(false); // Có thể chỉnh sửa
		JScrollPane scrollPane = new JScrollPane(textArea);

		// Thêm các thành phần vào frame
		frame.add(toolView, BorderLayout.WEST); // ToolView ở bên trái
		frame.add(matrixPanel, BorderLayout.CENTER); // Matrix panel ở giữa
		frame.add(scrollPane, BorderLayout.SOUTH); // JTextArea ở dưới

		// Hiển thị cửa sổ
		frame.setVisible(true);
	}

	// Test
	public static void main(String[] args) {
		MatrixView matrixView = new MatrixView();
		RoadView roadView = new RoadView();
		MapView mapView = new MapView(matrixView, roadView);
		ToolView toolView = new ToolView();

		// Tạo View và hiển thị GUI
		View view = new View(mapView, toolView);
		view.createGUI();
	}
}
