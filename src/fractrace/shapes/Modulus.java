package fractrace.shapes;

import fractrace.Traceable;
import fractrace.Vector;
import io.gsonfire.annotations.PostDeserialize;

import static fractrace.Vector.*;

public class Modulus implements Traceable {
	private Traceable subobject;
	private Vector modulus;
	private Vector shift;

	@Override
	public double distanceFromPoint(Vector a) {
		// We set the object in the middle so it appears to have sane behaviour
		// If this wasn't done the object would get cut in half if it was at the origin
		return subobject.distanceFromPoint(add(mod(sub(a, shift), modulus), shift));
	}
	
	@Override
	public Vector normalAtPoint(Vector a) {
		// Same thing as the distance, transform to local space then
		// invoke the subobject's function
		return subobject.normalAtPoint(add(mod(sub(a, shift), modulus), shift));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		// Same thing as the distance, transform to local space then
		// invoke the subobject's function
		return subobject.colourAtPoint(add(mod(sub(a, shift), modulus), shift));
	}

	@PostDeserialize
	private void deserializeHook() {
		// Initialize shift so that the origin is in the middle
		// of the repeated area
		shift = div(modulus, 2);
	}
	
	public Modulus(Traceable subobj, Vector mod) {
		this.subobject = subobj;
		this.modulus = mod;
		shift = div(mod, 2);
	}
}
