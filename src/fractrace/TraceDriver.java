package fractrace;

import static fractrace.Vector.*;
import java.util.ArrayList;

public final class TraceDriver {
	private static Tuple<Double, Traceable> calcMin(Traceable[] possible, Vector point) {
		Tuple<Double, Traceable> min = new Tuple<Double, Traceable>(Double.POSITIVE_INFINITY, null);
		for (int i = 0; i < possible.length; ++i) {
			double dist = possible[i].distanceFromPoint(point);

			if (dist < min.x)
				min = new Tuple<Double, Traceable>(dist, possible[i]);
		}

		return min;
	}
	private static Tuple<Double, Traceable> calcTraceStep(TraceData dat) {
		Vector point = add(dat.ray.start, mul(dat.ray.direction, dat.currentDist));
		return calcMin(dat.possible, point);
	}
	private static Traceable[] filter(Traceable[] options, Ray ray)	{
		ArrayList<Traceable> possible = new ArrayList<Traceable>();

		for (int i = 0; i < options.length; ++i) {
			if (options[i].canIntersect(ray))
				possible.add(options[i]);
		}

		return possible.toArray(new Traceable[0]);
	}

	private static TraceResult traceImpl(TraceData dat) {
		TraceResult result = null;

		int iterations = 0;
		while (result == null)
		{
			Tuple<Double, Traceable> step = calcTraceStep(dat);
			double newdist = dat.currentDist + step.x;
			if (newdist > dat.maxdist || dat.possible.length == 0) {
				result = new TraceResult(dat.ray, null, Double.POSITIVE_INFINITY);
			}
			else if (step.x < dat.threshold) {
				result = new TraceResult(dat.ray, step.y, newdist);
			}
			else {
				dat.currentDist += step.x;
				iterations++;
			}
		}
		result.itercount = iterations;

		return result;
	}

	public static TraceResult traceRay(Scene sc, Ray ray) {
		Traceable[] possible = filter(sc.objects, ray);
		TraceData dat = new TraceData(sc.threshold, 0, sc.maxDistance, ray, possible);

		return traceImpl(dat);
	}

	public static TraceResult[][][] traceScene(Scene sc) {
		TraceResult[][][] results = new TraceResult[sc.height][sc.width][sc.samples];

		final double xmult = 2.0 / ((double)sc.height - 1);
		final double ymult = 2.0 / ((double)sc.width - 1);
		for (int y = 0; y < sc.height; ++y) {
			System.out.println(y);
			for (int x = 0; x < sc.width; ++x) {
				for (int sample = 0; sample < sc.samples; ++sample) {
					final double jitterx = Math.random() * xmult * 0;
					final double jittery = Math.random() * ymult * 0;
					final double posy = (((double)y + jitterx) * xmult) - 1;
					final double posx = (((double)x + jittery) * ymult) - 1;
					results[y][x][sample] = traceRay(sc, new Ray(sc.camera, posx, posy));
				}
			}
		}

		return results;
	}
}
