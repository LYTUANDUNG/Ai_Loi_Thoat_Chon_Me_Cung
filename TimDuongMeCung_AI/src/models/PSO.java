package models;

import PSO.Swarm;

public class PSO implements IAlogrithm {
	@Override
	public Point[] execute(Point[] points) {
		Point[] result=null;
			result = new Swarm().run(points);
	    return result;
	}

}
