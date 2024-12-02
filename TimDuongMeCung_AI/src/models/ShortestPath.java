package models;

public class ShortestPath {
	private IAdapter finding;
	public ShortestPath() {
		finding= new AdapterFiding();
	}
	public Point[] getPath(Point[] points) {
		/*
		 * Phương thức trả về đường đi ngắn nhất dưới dạng mảng các điểm (Point) Sử dụng
		 * adapter 'finding' để thực hiện tìm đường
		 */
		return finding.execute(points);
	}

	public void setAL(IAlogrithm al) {
		finding.setAlogrithm(al);
	}

}
