package models;

public class Model implements IModel{
	private Matrix matrix;
	/*points để lưu lại mê cung, 
	 * nếu không có thì không có tham số để lấy đường đi */
    private Point[] points;
    private ShortestPath path;

	@Override
	public Point[] getMatrix() {
		/* Trả về ma trận dưới dạng mảng các điểm (Point) */
		return null;
	}

	@Override
	public Point[] getPath() {
		/* Trả về đường đi ngắn nhất dưới dạng mảng các điểm (Point) */
		return null;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	public void setPath(ShortestPath path) {
		this.path = path;
	}

}
