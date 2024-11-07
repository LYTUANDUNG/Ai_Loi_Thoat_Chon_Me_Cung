package models;

public interface IModel {
	/**
	 * Tạo mê cung
	 * 
	 * @return Point[]
	 */
	Point[] getMatrix();

	/**
	 * Tìm đường đi ngắn nhất
	 * 
	 * @return Point[] nếu tìm được đường đi, ngược lại null
	 */
	Point[] getPath();

}
