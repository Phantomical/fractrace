package fractrace;

import java.lang.reflect.Type;
import com.google.gson.*;
import io.gsonfire.*;
import fractrace.shapes.*;
import fractrace.passes.*;
import java.util.ArrayList;

public class SceneBuilder {
	private static ArrayList<SceneHook> hooks = null;
	private static Gson gson;
	private Scene scene;

	public SceneBuilder(String config) {
		hooks = new ArrayList<SceneHook>();
		try {
			scene = gson.fromJson(config, Scene.class);
			
			// Allow all elements that need access to the scene
			// to do their thing
			for (SceneHook hook : hooks) {
				hook.utilizeScene(scene);
			}
		} catch (JsonParseException e) {
			// Something messed up
			// print the message and delete scene
			scene = null;
			System.err.print(e.getMessage());
		} finally {
			// Clear out all the hooks
			hooks = null;			
		}
	}

	static {
		// Register all deserialize hooks
		GsonBuilder builder = new GsonFireBuilder()
				.enableHooks(Camera.class)
				.enableHooks(Modulus.class)
				.enableHooks(HardShadowPass.class)
				.enableHooks(PhongLightingPass.class)
				.enableHooks(AmbientOcclusionPass.class)
				.createGsonBuilder();		
		
		// Register all custom deserializers
		gson = builder
			.registerTypeAdapter(Vector.class, new VectorDeserializer())
			.registerTypeAdapter(Traceable.class, new TraceableDeserializer())
			.registerTypeAdapter(ImagePass.class, new ImagePassDeserializer())
			.create();
	}

	Scene create() {
		return scene;
	}
	
	public static void registerSceneHook(SceneHook hook) {
		// Add the hooks to be calle later
		hooks.add(hook);
	}

	private static class VectorDeserializer implements JsonDeserializer<Vector> {
		@Override
		public Vector deserialize(JsonElement elem, Type type, JsonDeserializationContext arg2)
				throws JsonParseException {
			// Vectors are stored as a JSON array
			// with a length of 3 containing only
			// numbers.
			JsonArray array = elem.getAsJsonArray();
			if (array.size() != 3)
				throw new JsonParseException("Vector was not of length 3");
			return new Vector(
					array.get(0).getAsDouble(),
					array.get(1).getAsDouble(),
					array.get(2).getAsDouble());
		}		
	}
	private static class TraceableDeserializer implements JsonDeserializer<Traceable> {
		@Override
		public Traceable deserialize(JsonElement elem, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {
			// Get the type of the object
			JsonObject obj = elem.getAsJsonObject();
			JsonElement type = obj.get("type");
			if (type == null)
				throw new JsonParseException("Shape declaration did not contain a type field.");
			String typename = type.getAsString();

			Traceable result = null;
			// Deserialize as the correct type
			switch (typename) {
			case "sphere":
				result = gson.fromJson(elem, Sphere.class);
				break;
			case "plane":
				result = gson.fromJson(elem, Plane.class);
				break;
			case "modulus":
				result = gson.fromJson(elem, Modulus.class);
				break;
			case "sierpinski":
				result = gson.fromJson(elem, Sierpinski.class);
				break;
			case "mandelbulb":
				result = gson.fromJson(elem, Mandelbulb.class);
				break;
			case "scale":
				result = gson.fromJson(elem, Scale.class);
				break;
			case "union":
				result = gson.fromJson(elem, Union.class);
				break;
			case "substraction":
				result = gson.fromJson(elem, Substraction.class);
				break;
			default:
				throw new JsonParseException("Unknown shape type: " + typename);
			}

			return result;
		}

	}
	private static class ImagePassDeserializer implements JsonDeserializer<ImagePass> {
		@Override
		public ImagePass deserialize(JsonElement elem, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {
			// Get the pass type
			JsonObject object = elem.getAsJsonObject();
			JsonElement name = object.get("pass");
			if (name == null)
				throw new JsonParseException("Pass description did not contain a pass field.");
			String typename = name.getAsString();
			
			ImagePass result = null;
			// Get the correct pass type
			// and then parse that type
			switch (typename) {
			case "ambientLighting":
				result = gson.fromJson(elem, AmbientLightingPass.class);
				break;
			case "hardShadow":
				result = gson.fromJson(elem, HardShadowPass.class);
				break;
			case "phongLighting":
				result = gson.fromJson(elem, PhongLightingPass.class);
				break;
			case "ambientOcclusion":
				result = gson.fromJson(elem, AmbientOcclusionPass.class);
				break;
			default:
				throw new JsonParseException("Unknown pass: " + typename);
			}			
			return result;
		}
		
	}
}
