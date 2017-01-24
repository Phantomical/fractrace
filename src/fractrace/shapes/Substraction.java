package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;

public class Substraction implements Traceable {
	private Traceable object1;
	private Traceable object2;
	
	@Override
	public double distanceFromPoint(Vector a) {
		return Math.max(
				-object1.distanceFromPoint(a),
				object2.distanceFromPoint(a));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return object1.colourAtPoint(a);
	}

}
