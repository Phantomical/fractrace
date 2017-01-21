package fractrace;

import static fractrace.Vector.*;

public final class Ray implements Cloneable {
    public Vector start;
    public Vector direction;

    public Ray(Vector st, Vector dir) {
        start = st;
        direction = dir;
    }
    public Ray(Ray ray) {
    	this(ray.start, ray.direction);
    }
    public Ray(Camera cam, double x, double y) {
        final double halfx = Math.tan(cam.fovx * 0.5) * x;
        final double halfy = Math.tan(cam.fovy * 0.5) * y;
        Vector right = cross(cam.up, cam.forward);
        Vector up = cross(mul(right, -1), cam.forward);

        start = cam.position;
        direction = normalize(rotate(rotate(cam.forward, up, halfx), right, halfy));
    }

    public Vector getPointAt(double distance) {
        return add(start, mul(direction, distance));
    }
}

