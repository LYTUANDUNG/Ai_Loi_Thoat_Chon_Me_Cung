package models;

import java.io.IOException;

import PSO.Swarm;

public class PSO implements IAlogrithm {
	@Override
	public Point[] execute(Point[] points) {
		Point[] result=null;
		try {
			result = new Swarm().run(points);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("--------------------------------------");
//		for (Point point : result) {
//			System.out.println(point);
//		}
	    return result;
	}

}
