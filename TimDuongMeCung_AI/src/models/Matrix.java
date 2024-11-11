package models;

public class Matrix {
	// Adapter tạo ma trận
	private IAdapter creating;

	/*
	 * Phương thức trả về ma trận dưới dạng mảng các điểm (Point) Thực hiện thông
	 * qua adapter 'creating'
	 */
	public Point[] getMatrix(Point[] points) {
		return creating.execute(points);
	}

}
