package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;

public class Union implements Traceable {
	private Traceable object1;
	private Traceable object2;
	private Vector colour = new Vector(1, 1, 1);
	
	@Override
	public double distanceFromPoint(Vector a) {
		// The union of two distance fields is min(d1, d2)
		return Math.min(
				object1.distanceFromPoint(a), 
				object2.distanceFromPoint(a));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return colour;
	}

}
