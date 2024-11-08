package models;

public class ShortestPath {
	private IAdapter finding;

	public ShortestPath(IAdapter finding) {
		this.finding = finding;
	}

	public Point[] getPath() {
		/*
		 * Phương thức trả về đường đi ngắn nhất dưới dạng mảng các điểm (Point) Sử dụng
		 * adapter 'finding' để thực hiện tìm đường
		 */
		return finding.execute();
	}

}
