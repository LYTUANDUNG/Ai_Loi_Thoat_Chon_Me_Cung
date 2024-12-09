package PSO;

public class FitnessDetails {

	public double eval(Vector v, Vector goal) {
		return Math.abs(v.getDimensionValue(0) - goal.getDimensionValue(0))
				+ Math.abs(v.getDimensionValue(1) - goal.getDimensionValue(1));
	}

}
