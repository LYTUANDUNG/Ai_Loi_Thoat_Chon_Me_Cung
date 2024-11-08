package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import models.Point;

public class View {
	private MapView mapView;
	private ToolView toolView;

	public View(MapView mapView, ToolView toolView) {
		this.mapView = mapView;
		this.toolView = toolView;
	}

	public void createMatrix(Point[] point) {

	}

	public void createRoad(Point[] point) {

	}

	public void displayResult(Point[] point, boolean exist) {

	}

	public void createGUI() {
		// Fram chính
		JFrame frame = new JFrame("Map View with Tools");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		frame.setLayout(new BorderLayout());

		// Gọi các button từ lớp toolView qua
		toolView = new ToolView();

		// Lớp matrix này mô phỏng
		JPanel matrixPanel = new JPanel();
		matrixPanel.setBackground(Color.LIGHT_GRAY);
		matrixPanel.setPreferredSize(new Dimension(400, 200));
		matrixPanel.add(new JLabel("Matrix sẽ được hiển thị ở đây."));

		// JTextArea để show kết quả
		JTextArea textArea = new JTextArea(5, 40);
		textArea.setText("Kết quả sẽ được hiển thị ở đây...");
		textArea.setEditable(false); // Có thể đổi
		JScrollPane scrollPane = new JScrollPane(textArea);

		// Thêm các thành phần vào frame
		frame.add(toolView, BorderLayout.WEST); // ToolView xếp bên trái
		frame.add(matrixPanel, BorderLayout.CENTER); // Matrix panel ở giữa
		frame.add(scrollPane, BorderLayout.SOUTH); // JTextArea ở dưới cùng

		// Hiển thị cửa sổ
		frame.setVisible(true);
	}

	// Test
	public static void main(String[] args) {
		MapView mapView = new MapView(null, null);
		ToolView toolView = new ToolView();

		// Tạo View và hiển thị GUI
		View view = new View(mapView, toolView);
		view.createGUI();
	}
}
