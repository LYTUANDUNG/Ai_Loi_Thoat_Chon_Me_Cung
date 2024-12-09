package PSO;

import models.Point;

public class Swarm {
	private Particle particleBest;
	private Particle[] particles;

	private void init(Point[] maze) {
		particles = new Particle[PSOConfig.PARTICLE_COUNT];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(maze, 2);
		}
		particleBest = particles[0];
		updateGBest();
	}

	public Point[] run(Point[] maze) {
		init(maze);
		boolean findGoal = false;
		for (int i = 0; i < PSOConfig.MAX_ITERATIONS; i++) {

			for (Particle particle : particles) {
				for (int j = 0; j < 2; j++) {
					particle.update(j);
				}
			}
			boolean isChange = false;
			for (Particle particle : particles) {
				if (particleBest.getgBest().getValue() < particle.getpBest().getValue()) {
					particleBest = particle;
					isChange = true;
				}
			}
			if (particleBest.getpBest().getValue() == 0)
				findGoal = true;
			if (isChange)
				updateGBest();

		}
		return particleBest.getPath().toArray(new Point[0]);
	}

	private void updateGBest() {
		for (Particle particle : particles) {
			particle.updateGBest(particleBest.getpBest());
		}
	}
}
