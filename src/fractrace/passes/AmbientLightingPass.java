package fractrace.passes;

import static fractrace.Vector.max;

import fractrace.ImagePass;
import fractrace.TraceResult;
import fractrace.Vector;

public class AmbientLightingPass implements ImagePass {
	private Vector colour;
	
	@Override
	public void execute(TraceResult target) {
		// All the ambient lighting does is make the light source
		// the same as the ambient light if the intensity is below
		// a certain level.
		if (target.isFinite()) {
			target.mult = max(target.mult, colour);
		}
	}
	
	public AmbientLightingPass() {
		colour = new Vector(0.1, 0.1, 0.1);
	}
	
	public AmbientLightingPass(Vector colour) {
		this.colour = colour;
	}
}
