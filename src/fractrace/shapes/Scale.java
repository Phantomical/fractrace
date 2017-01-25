package fractrace.shapes;

import fractrace.*;
import static fractrace.Vector.*;

public class Scale implements Traceable {
	private Traceable subobject;
	private double scale = 1;
	
	@Override
	public double distanceFromPoint(Vector a) {
		// To scale up a shape, divide the input
		// coordinates by the scale and then 
		// multiply the resulting distance by the scale
		Vector np = div(a, scale);
		return subobject.distanceFromPoint(np) * scale;
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		// Get colour with the input point scaled
		return subobject.colourAtPoint(div(a, scale));
	}

}
