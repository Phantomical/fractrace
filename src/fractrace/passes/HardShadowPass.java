package fractrace.passes;

import fractrace.*;
import io.gsonfire.annotations.PostDeserialize;

import static fractrace.Vector.*;

public class HardShadowPass implements ImagePass, SceneHook {
	private transient Vector tracedir;
	private transient Scene scene;
	private Vector lightdir;
	private double threshold;
		
	@Override
	public void execute(TraceResult target) {
		if (target.isFinite()) {
			final Vector point = target.endpoint();
			final Ray shadowRay = new Ray(add(point, mul(tracedir, threshold)), tracedir);
			TraceResult res = TraceDriver.traceRay(scene, shadowRay);
			if (res.isFinite()) {
				target.mult = new Vector(0, 0, 0);
			}
		}
	}

	public HardShadowPass(
			Vector lightdir,
			double threshold,
			Scene scene) {
		this.lightdir = lightdir;
		this.threshold = threshold;
		this.scene = scene;
		this.tracedir = normalize(lightdir).negate();
	}
	public HardShadowPass() {
		lightdir = new Vector(0, 1, 0);
		threshold = 0.001;
	}
	
	@PostDeserialize
	private void deserializeHook() {
		tracedir = normalize(lightdir).negate();
		SceneBuilder.registerSceneHook(this);
	}

	@Override
	public void utilizeScene(Scene sc) {
		scene = sc;		
	}
}
