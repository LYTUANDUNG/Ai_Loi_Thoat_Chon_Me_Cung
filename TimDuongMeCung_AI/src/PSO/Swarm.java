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

	private void init(Point[] maze) {
		particles = new Particle[PSOConfig.PARTICLE_COUNT];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(maze, 2);
			particles[i].updateGBest(particles[i].getpBest());
		}
		particleBest = new Particle(maze, 0);
		particleBest.setpBest(particles[0].getpBest());
	}

	public Point[] run(Point[] maze) throws IOException {
		init(maze);
		run: for (int i = 0; i < PSOConfig.MAX_ITERATIONS; i++) {
			for (int j = 0; j < 2; j++) {
				for (Particle particle : particles) {
					particle.update(j);
					if (particle.isGoal()) {
						particleBest = particle;
						break run;
					}
				}
				boolean isChange = false;
				for (Particle particle : particles) {
					if (particle.getpBest().getValue() < particleBest.getpBest().getValue()) {
						particleBest.setpBest(particle.getpBest());
						particleBest.setPath(particle.path());
						isChange = true;
						if (particleBest.isGoal()) {
							break run;
						}
					}
				}
				if (isChange) {
					updateGBest();
				}
			}
		}
		Map<Point[], Color> poins = new HashMap<>();
		for (Particle p : particles) {
			poins.put(p.getPath().toArray(new Point[0]), Color.blue);
		}
		poins.put(particleBest.getPath().toArray(new Point[0]), Color.red);
		MapView.roads = poins;
		return particleBest.getPath().toArray(new Point[0]);
	}

	private void updateGBest() {
		for (Particle particle : particles) {
			particle.updateGBest(particleBest.getpBest());
		}
	}

}
