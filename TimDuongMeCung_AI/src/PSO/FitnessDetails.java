package PSO;

public class FitnessDetails {

	public double eval(Vector v, Vector goal) {
		return Math.abs(v.getX() - goal.getX()) + Math.abs(v.getY() - goal.getY());
	}

}
