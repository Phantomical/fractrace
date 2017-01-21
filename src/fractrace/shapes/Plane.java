package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;
import static fractrace.Vector.*;

public class Plane implements Traceable {
	private Vector position;
	private Vector normal;
	private Vector colour;
	
	@Override
	public double distanceFromPoint(Vector a) {
		Vector w = sub(position, a).negate();
		return Math.abs(dot(normal, w)) / normal.magnitude();
	}
	
	@Override
	public Vector normalAtPoint(Vector a) {
		return normal;
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return colour;
	}

	public Plane() {
		position = new Vector(0, 0, 0);
		colour = new Vector(0, 0, 0);
		normal = new Vector(0, 1, 0);
	}
	public Plane(
			Vector position,
			Vector normal,
			Vector colour) {
		this.position = position;
		this.normal = normal;
		this.colour = colour;
	}
}
