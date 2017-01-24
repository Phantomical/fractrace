package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;

public class Union implements Traceable {
	private Traceable object1;
	private Traceable object2;
	private Vector colour;
	
	@Override
	public double distanceFromPoint(Vector a) {
		return Math.min(
				object1.distanceFromPoint(a), 
				object2.distanceFromPoint(a));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return colour;
	}

}
