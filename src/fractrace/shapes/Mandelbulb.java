package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;
import static fractrace.Vector.*;
import static java.lang.Math.*;

public class Mandelbulb implements Traceable {
	private Vector position;
	private Vector colour;
	private double power;
	private int iterations;
	
	private static final double BAILOUT = 1000;
	
	@Override
	public double distanceFromPoint(Vector a) {
		a = sub(a, position);
		Vector z = a;
		double dr = 1.0;
		double r = z.magnitude();
		
		for (int i = 0; i < iterations && r < BAILOUT; 
				++i, r = z.magnitude()) {
			if (r < BAILOUT) {
				// Convert to polar coordinates
				double theta = acos(z.z / r);
				double phi = atan2(z.y, z.x);
				dr = pow(r, power - 1.0) * power*dr + 1.0;
				
				// Scale and rotate the point
				double zr = pow(r, power);
				theta *= power;
				phi *= power;
				
				// Convert back to Cartesian coordinates
				z = mul(zr, new Vector(
						sin(theta) * cos(phi), 
						sin(phi) * sin(theta),
						cos(theta)));
				z = add(z, a);
			}
		}
		return 0.5 * log(r) * r / dr;
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return colour;
	}
	
	public Mandelbulb() {
		position = new Vector(0, 0, 0);
		colour = new Vector(1, 1, 1);
		power = 8;
		iterations = 32;
	} 
	public Mandelbulb(
			Vector position,
			Vector colour,
			double power,
			int iterations) {
		this.position = position;
		this.colour = colour;
		this.power = power;
		this.iterations = iterations;
	}

}
