package fractrace;

import static fractrace.Vector.*;

import fractrace.passes.*;

public final class Postprocess {

	public static void doPhongLighting(TraceResult[][][] results, Vector unnorm_lightdir) {
		doPhongLighting(results, unnorm_lightdir, new Vector(1, 1, 1));
	}
	public static void doPhongLighting(TraceResult[][][] results, Vector unnorm_lightdir, Vector colour) {
		PhongLightingPass pass = new PhongLightingPass(unnorm_lightdir, colour);
		
		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				for (int sample = 0; sample < results[y][x].length; ++sample) {
					pass.execute(results[y][x][sample]);
				}
			}
		}
	}

	public static void doHardShadowPass(TraceResult[][][] results, Vector unnorm_lightdir, Scene sc) {
		HardShadowPass pass = new HardShadowPass(unnorm_lightdir, 0.001, sc);
		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				for (int sample = 0; sample < results[y][x].length; ++sample) {
					pass.execute(results[y][x][sample]);
				}
			}
		}
	}
	
	public static void doAmbientPass(TraceResult[][][] results, Vector bg) {
		AmbientLightingPass pass = new AmbientLightingPass(bg);
		
		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				for (int sample = 0; sample < results[y][x].length; ++sample) {
					pass.execute(results[y][x][sample]);
				}
			}
		}
	}
	
	public static Vector realizeKernel(TraceResult[] results, Vector background) {
		int sample = 0;
		Vector total = new Vector(0, 0, 0);
		for (; sample < results.length; ++sample) {
			if (results[sample].isFinite())
				total = add(total, mul(results[sample].object.colourAtPoint(
						results[sample].endpoint()), results[sample].mult));
			else
				total = add(total, background);
		}
		return div(total, sample);
	}

	public static Vector[][] realize(TraceResult[][][] results, Vector background) {
		Vector[][] image = null;
		if (results.length != 0)
			image = new Vector[results.length][results[0].length];
		else
			image = new Vector[0][0];


		for (int y = 0; y < results.length; ++y) {
			for (int x = 0; x < results[y].length; ++x) {
				image[y][x] = realizeKernel(results[y][x], background);
			}
		}

		return image;
	}
}
