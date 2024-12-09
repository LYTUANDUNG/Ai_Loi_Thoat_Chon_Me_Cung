package PSO;

import models.Point;

public class Swarm {
	private Particle particleBest;
	private Particle[] particles;

	private void init(Point[] maze) {
		particles = new Particle[PSOConfig.PARTICLE_COUNT];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(maze, 2);
			particles[i].updateGBest(particles[0].getpBest());
		}
		particleBest = particles[0];
	}

	public Point[] run(Point[] maze) {
		init(maze);
		boolean findGoal = false;
		run: for (int i = 0; i < PSOConfig.MAX_ITERATIONS; i++) {
			for (int j = 0; j < 2; j++) {
				for (Particle particle : particles) {
					particle.update(j);

				}
				boolean isChange = false;
				for (Particle particle : particles) {
					if (particleBest.getpBest().getValue() > particle.getpBest().getValue()) {
						particleBest = particle;
						isChange = true;
					}
				}
				if (particleBest.isGoal()) {
					findGoal = true;
				}
				if (isChange)
					updateGBest();
				if (findGoal)
					break run;
			}
//			adjustPSOParameters();
		}
		return particleBest.getPath().toArray(new Point[0]);
	}

	private void updateGBest() {
		for (Particle particle : particles) {
			particle.updateGBest(particleBest.getpBest());
		}
	}
	private void adjustPSOParameters() {
	    PSOConfig.INERTIA_WEIGHT *= 0.9;
	    PSOConfig.COGNITIVE_COEFFICIENT += 0.1;
	    PSOConfig.SOCIAL_COEFFICIENT += 0.1;
	}
}
