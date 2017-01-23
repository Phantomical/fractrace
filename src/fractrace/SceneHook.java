package fractrace;

public interface SceneHook {
	// Allows the hook to use the scene to finish
	// setting its state after deserialization is
	// finished. This also allows for circular references
	public void utilizeScene(Scene sc);
}
