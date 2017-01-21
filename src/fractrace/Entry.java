package fractrace;

import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import fractrace.shapes.*;

public class Entry {
	private static final double DEG2RAD = Math.PI / 180;
	
	public static Scene scene = new Scene(
			new Camera(
					new Vector(0, 2, 0),
					new Vector(0, -0.2, -1),
					new Vector(0, 1, 0),
					80 * DEG2RAD,
					60 * DEG2RAD),
			new Traceable[] {
					//new Sierpinski(2, new Vector(0, 0, 0), 2, new Vector(1, 0, 0)),				
					new Modulus(new Sphere(new Vector(0, 0, 0), new Vector(1, 0, 0), 1), new Vector(3, 1000005, 3)),
					//new Mandelbulb(new Vector(0, 0, 0), new Vector(1, 0, 0), 8, 20),
					new Plane(new Vector(0, -2, 0), new Vector(0, 1, 0), new Vector(0.8, 0.8, 0.8))
			},
			1000,
			750,
			new Vector(1, 0, 1),
			10000,
			1e-8,
			1
			);
	
	
	public static void main(String[] args) {
		try {
			String config = readFile(args[0]);
			SceneBuilder builder = new SceneBuilder(config);
			scene = builder.create();
		} catch (IOException e1) {
			System.err.println("Unable to find given configuration file!");
			System.exit(1);
		}
		
		final Vector lightdir = new Vector(0, -1, -0.2);
				
		TraceResult[][][] results = TraceDriver.traceScene(scene);
		
		Postprocess.doHardShadowPass(results, lightdir, scene);
		Postprocess.doPhongLighting(results, lightdir);
		Postprocess.doAmbientPass(results, new Vector(0.5, 0.5, 0.5));
		
		Vector[][] pixels = Postprocess.realize(results, scene.background);
		BufferedImage image = makeImage(pixels);
		
		try {
			File outputFile = new File("D:\\output.png");
			ImageIO.write(image, "png", outputFile);
		} catch (IOException e) {
			System.out.println("Unable to write to file!");
		}
	}

	public static BufferedImage makeImage(Vector[][] pixels) {
		BufferedImage image = null;
		
		if (pixels.length != 0)
		{
			image = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
			
			for (int y = 0; y < pixels.length; ++y) {
				for (int x = 0; x < pixels[y].length; ++x) {
					int rgb = pixels[y][x].toRGB();
					image.setRGB(x, y, rgb);
				}
			}
		}
		
		return image;
	}
	
	public static String readFile(String filename) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filename)));
	}
}
