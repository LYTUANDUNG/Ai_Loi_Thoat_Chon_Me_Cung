package PSO;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.Point;
import views.MapView;

public class Swarm {
	private Particle particleBest;
	private Particle[] particles;
	private Vector gBest;

	public Vector getGBest() {
		return this.gBest;
	}

	private void init(Point[] maze) {
		particles = new Particle[PSOConfig.PARTICLE_COUNT];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(maze, 2, this);

		}
		particleBest = particles[PSOConfig.RANDOM.nextInt(0, particles.length)];
		gBest = particleBest.getpBest();
	}

	public Point[] run(Point[] maze) {
		init(maze);
		for (int i = 0; i < PSOConfig.MAX_ITERATIONS; i++) {
			for (int j = 0; j < 2; j++) {
				for (Particle particle : particles) {
					particle.update(j, gBest);
					if (particle.isGoal()) {
						particleBest = particle;
						j = 3;
						i = PSOConfig.MAX_ITERATIONS;
						break;
					}
				}
			}
		}
		Map<Point[], Color> poins = new HashMap<>();
		for (Particle p : particles) {
			poins.put(p.getPath().toArray(new Point[0]), Color.blue);
			if (p == particleBest)
				poins.put(particleBest.getPath().toArray(new Point[0]), Color.red);
		}
		MapView.roads = poins;
		return particleBest.getPath().toArray(new Point[0]);
	}

	public void updateGBest(Vector p, Particle current) {
		this.gBest = p;
		this.particleBest = current;
	}

}
