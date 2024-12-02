package models;

public class Matrix {
	// Adapter tạo ma trận
	private IAdapter creating;
	private int col;
	private int row;

	public Matrix() {
		creating = new AdapterMatrix();
	}

	public void setSize(int width, int height) {
		col = width;
		row = height;
	}

	/*
	 * Phương thức trả về ma trận dưới dạng mảng các điểm (Point) Thực hiện thông
	 * qua adapter 'creating'
	 */
	public Point[] getMatrix(Point[] points) {
		return creating.execute(points = new Point[col * row]);
	}

}
