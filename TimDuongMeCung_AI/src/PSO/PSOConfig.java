package PSO;

import java.util.Random;

public class PSOConfig {
	public static final double INERTIA_WEIGHT = 0.6;
	public static final double COGNITIVE_COEFFICIENT =2;
	public static final double SOCIAL_COEFFICIENT =2;
	public static final int PARTICLE_COUNT = 1;
	public static final int MAX_ITERATIONS = 1000;
	public static final Vector MAX_VELOCITY = new Vector(2, 2);
	public static final Vector MIN_VELOCITY = new Vector(-2, -2);
	public static final Random RANDOM = new Random();
}
