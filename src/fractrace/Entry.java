package fractrace;

import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Entry {
	public static Scene scene;
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("usage: fractrace <scene-desc.json>");
			System.exit(1);
		}
		
		Scene scene = null;
		
		try {
			// Build our scene from the configuration file
			String config = readFile(args[0]);
			SceneBuilder builder = new SceneBuilder(config);
			scene = builder.create();
		} catch (IOException e1) {
			System.err.println("Unable to find given configuration file!");
			System.exit(1);
		}
		
		assert(scene != null);
		
		// Trace the scene
		Vector[][] pixels = TraceDriver.traceScene(scene);
		// Convert the scene to an image
		BufferedImage image = makeImage(pixels);
		
		try {
			// Write our scene as a png image
			File outputFile = new File(scene.targetFile);
			ImageIO.write(image, "png", outputFile);
		} catch (IOException e) {
			System.out.println("Unable to write to file!");
		}
	}

	public static BufferedImage makeImage(Vector[][] pixels) {
		BufferedImage image = null;
		
		if (pixels.length != 0)
		{
			// Create a new buffered image with the same size as the pixel buffer
			image = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
			
			// Go through all the pixels of the image and set the corresponding pixel of the BufferedImage
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
