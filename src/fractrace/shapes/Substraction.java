package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;

public class Substraction implements Traceable {
	private Traceable object1;
	private Traceable object2;
	
	@Override
	public double distanceFromPoint(Vector a) {
		// Substraction formula is max(-d1, d2)
		return Math.max(
				-object1.distanceFromPoint(a),
				object2.distanceFromPoint(a));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		// Obj2 is the actual object we'll see so
		// just use its colour
		return object2.colourAtPoint(a);
	}

}
