package fractrace;

import io.gsonfire.annotations.PostDeserialize;

public final class Camera {
	private static final double DEG2RAD = Math.PI / 180;
	
    public Vector position;
    public Vector forward;
    public Vector up;
    public double fovx;
    public double fovy;

    public double aspect() {
        return fovy / fovx;
    }

    public Camera(
        Vector position,
        Vector forward,
        Vector up,
        double fovx,
        double fovy
    ) {
        this.position = position;
        this.forward = Vector.normalize(forward);
        this.up = up;
        this.fovx = fovx;
        this.fovy = fovy;    
    }
    
	@PostDeserialize
	private void deserializeHook() {
		fovx *= DEG2RAD;
		fovy *= DEG2RAD;
	}
}
