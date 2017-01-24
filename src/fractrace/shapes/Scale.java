package fractrace.shapes;

import fractrace.*;
import static fractrace.Vector.*;

public class Scale implements Traceable {
	private Traceable subobject;
	private double scale = 1;
	
	@Override
	public double distanceFromPoint(Vector a) {
		Vector np = div(a, scale);
		return subobject.distanceFromPoint(np) * scale;
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return subobject.colourAtPoint(div(a, scale));
	}

}
