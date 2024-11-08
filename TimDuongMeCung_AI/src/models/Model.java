package models;

public class Model implements IModel {
	private Matrix matrix;
	/*
	 * points để lưu lại mê cung, nếu không có thì không có tham số để lấy đường đi
	 */
	private Point[] points;
	private ShortestPath path;

	@Override
	public Point[] getMatrix() {
		/* Trả về ma trận dưới dạng mảng các điểm (Point) */
		points = matrix.getMatrix();
		return points;

	}

	@Override
	public Point[] getPath() {
		/* Trả về đường đi ngắn nhất dưới dạng mảng các điểm (Point) */
		return path.getPath();
	}
}
