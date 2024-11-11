package models;

public interface IAdapter {
	/**
	 * Thực hiện giải thuật
	 * 
	 * @return Kết quả giải thuật
	 */
	Point[] execute(Point[] points);
	 void setAlogrithm(IAlogrithm alogrithm);
}
