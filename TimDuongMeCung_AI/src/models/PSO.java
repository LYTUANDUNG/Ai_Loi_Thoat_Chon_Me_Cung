package models;

import PSO.Swarm;

public class PSO implements IAlogrithm {
	@Override
	public Point[] execute(Point[] points) {
		Point[] result =new Swarm().run(points);
//		System.out.println("--------------------------------------");
//		for (Point point : result) {
//			System.out.println(point);
//		}
	    return result;
	}

}
