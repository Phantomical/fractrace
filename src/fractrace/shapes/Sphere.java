package fractrace.shapes;

import static fractrace.Vector.*;
import fractrace.Traceable;
import fractrace.Vector;

public class Sphere implements Traceable {
	private Vector position;
	private Vector colour;
    private double radius;
	
	@Override
	public double distanceFromPoint(Vector a) {
		return sub(position, a).magnitude() - radius;
	}

	@Override
	public Vector normalAtPoint(Vector a) {
		return normalize(sub(a, position));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return colour;
	}

	public Sphere(Vector position, Vector colour, double radius) {
		this.position = position;
		this.colour = colour;
		this.radius = radius;
	}
}
