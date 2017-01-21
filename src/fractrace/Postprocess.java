package fractrace;

import static fractrace.Vector.*;

public final class Postprocess {
	private static double clamp(double v, double max, double min) {
		return Math.min(Math.max(v, min), max);
	}

	public static void doPhongLighting(TraceResult[][][] results, Vector unnorm_lightdir) {
		doPhongLighting(results, unnorm_lightdir, new Vector(1, 1, 1));
	}
	public static void doPhongLighting(TraceResult[][][] results, Vector unnorm_lightdir, Vector color) {
		final Vector lightdir = normalize(unnorm_lightdir).negate();
		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				for (int sample = 0; sample < results[y][x].length; ++sample) {
					if (results[y][x][sample].isFinite()) {
						final Vector normal = normalize(results[y][x][sample]
								.object.normalAtPoint(results[y][x][sample].endpoint()));
						final double mult = clamp(dot(normal, lightdir), 1, 0);
						results[y][x][sample].mult = mul(mul(results[y][x][sample].mult, mult), color);
					}
				}
			}
		}
	}

	public static void doHardShadowPass(TraceResult[][][] results, Vector unnorm_lightdir, Scene sc) {
		final Vector tracedir = normalize(unnorm_lightdir).negate();
		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				for (int sample = 0; sample < results[y][x].length; ++sample) {
					if (results[y][x][sample].isFinite()) {
						final Vector point = results[y][x][sample].endpoint();
						final Ray shadowRay = new Ray(add(point, mul(tracedir, 0.001)), tracedir);
						TraceResult res = TraceDriver.traceRay(sc, shadowRay);
						if (res.isFinite()) {
							results[y][x][sample].mult = new Vector(0, 0, 0);
						}
					}
				}
			}
		}
	}
	
	public static void doAmbientPass(TraceResult[][][] results, Vector bg) {
		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				for (int sample = 0; sample < results[y][x].length; ++sample) {
					if (results[y][x][sample].isFinite()) {
						results[y][x][sample].mult = 
								max(results[y][x][sample].mult, bg);
					}
				}
			}
		}
	}

	public static Vector[][] realize(TraceResult[][][] results, Vector background) {
		Vector[][] image = null;
		if (results.length != 0)
			image = new Vector[results.length][results[0].length];
		else
			image = new Vector[0][0];


		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				int sample = 0;
				Vector total = new Vector(0, 0, 0);
				for (; sample < results[y][x].length; ++sample) {
					if (results[y][x][sample].isFinite())
						total = add(total, mul(results[y][x][sample].object.colourAtPoint(
								results[y][x][sample].endpoint()), results[y][x][sample].mult));
					else
						total = add(total, background);
				}
				image[y][x] = div(total, sample);
			}
		}

		return image;
	}
}
