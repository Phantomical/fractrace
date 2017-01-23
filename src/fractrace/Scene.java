package fractrace;

public final class Scene {
	public String targetFile;
	public Camera camera;
	public Traceable[] objects;
	public int width;
	public int height;
	public Vector background;
	public double maxDistance;
	public double threshold;
	public int samples;
	public ImagePass[] passes;
	
	public Scene() {
		width = 100;
		height = 100;
		background = new Vector(0, 0, 0);
		maxDistance = 1e15;
		threshold = 1e-15;
		samples = 8;
		passes = new ImagePass[0];
	}
	public Scene(
			Camera camera,
			Traceable[] objects,
			int width,
			int height,
			Vector background,
			double maxDistance,
			double threshold,
			int samples) {
		this.camera = camera;
		this.objects = objects;
		this.width = width;
		this.height = height;
		this.background = background;
		this.maxDistance = maxDistance;
		this.threshold = threshold;
		this.samples = samples;
		this.passes = new ImagePass[0];
	}
}
