package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;
import static fractrace.Vector.*;

public class SierpinskiOctahedral implements Traceable {
	private int iterations;
	private Vector position;
	private double scale;
	private Vector colour;
	private static final double bailout = 1000;
	
	public SierpinskiOctahedral() {}
	public SierpinskiOctahedral(
			int iterations, 
			Vector position, 
			double scale, 
			Vector colour) {
		this.iterations = iterations;
		this.position = position;
		this.scale = scale;
		this.colour = colour;
	}
	
	@Override
	public double distanceFromPoint(Vector a) {
		Vector tmp = sub(a, position);
		double x = tmp.x, y = tmp.y, z = tmp.z;
		double r = x * x + y * y + z * z;
		int n = 0;
		for (; n < iterations && r < bailout; ++n) {
			// Do the necessary folds 
			x = Math.abs(x); y = Math.abs(y); z = Math.abs(z);
			if (x + y < 0) { double x1 = -y; y = -x; x = x1; }
			if (x + z < 0) { double x1 = -z; z = -x; x = x1; }
			if (y + z < 0) { double y1 = -z; z = -y; y = y1; }
			
			// Scale down for the subtriangles
			x = scale * x - (scale - 1);
			y = scale * y - (scale - 1);
			z = scale * z - (scale - 1);
			
			r = x * x + y * y + z * z;
		}
		
		// Scale the distance back up
		return (Math.sqrt(r) - 2) * Math.pow(scale, -n);
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return colour;
	}

}
