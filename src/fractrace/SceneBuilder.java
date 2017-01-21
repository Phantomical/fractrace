package fractrace;

import java.lang.reflect.Type;
import com.google.gson.*;
import io.gsonfire.*;
import fractrace.shapes.*;

public class SceneBuilder {
	private static Gson gson;
	private Scene scene;

	public SceneBuilder(String config) {	
		try {
			scene = gson.fromJson(config, Scene.class);
		} catch (JsonParseException e) {
			scene = null;
		}
	}

	static {
		GsonBuilder builder = new GsonFireBuilder()
				.enableHooks(Camera.class)
				.enableHooks(Modulus.class)
				.createGsonBuilder();		
		
		builder.registerTypeAdapter(Vector.class, new VectorDeserializer());
		builder.registerTypeAdapter(Traceable.class, new TraceableDeserializer());

		gson = builder.create();
	}

	Scene create() {
		return scene;
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
			default:
				throw new JsonParseException("Unknown shape type: " + typename);
			}

		}

	}
}
