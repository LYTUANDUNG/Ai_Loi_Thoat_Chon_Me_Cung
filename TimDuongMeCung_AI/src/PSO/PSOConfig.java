package PSO;

import java.util.Random;

public class PSOConfig {

	public static double INERTIA_WEIGHT = 0.7;
	public static  double COGNITIVE_COEFFICIENT =2;
	public static  double SOCIAL_COEFFICIENT =2;
	public static final int PARTICLE_COUNT = 100;
	public static final int MAX_ITERATIONS = 1000;
	public static final double MAX_VELOCITY =4;
	public static final double MIN_VELOCITY =-4;
	public static final double MAX_POSITION =5;
	public static final double MIN_POSITION =-5;
	public static final Random RANDOM = new Random();
}
