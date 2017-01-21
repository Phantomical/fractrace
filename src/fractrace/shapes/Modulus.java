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
		return subobject.distanceFromPoint(add(mod(sub(a, shift), modulus), shift));
	}
	
	@Override
	public Vector normalAtPoint(Vector a) {
		return subobject.normalAtPoint(add(mod(sub(a, shift), modulus), shift));
	}

	@Override
	public Vector colourAtPoint(Vector a) {
		return subobject.colourAtPoint(add(mod(sub(a, shift), modulus), shift));
	}

	@PostDeserialize
	private void deserializeHook() {
		shift = div(modulus, 2);
	}
	
	public Modulus(Traceable subobj, Vector mod) {
		this.subobject = subobj;
		this.modulus = mod;
		shift = div(mod, 2);
	}
}
