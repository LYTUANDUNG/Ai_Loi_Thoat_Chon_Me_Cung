package PSO;

import models.Point;

public class Swarm {
	private Particle particleBest;
	private Particle[] particles;

	public Swarm(Particle[] particles) {
		this.particles = particles;
	}

	public Point[] run(Point[] maze) {
		for (int i = 0; i < PSOParams.Iterations; i++) {
			for (Particle particle : particles) {
				particle.update();
			}
//			cập nhật lại gbest theo heuristic
			for (Particle particle : particles) {

			}
		}
		return null;
	}

}
