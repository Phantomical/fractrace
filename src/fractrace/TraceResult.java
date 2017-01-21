package fractrace;

public final class TraceResult {
    public final Ray ray;
    public final Traceable object;
    public double distance;
    public Vector mult;
    public int itercount;
    
    public TraceResult(Ray ray, Traceable obj, double dist) {
    	this.ray = new Ray(ray);
    	this.object = obj;
    	this.distance = dist;
    	this.mult = new Vector(1, 1, 1);
    	this.itercount = 0;
    }
    public TraceResult(TraceResult result) {
    	ray = result.ray;
    	object = result.object;
    	distance = result.distance;
    	mult = result.mult;
    	itercount = result.itercount;
    }
    
    public Vector endpoint() {
        return ray.getPointAt(distance);
    }
    public Boolean isFinite() {
    	return Double.isFinite(distance);
    }
    
    
}
