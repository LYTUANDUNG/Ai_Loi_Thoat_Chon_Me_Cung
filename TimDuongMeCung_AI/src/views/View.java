package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import models.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class View implements IView {
	private MapView mapView;
	private ToolView toolView;

	/**
	 * Sự kiện nút vẽ đường đi
	 * 
	 * @param event sự kiện cho button
	 */
	public void addEventFinding(ActionListener event) {
		toolView.addEventFinding(event);
	}

	/**
	 * Sự kiện nút vẽ mê cung
	 * 
	 * @param event sự kiện cho button
	 */
	public void addEventCreateMatrix(ActionListener event) {
		toolView.addEventCreateMatrix(event);
	}

	public void addEventChangeAL(ActionListener event) {
		toolView.addEventChangeAL(event);
	}

	// Phương thức để tạo ma trận
	public void createMatrix(Point[] points) {
		mapView.setMaze(points); // Truyền các điểm vào MapView để vẽ ma trận
		mapView.draw();
	}

	// Phương thức để tạo đường đi
	public void createRoad(Point[] points) {
		mapView.setRoad(points); // Truyền các điểm vào MapView để vẽ đường đi
		mapView.draw();
	}

	// Phương thức để hiển thị kết quả
	public void displayResult(Point[] points, boolean exist) {
		JTextArea textArea = toolView.getTextArea(); // Get the JTextArea from ToolView to update results
		createRoad(points);
		if (exist) {
			textArea.setText("Path found!");
		} else {
			textArea.setText("No path found.");
		}
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
		mapView = new MapView();
		mapView.setBackground(Color.LIGHT_GRAY);
		mapView.setPreferredSize(new Dimension(400, 200));

		// JTextArea để hiển thị kết quả
		JTextArea textArea = new JTextArea(5, 40);
		textArea.setText("Kết quả sẽ được hiển thị ở đây...");
		textArea.setEditable(false); // Không thể chỉnh sửa
		JScrollPane scrollPane = new JScrollPane(textArea);

		// Add the textArea to the ToolView for easy access
		toolView.setTextArea(textArea);

		// Thêm các thành phần vào frame
		frame.add(toolView, BorderLayout.WEST); // ToolView ở bên trái
		frame.add(mapView, BorderLayout.CENTER); // Matrix panel ở giữa
		frame.add(scrollPane, BorderLayout.SOUTH); // JTextArea ở dưới// Hiển thị cửa sổ
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}