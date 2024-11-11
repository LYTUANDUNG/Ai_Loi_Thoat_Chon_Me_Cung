package models;

/**
 * Trạng thái của một ô trong mê cung.
 */
public enum StatePoint {
	/** Lối vào mê cung */
	Entrance,

	/** Lối ra mê cung */
	Exit,

	/** Tường ngăn trong mê cung, không thể đi qua */
	Wall,

	/** Ô trống hoặc đường đi, có thể đi qua */
	Passage
}
