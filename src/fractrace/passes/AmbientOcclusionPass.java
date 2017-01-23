package fractrace.passes;

import fractrace.*;
import io.gsonfire.annotations.PostDeserialize;

import static fractrace.Vector.*;

public class AmbientOcclusionPass implements SceneHook, ImagePass {
	transient Scene scene;
	double offset = 0.05;
	double mult = 1.0;

	@Override
	public void execute(TraceResult target) {
		if (target.isFinite()) {
			Vector normal = target.object.normalAtPoint(target.endpoint());
			Vector point = add(target.endpoint(), mul(normal, offset));

			double d1 = offset + scene.threshold;
			Tuple<Double, Traceable> dist = TraceDriver.calcStepDistance(scene, point);
			double d2 = dist.x;
			
			if (d2 < 0)
				d2 = 0.0;
						
			target.mult = sub(target.mult, Math.max((1.0 - d2 / d1) * mult, 0));
		}
	}

	@Override
	public void utilizeScene(Scene sc) {
		scene = sc;
	}

	@PostDeserialize
	private void deserializeHook() {
		SceneBuilder.registerSceneHook(this);
	}

}
