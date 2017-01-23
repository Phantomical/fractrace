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
			String config = readFile(args[0]);
			SceneBuilder builder = new SceneBuilder(config);
			scene = builder.create();
		} catch (IOException e1) {
			System.err.println("Unable to find given configuration file!");
			System.exit(1);
		}
		
		assert(scene != null);
		
		Vector[][] pixels = TraceDriver.traceScene(scene);
		BufferedImage image = makeImage(pixels);
		
		try {
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
