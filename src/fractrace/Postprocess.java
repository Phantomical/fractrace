package fractrace;

import static fractrace.Vector.*;

final class Postprocess {
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
}
