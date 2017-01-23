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
			scene = null;
			System.err.print(e.getMessage());
		} finally {
			// Clear out all the hooks
			hooks = null;			
		}
	}

	static {
		GsonBuilder builder = new GsonFireBuilder()
				.enableHooks(Camera.class)
				.enableHooks(Modulus.class)
				.enableHooks(HardShadowPass.class)
				.enableHooks(PhongLightingPass.class)
				.enableHooks(AmbientOcclusionPass.class)
				.createGsonBuilder();		
		
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
		hooks.add(hook);
	}

	private static class VectorDeserializer implements JsonDeserializer<Vector> {
		@Override
		public Vector deserialize(JsonElement elem, Type type, JsonDeserializationContext arg2)
				throws JsonParseException {
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
			JsonObject obj = elem.getAsJsonObject();
			JsonElement type = obj.get("type");
			if (type == null)
				throw new JsonParseException("Shape declaration did not contain a type field.");
			String typename = type.getAsString();

			switch (typename) {
			case "sphere":
				return gson.fromJson(elem, Sphere.class);
			case "plane":
				return gson.fromJson(elem, Plane.class);
			case "modulus":
				return gson.fromJson(elem, Modulus.class);
			case "sierpinski":
				return gson.fromJson(elem, Sierpinski.class);
			case "mandelbulb":
				return gson.fromJson(elem, Mandelbulb.class);
			case "scale":
				return gson.fromJson(elem, Scale.class);
			default:
				throw new JsonParseException("Unknown shape type: " + typename);
			}

		}

	}
	private static class ImagePassDeserializer implements JsonDeserializer<ImagePass> {
		@Override
		public ImagePass deserialize(JsonElement elem, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {
			JsonObject object = elem.getAsJsonObject();
			JsonElement name = object.get("pass");
			if (name == null)
				throw new JsonParseException("Pass did not contain a pass field.");
			String typename = name.getAsString();
			switch (typename) {
			case "ambientLighting":
				return gson.fromJson(elem, AmbientLightingPass.class);
			case "hardShadow":
				return gson.fromJson(elem, HardShadowPass.class);
			case "phongLighting":
				return gson.fromJson(elem, PhongLightingPass.class);
			case "ambientOcclusion":
				return gson.fromJson(elem, AmbientOcclusionPass.class);
			default:
				throw new JsonParseException("Unknown pass: " + typename);
			}
		}
		
	}
}
