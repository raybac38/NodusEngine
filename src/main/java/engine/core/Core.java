package engine.core;

import engine.core.window.Window;

public class Core {
	private final Window window;
	private final SceneManager sceneManager;

	public Core(SceneManager sceneManager, Window window) {
		this.window = window;
		this.sceneManager = sceneManager;
	}
	
}
