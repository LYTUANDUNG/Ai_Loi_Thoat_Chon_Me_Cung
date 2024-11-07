package views;

import models.Point;

public interface IView {
	/**
	 * Tạo mê cung
	 * 
	 * @param points tọa độ vẽ mê cung
	 */
	void createMatrix(Point[] points);

	/**
	 * Hiển thị kết quả sao khi thực hiện tìm đường
	 * 
	 * @param points đường đi khi thực hiện giải thuật
	 * @param b      nếu true thì hiển thị đường đi và text, ngược lại chỉ hiển thị
	 *               text
	 */
	void displayResult(Point[] points, boolean b);

}
