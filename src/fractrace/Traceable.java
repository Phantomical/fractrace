package fractrace;

import static fractrace.Vector.add;
import static fractrace.Vector.sub;

public interface Traceable {
	public double distanceFromPoint(Vector a);
	public default Vector normalAtPoint(Vector a) {
		final double threshold = 0.0000001;
		double vxp = distanceFromPoint(add(a, new Vector(threshold, 0, 0)));
		double vxm = distanceFromPoint(sub(a, new Vector(threshold, 0, 0)));
		double vyp = distanceFromPoint(add(a, new Vector(0, threshold, 0)));
		double vym = distanceFromPoint(sub(a, new Vector(0, threshold, 0)));
		double vzp = distanceFromPoint(add(a, new Vector(0, 0, threshold)));
		double vzm = distanceFromPoint(sub(a, new Vector(0, 0, threshold)));

		return new Vector(vxp - vxm, vyp - vym, vzp - vzm);
	}
	public Vector colourAtPoint(Vector a);
	public default Boolean canIntersect(Ray a) {
		return true;
	}
}

