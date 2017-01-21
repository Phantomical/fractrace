package fractrace.passes;

import static fractrace.Vector.max;

import fractrace.ImagePass;
import fractrace.TraceResult;
import fractrace.Vector;

public class AmbientLightingPass implements ImagePass {
	private Vector colour;
	
	@Override
	public void execute(TraceResult target) {
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
